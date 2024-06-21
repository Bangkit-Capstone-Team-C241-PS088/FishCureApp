package com.example.fishcureapp.ui.onboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fishcureapp.databinding.FragmentOnboarding1Binding
import com.example.fishcureapp.ui.auth.login.LoginActivity

class OnboardingFragment1: Fragment() {

    private lateinit var binding: FragmentOnboarding1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboarding1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSkip1.setOnClickListener{

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

        }

    }


}