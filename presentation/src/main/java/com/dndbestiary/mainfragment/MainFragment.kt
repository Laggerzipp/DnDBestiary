package mainfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dndbestiary.FragmentCallback
import com.dndbestiary.databinding.FragmentMainBinding
import com.hfad.data.retrofit.ApiClient
import com.hfad.data.retrofit.ApiService
import com.hfad.data.retrofit.Potion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
private lateinit var apiService: ApiService

class MainFragment : Fragment(), MainAdapter.Listener {
    private lateinit var binding: FragmentMainBinding
    private var fragmentCallback: FragmentCallback? = null

    fun setFragmentCallback(callback: FragmentCallback){
        fragmentCallback = callback
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        apiService = ApiClient().getClient().create(ApiService::class.java)

        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MainAdapter(this)
        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),3)
        binding.rvMain.adapter = adapter


        CoroutineScope(Dispatchers.IO).launch {
           val potionsList = apiService.getPotions()
           withContext(Dispatchers.Main){
               adapter.submitList(potionsList.potions)
           }
        }
    }

    override fun onClick(potion: Potion) {
        fragmentCallback?.sendCallback("openPotionFragment", potion)
    }
}