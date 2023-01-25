package com.example.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post (
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        likedByMe = false,
        likes = 1599999,
        reposts = 100
            )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        data.value = post
    }

    override fun likeCount(like: Int): String{
        var p: Int
        var likeCount: Double = like/1000.0
        if (likeCount % 1 == 0.0 && like>1000 && like<10000){
            p = likeCount.toInt()
            return p.toString() + "K"
        }
        if(likeCount % 1 != 0.0 && like>1000 && like<10000){
            p = (likeCount * 10).toInt()
            likeCount = p.toDouble() / 10
            return likeCount.toString() + "K"
        }
        if(like in 10000..999999){
            p = likeCount.toInt()
            return p.toString() + "K"
        }
        if(like in 1000000..1099999){
            likeCount = like/1000000.0
            p = likeCount.toInt()
            return p.toString() + "М"
        }
        if(like>=1100000 && likeCount % 1000 != 0.0){
            likeCount = like/1000000.0
            p = (likeCount * 10).toInt()
            likeCount = p.toDouble() / 10
            return likeCount.toString() + "М"
        }
        else return like.toString()
    }
}