package com.example.ksqurezsipapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ksqurezsipapp.Adapter.ContactAdapter
import com.example.ksqurezsipapp.Adapter.HistoryAdapter
import com.example.ksqurezsipapp.Data.Model.ContactModel
import com.example.ksqurezsipapp.Data.Model.HistoryModel
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.FragmentContactsBinding
import com.example.ksqurezsipapp.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryBinding

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyList : List<HistoryModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_history, container, false)
        binding.rvCallHistory.layoutManager= LinearLayoutManager(context)

        val data = ArrayList<HistoryModel>()


        data.add(HistoryModel("1","Shubham Shukla","8128618176","M","0", "Today 14:42pm"))
        data.add(HistoryModel("2","Falguni","+91 95127 89775","F","1", "Today 12:00pm"))
        data.add(HistoryModel("3","Nagendra","+91 80000 90329","M","2", "Yesterday 10:00am"))


        //just for fun
        data.add(HistoryModel("1","Shubham Shukla","8128618176","M","0", "Today 14:42pm"))
        data.add(HistoryModel("2","Falguni","+91 95127 89775","F","1", "Today 12:00pm"))
        data.add(HistoryModel("3","Nagendra","+91 80000 90329","M","2", "Yesterday 10:00am"))
        data.add(HistoryModel("1","Shubham Shukla","8128618176","M","0", "Today 14:42pm"))
        data.add(HistoryModel("2","Falguni","+91 95127 89775","F","1", "Today 12:00pm"))
        data.add(HistoryModel("3","Nagendra","+91 80000 90329","M","2", "Yesterday 10:00am"))
        data.add(HistoryModel("1","Shubham Shukla","8128618176","M","0", "Today 14:42pm"))
        data.add(HistoryModel("2","Falguni","+91 95127 89775","F","1", "Today 12:00pm"))
        data.add(HistoryModel("3","Nagendra","+91 80000 90329","M","2", "Yesterday 10:00am"))
        data.add(HistoryModel("1","Shubham Shukla","8128618176","M","0", "Today 14:42pm"))
        data.add(HistoryModel("2","Falguni","+91 95127 89775","F","1", "Today 12:00pm"))
        data.add(HistoryModel("3","Nagendra","+91 80000 90329","M","2", "Yesterday 10:00am"))
        data.add(HistoryModel("1","Shubham Shukla","8128618176","M","0", "Today 14:42pm"))
        data.add(HistoryModel("2","Falguni","+91 95127 89775","F","1", "Today 12:00pm"))
        data.add(HistoryModel("3","Nagendra","+91 80000 90329","M","2", "Yesterday 10:00am"))
        data.add(HistoryModel("1","Shubham Shukla","8128618176","M","0", "Today 14:42pm"))
        data.add(HistoryModel("2","Falguni","+91 95127 89775","F","1", "Today 12:00pm"))
        data.add(HistoryModel("3","Nagendra","+91 80000 90329","M","2", "Yesterday 10:00am"))


        val adapter = HistoryAdapter(data)

        binding.rvCallHistory.adapter = adapter
        return binding.root
    }

}