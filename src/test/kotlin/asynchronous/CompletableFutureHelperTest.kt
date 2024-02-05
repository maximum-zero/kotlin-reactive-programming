package asynchronous

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletionStage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CompletableFutureHelperTest {
    private val log: Logger = LoggerFactory.getLogger(CompletableFutureHelperTest::class.java)

    @Test
    fun `done 상태에서 thenAccept()는 호출한 caller 쓰레드에서 action을 수행합니다`() {
        log.info("start")
        val stage: CompletionStage<Int> = CompletableFutureHelper.finishedStage()
        stage.thenAccept { i ->
            log.info("first thenAccept()")
            assertEquals(1, i)
        }.thenAccept { i ->
            log.info("second thenAccept()")
            assertNull(i)
        }
        log.info("end")
        Thread.sleep(1000)
    }

    @Test
    fun `done 상태에서 thenAcceptAsync()는 thread pool에 있는 쓰레드에서 action을 수행합니다`() {
        log.info("start")
        val stage: CompletionStage<Int> = CompletableFutureHelper.finishedStage()
        stage.thenAcceptAsync { i ->
            log.info("first thenAcceptAsync()")
            assertEquals(1, i)
        }.thenAcceptAsync { i ->
            log.info("second thenAcceptAsync()")
            assertNull(i)
        }
        log.info("end")
        Thread.sleep(1000)
    }


    @Test
    fun `done 상태가 아닌 thenAccept()는 callee 쓰레드에서 action을 수행합니다`() {
        log.info("start")
        val stage: CompletionStage<Int> = CompletableFutureHelper.runningStage()
        stage.thenAccept { i ->
            log.info("first thenAccept()")
            assertEquals(1, i)
        }.thenAccept { i ->
            log.info("second thenAccept()")
            assertNull(i)
        }
        log.info("end")
        Thread.sleep(1000)
    }

    @Test
    fun `done 상태가 아닌 thenAcceptAsync()는 thread pool에 있는 쓰레드에서 action을 수행합니다`() {
        log.info("start")
        val stage: CompletionStage<Int> = CompletableFutureHelper.runningStage()
        stage.thenAcceptAsync { i ->
            log.info("first thenAcceptAsync()")
            assertEquals(1, i)
        }.thenAcceptAsync { i ->
            log.info("second thenAcceptAsync()")
            assertNull(i)
        }
        log.info("end")
        Thread.sleep(1000)
    }
}