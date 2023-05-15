package com.example.ksqurezsipapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ksqurezsipapp.Adapter.ContactAdapter
import com.example.ksqurezsipapp.Model.ContactModel
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {
    lateinit var binding: FragmentContactsBinding

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var contactList : List<ContactModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_contacts, container, false)


        binding.rvContacts.layoutManager=LinearLayoutManager(context)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ContactModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        data.add(ContactModel("1","Shubham Shukla","+91 81286 18176","M",""))
        data.add(ContactModel("2","Falguni","+91 95127 89775","F",""))
        data.add(ContactModel("3","Nagedra","+91 80000 90329","M",""))
        data.add(ContactModel("4","Nikita","+91 88540 55379","F",""))
        data.add(ContactModel("4","Falguni Private","+91 63545 34589","",""))


        //just for fun
        data.add(ContactModel("1","Shubham Shukla","+91 81286 18176","M",""))
        data.add(ContactModel("2","Falguni","+91 95127 89775","F",""))
        data.add(ContactModel("3","Nagedra","+91 80000 90329","M",""))
        data.add(ContactModel("4","Nikita","+91 88540 55379","F",""))
        data.add(ContactModel("4","Falguni Private","+91 63545 34589","",""))
        data.add(ContactModel("1","Shubham Shukla","+91 81286 18176","M",""))
        data.add(ContactModel("2","Falguni","+91 95127 89775","F",""))
        data.add(ContactModel("3","Nagedra","+91 80000 90329","M",""))
        data.add(ContactModel("4","Nikita","+91 88540 55379","F",""))
        data.add(ContactModel("4","Falguni Private","+91 63545 34589","",""))
        data.add(ContactModel("1","Shubham Shukla","+91 81286 18176","M",""))
        data.add(ContactModel("2","Falguni","+91 95127 89775","F",""))
        data.add(ContactModel("3","Nagedra","+91 80000 90329","M",""))
        data.add(ContactModel("4","Nikita","+91 88540 55379","F",""))
        data.add(ContactModel("4","Falguni Private","+91 63545 34589","",""))


        // This will pass the ArrayList to our Adapter
        val adapter = ContactAdapter(data)

        // Setting the Adapter with the recyclerview
        binding.rvContacts.adapter = adapter
        return binding.root
    }




}