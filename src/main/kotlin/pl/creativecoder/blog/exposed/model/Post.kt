package pl.creativecoder.blog.exposed.model

import java.util.*

class Post(
    val id: Long? = null,
    val title: String,
    val content: String,
    val author: UUID
)