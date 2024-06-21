package com.example.tresstech.marketplace.detailproduct

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tresstech.cart.CartActivity
import com.example.tresstech.databinding.ActivityDetailProductBinding
import com.example.tresstech.marketplace.product.Product

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val product = intent.getParcelableExtra<Product>("PRODUCT")
        product?.let { displayProductDetails(it) }

        binding.detailBelibtn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        binding.detailCartbtn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        }
        private fun displayProductDetails(product: Product) {
            binding.apply {
                detailProductPic.setImageResource(product.productPic)
                detailPrice.text = product.productPrice
                detailName.text = product.productName
                detailSize.text = product.productSize
                detailDesc.text = product.productDesc
            }
        }
    }
