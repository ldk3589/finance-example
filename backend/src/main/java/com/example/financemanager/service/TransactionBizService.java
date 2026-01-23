package com.example.financemanager.service;

import com.example.financemanager.entity.Transaction;

/**
 * 交易业务服务接口
 * 专注于处理跨实体的复杂业务逻辑（如流水记录与余额联动）
 */
public interface TransactionBizService {

    /**
     * 记账（收入 / 支出）
     * 内部逻辑应包含：
     * 1. 校验账户状态
     * 2. 扣减/增加账户余额
     * 3. 记录交易流水
     *
     * @param transaction 交易信息
     * @return 包含生成的 ID 和时间的完整交易对象
     */
    Transaction record(Transaction transaction);

}