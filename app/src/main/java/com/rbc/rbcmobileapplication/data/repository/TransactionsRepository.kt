package com.rbc.rbcmobileapplication.data.repository

import com.rbc.rbcaccountlibrary.AccountProvider
import com.rbc.rbcaccountlibrary.AccountType
import com.rbc.rbcaccountlibrary.Transaction
import java.lang.Exception

class TransactionsRepository {

    fun getTransactions(accountNumber: String) : List<Transaction>? {
        return try {
            AccountProvider.getTransactions(accountNumber = accountNumber)
        } catch (e:Exception) {
            null
        }
    }

    fun getCreditCardTransactions(accountNumber: String) : List<Transaction>? {
        val result : List<Transaction>? = try {
            val transaction = AccountProvider.getTransactions(accountNumber = accountNumber)
            val additionalTransaction = AccountProvider.getAdditionalCreditCardTransactions(accountNumber = accountNumber)
            transaction + additionalTransaction
        } catch (e:Exception) {
            null
        }
        return result
    }

    fun getAllTransaction(): List<Transaction>? {
        var result = listOf<Transaction>()
        var transaction: List<Transaction>
        var additionalTransaction: List<Transaction>
        try{
            val accountList = AccountProvider.getAccountsList()
            for(account in accountList){
                transaction = AccountProvider.getTransactions(account.number)
                result = result + transaction
                if(account.type == AccountType.CREDIT_CARD){
                    additionalTransaction = AccountProvider.getAdditionalCreditCardTransactions(account.number)
                    result = result + additionalTransaction
                }
            }
        } catch (e:Exception) {
            return null
        }
        return result
    }

}