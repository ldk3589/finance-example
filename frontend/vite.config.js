import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    port: 5173, // 前端启动端口
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 后端服务器地址
        changeOrigin: true,
        // 如果后端接口本身就带有 /api 前缀，这里不需要 rewrite
        // 如果后端接口不带 /api，请取消下面这一行的注释
        // rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})