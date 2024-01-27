package mainfragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dndbestiary.R
import com.dndbestiary.databinding.MainMonsterItemBinding
import com.hfad.data.Monster


class MainAdapter: RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
    private val list = ArrayList<Monster>()
    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = MainMonsterItemBinding.bind(view)

        fun bind(item:Monster) = with(binding){
            imInfo.setImageResource(R.drawable.ic_launcher_background)
            tvInfo.text = item.title
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_monster_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: Monster){
        list.add(item)
        notifyDataSetChanged()
    }
}