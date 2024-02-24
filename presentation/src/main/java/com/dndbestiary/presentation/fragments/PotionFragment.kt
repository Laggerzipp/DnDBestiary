package com.dndbestiary.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dndbestiary.databinding.FragmentSearchBinding
import com.dndbestiary.presentation.viewmodel.MainViewModel
import com.domain.models.DomainPotion
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class PotionFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private var fragmentCallback: FragmentCallback? = null
    private lateinit var viewModel: MainViewModel

    fun setFragmentCallback(callback: FragmentCallback) {
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
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.potion = Gson().fromJson(arguments?.getString("potion"), DomainPotion::class.java)

        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        Picasso.get().load(viewModel.potion?.image).into(binding.imInfo)
        binding.apply {
            tvTitle.text = viewModel.potion?.name
                ?: "The name is unknown please consult with a master potionist"
            tvChar.text = viewModel.potion?.characteristics
                ?: "The characteristics are unknown please consult with a master potionist"
            tvDif.text = viewModel.potion?.difficulty
                ?: "The difficulty is unknown please consult with a master potionist"
            tvIng.text = viewModel.potion?.ingredients
                ?: "The ingredients are unknown please consult with a master potionist"
            tvEffect.text = viewModel.potion?.effect
                ?: "The effect is unknown please consult with a master potionist"
            tvSideEf.text = viewModel.potion?.sideEffects
                ?: "The side effects are unknown please consult with a master potionist"
        }
    }
}