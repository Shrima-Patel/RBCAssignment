package com.rbc.rbcmobileapplication.data;

public class AccountTypeItems extends AccountItems {

    private String accountType;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public int getType() {
        return TYPE_OF_ACCOUNT;
    }

}
