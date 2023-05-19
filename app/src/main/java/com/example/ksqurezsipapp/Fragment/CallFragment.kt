package com.example.ksqurezsipapp.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.BaseInputConnection
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ksqurezsipapp.Activity.CallActivity
import com.example.ksqurezsipapp.Activity.IncomingCallActivity
import com.example.ksqurezsipapp.Activity.MainActivity
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.FragmentCallBinding


class CallFragment : Fragment() {

    lateinit var binding: FragmentCallBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_call, container, false)


        //keep soft keyboard hide
        binding.editTextPhone.requestFocus()
        binding.editTextPhone.showSoftInputOnFocus=false

        binding.btnZero.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_0)
        }
        binding.btnOne.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_1)
        }
        binding.btnTwo.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_2)
        }
        binding.btnThree.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_3)
        }
        binding.btnFour.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_4)
        }
        binding.btnFive.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_5)
        }
        binding.btnSix.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_6)
        }
        binding.btnSeven.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_7)
        }
        binding.btnEight.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_8)
        }
        binding.btnNine.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_9)
        }
        binding.btnStar.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_STAR)
        }
        binding.btnDot.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_NUMPAD_DOT)
        }
        binding.btnBackslash.setOnClickListener {
            simulateKeyPress(KeyEvent.KEYCODE_DEL)
        }


        binding.ivMakeCall.setOnClickListener {
//            val i = Intent(context, CallActivity::class.java)
            val i = Intent(context, CallActivity::class.java)
            i.putExtra("sipAddress",binding.editTextPhone.text.toString())
            i.putExtra("sipDomain",binding.editTextPhone.text.toString())
            // on below line we are
            // starting a new activity.
            startActivity(i)
            binding.editTextPhone.text.clear()

        }

        return binding.root
    }



    private fun simulateKeyPress(key: Int) {
        val activity = context as Activity?
        activity!!.window.decorView.rootView
        val inputConnection = BaseInputConnection(activity.window.decorView.rootView, true)
        val downEvent = KeyEvent(KeyEvent.ACTION_DOWN, key)
        val upEvent = KeyEvent(KeyEvent.ACTION_UP, key)
        inputConnection.sendKeyEvent(downEvent)
        inputConnection.sendKeyEvent(upEvent)
    }


}