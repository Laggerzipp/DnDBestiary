package com.dndbestiary.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dndbestiary.MainAdapter
import com.dndbestiary.R
import com.dndbestiary.viewmodel.MainViewModel
import com.domain.FragmentCallback
import com.dndbestiary.databinding.FragmentMainBinding
import com.domain.models.DomainPotion

class MainFragment : Fragment(), MainAdapter.Listener {
    private lateinit var binding: FragmentMainBinding
    private var fragmentCallback: FragmentCallback? = null
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
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSwipeToRefresh()
        setupViewModelObserver()
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
        binding.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.red))
        binding.swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.layoutBackground))

        binding.swipeRefreshLayout.setOnRefreshListener {
            if(checkInternetConnection()) {
                viewModel.getPotions()
            }
            else{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),"NO INTERNET CONNECTION", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun observePotions() {
        binding.progressBar.visibility = View.VISIBLE
        if(checkInternetConnection()) {
            viewModel.getPotions()
        }
        else{
            binding.progressBar.visibility = View.GONE
            Toast.makeText(requireContext(),"NO INTERNET CONNECTION", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupViewModelObserver(){
        viewModel.potionList.observe(viewLifecycleOwner) { potions ->
            adapter.submitList(potions)
            binding.swipeRefreshLayout.isRefreshing = false
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupSearching() {
        val searchEditText = binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        searchEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.redSearch))

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