package mainfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dndbestiary.databinding.FragmentMainBinding
import com.hfad.data.retrofit.ApiClient
import com.hfad.data.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
private lateinit var apiService: ApiService

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

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

        val adapter = MainAdapter()
        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),3)
        binding.rvMain.adapter = adapter


        CoroutineScope(Dispatchers.IO).launch {
           val poitionsList = apiService.getPotions()
           withContext(Dispatchers.Main){
               adapter.submitList(poitionsList.potions)
           }
        }
    }
}