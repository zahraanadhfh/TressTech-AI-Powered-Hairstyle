package com.example.tresstech.fragment

import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.tresstech.R
import com.example.tresstech.databinding.FragmentScanDialogBinding
import com.example.tresstech.getImageUri
import com.example.tresstech.preview.PreviewActivity

class ScanDialogFragment : DialogFragment() {
    private var currentImageUri: Uri? = null
    private var _binding: FragmentScanDialogBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                startCamera()
            } else {
                Log.d("Permission", "Camera permission denied")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewScanBy.text = "Scan by"
        binding.imageViewCamera.setImageResource(R.drawable.camera_image_button)
        binding.imageViewGallery.setImageResource(R.drawable.gallery_image_button)

        binding.imageViewCamera.setOnClickListener { checkCameraPermissionAndStart() }
        binding.imageViewGallery.setOnClickListener { openGallery() }

        val textViewCamera = view.findViewById<TextView>(R.id.textViewCamera)
        val textViewGallery = view.findViewById<TextView>(R.id.textViewGallery)
    }

    private fun checkCameraPermissionAndStart() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            goToPreviewActivity()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        Log.d("Camera URI", "startCamera: $currentImageUri")
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            Log.d("Camera Success", "Image captured successfully: $currentImageUri")
            goToPreviewActivity()
        } else {
            Log.d("Camera Failure", "Image capture failed")
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.7).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun goToPreviewActivity() {
        currentImageUri?.let {
            val intent = Intent(requireContext(), PreviewActivity::class.java).apply {
                putExtra("image_uri", it.toString())
            }
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance(): ScanDialogFragment {
            return ScanDialogFragment()
        }
    }
}