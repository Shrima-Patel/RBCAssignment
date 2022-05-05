package com.rbc.rbcmobileapplication.data;

public abstract class TransactionsItems {

    public static final int TYPE_DATE = 0;
    public static final int TYPE_TRANSACTIONS_DETAILS = 1;

    abstract public int getType();

}
