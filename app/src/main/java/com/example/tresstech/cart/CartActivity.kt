package com.example.tresstech.cart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tresstech.R
import com.example.tresstech.cart.cart_item.CartAdapter
import com.example.tresstech.cart.cart_item.CartItem
import com.example.tresstech.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val productNames = resources.getStringArray(R.array.product_name)
        val productPhotos = resources.obtainTypedArray(R.array.product_photo)
        val productPrices = resources.getStringArray(R.array.product_hair)
        val productSizes = resources.getStringArray(R.array.product_size)

        val cartItems = mutableListOf<CartItem>()
        for (i in productNames.indices) {
            val photoResId = productPhotos.getResourceId(i, -1)
            if (photoResId != -1) {
                cartItems.add(CartItem(photoResId, productNames[i], productSizes[i], productPrices[i]))
            }
        }
        productPhotos.recycle()

        cartAdapter = CartAdapter(cartItems)
        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        binding.recyclerView2.adapter = cartAdapter
    }
}