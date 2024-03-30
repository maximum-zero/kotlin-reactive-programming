package stream.publisher

object SimpleHotPublisherMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val publisher = SimpleHotPublisher()

        val subscriber1 = SimpleNamedSubscriber<Int>("subscriber1")
        publisher.subscribe(subscriber1)

        Thread.sleep(5000)
        subscriber1.cancel()

        val subscriber2 = SimpleNamedSubscriber<Int>("subscriber2")
        val subscriber3 = SimpleNamedSubscriber<Int>("subscriber3")
        publisher.subscribe(subscriber2)
        publisher.subscribe(subscriber3)

        Thread.sleep(5000)
        subscriber2.cancel()
        subscriber3.cancel()

        Thread.sleep(1000)

        val subscriber4 = SimpleNamedSubscriber<Int>("subscriber4")
        publisher.subscribe(subscriber4)

        Thread.sleep(3000)
        subscriber4.cancel()

        publisher.shutdown()
    }
}