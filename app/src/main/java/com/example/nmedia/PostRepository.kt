package com.example.nmedia

import androidx.lifecycle.LiveData
import com.example.nmedia.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun repostById(id: Long)
}
