package com.example.ksqurezsipapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {
    lateinit var binding: FragmentContactsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_contacts, container, false)


        return binding.root
    }




}