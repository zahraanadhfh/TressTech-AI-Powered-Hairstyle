package com.example.tresstech

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.widget.Button
import androidx.core.view.WindowInsetsCompat
import com.example.tresstech.databinding.ActivityPreviewBinding
import com.example.tresstech.databinding.ActivityStartingpageBinding
import com.example.tresstech.login.LoginActivity
import com.example.tresstech.register.RegisterActivity

class startingpage : AppCompatActivity() {
    private lateinit var binding: ActivityStartingpageBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityStartingpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.getstartedbtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.signinbtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}