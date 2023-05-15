package com.example.ksqurezsipapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ksqurezsipapp.Model.HistoryModel
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.HistoryCardBinding

class HistoryAdapter(private val historyList: List<HistoryModel>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: HistoryCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        with(holder){
            with(historyList[position]){

                binding.tvName.text=this.name
                binding.tvTime.text=this.callTime

                //call type (0-missed call,1-out going call,2-incoming call)
                if (this.callType=="0")
                {
                    binding.ivCallType.setImageResource(R.drawable.ic_call_missed)
                }
                else if (this.callType=="1")
                {
                    binding.ivCallType.setImageResource(R.drawable.ic_call_made)
                }
                else if (this.callType=="2")
                {
                    binding.ivCallType.setImageResource(R.drawable.ic_call_received)
                }

                //set profile image
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
        return historyList.size
    }
}