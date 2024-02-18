package com.dndbestiary.mainfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dndbestiary.R
import com.dndbestiary.databinding.MainMonsterItemBinding
import com.domain.DomainPotion
import com.squareup.picasso.Picasso


class MainAdapter(private val listener: Listener): ListAdapter<DomainPotion, MainAdapter.MyViewHolder>(
    Comparator()
) {
    class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = MainMonsterItemBinding.bind(view)

        fun bind(item: DomainPotion, listener: Listener) = with(binding){
            val potionImage = item.image ?: getRandomPotionImage()
            Picasso.get().load(potionImage).into(imInfo)
            tvInfo.text = item.name
            imInfo.setOnClickListener {
                listener.onClick(item.potionId, potionImage)
            }
            ibLike.setOnClickListener {
                ibLike.setImageResource(R.drawable.ic_like_yes)
                item.isFavorite = true
                listener.onLikeClick(item)
            }
        }

        private fun getRandomPotionImage(): String{
            val potionImages = mutableListOf(
                "https://i.pinimg.com/550x/79/f0/93/79f09362e401954d2f8543c550b353a4.jpg",
                "https://static.vecteezy.com/system/resources/previews/023/174/912/non_2x/magic-potion-in-a-glass-bottle-3d-illustration-on-dark-background-ai-generative-image-free-photo.jpg",
                "https://cdn.openart.ai/uploads/image_Saf4KLz7_1690760621859_512.webp",
                "https://img.artpal.com/725682/48-23-8-7-5-12-27m.jpg",
                "https://img.freepik.com/premium-photo/dark-blue-magic-potion-glass-bottle-spooky-forest-background-generative-ai_634053-4261.jpg",
                "https://storage.googleapis.com/pai-images/f8c99915b885453982bde771d57338e7.jpeg",
                "https://storage.googleapis.com/pai-images/7ae195b0df4542a9a17d016d2b033eda.jpeg",
                "https://storage.googleapis.com/pai-images/56aa7fff53ae4577bd85179d1221b04d.jpeg",
                "https://storage.googleapis.com/pai-images/afbbac13bc0e4ca2b9cb66acbcf99bc7.jpeg",
                "https://storage.googleapis.com/pai-images/8774579e78314f53a71f050de232cac5.jpeg",
                "https://storage.googleapis.com/pai-images/cfb5d16e41a04231b1e320749522e54f.jpeg",
                "https://storage.googleapis.com/pai-images/e5bd35db23c049dbb65bdceccaed2b53.jpeg",
                "https://storage.googleapis.com/pai-images/e0881d171a5d41d28e0e8b99f3ca3202.jpeg",
                "https://storage.googleapis.com/pai-images/5f4c585a612145bd8cb2eec580a0860d.jpeg"
            )

            val randomIndex = (0 until potionImages.size).random()
            return potionImages.removeAt(randomIndex)
        }
    }

    class Comparator: DiffUtil.ItemCallback<DomainPotion>(){
        override fun areItemsTheSame(oldItem: DomainPotion, newItem: DomainPotion): Boolean {
            return oldItem.potionId == newItem.potionId
        }

        override fun areContentsTheSame(oldItem: DomainPotion, newItem: DomainPotion): Boolean {
           return oldItem == newItem
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_monster_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position),listener)
    }

    interface Listener{
        fun onClick(potionId: String, potionImage: String)
        fun onLikeClick(potion: DomainPotion)
    }
}