package com.example.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nmedia.databinding.CardPostBinding

interface OnInteractionListener{
    fun onLike(post: Post) {}
    fun onRepost(post: Post) {}
    fun onRemove(post: Post) {}
    fun onEdit(post: Post) {}
}
typealias  onInteractionListener = (post: Post) -> Unit

class PostAdapter(private val onInteractionListener: OnInteractionListener) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root){
    fun bind(post: Post){
        binding.apply{
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesButton.text =  likeCount(post.likes)
            likesButton.isChecked = post.likedByMe
            repostButton.text = post.reposts.toString()
            likesButton.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            repostButton.setOnClickListener {
                onInteractionListener.onRepost(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu)
                    setOnMenuItemClickListener { item->
                        when(item.itemId){
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

        }
    }

    private fun likeCount(like: Int): String{
        val p: Int
        var likeCount: Double = like/1000.0
        if (likeCount % 1 == 0.0 && like>1000 && like<10000){
            p = likeCount.toInt()
            return p.toString() + "K"
        }
        if(likeCount % 1 != 0.0 && like>1000 && like<10000){
            p = (likeCount * 10).toInt()
            likeCount = p.toDouble() / 10
            return likeCount.toString() + "K"
        }
        if(like in 10000..999999){
            p = likeCount.toInt()
            return p.toString() + "K"
        }
        if(like in 1000000..1099999){
            likeCount = like/1000000.0
            p = likeCount.toInt()
            return p.toString() + "М"
        }
        if(like>=1100000 && likeCount % 1000 != 0.0){
            likeCount = like/1000000.0
            p = (likeCount * 10).toInt()
            likeCount = p.toDouble() / 10
            return likeCount.toString() + "М"
        }
        else return like.toString()
    }
}

class PostDiffCallback: DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
