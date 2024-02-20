package com.dndbestiary.mainfragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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

    override fun onLikeClick(potion: DomainPotion, isAdd: Boolean) {
        if(isAdd){
            viewModel.insertPotionIntoDb(potion)
        }else{
            viewModel.deletePotionFromDbByIndex(potion.potionId)
        }
    }

    private fun setupRecyclerView() {
        binding.rvMain.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvMain.adapter = adapter
    }
    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            if(checkInternetConnection()) {
                viewModelObserver()
            }
            else{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),"NO INTERNET CONNECTION", Toast.LENGTH_LONG).show()
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
    private fun observePotions() {
        if(checkInternetConnection()) {
            viewModelObserver()
        }
        else{
            binding.progressBar.visibility = View.GONE
            Toast.makeText(requireContext(),"NO INTERNET CONNECTION", Toast.LENGTH_LONG).show()
        }
    }

    private fun viewModelObserver(){
        viewModel.getPotions().observe(viewLifecycleOwner) { potions ->
            adapter.submitList(potions)
            potionList = potions
            binding.progressBar.visibility = View.GONE
            viewModel.setPotionList(potionList)
        }
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

    private fun checkInternetConnection(): Boolean{
        val connectivityManager = this.context?.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities != null
    }
}