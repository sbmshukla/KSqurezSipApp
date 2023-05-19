package com.example.ksqurezsipapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ksqurezsipapp.Fragment.CallFragment
import com.example.ksqurezsipapp.Fragment.ContactsFragment
import com.example.ksqurezsipapp.Fragment.HistoryFragment
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadFragment(CallFragment())

        binding.apply {

            bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.item_dialer -> {
                        loadFragment(CallFragment())
                        true
                    }
                    R.id.item_history -> {
                        loadFragment(HistoryFragment())
                        true
                    }
                    R.id.item_contacts -> {
                        loadFragment(ContactsFragment())
                        true
                    }
                    else -> {
                        loadFragment(CallFragment())
                        true
                    }
                } }

            bottomNavigationView.setOnItemReselectedListener {  }

            toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            toggle.isDrawerIndicatorEnabled = true

            supportActionBar?.setDisplayHomeAsUpEnabled(true)


            appBar.ivDrawerMenu.setOnClickListener {
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }else{
                    drawerLayout.openDrawer(GravityCompat.START)
                }
            }

            appBar.tvPort.setOnClickListener {
                PopupMenu(this@MainActivity, it).apply {
                    setOnMenuItemClickListener { item ->
                        when (item?.itemId) {

                            R.id.network_tcp -> {
                                appBar.tvPort.setText(R.string.tcp)
                                true
                            }
                            R.id.network_tls -> {
                                appBar.tvPort.setText(R.string.tls)
                                true
                            }
                            R.id.network_udp -> {
                                appBar.tvPort.setText(R.string.udp)
                                true
                            }

                            else -> false
                        }
                    }
                    inflate(R.menu.network_tyes_menu)
                    show()
                }
            }


            navView.setNavigationItemSelectedListener {

                drawerLayout.openDrawer(GravityCompat.START)
                when (it.itemId) {
                    R.id.nav_setting -> {
                        Toast.makeText(this@MainActivity, "First Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_about -> {
                        Toast.makeText(this@MainActivity, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_logout -> {
                        Toast.makeText(this@MainActivity, "Third Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }

        }

    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

}