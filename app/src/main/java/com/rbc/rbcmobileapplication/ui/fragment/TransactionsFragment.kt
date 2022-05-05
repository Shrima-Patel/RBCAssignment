package com.rbc.rbcmobileapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbc.rbcmobileapplication.R
import com.rbc.rbcmobileapplication.databinding.FragmentTransactionsBinding
import com.rbc.rbcmobileapplication.data.TransactionsItems
import com.rbc.rbcmobileapplication.ui.adapter.TransactionsAdapter
import com.rbc.rbcmobileapplication.ui.viewmodel.TransactionsViewModel

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding

    private lateinit var transactionsViewModel: TransactionsViewModel
    private lateinit var transactionsAdapter: TransactionsAdapter

    private var accountNumber: String? = null
    private var accountType: String? = null
    private var accountBalance: String? = null
    private var accountName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountName = it.getString("account_name")
            accountBalance = it.getString("account_balance")
            accountNumber = it.getString("account_number")
            accountType = it.getString("account_type")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        transactionsViewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]

        binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(!accountName.isNullOrBlank() || !accountName.isNullOrEmpty()) {
            binding.toolbarNameTextView.text = accountName
            binding.toolbarNumberTextView.text = accountNumber
            binding.toolbarBalanceTextView.text =
                if (accountBalance.toString().contains("-")) {
                    "-$${accountBalance.toString().replace("-", "")}"
                } else {
                    "$${accountBalance}"
                }
        } else {
            binding.toolbarNumberTextView.text = resources.getString(R.string.all_transaction_text)
            binding.toolbarNumberTextView.textSize = (resources.getDimension(R.dimen.toolbar_account_balance) * 0.8).toFloat()
        }

        setupUI()
        setUpListOfTransactions()
        getTransactions()

        return root
    }

    private fun setupUI() {
        binding.transactionsListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        transactionsAdapter = TransactionsAdapter(arrayListOf())
        binding.transactionsListRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), (
                    binding.transactionsListRecyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        binding.transactionsListRecyclerView.adapter = transactionsAdapter
        transactionsViewModel.progressBarVisibility.observe(this, {
            if(it){
                binding.progressCircular.visibility = View.VISIBLE
            } else {
                binding.progressCircular.visibility = View.INVISIBLE
            }
        })
    }

    private fun setUpListOfTransactions() {
        transactionsViewModel.transactions.observe(this, {
            if((it?.size ?: 0) > 0) {
                transactionsAdapter.addData(it as ArrayList<TransactionsItems>)
                transactionsAdapter.notifyDataSetChanged()
            } else {
                binding.noTransactionTextView.text = getString(R.string.no_transactions)
            }
        })
    }

    private fun getTransactions(){
        transactionsViewModel.getTransactionsList(accountNumber, accountType)
    }

}