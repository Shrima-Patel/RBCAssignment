package com.rbc.rbcmobileapplication.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbc.rbcaccountlibrary.AccountType
import com.rbc.rbcaccountlibrary.Transaction
import com.rbc.rbcmobileapplication.data.TransactionsDateItems
import com.rbc.rbcmobileapplication.data.TransactionsDetailsItems
import com.rbc.rbcmobileapplication.data.TransactionsItems
import com.rbc.rbcmobileapplication.data.repository.TransactionsRepository
import com.rbc.rbcmobileapplication.utils.CalenderToDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TransactionsViewModel : ViewModel() {

    private val _transactions = MutableLiveData<List<TransactionsItems>>()
    val transactions: LiveData<List<TransactionsItems>?>
        get() = _transactions

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean>
        get() = _progressBarVisibility

    private val transactionsRespository = TransactionsRepository()

    fun getTransactionsList(_accountNumber: String?, _accountType: String?) {
        _progressBarVisibility.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val list: List<Transaction>? =
                    if (_accountNumber.isNullOrBlank() || _accountNumber.isNullOrEmpty()) {
                        transactionsRespository.getAllTransaction()
                    } else if (AccountType.CREDIT_CARD == AccountType.valueOf(_accountType.toString())) {
                        transactionsRespository.getCreditCardTransactions(_accountNumber.toString())
                    } else {
                        transactionsRespository.getTransactions(_accountNumber.toString())
                    }
                _transactions.postValue(groupTransactions(list ?: emptyList()))
            }
            _progressBarVisibility.value = false
        }
    }

    private fun groupTransactions(listOfTransaction: List<Transaction>): List<TransactionsItems> {
        val consolidatedList = arrayListOf<TransactionsItems>()
        val groupedHashMap: HashMap<String, List<Transaction>> =
            groupDataIntoHashMap(listOfTransaction)
        val sortedGroupedHashMap =
            groupedHashMap.toSortedMap(compareByDescending { CalenderToDate.getDateForSorting(it) })
        for (date in sortedGroupedHashMap.keys) {
            val dateItem = TransactionsDateItems()
            dateItem.date = date
            consolidatedList.add(dateItem)
            for (transactionDetails in groupedHashMap[date]!!) {
                val generalItem = TransactionsDetailsItems()
                generalItem.transaction = transactionDetails
                consolidatedList.add(generalItem)
            }
        }
        return consolidatedList
    }

    private fun groupDataIntoHashMap(listOfTransaction: List<Transaction>): HashMap<String, List<Transaction>> {
        val groupedHashMap: HashMap<String, List<Transaction>> = HashMap()
        for (transaction in listOfTransaction) {
            val hashMapKey: String = CalenderToDate.getDateFromCalender(transaction.date)
            if (groupedHashMap.containsKey(hashMapKey)) {
                val previousValue: List<Transaction>? = groupedHashMap[hashMapKey]
                val newValue: List<Transaction> = previousValue!! + listOf(transaction)
                groupedHashMap[hashMapKey] = newValue
            } else {
                val list = arrayListOf<Transaction>()
                list.add(transaction)
                groupedHashMap.put(hashMapKey, list)
            }
        }
        return groupedHashMap
    }

}