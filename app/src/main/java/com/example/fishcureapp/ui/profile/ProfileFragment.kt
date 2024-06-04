package com.example.fishcureapp.ui.profile

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.fishcureapp.data.factory.ViewModelFactory
import com.example.fishcureapp.databinding.FragmentProfileBinding
import com.example.fishcureapp.ui.auth.login.LoginActivity


class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    companion object {
        fun newInstance(email: String): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            args.putString("email", email)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val email = sharedPref.getString("email", "") // Retrieve email from SharedPreference
        binding.etEmail.setText(email)

        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            viewModel.logout()
            true
        }



        binding.btnReset.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            if (email.isNotEmpty() && newPassword.isNotEmpty()) {
                viewModel.resetPassword(email, newPassword)
            } else {
                Toast.makeText(requireContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.resetPasswordResult.observe(requireActivity()) { result ->
            if (result.success) {
                Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "Failed to update password: ${result.error}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}


