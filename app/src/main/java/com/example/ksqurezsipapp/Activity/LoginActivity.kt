package com.example.ksqurezsipapp.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ksqurezsipapp.Data.Helper.MyApplicationPreference
import com.example.ksqurezsipapp.Data.Helper.MyApplications
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.ActivityLoginBinding
import org.linphone.core.Account
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.Factory
import org.linphone.core.RegistrationState
import org.linphone.core.TransportType
import org.linphone.core.tools.Log

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var core: Core
    var userVerified: Boolean = true
    var port:String=""

    lateinit var myShared : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val factory = Factory.instance()
        factory.setDebugMode(true, "Call Sip")
        core = factory.createCore(null, null, this)

        myShared = this.getSharedPreferences("pref_file", Context.MODE_PRIVATE)
        editor =myShared.edit()

        binding.btnLogin.setOnClickListener {

            if (binding.username.text.isEmpty()) {
                binding.username.error = "Enter valid username !"
                userVerified = false
            } else {
                userVerified = true
            }
            if (binding.password.text.isEmpty()) {
                binding.password.error = "Enter valid password !"
                userVerified = false
            } else {
                userVerified = true
            }
            if (binding.domain.text.isEmpty()) {
                binding.domain.error = "Enter valid domain !"
                userVerified = false
            } else {
                userVerified = true
            }

            if (userVerified) {
                login()
            }
        }
    }

    private fun login() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        val domain = binding.domain.text.toString()
        // Get the transport protocol to use.
        // TLS is strongly recommended
        // Only use UDP if you don't have the choice
        val transportType = if (binding.rbTcp.isChecked) {
            port="tcp"
            TransportType.Tcp
        } else if (binding.rbTls.isChecked) {
            port="tls"
            TransportType.Tls
        } else if (binding.rbUdp.isChecked) {
            port="udp"
            TransportType.Udp
        } else {
            port="udp"
            TransportType.Udp
        }

        editor.putString("username",username)
        editor.putString("password",password)
        editor.putString("domain",domain)
        editor.putString("transportType",port)
        editor.putBoolean("isLogin",true)

        // To configure a SIP account, we need an Account object and an AuthInfo object
        // The first one is how to connect to the proxy server, the second one stores the credentials

        // The auth info can be created from the Factory as it's only a data class
        // userID is set to null as it's the same as the username in our case
        // ha1 is set to null as we are using the clear text password. Upon first register, the hash will be computed automatically.
        // The realm will be determined automatically from the first register, as well as the algorithm
        val authInfo =
            Factory.instance().createAuthInfo(username, null, password, null, null, domain, null)

        // Account object replaces deprecated ProxyConfig object
        // Account object is configured through an AccountParams object that we can obtain from the Core
        val accountParams = core.createAccountParams()

        // A SIP account is identified by an identity address that we can construct from the username and domain
        val identity = Factory.instance().createAddress("sip:$username@$domain")
        accountParams.identityAddress = identity

        // We also need to configure where the proxy server is located
        val address = Factory.instance().createAddress("sip:$domain")
        // We use the Address object to easily set the transport protocol
        address?.transport = transportType
        accountParams.serverAddress = address
        // And we ensure the account will start the registration process
        accountParams.registerEnabled = true

        // Now that our AccountParams is configured, we can create the Account object
        val account = core.createAccount(accountParams)

        // Now let's add our objects to the Core
        core.addAuthInfo(authInfo)
        core.addAccount(account)

        // Also set the newly added account as default
        core.defaultAccount = account

        // Allow account to be removed


        // To be notified of the connection status of our account, we need to add the listener to the Core
        core.addListener(coreListener)
        // We can also register a callback on the Account object
        account.addListener { _, state, message ->
            // There is a Log helper in org.linphone.core.tools package
            Log.i("[Account] Registration state changed: $state, $message")
        }

        // Finally we need the Core to be started for the registration to happen (it could have been started before)
        core.start()
    }

    // Create a Core listener to listen for the callback we need
    // In this case, we want to know about the account registration status
    private val coreListener = object : CoreListenerStub() {
        override fun onAccountRegistrationStateChanged(
            core: Core,
            account: Account,
            state: RegistrationState,
            message: String
        ) {
            // If account has been configured correctly, we will go through Progress and Ok states
            // Otherwise, we will be Failed.
//            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            if (state == RegistrationState.Failed) {
                binding.progressLogin.visibility = View.GONE
                binding.btnLogin.visibility = View.VISIBLE
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
            } else if (state == RegistrationState.Ok) {
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                //Saving in shared preference

                editor.apply()
                editor.commit() // shared preference save

                val intent = Intent(this@LoginActivity, MainActivity::class.java)



                // on below line we are
                // starting a new activity.
                startActivity(intent)
            } else if (state == RegistrationState.Progress) {
                binding.progressLogin.visibility = View.VISIBLE
                binding.btnLogin.visibility = View.GONE
                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
            }

        }
    }
}