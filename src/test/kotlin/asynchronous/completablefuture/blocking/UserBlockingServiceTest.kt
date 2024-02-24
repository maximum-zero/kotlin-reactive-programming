package asynchronous.completablefuture.blocking

import asynchronous.completablefuture.blocking.UserBlockingService
import asynchronous.completablefuture.common.model.User
import asynchronous.completablefuture.blocking.repository.ArticleRepository
import asynchronous.completablefuture.blocking.repository.FollowRepository
import asynchronous.completablefuture.blocking.repository.ImageRepository
import asynchronous.completablefuture.blocking.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserBlockingServiceTest {
    private lateinit var userBlockingService: UserBlockingService
    private lateinit var userRepository: UserRepository
    private lateinit var articleRepository: ArticleRepository
    private lateinit var imageRepository: ImageRepository
    private lateinit var followRepository: FollowRepository

    @BeforeEach
    fun setUp() {
        userRepository = UserRepository()
        articleRepository = ArticleRepository()
        imageRepository = ImageRepository()
        followRepository = FollowRepository()
        userBlockingService = UserBlockingService(userRepository, articleRepository, imageRepository, followRepository)
    }

    @Test
    fun `유효하지 않은 userId는 빈 값을 반환합니다`() {
        // Given
        val userId = "invalid_user_id"

        // When
        val user: Optional<User> = userBlockingService.getUserById(userId)

        // Then
        assertTrue { user.isEmpty }
    }

    @Test
    fun `유효한 userId는 User 객체를 반환합니다`() {
        // Given
        val userId = "1234"

        // When
        val optionalUser = userBlockingService.getUserById(userId)

        // Then
        assertFalse { optionalUser.isEmpty }
        val user = optionalUser.get()
        assertEquals(user.name, "maximum0")
        assertEquals(user.age, 29)

        assertFalse { user.image.isEmpty }
        val image = user.image.get()
        assertEquals(image.id, "image#1000")
        assertEquals(image.name, "profileImage")
        assertEquals(image.url, "http://www.naver.com")

        assertEquals(user.articles.size, 3)

        assertEquals(user.followCount, 1000)
    }
}