package com.example.nmedia

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepositorySharedPrefImpl (context: Context) : PostRepository {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var nextId = 1L
    private var post = emptyList<Post>()
    private val data = MutableLiveData(post)


    init {
        prefs.getString(key, null)?.let {
            post = gson.fromJson(it, type)
            data.value = post
        }
    }

   override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        post = post.map{
            if (it.id != id) it else {
                val liked = it.copy(likedByMe = !it.likedByMe)
                if (it.likedByMe) liked.copy(likes = it.likes - 1) else liked.copy(likes = it.likes + 1)
            }
        }
        data.value = post
        sync()
    }

    override fun save(posts: Post) {
        if (posts.id == 0L) {
            post = listOf(
                posts.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + post
            data.value = post
            sync()
            return
        }

        post = post.map {
            if (it.id != posts.id) it else it.copy(content = posts.content)
        }
        data.value = post
        sync()
    }

    override fun edit(post: Post) {
        sync()
    }

    override fun repostById(id: Long){
        post = post.map {
            if (it.id != id) it else it.copy(reposts = it.reposts + 1)
        }
        data.value = post
        sync()
    }

    override fun removeById(id: Long) {
        post = post.filter { it.id != id }
        data.value = post
        sync()
    }

    private fun sync(){
        with(prefs.edit()){
            putString(key, gson.toJson(post))
            apply()
        }
    }

}