package com.example.fishcureapp.ui.article

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishcureapp.data.factory.ViewModelFactory
import com.example.fishcureapp.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val articleAdapter = ArticleAdapter()

    private val viewModel: ArticleViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvArticle.adapter = articleAdapter

        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = articleAdapter
        }

        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", "")

        email?.let {
            viewModel.getArticles(it)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.articleResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { articles ->
                    articleAdapter.submitList(articles)
                },
                onFailure = { error ->
                    Toast.makeText(requireContext(), "Failed to fetch articles: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}