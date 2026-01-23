package com.example.financemanager.service.impl;

import com.example.financemanager.entity.Transaction;
import com.example.financemanager.mapper.TransactionMapper;
import com.example.financemanager.service.TransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dk
 * @since 2026-01-22
 */
@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

}
