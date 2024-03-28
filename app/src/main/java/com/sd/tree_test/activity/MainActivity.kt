package com.sd.tree_test.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sd.tree_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}