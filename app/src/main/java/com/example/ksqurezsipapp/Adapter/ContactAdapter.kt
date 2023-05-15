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

    inner class ViewHolder(val binding: ContactCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mList[position]){
                binding.tvName.text=this.name
                binding.tvNumber.text=this.mobile_no
                if (this.gender == "F")
                {
                    binding.tvContactProfile.setImageResource(R.drawable.ic_women)
                }
                else if (this.gender=="M"){
                    binding.tvContactProfile.setImageResource(R.drawable.ic_avatar)
                }
                else{
                    binding.tvContactProfile.setImageResource(R.drawable.ic_unknown_user)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}