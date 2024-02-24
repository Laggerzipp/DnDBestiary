package com.dndbestiary.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dndbestiary.R
import com.dndbestiary.app.App
import com.dndbestiary.databinding.ActivityMainBinding
import com.dndbestiary.presentation.fragments.LibraryFragment
import com.dndbestiary.presentation.fragments.PotionFragment
import com.dndbestiary.presentation.fragments.MainFragment
import com.dndbestiary.presentation.fragments.SplashFragment
import com.domain.models.DomainPotion
import com.dndbestiary.presentation.fragments.FragmentCallback
import com.dndbestiary.presentation.viewmodel.MainViewModel
import com.dndbestiary.presentation.viewmodel.MainViewModelFactory
import com.google.gson.Gson
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentCallback {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComponent.inject(this)

        setupViewModel()
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
                binding.apply {
                    toolbar.visibility = View.VISIBLE
                    btmNav.visibility = View.VISIBLE
                }
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                fragment = MainFragment.newInstance()
                fragment.setFragmentCallback(this)

                when (supportFragmentManager.backStackEntryCount) {
                    1 -> {
                        supportFragmentManager.popBackStack()
                    }

                    2 -> {
                        supportFragmentManager.popBackStack()
                        supportFragmentManager.popBackStack()
                    }

                    else -> {
                        supportFragmentManager.popBackStack()
                        openFragment(fragment, false)
                    }
                }
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
                when (supportFragmentManager.backStackEntryCount) {
                    0 -> {
                        openFragment(fragment, true)
                    }

                    1 -> {
                        supportFragmentManager.popBackStack()
                        openFragment(fragment, true)
                    }

                    2 -> {
                        supportFragmentManager.popBackStack()
                    }
                }
            }

            else -> return false
        }
        return true
    }

    private fun openFragment(fragment: Fragment, tag: Boolean) {
        if (!tag) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frMain, fragment)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frMain, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        binding.apply {
            toolbar.visibility = View.GONE
            btmNav.menu.getItem(1).isEnabled = false
            btmNav.visibility = View.GONE
            btmNav.selectedItemId = R.id.catalog
            btmNav.itemIconTintList = null
        }

        sendCallback("openSplashFragment", null)

        binding.btmNav.setOnItemSelectedListener {
            when (it.itemId) {
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
                    if (fragmentManager.backStackEntryCount - 1 == 0) {
                        supportActionBar?.setDisplayHomeAsUpEnabled(false)
                        binding.btmNav.selectedItemId = R.id.catalog
                    }
                } else {
                    finish()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount - 1 == 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        super.onBackPressed()
    }
}