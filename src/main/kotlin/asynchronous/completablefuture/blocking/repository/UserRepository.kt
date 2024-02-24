package asynchronous.completablefuture.blocking.repository

import asynchronous.completablefuture.common.entity.UserEntity
import java.util.Optional

class UserRepository {
    private val userMap: Map<String, UserEntity>

    init {
        val user = UserEntity("1234", "maximum0", 29, "image#1000")
        userMap = mapOf("1234" to user)
    }

    fun findById(id: String): Optional<UserEntity> {
        println("UserRepository.findById: $id")
        Thread.sleep(1000)
        return Optional.ofNullable(userMap[id])
    }
}