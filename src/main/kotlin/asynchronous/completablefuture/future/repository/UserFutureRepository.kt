package asynchronous.completablefuture.future.repository

import asynchronous.completablefuture.common.entity.UserEntity
import java.util.Optional
import java.util.concurrent.CompletableFuture

class UserFutureRepository {
    private val userMap: Map<String, UserEntity>

    init {
        val user = UserEntity("1234", "maximum0", 29, "image#1000")
        userMap = mapOf("1234" to user)
    }

    fun findById(id: String): CompletableFuture<Optional<UserEntity>> {
        return CompletableFuture.supplyAsync {
            println("UserRepository.findById: $id")
            Thread.sleep(1000)
            Optional.ofNullable(userMap[id])
        }
    }
}