package com.example.ksqurezsipapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.ActivityCallBinding
import com.example.ksqurezsipapp.databinding.ActivitySplashScreenBinding

class CallActivity : AppCompatActivity() {
    lateinit var binding: ActivityCallBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_call)

        binding.ivPutCall.setOnClickListener { finish() }

    }
}