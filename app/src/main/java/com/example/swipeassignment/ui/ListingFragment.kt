package com.example.swipeassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.swipeassignment.R
import com.example.swipeassignment.adapters.ProductAdapter
import com.example.swipeassignment.data.ProductResponseItem
import com.example.swipeassignment.databinding.FragmentListingBinding
import com.example.swipeassignment.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListingFragment : Fragment() {
    private val TAG = "ListingFragment"
    private var _binding:FragmentListingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ProductViewModel>()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListingBinding.inflate(inflater)
        productAdapter = ProductAdapter(::onProductClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productRv.adapter = productAdapter
        viewModel.productResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Error -> {
                    Log.d(TAG, "onViewCreated: Error occurred ${it.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d(TAG, "onViewCreated: Loading")
                }
                is NetworkResult.Success -> {
                    productAdapter.submitList(it.data)
                    Log.d(TAG, "onViewCreated: Success with data ${it.data}")
                }

            }
        }
    }

    private fun onProductClicked(product:ProductResponseItem){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}