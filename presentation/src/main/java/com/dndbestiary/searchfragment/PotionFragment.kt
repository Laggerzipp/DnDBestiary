package com.dndbestiary.searchfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dndbestiary.FragmentCallback
import com.dndbestiary.databinding.FragmentSearchBinding
import com.google.gson.Gson
import com.hfad.data.retrofit.Potion
import com.squareup.picasso.Picasso

class PotionFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private var fragmentCallback: FragmentCallback? = null
    private var viewModel : PotionViewModel? = null

    fun setFragmentCallback(callback: FragmentCallback){
        fragmentCallback = callback
    }
    companion object {
        @JvmStatic
        fun newInstance() = PotionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = PotionViewModel(Gson().fromJson(arguments?.getString("potion"),Potion::class.java))

        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(viewModel?.potion?.attributes?.image).into(binding.imInfo)
        binding.apply {
            tvTitle.text = viewModel?.potion?.attributes?.name ?: "The name is unknown please consult with a master potionist"
            tvChar.text = viewModel?.potion?.attributes?.characteristics ?: "The characteristics are unknown please consult with a master potionist"
            tvDif.text = viewModel?.potion?.attributes?.difficulty ?: "The difficulty is unknown please consult with a master potionist"
            tvIng.text = viewModel?.potion?.attributes?.ingredients ?: "The ingredients are unknown please consult with a master potionist"
            tvEffect.text = viewModel?.potion?.attributes?.effect ?: "The effect is unknown please consult with a master potionist"
            tvSideEf.text = (viewModel?.potion?.attributes?.sideEffects ?: "The side effects are unknown please consult with a master potionist").toString()
        }
    }
}