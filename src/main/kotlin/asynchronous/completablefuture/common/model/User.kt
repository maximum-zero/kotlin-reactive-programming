package asynchronous.completablefuture.common.model

import java.util.*

data class User(
    val id: String,
    val name: String,
    val age: Int,
    val image: Optional<Image>,
    val articles: List<Article>,
    val followCount: Long
)
