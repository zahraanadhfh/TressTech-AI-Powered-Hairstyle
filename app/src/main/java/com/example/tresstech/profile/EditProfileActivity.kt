package com.example.tresstech.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import com.example.tresstech.R
import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val saveButton: ImageView = findViewById(R.id.save_btn)

        saveButton.setOnClickListener {
            val editText1: EditText = findViewById(R.id.edit_text_fullname)
            val editText2: EditText = findViewById(R.id.edit_text_username)
            val editText3: EditText = findViewById(R.id.edit_text_email)
            val editText4: EditText = findViewById(R.id.edit_text_adress)
            val editText5: EditText = findViewById(R.id.edit_text_number)

            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("edit_text_fullname", editText1.text.toString())
            intent.putExtra("edit_text_username", editText2.text.toString())
            intent.putExtra("edit_text_email", editText3.text.toString())
            intent.putExtra("edit_text_adress", editText4.text.toString())
            intent.putExtra("edit_text_number", editText5.text.toString())
            startActivity(intent)
        }
    }
}