package com.example.nmedia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = "",
    likes = 0,
    reposts = 0
)

class MainViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun repost(id: Long) = repository.repostById(id)
    fun remove(id: Long) = repository.removeById(id)
    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String){
        val text = content.trim()
        if (edited.value?.content == text){
            return
        }
        edited.value = edited.value?.copy(content = text)
    }


    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }



}