package com.dndbestiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dndbestiary.databinding.ActivityMainBinding
import com.dndbestiary.libraryfragment.LibraryFragment
import com.dndbestiary.searchfragment.PotionFragment
import com.dndbestiary.mainfragment.MainFragment
import com.dndbestiary.splashfragment.SplashFragment
import com.google.gson.Gson
import com.hfad.data.retrofit.Potion

class MainActivity : AppCompatActivity(),FragmentCallback {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toolbar.visibility = View.GONE
            btmNav.visibility = View.GONE
            btmNav.selectedItemId = R.id.catalog
            btmNav.itemIconTintList = null
        }

        sendCallback("openSplashFragment", null)

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
        val bundle = Bundle()
        val potionString = Gson().toJson(potion)
        if(callback == "openSplashFragment"){
            fragment = SplashFragment.newInstance()
            fragment.setFragmentCallback(this)
            openFragment(fragment, false)
            return true
        }
        if(callback == "openMainFragment"){
            binding.btmNav.visibility = View.VISIBLE
            binding.toolbar.visibility = View.VISIBLE

            fragment = MainFragment.newInstance()
            fragment.setFragmentCallback(this)
            openFragment(fragment,false)
            return true
        }
        if(callback == "openPotionFragment"){
            fragment = PotionFragment.newInstance()
            fragment.setFragmentCallback(this)
            bundle.putString("potion", potionString)
            fragment.arguments = bundle
            openFragment(fragment,true)
            return true
        }
        if(callback == "openLibraryFragment"){
            fragment = LibraryFragment.newInstance()
            fragment.setFragmentCallback(this)
            openFragment(fragment,true)
            return true
        }
        return false
    }

    private fun openFragment(fragment: Fragment, tag:Boolean){
        if(!tag){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frMain, fragment)
                .commit()
        }
        else{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frMain, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}