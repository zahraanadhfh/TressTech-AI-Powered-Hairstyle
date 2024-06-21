package com.example.tresstech.preview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tresstech.R
import com.example.tresstech.adapter.recomendation.AdapterRec
import com.example.tresstech.adapter.recomendation.Recommendation
import com.example.tresstech.databinding.ActivityRecommendationBinding

class RecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menerima data yang dikirim dari ResultActivity
        val hairStyle = intent.getStringExtra("HAIR_STYLE")

        // Menampilkan data di TextView atau di tempat yang sesuai
        binding.recommendationText.text = "Rekomendasi $hairStyle"

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Declare the imageList variable here
        val imageList: List<Recommendation>

        if (hairStyle.equals("Bald Hair", ignoreCase = true)) {
            imageList = listOf(
                Recommendation(R.drawable.bald_1, "Fade Buzz Cut"),
                Recommendation(R.drawable.bald_2, "Buzz Cut"),
                Recommendation(R.drawable.bald_3, "Faded Cut")
            )
        } else if (hairStyle.equals("Curly Hair", ignoreCase = true)) {
            imageList = listOf(
                Recommendation(R.drawable.curly_fringe_cut, "Curly Fringe Cut"),
                Recommendation(R.drawable.curly_tapered_cut, "Curly Tapered Cut"),
                Recommendation(R.drawable.curly_top_fade_cut, "Curly Top Fade")
            )
        } else if (hairStyle.equals("Straight Hair", ignoreCase = true)) {
            imageList = listOf(
                Recommendation(R.drawable.straight_1, "Curtain Haircut"),
                Recommendation(R.drawable.straight_2, "Comma Hair"),
                Recommendation(R.drawable.straight_3, "Faux Hawk")
            )
        } else if (hairStyle.equals("Wavy Hair", ignoreCase = true)) {
            imageList = listOf(
                Recommendation(R.drawable.wavy_1, "Tousled Top"),
                Recommendation(R.drawable.wavy_2, "Curtain Wavy Bangs"),
                Recommendation(R.drawable.wavy_3, "Slicked Back Waves")
            )
        } else {
            // Handle the case when hairStyle doesn't match any of the known styles
            imageList = emptyList()
        }

        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = AdapterRec(imageList)

        binding.backbtn.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
    }
}
