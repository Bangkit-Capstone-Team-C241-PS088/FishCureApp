package com.example.fishcureapp.ui.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishcureapp.data.adapter.HistoryAdapter
import com.example.fishcureapp.data.factory.ViewModelFactory
import com.example.fishcureapp.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val historyAdapter = HistoryAdapter()

    private val viewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHistory.adapter = historyAdapter

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = historyAdapter
        }

        observeViewModel()

        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "")

        email?.let {
            viewModel.getHistory(it)
        }

    }

    private fun observeViewModel() {
        viewModel.articleResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { articles ->
                    historyAdapter.submitList(articles)
                },
                onFailure = { error ->
                    Toast.makeText(requireContext(), "Failed to fetch histories: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}