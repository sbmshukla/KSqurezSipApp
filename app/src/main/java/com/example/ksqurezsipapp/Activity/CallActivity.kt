package com.example.ksqurezsipapp.Activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ksqurezsipapp.R
import com.example.ksqurezsipapp.databinding.ActivityCallBinding
import com.example.ksqurezsipapp.databinding.ActivitySplashScreenBinding
import org.linphone.core.Account
import org.linphone.core.Call
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.Factory
import org.linphone.core.MediaEncryption
import org.linphone.core.RegistrationState
import org.linphone.core.TransportType

class CallActivity : AppCompatActivity() {

    lateinit var binding: ActivityCallBinding
    private lateinit var core: Core
    lateinit var myShared: SharedPreferences
    lateinit var sipUserId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_call)

        myShared = this.getSharedPreferences("pref_file", Context.MODE_PRIVATE)
        val username = myShared.getString("username","default")
        val password = myShared.getString("password","default")
        val domain = myShared.getString("domain", "default")

        val sipAddress: String? = intent.getStringExtra("sipAddress")

        sipUserId= "sip:$sipAddress@$domain"

        binding.tvSipId.text = sipUserId

        // We will need the RECORD_AUDIO permission for video call
        if (packageManager.checkPermission(Manifest.permission.RECORD_AUDIO, packageName) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 0)
            return
        }

        val factory = Factory.instance()
        factory.setDebugMode(true, "Call Sip")
        core = factory.createCore(null, null, this)

        val authInfo = Factory.instance().createAuthInfo(username!!, null, password, null, null, domain, null)

        val params = core.createAccountParams()
        val identity = Factory.instance().createAddress("sip:$username@$domain")
        params.identityAddress = identity

        val address = Factory.instance().createAddress("sip:$domain")
        address?.transport = TransportType.Udp
        params.serverAddress = address
        params.registerEnabled = true
        val account = core.createAccount(params)

        core.addAuthInfo(authInfo)
        core.addAccount(account)

        core.defaultAccount = account
        core.addListener(coreListener)
        core.start()

        outgoingCall()

        binding.ivPutCall.setOnClickListener {
            hangUp()
        }

        binding.ivPause.setOnClickListener {
            pauseOrResume()
        }

    }

    private val coreListener = object: CoreListenerStub() {
        override fun onCallStateChanged(core: Core, call: Call, state: Call.State?, message: String) {
            // This function will be called each time a call state changes,
            // which includes new incoming/outgoing calls
            binding.tvCallStatus.text = message

            when (state) {
                Call.State.OutgoingInit -> {
                    // First state an outgoing call will go through
                }
                Call.State.OutgoingProgress -> {
                    // Right after outgoing init
                }
                Call.State.OutgoingRinging -> {
                    // This state will be reached upon reception of the 180 RINGING
                }
                Call.State.Connected -> {
                    // When the 200 OK has been received
                }
                Call.State.StreamsRunning -> {
                    // This state indicates the call is active.
                    // You may reach this state multiple times, for example after a pause/resume
                    // or after the ICE negotiation completes
                    // Wait for the call to be connected before allowing a call update

                  /*  findViewById<Button>(org.linphone.core.R.id.toggle_video).isEnabled = true*/

                    // Only enable toggle camera button if there is more than 1 camera and the video is enabled
                    // We check if core.videoDevicesList.size > 2 because of the fake camera with static image created by our SDK (see below)
                    /*findViewById<Button>(org.linphone.core.R.id.toggle_camera).isEnabled = core.videoDevicesList.size > 2 && call.currentParams.videoEnabled()*/
                }
                Call.State.Paused -> {
                    // When you put a call in pause, it will became Paused
                    /*findViewById<Button>(org.linphone.core.R.id.pause).text = "Resume"
                    findViewById<Button>(org.linphone.core.R.id.toggle_video).isEnabled = false*/
                }
                Call.State.PausedByRemote -> {
                    // When the remote end of the call pauses it, it will be PausedByRemote
                }
                Call.State.Updating -> {
                    // When we request a call update, for example when toggling video
                }
                Call.State.UpdatedByRemote -> {
                    // When the remote requests a call update
                }
                Call.State.Released -> {
                    // Call state will be released shortly after the End state
                }
                Call.State.Error -> {
                }

                else -> {}
            }
        }
    }

    private fun outgoingCall() {
        // As for everything we need to get the SIP URI of the remote and convert it to an Address
        val remoteAddress = Factory.instance().createAddress(sipUserId)
        remoteAddress ?: return // If address parsing fails, we can't continue with outgoing call process

        // We also need a CallParams object
        // Create call params expects a Call object for incoming calls, but for outgoing we must use null safely
        val params = core.createCallParams(null)
        params ?: return // Same for params

        // We can now configure it
        // Here we ask for no encryption but we could ask for ZRTP/SRTP/DTLS
        params.mediaEncryption = MediaEncryption.None
        // If we wanted to start the call with video directly
        //params.enableVideo(true)

        // Finally we start the call
        core.inviteAddressWithParams(remoteAddress, params)
        // Call process can be followed in onCallStateChanged callback from core listener
    }

    private fun hangUp() {
        if (core.callsNb == 0) return

        // If the call state isn't paused, we can get it using core.currentCall
        val call = if (core.currentCall != null) core.currentCall else core.calls[0]
        call ?: return

        // Terminating a call is quite simple
        call.terminate()
        var intent = Intent(this@CallActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
        core.stop()
    }

    private fun pauseOrResume() {
        if (core.callsNb == 0) return
        val call = if (core.currentCall != null) core.currentCall else core.calls[0]
        call ?: return

        if (call.state != Call.State.Paused && call.state != Call.State.Pausing) {
            // If our call isn't paused, let's pause it
            call.pause()
        } else if (call.state != Call.State.Resuming) {
            // Otherwise let's resume it
            call.resume()
        }
    }


}