package mainfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dndbestiary.R
import com.dndbestiary.databinding.MainMonsterItemBinding
import com.hfad.data.retrofit.Potion
import com.squareup.picasso.Picasso


class MainAdapter(): ListAdapter<Potion,MainAdapter.MyViewHolder>(Comparator()) {
    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = MainMonsterItemBinding.bind(view)

        fun bind(item: Potion) = with(binding){
            Picasso.get().load(item.attributes.image).into(imInfo)
            tvInfo.text = item.attributes.name
        }
    }

    class Comparator: DiffUtil.ItemCallback<Potion>(){
        override fun areItemsTheSame(oldItem: Potion, newItem: Potion): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Potion, newItem: Potion): Boolean {
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