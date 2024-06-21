package com.example.tresstech.marketplace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tresstech.marketplace.product.Product
import com.example.tresstech.databinding.ItemProductBinding

class StoreAdapter(
    private val productList: List<Product>,
    private val onItemClick: (Product) -> Unit
    ) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    inner class StoreViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.Productimage.setImageResource(product.productPic)
            binding.productName.text = product.productName
            binding.productSize.text = product.productSize
            binding.textView17.text = product.productPrice
            binding.root.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount() = productList.size
}