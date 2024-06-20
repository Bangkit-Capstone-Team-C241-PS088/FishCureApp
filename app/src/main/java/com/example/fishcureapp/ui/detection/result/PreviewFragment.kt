package com.example.fishcureapp.ui.detection.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fishcureapp.databinding.FragmentImagePreviewBinding

class PreviewFragment : Fragment() {

    private lateinit var binding: FragmentImagePreviewBinding
    private val navArgs by navArgs<PreviewFragmentArgs>()
    private var imgCapture: ImageCapture? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImagePreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgResult = navArgs.imageResult

        // Display image using Glide
        Glide.with(requireActivity())
            .load(if (imgResult.isFromCamera) imgResult.imageBitmap else imgResult.imageUri)
            .into(binding.ivPreviewImage)

        binding.btnUpload.setOnClickListener {
            // Navigate to DetectionResultFragment with imageResult
            val action = PreviewFragmentDirections.actionPreviewFragmentToResultFragment(imgResult)
            findNavController().navigate(action)
        }
    }
}
