package com.example.fishcureapp.ui.onboard.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fishcureapp.ui.onboard.OnboardingFragment1
import com.example.fishcureapp.ui.onboard.OnboardingFragment2
import com.example.fishcureapp.ui.onboard.OnboardingFragment3

class OnboardListAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragmentList = listOf(
        OnboardingFragment1(),
        OnboardingFragment2(),
        OnboardingFragment3()

    )

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}