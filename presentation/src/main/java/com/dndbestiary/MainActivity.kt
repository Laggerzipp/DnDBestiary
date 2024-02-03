package com.dndbestiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dndbestiary.databinding.ActivityMainBinding
import com.dndbestiary.libraryfragment.LibraryFragment
import com.dndbestiary.searchfragment.PotionFragment
import com.hfad.data.retrofit.Potion
import mainfragment.MainFragment

class MainActivity : AppCompatActivity(),FragmentCallback {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btmNav.selectedItemId = R.id.catalog
        sendCallback("openMainFragment", null)

        binding.btmNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.catalog -> sendCallback("openMainFragment", null)
                R.id.favorites -> sendCallback("openLibraryFragment", null)
                else -> true
            }
        }
    }

    override fun sendCallback(callback: String, potion: Potion?): Boolean {
        val fragment: Fragment
        if(callback == "openMainFragment"){
            fragment = MainFragment.newInstance()
            fragment.setFragmentCallback(this)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frMain, fragment)
                .commit()
            return true
        }
        if(callback == "openPotionFragment"){
            fragment = PotionFragment.newInstance()
            fragment.setFragmentCallback(this)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frMain, fragment)
                .addToBackStack(null)
                .commit()
            return true
        }
        if(callback == "openLibraryFragment"){
            fragment = LibraryFragment.newInstance()
            fragment.setFragmentCallback(this)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frMain, fragment)
                .commit()
            return true
        }
        return false
    }
}