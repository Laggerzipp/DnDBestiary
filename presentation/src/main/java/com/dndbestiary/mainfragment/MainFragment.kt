package mainfragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.dndbestiary.databinding.FragmentMainBinding
import com.hfad.data.Monster

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
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MainAdapter()
        val monster1 = Monster("first","gfdgdf")
        val monster2 = Monster("second","dfhdfh")
        adapter.addItem(monster1)
        adapter.addItem(monster2)
        binding.rvMain.layoutManager = GridLayoutManager(requireContext(),3)
        binding.rvMain.adapter = adapter
    }
}