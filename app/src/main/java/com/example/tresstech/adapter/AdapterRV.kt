package com.example.tresstech.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.tresstech.R

class AdapterRV(private val productList:ArrayList<Product>)
    : RecyclerView.Adapter<AdapterRV.ProductViewHolder>(){

    var onItemClick : ((Product) -> Unit)? = null

    class ProductViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView){
        val imageProduct : ImageView = itemView.findViewById(R.id.imageProduct)
        val judul : TextView = itemView.findViewById(R.id.judul)
        val price: TextView = itemView.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.imageProduct.setImageResource(product.imageProduct)
        holder.judul.text = product.judul
        holder.price.text = product.price

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(product)
        }
    }
}