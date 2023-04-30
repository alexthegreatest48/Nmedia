package com.example.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = listOf(
            Post (
        id = 0,
        author = "Нетология. Университет интернет-профессий будущего",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        likedByMe = true,
        likes = 2,
        reposts = 100,
                videoLink = "https://youtu.be/dQw4w9WgXcQ"
            ),
        Post (
            id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        likedByMe = false,
        likes = 1599999,
        reposts = 100,
                "https://youtu.be/dQw4w9WgXcQ"
    ),
        Post (
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedByMe = false,
            likes = 1599999,
            reposts = 100,
                    videoLink = "https://youtu.be/dQw4w9WgXcQ"
        ),
    )
    private val data = MutableLiveData(post)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        post = post.map{
            if (!it.id.equals(id)) it else {
                val liked = it.copy(likedByMe = !it.likedByMe)
                if (it.likedByMe) liked.copy(likes = it.likes - 1) else liked.copy(likes = it.likes + 1)
            }
        }
        data.value = post
    }

    override fun save(posts: Post) {
        var nextId = post.size
        var nextID = nextId.toLong()
        if (posts.id == 0L) {
            // TODO: remove hardcoded author & published
            post = listOf(
                posts.copy(
                    id = nextID++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + post
            data.value = post
            return
        }

        post = post.map {
            if (it.id != posts.id) it else it.copy(content = posts.content)
        }
        data.value = post
    }

    override fun edit(post: Post) {

    }

    override fun repostById(id: Long){
        post = post.map {
            if (it.id != id) it else it.copy(reposts = it.reposts + 1)
        }
        data.value = post
    }

    override fun removeById(id: Long) {
        post = post.filter { it.id != id }
        data.value = post
    }
}