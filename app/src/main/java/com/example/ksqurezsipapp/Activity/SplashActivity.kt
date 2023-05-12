package com.example.ksqurezsipapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.ActivitySplashScreenBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        var anim=AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        binding.ivSplash.startAnimation(anim)

        Handler().postDelayed({
//            val i = Intent(this@SplashActivity, LoginActivity::class.java)
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 2000)

    }
}