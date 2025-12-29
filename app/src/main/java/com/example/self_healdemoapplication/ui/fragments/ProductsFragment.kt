package com.example.self_healdemoapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.self_healdemoapplication.MainActivity
import com.example.self_healdemoapplication.databinding.FragmentProductsBinding
import com.example.self_healdemoapplication.ui.adapters.ProductsAdapter
import com.example.self_healdemoapplication.viewmodel.SelfHealViewModel

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SelfHealViewModel
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).getViewModel()

        setupRecyclerView()
        setupLogoutButton()
        setupDemoModeObserver()
    }

    private fun setupRecyclerView() {
        productsAdapter = ProductsAdapter(viewModel)
        binding.productsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }
    }

    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(LoginFragment())
        }
    }

    private fun setupDemoModeObserver() {
        viewModel.isDemoModeEnabled.observe(viewLifecycleOwner) { isDemoMode ->
            // Update IDs dynamically
            productsAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
