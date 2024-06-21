package com.example.tresstech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tresstech.adapter.AdapterRV
import com.example.tresstech.adapter.Product
import com.example.tresstech.databinding.ActivityMainBinding
import com.example.tresstech.list_hairstyle.ListHairStyle
import com.example.tresstech.login.LoginActivity
import com.example.tresstech.map.MapActivity
import com.example.tresstech.marketplace.Store
import com.example.tresstech.payment.PaymentMethodActivity
import com.example.tresstech.preview.PreviewActivity
import com.example.tresstech.profile.ProfileActivity
import com.example.tresstech.register.RegisterActivity
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productList: ArrayList<Product>
    private lateinit var adapterRV: AdapterRV

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupRecyclerView()
        setupNavigationDrawer()
        binding.cart.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnShop.setOnClickListener{
            val intent = Intent(this,Store::class.java)
            startActivity(intent)
        }
        binding.btnHair.setOnClickListener {
            val intent = Intent(this,ListHairStyle::class.java)
            startActivity(intent)
        }
    }

    private fun setupViews() {
        //map
        binding.btnLocation.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        binding.btnScanNow.setOnClickListener {
            val intent = Intent(this, PreviewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        productList = ArrayList()
        productList.add(Product(R.drawable.vaseline, "Vaseline hair tonic and scalp conditioner (100ml)", "Rp. 45.000"))
        productList.add(Product(R.drawable.gatsby, "Gatsby Styling Gel Wet & Hard (200ml)", "Rp. 25.000"))
        productList.add(Product(R.drawable.american_pomade, "American Crew Pomade", "Rp. 30.000"))

        adapterRV = AdapterRV(productList)
        binding.recyclerView.adapter = adapterRV

        adapterRV.onItemClick = {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("anime", it)
            startActivity(intent)
        }
    }

    private fun setupNavigationDrawer() {
        binding.drawerButton.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
                binding.drawerLayout.closeDrawer(binding.navView)
            } else {
                binding.drawerLayout.openDrawer(binding.navView)
            }
        }

        val navigationListener = NavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_payment -> {
                    val intent = Intent(this, PaymentMethodActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_setting -> {
                    Toast.makeText(this, "Setting selected", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_about_us -> {
                    Toast.makeText(this, "About Us selected", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    logout()
                }
            }
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawers()
            true
        }
        binding.navView.setNavigationItemSelectedListener(navigationListener)
    }
    private fun logout() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isLoggedIn", false)
            remove("accessToken")
            apply()
        }
        val intent = Intent(this, startingpage::class.java)
        startActivity(intent)
        finish()
    }
}