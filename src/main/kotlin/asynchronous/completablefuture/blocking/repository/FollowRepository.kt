package asynchronous.completablefuture.blocking.repository

import java.util.*

class FollowRepository {
    private val userFollowCountMap: Map<String, Long>

    init {
        val follow = 1000L
        userFollowCountMap = mapOf("1234" to follow)
    }

    fun countByUserId(userId: String): Long {
        println("FollowRepository.countByUserId: $userId")
        Thread.sleep(1000)
        return userFollowCountMap.getOrDefault(userId, 0L)
    }
}