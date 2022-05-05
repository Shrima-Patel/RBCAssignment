package com.rbc.rbcmobileapplication.data.repository

import com.rbc.rbcaccountlibrary.Account
import com.rbc.rbcaccountlibrary.AccountProvider

class BankAccountsRepository {
    fun getAccountsList() : List<Account> = AccountProvider.getAccountsList()
}