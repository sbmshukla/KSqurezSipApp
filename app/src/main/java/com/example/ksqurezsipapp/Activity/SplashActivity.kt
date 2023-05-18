package com.example.ksqurezsipapp.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.ksqurezsipapp.Data.Helper.MyApplications
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.ActivitySplashScreenBinding
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    lateinit var myShared : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)
        myShared = this.getSharedPreferences("pref_file", Context.MODE_PRIVATE)


        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        binding.ivSplash.startAnimation(anim)

        Handler().postDelayed({


            val checkLogin: Boolean=myShared.getBoolean("isLogin",false)


            val i: Intent = if (checkLogin) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }

            startActivity(i)
            finish()
        }, 2000)

    }
}