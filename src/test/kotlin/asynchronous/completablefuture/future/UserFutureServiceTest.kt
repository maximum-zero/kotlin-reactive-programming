package asynchronous.completablefuture.future

import asynchronous.completablefuture.common.model.User
import asynchronous.completablefuture.future.repository.ArticleFutureRepository
import asynchronous.completablefuture.future.repository.FollowFutureRepository
import asynchronous.completablefuture.future.repository.ImageFutureRepository
import asynchronous.completablefuture.future.repository.UserFutureRepository
import org.junit.jupiter.api.BeforeEach
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserFutureServiceTest {
    private lateinit var userFutureService: UserFutureService
    private lateinit var userFutureRepository: UserFutureRepository
    private lateinit var articleFutureRepository: ArticleFutureRepository
    private lateinit var imageFutureRepository: ImageFutureRepository
    private lateinit var followFutureRepository: FollowFutureRepository

    @BeforeEach
    fun setUp() {
        userFutureRepository = UserFutureRepository()
        articleFutureRepository = ArticleFutureRepository()
        imageFutureRepository = ImageFutureRepository()
        followFutureRepository = FollowFutureRepository()
        userFutureService = UserFutureService(userFutureRepository, articleFutureRepository, imageFutureRepository, followFutureRepository)
    }

    @Test
    fun `유효하지 않은 userId는 빈 값을 반환합니다`() {
        // Given
        val userId = "invalid_user_id"

        // When
        val user: Optional<User> = userFutureService.getUserById(userId).get()

        // Then
        assertTrue { user.isEmpty }
    }

    @Test
    fun `유효한 userId는 User 객체를 반환합니다`() {
        // Given
        val userId = "1234"

        // When
        val optionalUser = userFutureService.getUserById(userId).get()

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