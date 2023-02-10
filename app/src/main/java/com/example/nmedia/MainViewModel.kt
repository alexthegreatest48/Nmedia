package com.example.nmedia

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun like(id: Long) = repository.likeById(id)
    fun repost(id: Long) = repository.repostById(id)
}