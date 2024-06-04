package com.example.fishcureapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.fishcureapp.ui.auth.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var duration: Long = 1500 //1.5 seconds

    private val mRunnable: Runnable = Runnable {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        mDelayHandler = Handler()

        mDelayHandler!!.postDelayed(mRunnable,duration)


    }
}
