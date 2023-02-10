package com.example.nmedia

data class Post (
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    var likedByMe: Boolean,
    var likes: Int,
    var reposts: Int
)