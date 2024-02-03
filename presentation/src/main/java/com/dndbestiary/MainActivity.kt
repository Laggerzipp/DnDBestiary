package com.dndbestiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dndbestiary.databinding.ActivityMainBinding
import mainfragment.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btmNav.selectedItemId = R.id.catalog

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frMain, MainFragment.newInstance())
            .commit()
    }
}