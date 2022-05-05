package com.rbc.rbcmobileapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rbc.rbcmobileapplication.data.AccountDetailsItems
import com.rbc.rbcmobileapplication.data.AccountItems
import com.rbc.rbcmobileapplication.data.AccountTypeItems
import com.rbc.rbcmobileapplication.databinding.BankAccountsListItemsBinding
import com.rbc.rbcmobileapplication.databinding.ListHeadingItemsBinding
import com.rbc.rbcmobileapplication.ui.fragment.HomeFragmentDirections

class BankAccountsAdapter(private val accountItemsList: ArrayList<AccountItems>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class AccountDetailsViewHolder(private val binding: BankAccountsListItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(accountDetailsItems: AccountDetailsItems) {
            binding.nameTextView.text = accountDetailsItems.account.name
            binding.numberTextView.text = accountDetailsItems.account.number
            val balance = if (accountDetailsItems.account.balance.contains("-")) {
                "-$${accountDetailsItems.account.balance.replace("-", "")}"
            } else {
                "$${accountDetailsItems.account.balance}"
            }
            binding.balanceTextView.text = balance
        }
    }

    class HeadingViewHolder(private val binding: ListHeadingItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(accountTypeItems: AccountTypeItems) {
            binding.headingTextView.text = accountTypeItems.accountType.toString().replace("_", " ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == AccountItems.TYPE_OF_ACCOUNT) {
            HeadingViewHolder(ListHeadingItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            AccountDetailsViewHolder(BankAccountsListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            AccountItems.TYPE_ACCOUNT_DETAILS -> {
                val accountDetailsViewHolder = holder as AccountDetailsViewHolder
                accountDetailsViewHolder.bind(accountItemsList[position] as AccountDetailsItems)
                accountDetailsViewHolder.itemView.setOnClickListener {
                    it.findNavController().navigate(
                        HomeFragmentDirections.actionNavigationHomeToNavigationTransactions(
                            (accountItemsList[position] as AccountDetailsItems).account.number,
                            (accountItemsList[position] as AccountDetailsItems).account.type.toString(),
                            (accountItemsList[position] as AccountDetailsItems).account.name,
                            (accountItemsList[position] as AccountDetailsItems).account.balance
                        )
                    )
                }
            }
            AccountItems.TYPE_OF_ACCOUNT -> {
                val headingViewHolder = holder as HeadingViewHolder
                headingViewHolder.bind(accountItemsList[position] as AccountTypeItems)
            }
        }
    }

    override fun getItemCount() = accountItemsList.size

    override fun getItemViewType(position: Int) = accountItemsList[position].type

    fun addData(list: ArrayList<AccountItems>) = this.accountItemsList.addAll(list)
}