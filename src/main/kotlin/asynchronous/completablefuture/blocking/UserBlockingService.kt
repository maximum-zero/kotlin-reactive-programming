package asynchronous.completablefuture.blocking

import asynchronous.completablefuture.common.model.Article
import asynchronous.completablefuture.common.model.Image
import asynchronous.completablefuture.common.model.User
import asynchronous.completablefuture.blocking.repository.ArticleRepository
import asynchronous.completablefuture.blocking.repository.FollowRepository
import asynchronous.completablefuture.blocking.repository.ImageRepository
import asynchronous.completablefuture.blocking.repository.UserRepository
import java.util.Optional

class UserBlockingService (
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val imageRepository: ImageRepository,
    private val followRepository: FollowRepository,
) {
    fun getUserById(id: String): Optional<User> {
        return userRepository.findById(id)
            .map { user ->
                val image = imageRepository.findById(user.profileImageId)
                    .map { imageEntity ->
                        Image(imageEntity.id, imageEntity.name, imageEntity.url)
                    }
                val articles = articleRepository.findAllByUserId(user.id)
                    .map { articleEntity ->
                        Article(articleEntity.id, articleEntity.title, articleEntity.content)
                    }

                val followCount = followRepository.countByUserId(user.id)

                User(
                    user.id,
                    user.name,
                    user.age,
                    image,
                    articles,
                    followCount
                )
            }
    }
}