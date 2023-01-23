package com.example.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.nmedia.databinding.ActivityMainBinding
import com.example.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            1,
            "Нетология. Университет интернет-профессий будущего",
        "21 мая в 18:36",
        "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        false,
            1599999,
        100
        )

        fun likeCount(like: Int): String{
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

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text =  likeCount(post.likes)
            repost.text = post.reposts.toString()




            if (post.likedByMe) {
                likesButton.setImageResource(R.drawable.ic_baseline_thumb_up_24)
            }

            likesButton.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) post.likes++ else post.likes--
                likesButton.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_thumb_up_24 else R.drawable.ic_baseline_thumb_up_off_alt_24)
                likes.text = likeCount(post.likes)
            }

            repostButton.setOnClickListener {
                post.reposts++
                repost.text = post.reposts.toString()
            }
        }
    }
}