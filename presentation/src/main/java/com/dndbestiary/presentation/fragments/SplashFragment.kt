package com.dndbestiary.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.dndbestiary.R
import com.dndbestiary.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private var fragmentCallback: FragmentCallback? = null

    fun setFragmentCallback(callback: FragmentCallback) {
        fragmentCallback = callback
    }

    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashBinding.inflate(inflater)

        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.imageView2.startAnimation(fadeInAnimation)
        binding.imageView2.visibility = View.VISIBLE

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val delayMillis = 1500
        Handler(Looper.getMainLooper()).postDelayed({
            fragmentCallback?.sendCallback("openMainFragment", null)
        }, delayMillis.toLong())
    }

}