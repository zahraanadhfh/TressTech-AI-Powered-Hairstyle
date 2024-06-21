package com.example.tresstech.cart.cart_item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tresstech.databinding.ItemCartBinding

class CartAdapter(private val cartItems: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.binding.apply {
            HairStyleImage.setImageResource(cartItem.productImage)
            HairStyleName.text = cartItem.productName
            descTextView.text = cartItem.productSize
            textView22.text = cartItem.productPrice
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}