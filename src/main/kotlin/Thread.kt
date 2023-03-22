import kotlin.concurrent.thread

fun main() {

    thread(start = true) {
        println("Jobb utført på en annen tråd")
        getUser(1)
    }

    println("Hovedtråd fortsetter å kjøre mens jobben utføres på en annen tråd")
}
