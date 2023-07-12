package com.example.swipeassignment.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.swipeassignment.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view).load(url).placeholder(R.drawable.image).error(R.drawable.image).into(view)
    }
}