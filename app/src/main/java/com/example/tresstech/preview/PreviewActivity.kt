package com.example.tresstech.preview

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import org.tensorflow.lite.Interpreter
import java.io.File
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import android.graphics.BitmapFactory
import com.example.tresstech.MainActivity
import com.example.tresstech.R
import com.example.tresstech.databinding.ActivityPreviewBinding
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding
    private lateinit var interpreter: Interpreter
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            interpreter = Interpreter(loadModelFile())
        } catch (e: IOException) {
            e.printStackTrace()
        }

        binding.imgCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 100)
            }
        }

        binding.imgGallery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 200)
        }

        binding.imgAnalyze.setOnClickListener {
            if (this::bitmap.isInitialized) {
                binding.imageContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.main_background))
                analyzeImage()
            } else {
                binding.imageContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.brown))
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }
        binding.backbtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 100)
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    val photo = data?.extras?.get("data") as Bitmap
                    bitmap = Bitmap.createScaledBitmap(photo, 128, 128, false)
                    binding.imagePreview.setImageBitmap(bitmap)
                    binding.imageContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.main_background))
                }
                200 -> {
                    val imageUri = data?.data
                    val inputStream = contentResolver.openInputStream(imageUri!!)
                    bitmap = BitmapFactory.decodeStream(inputStream)
                    bitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, false)
                    binding.imagePreview.setImageBitmap(bitmap)
                    binding.imageContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.main_background))
                }
            }
        }
    }

    private fun saveBitmapToFile(bitmap: Bitmap): Uri {
        val file = File(cacheDir, "preview_image.png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return Uri.fromFile(file)
    }

    private fun analyzeImage() {
        val imgData = ByteBuffer.allocateDirect(4 * 128 * 128 * 3)
        imgData.order(ByteOrder.nativeOrder())

        for (i in 0 until 128) {
            for (j in 0 until 128) {
                val pixel = bitmap.getPixel(i, j)
                imgData.putFloat((pixel shr 16 and 0xFF) / 255.0f)
                imgData.putFloat((pixel shr 8 and 0xFF) / 255.0f)
                imgData.putFloat((pixel and 0xFF) / 255.0f)
            }
        }

        val inputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 128, 128, 3), DataType.FLOAT32)
        inputBuffer.loadBuffer(imgData)

        val outputFaceShape = TensorBuffer.createFixedSize(intArrayOf(1, 4), DataType.FLOAT32)
        val outputHairStyle = TensorBuffer.createFixedSize(intArrayOf(1, 4), DataType.FLOAT32)

        interpreter.runForMultipleInputsOutputs(arrayOf(inputBuffer.buffer), mapOf(0 to outputFaceShape.buffer, 1 to outputHairStyle.buffer))

        val faceShape = outputFaceShape.floatArray
        val hairStyle = outputHairStyle.floatArray

        val faceShapeIndex = faceShape.indices.maxByOrNull { faceShape[it] } ?: -1
        val hairStyleIndex = hairStyle.indices.maxByOrNull { hairStyle[it] } ?: -1

        val faceShapeLabel = when (faceShapeIndex) {
            0 -> "ovale"
            1 -> "rectangular"
            2 -> "round"
            3 -> "square"
            else -> "unknown"
        }

        val hairStyleLabel = when (hairStyleIndex) {
            0 -> "bald hair"
            1 -> "curly hair"
            2 -> "straight hair"
            3 -> "wavy hair"
            else -> "unknown"
        }

        val imageUri = saveBitmapToFile(bitmap)

        val resultIntent = Intent(this, ResultActivity::class.java).apply {
            putExtra("FACE_SHAPE", faceShapeLabel)
            putExtra("HAIR_STYLE", hairStyleLabel)
            putExtra("IMAGE_URI", imageUri.toString())
        }
        startActivity(resultIntent)
    }

    @Throws(IOException::class)
    private fun loadModelFile(): ByteBuffer {
        val fileDescriptor = assets.openFd("final_model_recommendations.tflite")
        val inputStream = fileDescriptor.createInputStream()
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}
