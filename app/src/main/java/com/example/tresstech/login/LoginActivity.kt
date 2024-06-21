package com.example.tresstech.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tresstech.MainActivity
import com.example.tresstech.R
import com.example.tresstech.api.ApiConfig
import com.example.tresstech.api.LoginRequest
import com.example.tresstech.databinding.ActivityLoginBinding
import com.example.tresstech.register.RegisterActivity
import com.example.tresstech.startingpage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val sharedPreferences by lazy {
        getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            navigateToMain()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener {
            val email = binding.editTextText.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            login(email, password)
        }
        binding.imageButton.setOnClickListener{
            val intent = Intent(this, startingpage::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiConfig.getApiService().login(loginRequest)
                Log.d("LoginActivity", "Request: email=$email, password=$password")
                Log.d("LoginActivity", "Response: $response")
                if (response.isSuccessful) {
                    Log.d("LoginActivity", "Response.isSuccessful")
                    response.body()?.let {
                        // Simpan status login dan token
                        with(sharedPreferences.edit()) {
                            putBoolean("isLoggedIn", true)
                            putString("accessToken", it.accessToken)
                            apply()
                        }
                        navigateToMain()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("LoginActivity", "Error Body: $errorBody")
                    showToast("Login failed: ${errorBody}")
                }
            } catch (e: Exception) {
                Log.e("LoginActivity", "Exception: ${e.message}", e)
                showToast("Error: ${e.message}")
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showToast(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}