package com.example.swipeassignment.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipeassignment.data.AddProductRequest
import com.example.swipeassignment.data.AddProductResponse
import com.example.swipeassignment.data.ProductResponse
import com.example.swipeassignment.data.ProductResponseItem
import com.example.swipeassignment.repository.ProductRepository
import com.example.swipeassignment.utils.NetworkResult
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository):ViewModel() {

    private val _productResponse = MutableLiveData<NetworkResult<List<ProductResponseItem>>>()
    val productResponse : LiveData<NetworkResult<List<ProductResponseItem>>> get() = _productResponse

    private val _addProductResponse = MutableLiveData<NetworkResult<AddProductResponse>>()
    val addProductResponse : LiveData<NetworkResult<AddProductResponse>> get() = _addProductResponse

    private val TAG = "ProductViewModel"

    init {
        viewModelScope.launch(IO) {
            getAllProducts()
        }
    }

    private suspend fun getAllProducts(){
        _productResponse.postValue(NetworkResult.Loading())
        val response = productRepository.getProducts()
        try {
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!
                Log.d(TAG, "getAllProducts: $data")
                _productResponse.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _productResponse.postValue(NetworkResult.Error(errorObj.getString("message")))
            } else {
                _productResponse.postValue(NetworkResult.Error("Something Went Wrong"))
            }
        }catch (t:Throwable){
            Log.d("Exception", "handleResponse: ${t.message}")
            _productResponse.postValue(NetworkResult.Error(t.message))
        }
    }

    fun addProduct(product:AddProductRequest){
        _addProductResponse.postValue(NetworkResult.Loading())
        val textMediaType = "text/plain".toMediaType()
        val request : HashMap<String, RequestBody> = hashMapOf()
        request["product_name"] = product.productName.toRequestBody(textMediaType)
        request["product_type"] = product.productType.toRequestBody(textMediaType)
        request["price"] = product.price.toRequestBody(textMediaType)
        request["tax"] = product.tax.toRequestBody(textMediaType)
        viewModelScope.launch(IO) {
            val response = productRepository.addProduct(request)
            handleAddProductResponse(response)
        }
    }

    private fun handleAddProductResponse(response: Response<AddProductResponse>){
        try {
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!
                Log.d(TAG, "getAllProducts: $data")
                _addProductResponse.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _addProductResponse.postValue(NetworkResult.Error(errorObj.getString("message")))
            } else {
                _addProductResponse.postValue(NetworkResult.Error("Something Went Wrong"))
            }
        }catch (t:Throwable){
            Log.d("Exception", "handleResponse: ${t.message}")
            _addProductResponse.postValue(NetworkResult.Error(t.message))
        }
    }

}