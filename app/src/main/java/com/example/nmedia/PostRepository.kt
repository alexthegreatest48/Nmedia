package com.example.nmedia

import androidx.lifecycle.LiveData
import com.example.nmedia.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun likeCount(like: Int): String
}