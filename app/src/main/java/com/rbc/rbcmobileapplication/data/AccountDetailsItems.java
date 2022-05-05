package com.rbc.rbcmobileapplication.data;

import com.rbc.rbcaccountlibrary.Account;

public class AccountDetailsItems extends AccountItems {

    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int getType() {
        return TYPE_ACCOUNT_DETAILS;
    }

}
