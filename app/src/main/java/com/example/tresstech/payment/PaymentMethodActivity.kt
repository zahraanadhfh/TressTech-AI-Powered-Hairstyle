package com.example.tresstech.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tresstech.MainActivity
import com.example.tresstech.databinding.ActivityPaymentMethodBinding

class PaymentMethodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentMethodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backbtnTomain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}