package com.dndbestiary.searchfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dndbestiary.FragmentCallback
import com.dndbestiary.databinding.FragmentSearchBinding
import com.hfad.data.retrofit.Potion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PotionFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private var fragmentCallback: FragmentCallback? = null
    private var viewModel = PotionViewModel(arguments?.getString("id"))

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

        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var potion: Potion
        CoroutineScope(Dispatchers.IO).launch {
            //potion = ApiClient.apiService.getPotionByIndex(viewModel.potionId)
//            withContext(Dispatchers.Main){
//                Picasso.get().load(potion.attributes.image).into(binding.imInfo)
//            }
        }
    }
}