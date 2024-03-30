package stream.publisher

import java.util.concurrent.Flow
import org.slf4j.LoggerFactory

class SimpleNamedSubscriber<T>(private val name: String) : Flow.Subscriber<T> {
    private lateinit var subscription: Flow.Subscription
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun onSubscribe(subscription: Flow.Subscription?) {
        this.subscription = subscription ?: throw NullPointerException("Subscription cannot be null")
        this.subscription.request(1)
        log.info("onSubscribe")
    }

    override fun onNext(item: T) {
        log.info("name: {}, onNext: {}", name, item)
        subscription.request(1)
    }

    override fun onError(throwable: Throwable?) {
        log.error("onError: {}", throwable?.message)
    }

    override fun onComplete() {
        log.info("onComplete")
    }

    fun cancel() {
        log.info("cancel")
        this.subscription.cancel()
    }
}