package com.example.tresstech.list_hairstyle

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tresstech.MainActivity
import com.example.tresstech.R
import com.example.tresstech.databinding.ActivityListHairStyleBinding
import com.example.tresstech.list_hairstyle.hairstyle.HairStyle

class ListHairStyle : AppCompatActivity() {
    private lateinit var binding: ActivityListHairStyleBinding
    private lateinit var hairStyleAdapter: HairStyleAdapter
    private var hairStyleList = mutableListOf<HairStyle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListHairStyleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.backbtnTomain.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        setupRecyclerView()

        loadHairStyles()

    }

    private fun setupRecyclerView() {
        hairStyleAdapter = HairStyleAdapter(hairStyleList)
        binding.rvHairStyle.layoutManager = LinearLayoutManager(this)
        binding.rvHairStyle.adapter = hairStyleAdapter
    }

    private fun loadHairStyles() {
        val pictures = resources.obtainTypedArray(R.array.data_Haircut_picture)
        val names = resources.getStringArray(R.array.data_haircut_name)
        val descriptions = resources.getStringArray(R.array.data_haircut_description)

        for (i in names.indices) {
            val picture = pictures.getResourceId(i, -1)
            val name = names[i]
            val description = descriptions[i]
            hairStyleList.add(HairStyle(picture, name, description))
        }
        pictures.recycle()

        hairStyleAdapter.notifyDataSetChanged()
    }

}