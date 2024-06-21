package com.example.tresstech.preview

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tresstech.MainActivity
import com.example.tresstech.R
import com.example.tresstech.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val faceShapeLabel = intent.getStringExtra("FACE_SHAPE")
        val hairStyleLabel = intent.getStringExtra("HAIR_STYLE")
        val imageUri = intent.getStringExtra("IMAGE_URI")

        // Display the result in TextViews
        binding.faceResult.text = "${getFaceShapeLabel(faceShapeLabel)}"
        binding.hairResult.text = "${getHairStyleLabel(hairStyleLabel)}"

        // Display the image
        if (imageUri != null) {
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(Uri.parse(imageUri)))
            binding.resultImage.setImageBitmap(bitmap)
        }

        val btnRecommendation: Button = findViewById(R.id.btnToRecommendation)
        btnRecommendation.setOnClickListener {
            val intent = Intent(this, RecommendationActivity::class.java).apply {
                putExtra("HAIR_STYLE", hairStyleLabel)
            }
            startActivity(intent)
        }
        binding.btnTryAgain.setOnClickListener {
            val intent = Intent (this, PreviewActivity::class.java)
            startActivity(intent)
        }
        binding.btnToHome.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.backbtn.setOnClickListener {
            val intent = Intent(this, PreviewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getFaceShapeLabel(label: String?): String {
        return when (label) {
            "ovale" -> "Ovale"
            "rectangular" -> "Rectangular"
            "round" -> "Round"
            "square" -> "Square"
            else -> "Unknown"
        }
    }
    private fun getHairStyleLabel(label: String?): String {
        return when (label) {
            "bald hair" -> "Bald Hair"
            "curly hair" -> "Curly Hair"
            "straight hair" -> "Straight Hair"
            "wavy hair" -> "Wavy Hair"
            else -> "Unknown"
        }
    }
}