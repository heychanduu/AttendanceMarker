package com.example.attendancemarker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.attendancemarker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTeacherLogin.setOnClickListener {
            startActivity(Intent(this, TeacherActivity::class.java))
        }

        binding.btnStudentLogin.setOnClickListener {
            startActivity(Intent(this, StudentActivity::class.java))
        }
    }
}