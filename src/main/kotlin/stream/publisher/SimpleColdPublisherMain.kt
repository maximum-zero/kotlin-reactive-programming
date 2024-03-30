package stream.publisher

object SimpleColdPublisherMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val publisher = SimpleColdPublisher()

        val subscriber1 = SimpleNamedSubscriber<Int>("subscriber1")
        publisher.subscribe(subscriber1)

        Thread.sleep(5000)

        val subscriber2 = SimpleNamedSubscriber<Int>("subscriber2")
        publisher.subscribe(subscriber2)
    }
}