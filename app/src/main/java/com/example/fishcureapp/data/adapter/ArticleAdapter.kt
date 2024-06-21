package com.example.fishcureapp.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fishcureapp.R
import com.example.fishcureapp.data.local.model.Article
import com.example.fishcureapp.data.network.response.DataArticle
import com.example.fishcureapp.databinding.ItemArticleBinding
import com.example.fishcureapp.ui.article.detailarticle.DetailArticleActivity

class ArticleAdapter: ListAdapter<DataArticle, ArticleAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding : ItemArticleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: DataArticle) {
            binding.apply {
                tvTitleArticle.text = article.title
                tvDescArticle.text = article.article.values.joinToString("\n")

                Glide.with(itemView.context)
                    .load(article.image)
                    .placeholder(R.drawable.bg_image_loading)
                    .into(imgItemArticle)
            }
            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, DetailArticleActivity::class.java)
                intentDetail.putExtra(DetailArticleActivity.EXTRA_ARTICLE, article)
                itemView.context.startActivity(intentDetail)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<DataArticle> =
            object : DiffUtil.ItemCallback<DataArticle>() {


                override fun areItemsTheSame(oldItem: DataArticle, storyItem: DataArticle): Boolean {
                    return oldItem.id_article == storyItem.id_article
                }


                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: DataArticle, storyItem: DataArticle): Boolean {
                    return oldItem == storyItem
                }
            }
    }

}