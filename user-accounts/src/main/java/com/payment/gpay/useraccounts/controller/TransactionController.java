package com.payment.gpay.useraccounts.controller;

import com.payment.gpay.common.models.Transaction;
import com.payment.gpay.useraccounts.dto.TransactionDto;
import com.payment.gpay.useraccounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/txn")
public class TransactionController {

    private AccountService accountService;

    @Autowired
    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = this.accountService.sendMoney(transactionDto);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}