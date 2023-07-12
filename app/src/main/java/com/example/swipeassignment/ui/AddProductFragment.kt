package com.example.swipeassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.swipeassignment.R
import com.example.swipeassignment.data.AddProductRequest
import com.example.swipeassignment.databinding.FragmentAddProductBinding
import com.example.swipeassignment.utils.NetworkResult
import com.google.android.material.snackbar.Snackbar

class AddProductFragment : Fragment() {
    private val TAG = "AddProductFragment"
    private var _binding:FragmentAddProductBinding? = null
    private val viewModel by activityViewModels<ProductViewModel>()
    private val binding get() = _binding!!
    private val productTypes = listOf("Service","Furniture",
        "Product", "Electronics","Food", "Books", "Sports")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.addProductResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Error -> {
                    Log.d(TAG, "onViewCreated: Error occurred ${it.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d(TAG, "onViewCreated: Loading")
                }
                is NetworkResult.Success -> {
                    Snackbar.make(requireView(),it.data.toString(), Snackbar.LENGTH_LONG).show()
                    Log.d(TAG, "onViewCreated: Success with data ${it.data}")
                }

            }
        }
        binding.submitBttn.setOnClickListener {
            addProduct()
        }

    }

    private fun addProduct(){
        viewModel.addProduct(AddProductRequest(
            productName = binding.productName.text.toString(),
            productType = binding.spinner.selectedItem.toString(),
            tax = binding.productTax.text.toString(),
            price = binding.productPrice.text.toString()
        ))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}