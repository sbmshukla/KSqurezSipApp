package com.example.ksqurezsipapp.Activity

import android.Manifest
import android.content.Context
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
        val sipDomain = myShared.getString("domain", "default")

        val sipAddress: String? = intent.getStringExtra("sipAddress")

        binding.tvSipId.text = "sip:$sipAddress@$sipDomain"

        sipUserId= "sip:$sipAddress@$sipDomain"

        // We will need the RECORD_AUDIO permission for video call
        if (packageManager.checkPermission(Manifest.permission.RECORD_AUDIO, packageName) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 0)
            return
        }
    }

    private val coreListener = object: CoreListenerStub() {
       /* override fun onAccountRegistrationStateChanged(core: Core, account: Account, state: RegistrationState?, message: String) {
            findViewById<TextView>(org.linphone.core.R.id.registration_status).text = message

            if (state == RegistrationState.Failed) {
                findViewById<Button>(org.linphone.core.R.id.connect).isEnabled = true
            } else if (state == RegistrationState.Ok) {
                findViewById<LinearLayout>(org.linphone.core.R.id.register_layout).visibility = View.GONE
                findViewById<RelativeLayout>(org.linphone.core.R.id.call_layout).visibility = View.VISIBLE
            }
        }*/

        override fun onCallStateChanged(
            core: Core,
            call: Call,
            state: Call.State?,
            message: String
        ) {
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
                    binding.ivPause.isEnabled = true

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
                    binding.ivPause.isEnabled = false
                    binding.ivPutCall.isEnabled = false
                }
                Call.State.Error -> {

                }

                else -> {}
            }
        }
    }

    private fun outgoingCall() {
        // As for everything we need to get the SIP URI of the remote and convert it to an Address
        val remoteSipUri = sipUserId
        val remoteAddress = Factory.instance().createAddress(remoteSipUri)
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
    }

    private fun toggleVideo() {
        if (core.callsNb == 0) return
        val call = if (core.currentCall != null) core.currentCall else core.calls[0]
        call ?: return

        // We will need the CAMERA permission for video call
        if (packageManager.checkPermission(Manifest.permission.CAMERA, packageName) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 0)
            return
        }

        // To update the call, we need to create a new call params, from the call object this time
        val params = core.createCallParams(call)
        // Here we toggle the video state (disable it if enabled, enable it if disabled)
        // Note that we are using currentParams and not params or remoteParams
        // params is the object you configured when the call was started
        // remote params is the same but for the remote
        // current params is the real params of the call, resulting of the mix of local & remote params
        params?.enableVideo(!call.currentParams.videoEnabled())
        // Finally we request the call update
        call.update(params)

        // Note that when toggling off the video, TextureViews will keep showing the latest frame displayed
    }

    private fun toggleCamera() {
        // Currently used camera
        val currentDevice = core.videoDevice

        // Let's iterate over all camera available and choose another one
        for (camera in core.videoDevicesList) {
            // All devices will have a "Static picture" fake camera, and we don't want to use it
            if (camera != currentDevice && camera != "StaticImage: Static picture") {
                core.videoDevice = camera
                break
            }
        }
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