package com.example.fishcureapp.ui.detection.result

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fishcureapp.data.factory.ViewModelFactory
import com.example.fishcureapp.data.factory.ViewModelFactoryDetect
import com.example.fishcureapp.databinding.FragmentDetectionResultBinding
import com.example.fishcureapp.ui.MenuActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class DetectionResultFragment : Fragment() {

    private lateinit var binding: FragmentDetectionResultBinding
    private val navArgs by navArgs<DetectionResultFragmentArgs>()

    private val viewModel: DetectViewModel by viewModels {
        ViewModelFactoryDetect.getInstance(requireActivity())
    }

    private val solutionViewModel: SolutionViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetectionResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observeViewModel()

        binding.btnHome.setOnClickListener {
            val intent = Intent(requireContext(), MenuActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpView() {
        val imgResult = navArgs.imageResult

        val imageFile = if (imgResult.isFromCamera) {
            imgResult.imageBitmap?.let { saveBitmapToFile(it) }
        } else {
            imgResult.imageUri?.let { getFileFromUri(it) }
        }

        imageFile?.let { file ->
            Glide.with(requireActivity())
                .load(file)
                .into(binding.imgDetectResult)
            viewModel.predictDisease(file)
        } ?: run {
            binding.tvDetection.text = "Error loading image"
        }
    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun observeViewModel() {
        viewModel.predictResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { response ->
                    val formattedDiseaseName = formatDiseaseName(response.detectResponseClass)
                    binding.tvDetection.text = formattedDiseaseName

                    val confidencePercentage = response.confidence * 100
                    val formattedConfidence = String.format("%.2f", confidencePercentage)

                    binding.tvDescAccuracy.text = "${formattedConfidence}%"
                    // Fetch solution based on detected disease
                    solutionViewModel.getDiseaseSolution(response.detectResponseClass)
                },
                onFailure = { error ->
                    binding.tvDetection.text = "Prediction failed: ${error.message}"
                }
            )
        }

        solutionViewModel.solutionResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { response ->
                    binding.tvDescSymptoms.text = response.gejala.values.joinToString("\n") { "• $it" }
                    binding.tvDescPenanganan.text = response.langkahPenanganan.values.joinToString("\n") { "• $it" }
                    binding.tvDescObat.text = response.obat.values.joinToString("\n") { "• $it" }

                    // Save history once the solution is retrieved
                    saveSolutionHistory()
                },
                onFailure = { error ->
                    binding.tvDetection.text = "Solution retrieval failed: ${error.message}"
                }
            )
        }

        solutionViewModel.saveResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    Toast.makeText(requireContext(), "History saved successfully", Toast.LENGTH_SHORT).show()
                },
                onFailure = { error ->
                    // Toast.makeText(requireContext(), "Failed to save history: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun formatDiseaseName(diseaseName: String): String {
        return diseaseName.split('_').joinToString(" ") { it.capitalize() }
    }

    private fun saveSolutionHistory() {
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "") ?: "" // Retrieve email from SharedPreference

        val diseaseName = binding.tvDetection.text.toString()
        val akurasi = binding.tvDescAccuracy.text.toString().replace("%", "").toDoubleOrNull()
        val imgResult = navArgs.imageResult
        val imageFile: File? = if (imgResult.isFromCamera) {
            imgResult.imageBitmap?.let { saveBitmapToFile(it) }
        } else {
            imgResult.imageUri?.let { getFileFromUri(it) }
        }

        if (imageFile == null || akurasi == null) {
            Toast.makeText(requireContext(), "Failed to process image or accuracy", Toast.LENGTH_SHORT).show()
            return
        }

        solutionViewModel.addHistory(email, diseaseName, akurasi, imageFile)
    }

    private fun getFileFromUri(uri: Uri): File? {
        val path = getPathFromUri(uri)
        return if (path != null) {
            File(path)
        } else {
            null
        }
    }

    private fun getPathFromUri(uri: Uri): String? {
        val cursor = requireActivity().contentResolver.query(uri, null, null, null, null)
        return if (cursor != null) {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx).also {
                cursor.close()
            }
        } else {
            uri.path
        }
    }

    private fun saveBitmapToFile(bitmap: Bitmap): File {
        val filesDir = requireActivity().filesDir
        val imageFile = File(filesDir, "detected_image_${System.currentTimeMillis()}.jpg")
        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return imageFile
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}