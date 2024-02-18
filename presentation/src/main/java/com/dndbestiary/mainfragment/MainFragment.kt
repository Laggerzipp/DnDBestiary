package com.dndbestiary.mainfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.dndbestiary.FragmentCallback
import com.dndbestiary.databinding.FragmentMainBinding
import com.domain.DomainPotion

class MainFragment : Fragment(), MainAdapter.Listener {
    private lateinit var binding: FragmentMainBinding
    private var fragmentCallback: FragmentCallback? = null
    private var potionList: List<DomainPotion> = listOf()
    private val adapter = MainAdapter(this)
    private lateinit var viewModel: MainViewModel

    fun setFragmentCallback(callback: FragmentCallback){
        fragmentCallback = callback
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = MainViewModel(requireContext())

        setupRecyclerView()
        setupSwipeToRefresh()
        observePotions()
        setupSearching()
    }

    override fun onClick(potionId: String, potionImage: String) {
        fragmentCallback?.sendCallback("openPotionFragment",
            viewModel.getPotionById(potionId, potionImage))
    }

    override fun onLikeClick(potion: DomainPotion) {
        viewModel.insertPotionIntoDb(potion)
    }

    private fun setupRecyclerView() {
        binding.rvMain.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvMain.adapter = adapter
    }
    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPotions()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
    private fun observePotions() {
        viewModel.getPotions().observe(viewLifecycleOwner, Observer { potions ->
            adapter.submitList(potions)
            potionList = potions
            binding.progressBar.visibility = View.GONE
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.setPotionList(potionList)
        })
    }
    private fun setupSearching() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
               viewModel.searchPotionByName(adapter, text)
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                viewModel.searchPotionByName(adapter, text)
                return true
            }
        })
    }
}