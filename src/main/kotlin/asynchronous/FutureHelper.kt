package asynchronous

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class FutureHelper {
    companion object {
        fun getFuture(): Future<Int> {
            val executor = Executors.newSingleThreadScheduledExecutor()

            return try {
                executor.submit (Callable { 1 } )
            } finally {
                executor.shutdown()
            }
        }

        fun getFutureCompleteAfter1s(): Future<Int> {
            val executor = Executors.newSingleThreadScheduledExecutor()

            return try {
                executor.submit (Callable {
                    Thread.sleep(1000)
                    1
                } )
            } finally {
                executor.shutdown()
            }
        }
    }
}