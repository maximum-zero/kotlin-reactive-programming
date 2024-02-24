package asynchronous.completablefuture.future.repository

import asynchronous.completablefuture.common.entity.ArticleEntity
import java.util.concurrent.CompletableFuture

class ArticleFutureRepository {
    companion object {
        private val articleEntities: List<ArticleEntity> = listOf(
            ArticleEntity("id1", "Title 1", "Content 1", "1234"),
            ArticleEntity("id2", "Title 2", "Content 2", "1234"),
            ArticleEntity("id3", "Title 3", "Content 3", "1234")
        )
    }

    fun findAllByUserId(userId: String): CompletableFuture<List<ArticleEntity>> {
        return CompletableFuture.supplyAsync {
            println("ArticleFutureRepository.findAllByUserId(): $userId")
            Thread.sleep(1000)
            articleEntities.filter { it.userId == userId }
        }
    }
}