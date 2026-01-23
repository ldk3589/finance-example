import axios from 'axios'
import { ElMessage } from 'element-plus' 

const request = axios.create({
  baseURL: '/api',
  timeout: 5000
})

// 请求拦截器：携带 Token
request.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers['Authorization'] = token; 
    }
    return config;
}, error => {
    return Promise.reject(error);
});

// 💡 新增响应拦截器：直接剥离 Result 包装
request.interceptors.response.use(
    response => {
        // response.data 才是后端返回的 { "code": 0, "message": "success", "data": {...} }
        const res = response.data;

        // 如果 code 不为 0，说明业务逻辑上有错误（如密码错误、用户已存在）
        if (res.code !== 0) {
            ElMessage.error(res.message || '系统异常');
            return Promise.reject(new Error(res.message || 'Error'));
        }

        return res; 
    },
    error => {
        // 处理 HTTP 状态码错误（如 404, 500）
        console.log('err' + error);
        ElMessage.error(error.message || '网络请求失败');
        return Promise.reject(error);
    }
);

export default request