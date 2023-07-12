package com.example.swipeassignment.api

import com.example.swipeassignment.BASE_URL
import com.example.swipeassignment.data.ProductResponseItem
import com.example.swipeassignment.data.AddProductRequest
import com.example.swipeassignment.data.AddProductResponse
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface ApiService {

    @GET("get")
    suspend fun getAllProducts():Response<List<ProductResponseItem>>

    @Multipart
    @POST("add")
    suspend fun addProduct(
        @PartMap product: HashMap<String, RequestBody>
    ):Response<AddProductResponse>

    companion object{
        fun create():ApiService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}