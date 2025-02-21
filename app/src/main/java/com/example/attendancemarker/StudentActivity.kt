package com.example.attendancemarker

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.attendancemarker.databinding.ActivityStudentBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener

class StudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentBinding
    private lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager

        binding.btnScanQr.setOnClickListener {
            Dexter.withContext(this)
                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        connectToTeacherHotspot("Attendance_123", "attend123")
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(this@StudentActivity, "Permission required", Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: com.karumi.dexter.listener.PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token?.continuePermissionRequest()
                    }
                }).check()
        }
    }

    private fun connectToTeacherHotspot(ssid: String, password: String) {
        val wifiConfig = android.net.wifi.WifiConfiguration().apply {
            SSID = "\"$ssid\""
            preSharedKey = "\"$password\""
        }

        wifiManager.addNetwork(wifiConfig)
        wifiManager.enableNetwork(wifiManager.addNetwork(wifiConfig), true)
        wifiManager.reconnect()

        val currentSsid = wifiManager.connectionInfo.ssid
        if (currentSsid == "\"$ssid\"") {
            Toast.makeText(this, "Attendance Marked!", Toast.LENGTH_SHORT).show()
            wifiManager.disconnect()
        } else {
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show()
        }
    }
}