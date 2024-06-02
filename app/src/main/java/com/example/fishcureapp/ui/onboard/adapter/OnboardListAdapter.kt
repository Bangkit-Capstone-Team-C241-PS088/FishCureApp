package com.example.fishcureapp.ui.onboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fishcureapp.data.local.model.Onboard
import com.example.fishcureapp.databinding.ItemOnboardBinding

class OnboardListAdapter(private val onboardingList: List<Onboard>) :
    RecyclerView.Adapter<OnboardListAdapter.OnboardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardViewHolder {
        val binding = ItemOnboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardViewHolder, position: Int) {
        holder.bind(onboardingList[position])
    }

    override fun getItemCount(): Int = onboardingList.size

    inner class OnboardViewHolder(private val binding: ItemOnboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onboard: Onboard) {
            binding.imgOnboard.setImageResource(onboard.img)
            binding.tvDescOnboard.text = onboard.description
        }
    }
}