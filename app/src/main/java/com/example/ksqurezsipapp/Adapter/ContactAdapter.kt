package com.example.ksqurezsipapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ksqurezsipapp.Model.ContactModel
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.ContactCardBinding

class ContactAdapter(private val mList: List<ContactModel>): RecyclerView.Adapter<ContactAdapter.ViewHolder>()  {
    lateinit var  binding : ContactCardBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        binding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.contact_card,parent,false)


        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
}