package asynchronous.completablefuture.future

import asynchronous.completablefuture.common.entity.UserEntity
import asynchronous.completablefuture.common.model.Article
import asynchronous.completablefuture.common.model.Image
import asynchronous.completablefuture.common.model.User
import asynchronous.completablefuture.future.repository.ArticleFutureRepository
import asynchronous.completablefuture.future.repository.FollowFutureRepository
import asynchronous.completablefuture.future.repository.ImageFutureRepository
import asynchronous.completablefuture.future.repository.UserFutureRepository
import java.util.Optional
import java.util.concurrent.CompletableFuture

class UserFutureService (
    private val userFutureRepository: UserFutureRepository,
    private val articleFutureRepository: ArticleFutureRepository,
    private val imageFutureRepository: ImageFutureRepository,
    private val followFutureRepository: FollowFutureRepository,
) {
    fun getUserById(id: String): CompletableFuture<Optional<User>> {
        return userFutureRepository.findById(id)
            .thenComposeAsync(::getUser)
    }

    private fun getUser(userEntityOptional: Optional<UserEntity>): CompletableFuture<Optional<User>> {
        if (userEntityOptional.isEmpty) {
            return CompletableFuture.completedFuture(Optional.empty())
        }

        val userEntity = userEntityOptional.get()

        val imageFuture: CompletableFuture<Optional<Image>> = imageFutureRepository.findById(userEntity.profileImageId)
            .thenApplyAsync { imageEntityOptional ->
                imageEntityOptional.map { imageEntity ->
                    Image(imageEntity.id, imageEntity.name, imageEntity.url)
                }
            }

        val articleFuture: CompletableFuture<List<Article>> = articleFutureRepository.findAllByUserId(userEntity.id)
            .thenApplyAsync {articleEntities ->
                articleEntities.map { articleEntity ->
                    Article(articleEntity.id, articleEntity.title, articleEntity.content)
                }
            }

        val followCountFuture: CompletableFuture<Long> = followFutureRepository.countByUserId(userEntity.id)

        return CompletableFuture.allOf(imageFuture, articleFuture, followCountFuture)
            .thenAcceptAsync {
                println("Futures are completed")
            }
            .thenRunAsync {
                println("Futures are also completed")
            }
            .thenApplyAsync {
                try {
                    val image = imageFuture.get()
                    val articles = articleFuture.get()
                    val followCount = followCountFuture.get()

                    return@thenApplyAsync Optional.of(
                        User(
                            userEntity.id,
                            userEntity.name,
                            userEntity.age,
                            image,
                            articles,
                            followCount
                        )
                    )
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
    }
}