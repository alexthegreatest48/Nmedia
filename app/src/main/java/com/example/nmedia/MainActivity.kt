package com.example.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MainViewModel by viewModels()

        val adapter = PostAdapter ({
            viewModel.like(it.id)
        }, {
            viewModel.repost(it.id)
        })
        binding.container.adapter = adapter
        viewModel.data.observe(this){ post ->
            adapter.submitList(post)
        }
    }
}