package pl.creativecoder.blog.exposed.db

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.dao.UUIDTable

object PostsTable : LongIdTable(name = "blog_posts") {
    val title = varchar(name = "title", length = 255)
    val content = text(name = "content")
    val author = uuid(name = "author_id").references(UsersTable.id)
}

object PostTagsTable : LongIdTable(name = "blog_post_tags") {
    val postId = long(name = "post_id").references(PostsTable.id)
    val tagId = long(name = "tag_id").references(TagTable.id)
}

object TagTable : LongIdTable(name = "tags") {
    val name = varchar(name = "name", length = 255)
}

object UsersTable : UUIDTable(name = "users") {
    val mail = varchar(name = "mail", length = 255)
}