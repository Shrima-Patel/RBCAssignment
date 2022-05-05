package com.rbc.rbcmobileapplication.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbc.rbcaccountlibrary.Account
import com.rbc.rbcmobileapplication.data.AccountDetailsItems
import com.rbc.rbcmobileapplication.data.AccountItems
import com.rbc.rbcmobileapplication.data.AccountTypeItems
import com.rbc.rbcmobileapplication.data.repository.BankAccountsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.HashMap

class HomeViewModel : ViewModel() {

    private val _bankAccounts = MutableLiveData<List<AccountItems>?>()
    val bankAccounts: LiveData<List<AccountItems>?>
        get() = _bankAccounts

    private val _progressBarVisibility = MutableLiveData<Boolean>()
    val progressBarVisibility: LiveData<Boolean>
        get() = _progressBarVisibility

    private val bankAccountsRepository = BankAccountsRepository()

    init {
        getAllAccountsList()
    }

    private fun getAllAccountsList() {
        _progressBarVisibility.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val listOfAccounts = bankAccountsRepository.getAccountsList()
                _bankAccounts.postValue(groupTransactions(listOfAccounts))
            }
            _progressBarVisibility.value = false
        }
    }

    private fun groupTransactions(listOfAccount: List<Account>): List<AccountItems> {
        val consolidatedList = arrayListOf<AccountItems>()
        val groupedHashMap: HashMap<String, List<Account>> = groupDataIntoHashMap(listOfAccount)
        for (accountItem in groupedHashMap.keys) {
            val accType = AccountTypeItems()
            accType.accountType = accountItem
            consolidatedList.add(accType)
            for (accountDetails in groupedHashMap[accountItem]!!) {
                val generalItem = AccountDetailsItems()
                generalItem.account = accountDetails
                consolidatedList.add(generalItem)
            }
        }
        return consolidatedList
    }

    private fun groupDataIntoHashMap(listOfAccount: List<Account>): HashMap<String, List<Account>> {
        val groupedHashMap: HashMap<String, List<Account>> = HashMap()
        for (accountList in listOfAccount) {
            val hashMapKey: String = accountList.type.toString()
            if (groupedHashMap.containsKey(hashMapKey)) {
                val previousValue: List<Account>? = groupedHashMap[hashMapKey]
                val newValue: List<Account> = previousValue!! + listOf(accountList)
                groupedHashMap[hashMapKey] = newValue
            } else {
                val list = arrayListOf<Account>()
                list.add(accountList)
                groupedHashMap.put(hashMapKey, list)
            }
        }
        return groupedHashMap
    }

}