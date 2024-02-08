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
import com.hfad.data.repository.MPRepository
import com.hfad.data.retrofit.PotionResponse

class MainFragment : Fragment(), MainAdapter.Listener {
    private lateinit var binding: FragmentMainBinding
    private var fragmentCallback: FragmentCallback? = null
    private var potionResponse: PotionResponse? = null
    private var potionList: List<DomainPotion> = listOf()

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

        val adapter = MainAdapter(this)
        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),3)
        binding.rvMain.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            getData(adapter,false)
        }
        getData(adapter, true)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                searchPotionByName(adapter, text)
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                searchPotionByName(adapter, text)
                return true
            }
        })
    }

    override fun onClick(potionId: String, potionImage: String) {
        fragmentCallback?.sendCallback("openPotionFragment", getPotionById(potionId, potionImage))
    }

    private fun getData(adapter: MainAdapter, visibility: Boolean){
        if(visibility){
            binding.progressBar.visibility = View.VISIBLE
        }

        MPRepository().potionList.observe(viewLifecycleOwner, Observer { potions ->
            adapter.submitList(potions)
            binding.progressBar.visibility = View.GONE
            binding.swipeRefreshLayout.isRefreshing = false
            potionList = potions
        })
    }

    private fun getPotionById(potionId: String, potionImage: String): DomainPotion?{
        var potion: DomainPotion? = null
        for(p in potionList){
            if(p.id == potionId){
                potion = p
                break
            }
        }
        if(potion?.image == null){
            potion?.image = potionImage
        }
        return potion
    }

    private fun searchPotionByName(adapter:MainAdapter, text: String?):Boolean{
        if (text.isNullOrEmpty()) {
            adapter.submitList(potionList)
            return true
        }

        val filteredPotions: ArrayList<DomainPotion> = ArrayList()
        val searchText = text.lowercase().trim()

        for (p in potionList) {
            if (p.name.lowercase().contains(searchText)) {
                filteredPotions.add(p)
            }
        }

        adapter.submitList(filteredPotions)
        return true
    }
}