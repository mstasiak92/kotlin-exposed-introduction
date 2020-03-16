package pl.creativecoder.blog.exposed

import org.h2.Driver
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import pl.creativecoder.blog.exposed.db.JpaPostRepository
import pl.creativecoder.blog.exposed.db.PostsRepository
import pl.creativecoder.blog.exposed.db.PostsTable
import pl.creativecoder.blog.exposed.db.UsersTable
import pl.creativecoder.blog.exposed.model.Post
import javax.xml.validation.Schema

object DbParams {
    val url: String = "jdbc:h2:./database"
    val driver: String = Driver::class.java.name
    val user: String = "sa"
    val password: String = ""
}

class ExposedBasicsApplication

fun main() {
    Database.connect(url = DbParams.url, driver = DbParams.driver, user = DbParams.user, password = DbParams.password)

    val repository: PostsRepository = JpaPostRepository()
    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(PostsTable)
        SchemaUtils.create(UsersTable)

        val testUserId = UsersTable.insertAndGetId {
            it[UsersTable.mail] = "test@test.eu"
        }

        repository.selectAll()
        println("Inserting new post")
        val post = repository.create(Post(title = "First post!", content = "Content!", author = testUserId.value))

        repository.selectAll()
        post.id?.let {
            println("Selecting post with id = $it")
            repository.selectById(it)
        }

        post.id?.let {
            println("Update post with id = $it. Setting title to 'Updated title'")
            repository.update(Post(id = it, title = "Updated title", content = post.content, author = post.author))
        }

        post.id?.let {
            println("Deleting post with id = $it")
            repository.delete(it)
        }
    }

}