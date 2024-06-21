package com.example.tresstech.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tresstech.MainActivity
import com.example.tresstech.api.ApiConfig
import com.example.tresstech.api.NetworkUtil
import com.example.tresstech.api.RegisterRequest
import com.example.tresstech.databinding.ActivityRegisterBinding
import com.example.tresstech.login.LoginActivity
import com.example.tresstech.startingpage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.regisButton.setOnClickListener {
            val name = binding.regisusername.text.toString()
            val email = binding.regisemail.text.toString()
            val password = binding.regisPassword.text.toString()
            val confPassword = binding.regisConfPassword.text.toString()
            Log.d("RegisterActivity", "Name: $name, Email: $email, Password: $password, Confirm Password: $confPassword")
            if (NetworkUtil.isConnected(this)) {
                register(name, email, password, confPassword)
                goToSignin()
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imageButton.setOnClickListener{
            val intent = Intent(this,startingpage::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun register(name: String, email: String, password: String, confPassword: String) {
        val registerRequest = RegisterRequest(name, email, password, confPassword)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().register(registerRequest)
                }
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.msg == "User created successfully") {
                        Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RegisterActivity, body?.msg ?: "Unknown error dalem", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, response.errorBody()?.string() ?: "Unknown error luar", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@RegisterActivity, "Registration failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun goToSignin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}