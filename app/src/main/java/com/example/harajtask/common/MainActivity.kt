package com.example.harajtask.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.harajtask.databinding.ActivityMainBinding
import com.example.harajtask.di.injector

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injector.inject(this)
        overridePendingTransition(0, 0)
    }
}
