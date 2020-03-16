package pl.creativecoder.blog.exposed.db

import org.jetbrains.exposed.sql.*
import pl.creativecoder.blog.exposed.model.Post
import java.util.*

class JpaPostRepository : PostsRepository {

    override fun create(post: Post): Post {
        val newPostId = PostsTable.insertAndGetId {
            it[PostsTable.title] = post.title
            it[PostsTable.content] = post.content
            it[PostsTable.author] = post.author
        }

        return selectById(newPostId.value)!!
    }

    override fun selectAll(): List<Post> {
        return PostsTable.selectAll().map {
            Post(
                id = it[PostsTable.id].value,
                author = it[PostsTable.author],
                content = it[PostsTable.content],
                title = it[PostsTable.title]
            )
        }
    }

    override fun selectById(id: Long): Post? {
        return PostsTable.select { PostsTable.id eq id }.map {
            Post(
                id = it[PostsTable.id].value,
                author = it[PostsTable.author],
                content = it[PostsTable.content],
                title = it[PostsTable.title]
            )
        }.firstOrNull()
    }

    override fun delete(id: Long) {
        PostsTable.deleteWhere { PostsTable.id eq id }
    }

    override fun update(post: Post): Post {
        PostsTable.update({PostsTable.id eq post.id}) {
            it[PostsTable.title] = post.title
        }

        return selectById(post.id!!)!!
    }

}