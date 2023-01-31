package com.example.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: MainViewModel by viewModels()

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun likeCount(like: Int): String{
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


        viewModel.data.observe(this){post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likes.text =  likeCount(post.likes)
                repost.text = post.reposts.toString()

                if (post.likedByMe) {
                    likesButton.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                }

                likesButton.setOnClickListener {
                    viewModel.like()
                    likesButton.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_thumb_up_24 else R.drawable.ic_baseline_thumb_up_off_alt_24)
                    likes.text = likeCount(post.likes)
                }

                repostButton.setOnClickListener {
                    viewModel.repost()
                }
            }
        }
    }
}