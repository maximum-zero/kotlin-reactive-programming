package stream.publisher

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Flow
import java.util.concurrent.Future

class SimpleHotPublisher : Flow.Publisher<Int> {
    private val publisherExecutor = Executors.newSingleThreadExecutor()
    private val numbers = mutableListOf<Int>()
    private val subscriptions = mutableListOf<SimpleHotSubscription>()
    private val task: CompletableFuture<Void>

    init {
        numbers.add(1)
        task = CompletableFuture.runAsync {
            var i = 2
            while (!Thread.interrupted()) {
                numbers.add(i++)
                subscriptions.forEach { it.wakeup() }
                Thread.sleep(100)
            }
        }
    }

    fun shutdown() {
        task.cancel(true)
        publisherExecutor.shutdown()
    }

    override fun subscribe(subscriber: Flow.Subscriber<in Int>) {
        val subscription = SimpleHotSubscription(subscriber)
        subscriber.onSubscribe(subscription)
        subscriptions.add(subscription)
    }

    inner class SimpleHotSubscription(private val subscriber: Flow.Subscriber<in Int>) : Flow.Subscription {
        private var offset = numbers.size - 1
        private var requiredOffset = numbers.size - 1
        private val subscriptionExecutorService = Executors.newSingleThreadExecutor()

        init {
            val lastElementIndex = numbers.size - 1
            offset = lastElementIndex
            requiredOffset = lastElementIndex
        }

        override fun request(n: Long) {
            requiredOffset += n.toInt()
            onNextWhilePossible()
        }

        override fun cancel() {
            subscriber.onComplete()
            if (subscriptions.contains(this)) {
                subscriptions.remove(this)
            }
            subscriptionExecutorService.shutdown()
        }

        fun wakeup() {
            onNextWhilePossible()
        }

        private fun onNextWhilePossible() {
            subscriptionExecutorService.submit {
                while (offset < requiredOffset && offset < numbers.size) {
                    val item = numbers[offset]
                    subscriber.onNext(item)
                    offset++
                }
            }
        }
    }
}