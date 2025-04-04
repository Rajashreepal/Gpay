package com.payment.gpay.useraccounts.service;

import com.payment.gpay.common.exceptions.NotFoundException;
import com.payment.gpay.common.models.Transaction;
import com.payment.gpay.common.models.Account;
import com.payment.gpay.common.models.User;
import com.payment.gpay.transactions.service.TransactionService;
import com.payment.gpay.useraccounts.dto.TransactionDto;
import com.payment.gpay.useraccounts.mapper.TransactionMapper;
import com.payment.gpay.useraccounts.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, TransactionService transactionService, TransactionMapper transactionMapper) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    public Account addAccount(User user) {
        Account account = Account.builder()
                .user(user)
                .build();
        account = this.accountRepository.save(account);
        log.info("Account with ID: {} for user with ID: {} was successfully created.", account.getAccountId(),
                user.getUserId());
        return account;
    }

    public Account getAccountById(UUID accountId) {
        Optional<Account> accountOptional = this.accountRepository.findById(accountId);
        return accountOptional.orElseThrow(new NotFoundException(Account.class, "accountId", accountId));
    }

    public Account getAccountByUser(User user) {
        return this.getAccountByUser(user.getUserId());
    }

    public Account getAccountByUser(UUID userId) {
        Optional<Account> accountOptional = this.accountRepository.findByUserId(userId);
        return accountOptional.orElseThrow(() -> new NotFoundException(Account.class, "userId", userId));
    }

    @Transactional
    public Transaction sendMoney(TransactionDto transactionDto) {
        Transaction transaction = this.transactionMapper.mapToTransaction(transactionDto);
        Account senderAccount = transaction.getSenderAccount();
        Account receiverAccount = transaction.getRecieverAccount();
        transaction = this.transactionService.createTransaction(transaction);
        senderAccount.send(receiverAccount, transaction.getAmount());
        this.accountRepository.saveAll(List.of(senderAccount, receiverAccount));
        return transaction;
    }

    public List<Account> getAccountList(List<UUID> accountIdList) {
        return this.accountRepository.findAllById(accountIdList);
    }

}