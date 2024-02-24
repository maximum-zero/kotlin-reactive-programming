package asynchronous.completablefuture.future.repository

import asynchronous.completablefuture.common.entity.ImageEntity
import java.util.Optional
import java.util.concurrent.CompletableFuture

class ImageFutureRepository {
    private val imageMap :Map<String, ImageEntity>

    init {
        val image = ImageEntity("image#1000", "profileImage", "http://www.naver.com")
        imageMap = mapOf("image#1000" to image)
    }

    fun findById(id: String): CompletableFuture<Optional<ImageEntity>> {
        return CompletableFuture.supplyAsync {
            println("ImageRepository.findById: $id")
            Thread.sleep(1000)
            Optional.ofNullable(imageMap[id])
        }
    }
}