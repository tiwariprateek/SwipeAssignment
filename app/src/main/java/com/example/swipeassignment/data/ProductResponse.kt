package com.example.swipeassignment.data

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("ProductResponse")
	val productResponse: List<ProductResponseItem?>? = null
)

data class ProductResponseItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("product_type")
	val productType: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("tax")
	val tax: String? = null,

	@field:SerializedName("product_name")
	val productName: String? = null
)
