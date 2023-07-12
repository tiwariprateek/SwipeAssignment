package com.example.swipeassignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.swipeassignment.data.ProductResponseItem
import com.example.swipeassignment.databinding.ProductItemBinding

class ProductAdapter(private val onProductClicked: (ProductResponseItem) -> Unit) :
    ListAdapter<ProductResponseItem, ProductAdapter.ProductViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }
    }

    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductResponseItem) {
            binding.product = product
            binding.root.setOnClickListener {
                onProductClicked(product)
            }
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<ProductResponseItem>() {
        override fun areItemsTheSame(oldItem: ProductResponseItem, newItem: ProductResponseItem): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: ProductResponseItem, newItem: ProductResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}