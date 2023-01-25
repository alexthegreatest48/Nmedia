package com.example.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.example.nmedia.databinding.ActivityMainBinding
import com.example.nmedia.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: MainViewModel by viewModels() // почему не видит viewModels?

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.data.observe(this){post ->
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text =  likeCount(post.likes) // Как обратиться к функции в данном случае
            repost.text = post.reposts.toString()

            if (post.likedByMe) {
                likesButton.setImageResource(R.drawable.ic_baseline_thumb_up_24)
            }

            likesButton.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) post.likes++ else post.likes--
                likesButton.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_thumb_up_24 else R.drawable.ic_baseline_thumb_up_off_alt_24)
                likes.text = likeCount(post.likes)
            }

            repostButton.setOnClickListener {
                post.reposts++
                repost.text = post.reposts.toString()
            }
        }
    }
    }
}