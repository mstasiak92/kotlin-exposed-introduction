package pl.creativecoder.blog.exposed.db

import pl.creativecoder.blog.exposed.model.Post
import java.util.*

interface PostsRepository {
    fun create(post: Post): Post
    fun selectAll(): List<Post>
    fun selectById(id: Long): Post?
    fun delete(id: Long)
    fun update(post: Post): Post
}