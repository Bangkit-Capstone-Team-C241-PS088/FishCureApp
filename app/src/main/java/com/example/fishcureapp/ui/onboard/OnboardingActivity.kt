package com.example.fishcureapp.ui.onboard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.fishcureapp.R
import com.example.fishcureapp.ui.onboard.adapter.OnboardListAdapter

class OnboardingActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onbarding)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = OnboardListAdapter(this)
        viewPager.adapter = adapter


    }
}