package com.example.swipeassignment.repository

import com.example.swipeassignment.api.ApiService
import com.example.swipeassignment.data.AddProductRequest
import okhttp3.RequestBody
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getProducts() = apiService.getAllProducts()
    suspend fun addProduct(product:HashMap<String, RequestBody>) = apiService.addProduct(product)

}