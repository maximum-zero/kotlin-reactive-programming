package asynchronous

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

class CompletableFutureHelper {
    companion object {
        fun finishedStage(): CompletionStage<Int> {
            return CompletableFuture.supplyAsync {
                1
            }.also { Thread.sleep(100) }
        }

        fun runningStage(): CompletionStage<Int> {
            return CompletableFuture.supplyAsync {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                }
                1
            }
        }
    }
}