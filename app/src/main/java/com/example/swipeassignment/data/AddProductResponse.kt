package com.example.swipeassignment.data

import com.google.gson.annotations.SerializedName

data class AddProductResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("product_details")
	val productDetails: ProductDetails? = null
)

data class ProductDetails(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("product_type")
	val productType: String? = null,

	@field:SerializedName("price")
	val price: Any? = null,

	@field:SerializedName("tax")
	val tax: Any? = null,

	@field:SerializedName("product_name")
	val productName: String? = null
)
