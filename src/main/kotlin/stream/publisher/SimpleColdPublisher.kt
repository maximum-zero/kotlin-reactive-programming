package stream.publisher

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Flow

class SimpleColdPublisher : Flow.Publisher<Int>{
    override fun subscribe(subscriber: Flow.Subscriber<in Int>) {
        val iterator = (1 until 10).toList().iterator()

        val subscription = SimpleColdSubscription(iterator, subscriber)
        subscriber.onSubscribe(subscription)
    }

    inner class SimpleColdSubscription(
        private val iterator: Iterator<Int>,
        private val subscriber: Flow.Subscriber<in Int>
    ) : Flow.Subscription {
        private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

        override fun request(n: Long) {
            executorService.submit {
                repeat(n.toInt()) {
                    if (iterator.hasNext()) {
                        val number = iterator.next()
                        subscriber.onNext(number)
                    } else {
                        subscriber.onComplete()
                        executorService.shutdown()
                        return@submit
                    }
                }
            }
        }

        override fun cancel() {
            subscriber.onComplete()
        }
    }
}