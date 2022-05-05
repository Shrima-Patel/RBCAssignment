package com.rbc.rbcmobileapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rbc.rbcmobileapplication.databinding.ListHeadingItemsBinding
import com.rbc.rbcmobileapplication.databinding.TransactionsListItemsBinding
import com.rbc.rbcmobileapplication.data.TransactionsDateItems
import com.rbc.rbcmobileapplication.data.TransactionsDetailsItems
import com.rbc.rbcmobileapplication.data.TransactionsItems
import com.rbc.rbcmobileapplication.utils.CalenderToDate

class TransactionsAdapter(private val transactionsItemsList: ArrayList<TransactionsItems>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class TransactionViewHolder(private val binding: TransactionsListItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionsDetailsItems: TransactionsDetailsItems) {
            val amount = if (transactionsDetailsItems.transaction.amount.contains("-")) {
                "-$${transactionsDetailsItems.transaction.amount.replace("-", "")}"
            } else {
                "$${transactionsDetailsItems.transaction.amount}"
            }
            binding.amountTextView.text = amount
            binding.descriptionTextView.text = transactionsDetailsItems.transaction.description
        }
    }

    class HeadingViewHolder(private val binding: ListHeadingItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionsDate: TransactionsDateItems) {
            binding.headingTextView.text = CalenderToDate.getDisplayDate(transactionsDate.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TransactionsItems.TYPE_DATE) {
            HeadingViewHolder(ListHeadingItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            TransactionViewHolder(TransactionsListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TransactionsItems.TYPE_TRANSACTIONS_DETAILS -> {
                val transactionViewHolder = holder as TransactionViewHolder
                transactionViewHolder.bind(transactionsItemsList[position] as TransactionsDetailsItems)
            }
            TransactionsItems.TYPE_DATE -> {
                val headingViewHolder = holder as HeadingViewHolder
                headingViewHolder.bind(transactionsItemsList[position] as TransactionsDateItems)
            }
        }
    }

    override fun getItemCount() = transactionsItemsList.size

    override fun getItemViewType(position: Int) = transactionsItemsList[position].type

    fun addData(list: ArrayList<TransactionsItems>) {
        this.transactionsItemsList.removeAll(list)
        this.transactionsItemsList.addAll(list)
    }
}