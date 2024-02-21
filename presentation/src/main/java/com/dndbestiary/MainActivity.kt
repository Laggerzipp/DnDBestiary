package com.dndbestiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.dndbestiary.databinding.ActivityMainBinding
import com.dndbestiary.libraryfragment.LibraryFragment
import com.dndbestiary.potionfragment.PotionFragment
import com.dndbestiary.mainfragment.MainFragment
import com.dndbestiary.splashfragment.SplashFragment
import com.domain.DomainPotion
import com.google.gson.Gson

class MainActivity : AppCompatActivity(),FragmentCallback {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    override fun sendCallback(callback: String, potion: DomainPotion?): Boolean {
        val fragment: Fragment
        val bundle = Bundle()
        val potionString = Gson().toJson(potion)
        when (callback) {
            "openSplashFragment" -> {
                fragment = SplashFragment.newInstance()
                fragment.setFragmentCallback(this)
                openFragment(fragment, false)
            }
            "openMainFragment" -> {
                binding.btmNav.visibility = View.VISIBLE
                binding.toolbar.visibility = View.VISIBLE
                supportActionBar?.setDisplayHomeAsUpEnabled(false)

                fragment = MainFragment.newInstance()
                fragment.setFragmentCallback(this)
                openFragment(fragment, false)
            }
            "openPotionFragment" -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                fragment = PotionFragment.newInstance()
                fragment.setFragmentCallback(this)
                bundle.putString("potion", potionString)
                fragment.arguments = bundle
                openFragment(fragment, true)
            }
            "openLibraryFragment" -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                fragment = LibraryFragment.newInstance()
                fragment.setFragmentCallback(this)
                openFragment(fragment, true)
            }
            else -> return false
        }
        return true
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

    private fun setupUI(){
        setSupportActionBar(binding.toolbar)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val fragmentManager = supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0) {
                    fragmentManager.popBackStack()
                    if(fragmentManager.backStackEntryCount - 1 == 0){
                        supportActionBar?.setDisplayHomeAsUpEnabled(false)
                        binding.btmNav.selectedItemId = R.id.catalog
                    }
                }
                else {
                    finish()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onBackPressed()
    }
}