import kotlin.system.measureTimeMillis

fun main() {
    val measure = measureTimeMillis {
        println("Start requester")
        getUser(1)
        getUser(2)
    }

    println("Time: $measure")
}
