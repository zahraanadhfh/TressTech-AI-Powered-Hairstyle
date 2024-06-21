package com.example.tresstech.marketplace

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tresstech.R
import com.example.tresstech.cart.CartActivity
import com.example.tresstech.databinding.ActivityStoreBinding
import com.example.tresstech.marketplace.detailproduct.DetailProductActivity
import com.example.tresstech.marketplace.product.Product


class Store : AppCompatActivity() {
    private lateinit var binding: ActivityStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.cartProduct.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        val productNames = resources.getStringArray(R.array.product_name)
        val productPhotos = resources.obtainTypedArray(R.array.product_photo)
        val productPrices = resources.getStringArray(R.array.product_hair)
        val productSizes = resources.getStringArray(R.array.product_size)
        val productDesc = resources.getStringArray(R.array.product_desc)

        val productList = ArrayList<Product>()
        for (i in productNames.indices) {
            val product = Product(
                productPhotos.getResourceId(i, -1),
                productNames[i],
                productSizes[i],
                productPrices[i],
                productDesc[i]
            )
            productList.add(product)
        }
        productPhotos.recycle()

        val adapter = StoreAdapter(productList) { product ->
            // Menggunakan lambda untuk mengirim data ke DetailProductActivity
            val intent = Intent(this, DetailProductActivity::class.java).apply {
                putExtra("PRODUCT", product)
            }
            startActivity(intent)
        }
        binding.rvProduct.layoutManager = GridLayoutManager(this, 2)
        binding.rvProduct.adapter = adapter
        binding.rvProduct.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                val spanCount = 2 // Number of columns in the grid
                val column = position % spanCount

                outRect.left = if (column == 0) 0 else 0
                outRect.right = if (column == spanCount - 1) 0 else 0
                outRect.top = 0
                outRect.bottom = 0
            }
        })
    }
}