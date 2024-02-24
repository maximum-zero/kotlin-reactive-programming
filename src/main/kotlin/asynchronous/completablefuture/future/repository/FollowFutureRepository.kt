package asynchronous.completablefuture.future.repository

import java.util.*
import java.util.concurrent.CompletableFuture

class FollowFutureRepository {
    private val userFollowCountMap: Map<String, Long>

    init {
        val follow = 1000L
        userFollowCountMap = mapOf("1234" to follow)
    }

    fun countByUserId(userId: String): CompletableFuture<Long> {
        return CompletableFuture.supplyAsync {
            println("FollowRepository.countByUserId: $userId")
            Thread.sleep(1000)
            userFollowCountMap.getOrDefault(userId, 0L)
        }
    }
}