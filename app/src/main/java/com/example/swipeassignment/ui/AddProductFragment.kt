package com.example.swipeassignment.ui

import android.content.DialogInterface
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
                    Snackbar.make(requireView(),it.message.toString(), Snackbar.LENGTH_LONG).show()
                    Log.d(TAG, "onViewCreated: Error occurred ${it.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d(TAG, "onViewCreated: Loading")
                }
                is NetworkResult.Success -> {
                    showSuccessAlert(it.data?.message!!)
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
    private fun showSuccessAlert(message: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
        builder.setTitle("Product Added!!")
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { _: DialogInterface?, _: Int ->
            findNavController().navigateUp()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}