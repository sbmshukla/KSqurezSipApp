package com.example.ksqurezsipapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.ActivityIncomingCallBinding
import com.example.ksqurezsipapp.databinding.ActivityLoginBinding

class IncomingCallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIncomingCallBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_incoming_call)


        binding.lottiePickCall.setOnClickListener {
            var i= Intent(this@IncomingCallActivity, CallActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.lottiePutCall.setOnClickListener {

            finish()
        }

    }
}