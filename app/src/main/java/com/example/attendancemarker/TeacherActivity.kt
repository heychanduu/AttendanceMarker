package com.example.attendancemarker

import android.graphics.Bitmap
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.attendancemarker.databinding.ActivityTeacherBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener

class TeacherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeacherBinding
    private lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wifiManager = getSystemService(WIFI_SERVICE) as WifiManager

        binding.btnStartHotspot.setOnClickListener {
            Dexter.withContext(this)
                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        startHotspotAndGenerateQR()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(this@TeacherActivity, "Permission required", Toast.LENGTH_SHORT).show()
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

    private fun startHotspotAndGenerateQR() {
        val ssid = "Attendance_${System.currentTimeMillis()}"
        val password = "attend123"

        val config = WifiConfiguration().apply {
            SSID = ssid
            preSharedKey = password
            allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
        }

        wifiManager.isWifiEnabled = true
        Toast.makeText(this, "Hotspot Started: $ssid", Toast.LENGTH_LONG).show()

        val qrText = "WIFI:S:$ssid;T:WPA;P:$password;;"
        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix = multiFormatWriter.encode(qrText, BarcodeFormat.QR_CODE, 200, 200)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

        binding.ivQrCode.setImageBitmap(bitmap)
        binding.ivQrCode.visibility = android.view.View.VISIBLE

        Toast.makeText(this, "Share QR via WhatsApp manually", Toast.LENGTH_LONG).show()
    }
}