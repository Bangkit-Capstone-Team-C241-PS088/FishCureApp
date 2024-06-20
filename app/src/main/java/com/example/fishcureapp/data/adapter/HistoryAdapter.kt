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
import com.example.fishcureapp.data.network.response.SavedDataHistory
import com.example.fishcureapp.databinding.ItemHistoryBinding
import com.example.fishcureapp.ui.history.detailhistory.DetailHistoryActivity

class HistoryAdapter : ListAdapter<SavedDataHistory, HistoryAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(history: SavedDataHistory) {
            binding.apply {

                tvDateHistory.text = history.dateTime
                tvDetectionCategory.text = history.disease_name
                tvDetection.text = "Akurasi: ${history.akurasi}%"

                Glide.with(itemView.context)
                    .load(history.image)
                    .placeholder(R.drawable.bg_image_loading)
                    .into(imgItemHistory)

            }

            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, DetailHistoryActivity::class.java)
                intentDetail.putExtra(DetailHistoryActivity.EXTRA_HISTORY, history)
                itemView.context.startActivity(intentDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DiffCallback: DiffUtil.ItemCallback<SavedDataHistory> =
            object : DiffUtil.ItemCallback<SavedDataHistory>() {

                override fun areItemsTheSame(oldItem: SavedDataHistory, newItem: SavedDataHistory): Boolean {
                    return oldItem.disease_name == newItem.disease_name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: SavedDataHistory, newItem: SavedDataHistory): Boolean {
                    return oldItem == newItem
                }
            }
    }
}