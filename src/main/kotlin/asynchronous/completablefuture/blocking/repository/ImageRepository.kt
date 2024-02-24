package asynchronous.completablefuture.blocking.repository

import asynchronous.completablefuture.common.entity.ImageEntity
import java.util.Optional

class ImageRepository {
    private val imageMap :Map<String, ImageEntity>

    init {
        val image = ImageEntity("image#1000", "profileImage", "http://www.naver.com")
        imageMap = mapOf("image#1000" to image)
    }

    fun findById(id: String): Optional<ImageEntity> {
        println("ImageRepository.findById: $id")
        Thread.sleep(1000)
        return Optional.ofNullable(imageMap[id])
    }
}