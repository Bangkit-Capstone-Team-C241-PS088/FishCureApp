package com.example.fishcureapp.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fishcureapp.databinding.FragmentHomeBinding
import com.example.fishcureapp.ui.detection.DetectActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "")

        binding.tvGreetName.text = email

        binding.btnTry.setOnClickListener{
            val intent = Intent(requireContext(), DetectActivity::class.java)
            startActivity(intent)
        }

        binding.ivHome.setOnClickListener{
            val intent = Intent(requireContext(), DetectActivity::class.java)
            startActivity(intent)
        }

    }




}