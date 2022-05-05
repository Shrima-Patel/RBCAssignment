package com.rbc.rbcmobileapplication.data;

public abstract class AccountItems {

    public static final int TYPE_OF_ACCOUNT = 0;
    public static final int TYPE_ACCOUNT_DETAILS = 1;

    abstract public int getType();

}