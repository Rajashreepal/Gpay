package com.payment.gpay.useraccounts.mapper;

import com.payment.gpay.common.models.Account;
import com.payment.gpay.common.models.Transaction;
import com.payment.gpay.useraccounts.dto.TransactionDto;
import com.payment.gpay.useraccounts.repository.AccountRepository;
import com.payment.gpay.useraccounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class TransactionMapper {

    private final AccountRepository accountRepository;

    @Autowired
    public TransactionMapper(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Transaction mapToTransaction(TransactionDto transactionDto) {
        List<Account> accounts =
                this.accountRepository.findAllById(List.of(transactionDto.getSenderAccId(), transactionDto.getReceiverAccId()));
        Account senderAccount = null, receiverAccount = null;
        for (Account account : accounts) {
            if (senderAccount != null && receiverAccount != null) {
                break;
            }
            if (account.getAccountId().equals(transactionDto.getSenderAccId())) {
                senderAccount = account;
            }
            if (account.getAccountId().equals(transactionDto.getReceiverAccId())) {
                receiverAccount = account;
            }
        }
        return Transaction.builder()
                .senderAccount(senderAccount)
                .recieverAccount(receiverAccount)
                .amount(transactionDto.getAmount())
                .createdAt(Instant.now())
                .build();
    }
}