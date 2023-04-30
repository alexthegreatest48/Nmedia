package com.example.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity.apply
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MainViewModel by viewModels()

        val adapter = PostAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onRemove(post: Post) {
                viewModel.remove(post.id)
            }

            override fun onVideo(post: Post) {
                val webpage: Uri = Uri.parse(post.videoLink)
                val intent = Intent().apply{
                    action = Intent.ACTION_VIEW
                    putExtra(Intent.ACTION_VIEW, webpage)
                }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }

        })

        binding.container.adapter = adapter

        viewModel.data.observe(this) { post ->
            val newPost = adapter.currentList.size < post.size
            adapter.submitList(post) {
                if (newPost) {
                    binding.container.smoothScrollToPosition(0)
                }
            }
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            binding.group.visibility = View.VISIBLE
            with(binding.content) {
                requestFocus()
                setText(post.content)
            }
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Content can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
            }
        }

        binding.cancel.setOnClickListener {
            binding.content.setText("")
            binding.content.clearFocus()
            binding.group.visibility = View.INVISIBLE
        }

    }
}
