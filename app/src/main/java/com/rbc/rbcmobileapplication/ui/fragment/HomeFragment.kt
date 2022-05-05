package com.rbc.rbcmobileapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rbc.rbcmobileapplication.databinding.FragmentHomeBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbc.rbcmobileapplication.R
import com.rbc.rbcmobileapplication.data.AccountItems
import com.rbc.rbcmobileapplication.ui.adapter.BankAccountsAdapter
import com.rbc.rbcmobileapplication.ui.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeViewModel : HomeViewModel
    private lateinit var bankAccountsAdapter: BankAccountsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupUI()
        setUpListOfAccounts()

        binding.allTransactionTextView.setOnClickListener {
            it.findNavController().navigate(R.id.navigation_transactions)
        }

        return root
    }

    private fun setupUI() {
        binding.bankAccountListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bankAccountsAdapter = BankAccountsAdapter(arrayListOf())
        binding.bankAccountListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (binding.bankAccountListRecyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.bankAccountListRecyclerView.adapter = bankAccountsAdapter
        homeViewModel.progressBarVisibility.observe(this, {
            if(it){
                binding.progressCircular.visibility = View.VISIBLE
            } else {
                binding.progressCircular.visibility = View.INVISIBLE
            }
        })
    }

    private fun setUpListOfAccounts() {
        homeViewModel.bankAccounts.observe(this, {
            bankAccountsAdapter.addData(it as ArrayList<AccountItems>)
            bankAccountsAdapter.notifyDataSetChanged()
        })
    }

}