package com.payment.gpay.transactions.util;


import com.payment.gpay.common.models.Transaction;

public abstract class TransactionValidation {

    private TransactionValidation() {
    }

    public static boolean isValidTransaction(Transaction transaction) {
        boolean amountValidation = transaction.getSenderAccount().getBalance() >= transaction.getAmount();
        boolean kycValidation =
                transaction.getSenderAccount().isKycComplete() && transaction.getRecieverAccount().isKycComplete();
        return amountValidation && kycValidation;
    }
}