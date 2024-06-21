package com.example.fishcureapp.ui.article.detailarticle

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.fishcureapp.data.network.response.DataArticle
import com.example.fishcureapp.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetailData()
    }

    private fun setDetailData() {


        val article = intent.getParcelableExtra<DataArticle>(EXTRA_ARTICLE) as DataArticle

       // showLoading(false)
        binding.apply {
            article?.let {

                tvTitleArticle.text = article.title
                tvDescArticle.text = article.article.values.joinToString("\n")
                tvDateArticle.text = article.date_time
                tvProfile.text = article.writer
                tvLink.text = article.source

                Glide.with(this@DetailArticleActivity)
                    .load(article.image)
                    .into(binding.imgArticle)
            }
        }

        Log.e(ContentValues.TAG, "showSelectedArticle: ${article.title}")
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }


}