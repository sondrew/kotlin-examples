import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis

fun runParallelWithLaunch() {
    GlobalScope.launch(Dispatchers.Default) {
        val measure = measureTimeMillis {
            val job1 = launch { getUser(1) }
            val job2 = launch { getUser(2) }
            yield()
            job1.join()
            job2.join()
        }
        println("Time: $measure")
    }
}

suspend fun runParallelWithCoroutineScope() {
    coroutineScope {
        val measure = measureTimeMillis {
            val request1 = async { getUser(1) }
            val request2 = async { getUser(2) }
            val request3 = async { getUser(2) }
            val request4 = async { getUser(2) }
            val results = "${request1.await()} ${request2.await()}"
        }
        println("Time: $measure")
    }
}

fun runParallelWithRunBlocking() {
    runBlocking {
        val measure = measureTimeMillis {
            val job1 = launch { getUser(1) }
            val job2 = launch { getUser(2) }
            job1.join()
            job2.join()
        }
        println("Time: $measure")
    }
}

fun runParallelWithRunBlocking2() = runBlocking {
    val measure = measureTimeMillis {
        val request1 = async { getUser(1) }
        val request2 = withContext(Dispatchers.Default) {
            async { getUser(2) }
        }
        yield()
        println(Thread.currentThread().name)
        val result1 = request1.await()
        val result2 = request2.await()
        println("$result1 $result2")
    }
    println("Time: $measure")
}

suspend fun runParallelWithContext() {
    val result = withContext(Dispatchers.Default) {
        val measure = measureTimeMillis {
            val request1 = async { getUser(1) }
            val request2 = async { getUser(2) }
            val results = "${request1.await()} ${request2.await()}"
        }
        println("Time: $measure")
    }
}

suspend fun main() {
    //runParallelWithLaunch()
    //runParallelWithCoroutineScope()
    //runParallelWithRunBlocking2()
    runParallelWithContext()
    Thread.sleep(6000)
}
