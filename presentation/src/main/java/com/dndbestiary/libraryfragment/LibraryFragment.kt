package com.dndbestiary.libraryfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.domain.FragmentCallback
import com.dndbestiary.databinding.FragmentLibraryBinding
import com.dndbestiary.mainfragment.MainAdapter
import com.dndbestiary.MainViewModel
import com.domain.model.DomainPotion

class LibraryFragment : Fragment(), MainAdapter.Listener {
    private lateinit var binding: FragmentLibraryBinding
    private var fragmentCallback: FragmentCallback? = null
    private lateinit var viewModel: MainViewModel
    private val adapter = MainAdapter(this)

    fun setFragmentCallback(callback: FragmentCallback){
        fragmentCallback = callback
    }
    companion object {
        @JvmStatic
        fun newInstance() = LibraryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding = FragmentLibraryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeToRefresh()
        observePotions()
    }

    override fun onClick(potionId: String, potionImage: String) {
        fragmentCallback?.sendCallback("openPotionFragment",
            viewModel.getPotionById(potionId, potionImage))
    }

    override fun onLikeClick(potion: DomainPotion, isAdd: Boolean) {
        if(isAdd){
            viewModel.insertPotionIntoDb(potion)
        }else{
            viewModel.deletePotionFromDbByIndex(potion.potionId)
        }
    }

    private fun setupRecyclerView(){
        binding.rvLibrary.layoutManager = GridLayoutManager(requireContext(),2)
        binding.rvLibrary.adapter = adapter
    }
    private fun observePotions(){
        viewModel.getPotionsFromDb().observe(viewLifecycleOwner) { potions ->
            adapter.submitList(potions)
            viewModel.setPotionList(potions)
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPotionsFromDb().observe(viewLifecycleOwner) { potions ->
                adapter.submitList(potions)
                viewModel.setPotionList(potions)
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}