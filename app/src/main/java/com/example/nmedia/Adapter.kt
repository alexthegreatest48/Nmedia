package com.example.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nmedia.databinding.CardPostBinding

typealias  OnLikeListener = (post: Post) -> Unit
typealias  OnRepostListener = (post: Post) -> Unit

class PostAdapter(private val onLikeListener: OnLikeListener, private val onRepostListener: OnRepostListener) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onRepostListener)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onRepostListener: OnRepostListener,
) : RecyclerView.ViewHolder(binding.root){
    fun bind(post: Post){
        binding.apply{
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text =  likeCount(post.likes)
            repost.text = post.reposts.toString()
            likesButton.setImageResource(
                if (post.likedByMe) R.drawable.ic_baseline_thumb_up_24 else R.drawable.ic_baseline_thumb_up_off_alt_24
            )
                        likesButton.setOnClickListener {
                onLikeListener(post)
            }
            repostButton.setOnClickListener {
                onRepostListener(post)
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
