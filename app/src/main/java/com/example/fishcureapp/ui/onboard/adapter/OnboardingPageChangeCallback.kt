package com.example.fishcureapp.ui.onboard.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2

class OnboardingPageChangeCallback(
    private val viewPager: ViewPager2,
    private val btnLeft: ImageView,
    private val btnRight: ImageView,
    private val btnOnboard: Button,
    private val pageSize:Int,
) : ViewPager2.OnPageChangeCallback() {
    private var selectedPosition = 0


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        setButtonVisibility()
    }

    override fun onPageSelected(position: Int) {
        selectedPosition = position
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    fun getSelectedPosition(): Int {
        return selectedPosition
    }

    fun setCurrentItem(position: Int) {
        viewPager.setCurrentItem(position, true)
    }

    private fun setButtonVisibility() {
        if (selectedPosition == pageSize - 1)
            btnRight.visibility = View.INVISIBLE
        else if (selectedPosition == 0)
            btnLeft.visibility = View.INVISIBLE
        else {
            btnLeft.visibility = View.VISIBLE
            btnRight.visibility = View.VISIBLE
        }

        if(selectedPosition == pageSize - 1)
            btnOnboard.visibility = View.VISIBLE
        else
            btnOnboard.visibility = View.INVISIBLE
    }
}