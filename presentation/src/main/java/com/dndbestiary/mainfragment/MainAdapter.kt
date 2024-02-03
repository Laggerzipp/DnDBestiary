package mainfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dndbestiary.R
import com.dndbestiary.databinding.MainMonsterItemBinding
import com.hfad.data.retrofit.MonsterGeneral
import com.hfad.data.retrofit.MonsterList
import com.squareup.picasso.Picasso


class MainAdapter(): ListAdapter<MonsterGeneral,MainAdapter.MyViewHolder>(Comparator()) {
    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = MainMonsterItemBinding.bind(view)

        fun bind(item: MonsterGeneral) = with(binding){
            Picasso.get().load("https://www.dnd5eapi.co/api/images"+item.url.substringAfter("/api")+".png").into(imInfo)
            tvInfo.text = item.name
        }
    }

    class Comparator: DiffUtil.ItemCallback<MonsterGeneral>(){
        override fun areItemsTheSame(oldItem: MonsterGeneral, newItem: MonsterGeneral): Boolean {
            return oldItem.index == newItem.index
        }

        override fun areContentsTheSame(oldItem: MonsterGeneral, newItem: MonsterGeneral): Boolean {
           return oldItem == newItem
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_monster_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}