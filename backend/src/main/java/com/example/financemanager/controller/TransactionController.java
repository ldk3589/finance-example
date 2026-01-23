package com.example.financemanager.controller;

import com.example.financemanager.common.Result;
import com.example.financemanager.entity.Transaction;
import com.example.financemanager.service.TransactionBizService;
import org.springframework.web.bind.annotation.*;

/**
 * 交易管理 前端控制器
 */
@RestController
@RequestMapping("/api/transactions") // 建议增加 api 前缀
public class TransactionController {

    private final TransactionBizService transactionBizService;

    public TransactionController(TransactionBizService transactionBizService) {
        this.transactionBizService = transactionBizService;
    }

    /**
     * 记录一笔新交易
     * 优化点：可以在此处添加简单的逻辑校验
     */
    @PostMapping
    public Result<Transaction> record(@RequestBody Transaction transaction) {
        // 1. 基本校验（也可以通过 Validation 框架自动完成）
        if (transaction.getAmount() == null || transaction.getAccountId() == null) {
            return Result.error(400, "交易金额和账户不能为空");
        }

        // 2. 调用业务层处理（包含扣减余额、记录流水等）
        Transaction result = transactionBizService.record(transaction);

        // 3. 返回处理后的对象（包含生成的 ID 和时间）
        return Result.success(result);
    }
}