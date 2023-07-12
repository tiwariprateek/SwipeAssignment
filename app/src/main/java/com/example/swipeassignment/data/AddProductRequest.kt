package com.example.swipeassignment.data

import com.google.gson.annotations.SerializedName

data class AddProductRequest(
    @SerializedName("product_name")
    var productName:String,
    @SerializedName("product_type")
    var productType:String,
    @SerializedName("price")
    var price:String,
    @SerializedName("tax")
    var tax:String,
)