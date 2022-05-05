package com.rbc.rbcmobileapplication.data;

import com.rbc.rbcaccountlibrary.Transaction;

public class TransactionsDetailsItems extends TransactionsItems {

    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public int getType() {
        return TYPE_TRANSACTIONS_DETAILS;
    }

}
