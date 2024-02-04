package asynchronous

import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.test.*

class FutureHelperTest {
    @Test
    fun `test getFuture()`() {
        val future: Future<Int> = FutureHelper.getFuture()
        assertFalse(future.isDone)
        assertFalse(future.isCancelled)

        val result: Int = future.get()

        assertEquals(1, result)
        assertTrue(future.isDone)
        assertFalse(future.isCancelled)
    }

    @Test
    fun `test getFuture() cancel`() {
        val future: Future<Int> = FutureHelper.getFuture()
        var successToCancel = future.cancel(true);
        assertTrue(future.isDone)
        assertTrue(future.isCancelled)
        assertTrue(successToCancel)

        successToCancel = future.cancel(true);
        assertTrue(future.isDone)
        assertTrue(future.isCancelled)
        assertFalse(successToCancel)
    }

    @Test
    fun `test getFutureCompleteAfter1s()`() {
        val future: Future<Int> = FutureHelper.getFutureCompleteAfter1s()
        val result: Int = future.get(1500, TimeUnit.MILLISECONDS)
        assertEquals(1, result)
    }

    @Test
    fun `test getFutureCompleteAfter1s() timeout`() {
        val futureToTimeout: Future<Int> = FutureHelper.getFutureCompleteAfter1s()
        var exception: Exception? = null

        try {
            futureToTimeout.get(500, TimeUnit.MILLISECONDS)
        } catch (e: TimeoutException) {
            exception = e
        }

        assertNotNull(exception)
    }
}