import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun launchSequential() {
    GlobalScope.launch {
        val measure = measureTimeMillis {
            println("Starter request")
            getUser(1)
            getUser(2)
        }
        println("Time: $measure")
    }
    println("Hovedtråd fortsetter å kjøre mens jobben utføres asynkront")
    Thread.sleep(7000L)
}

fun runBlockingSequential() = runBlocking {
    val measure = measureTimeMillis {
        getUser(1)
        getUser(2)
    }
    println("Time: $measure")
}

fun runAsyncSequential() = runBlocking {
    val measure = measureTimeMillis {
        val request1 = async { getUser(1) }
        val request2 = async { getUser(2) }
        request1.await()
        request2.await()
    }
    println("Time: $measure")
}

//@OptIn(DelicateCoroutinesApi::class)
fun runAsyncSequential2() = runBlocking { //GlobalScope.launch(Dispatchers.IO) {
    println("run")
    val measure = measureTimeMillis {
        val request1 = async { getUser(1) }
        val request2 = async { getUser(2) }
        val result1 = request1.await()
        val result2 = request2.await()
    }
    println("Time: $measure")
}

fun runAsyncParallel2() = runBlocking {
    val measure = measureTimeMillis {
        val request1 = async { getUser(1) }
        val request2 = async { getUser(2) }
        val result1 = request1.await()
        val result2 = request2.await()
        println("$result1 $result2")
    }
    println("Time: $measure")
}

fun runAsyncParallel() {
    val measure = measureTimeMillis {
        runBlocking {
            val result1 = async { getUser(1) }
            println(result1.await())
        }
        runBlocking {
            val result2 = async { getUser(2) }
            println(result2.await())
        }
    }
    println("Time: $measure")
}

suspend fun runAsyncParallel3() {
    coroutineScope {
        val measure = measureTimeMillis {
            val request1 = async { getUser(1) }
            val request2 = async { getUser(2) }
            val result1 = request1.await()
            val result2 = request2.await()
            println("$result1 $result2")
        }
        println("Time: $measure")
    }
}

suspend fun doLongRunningTaskOne(): String {
    return withContext(Dispatchers.Default) {
        getUser(1)
    }
}

suspend fun doLongRunningTaskTwo(): String {
    return withContext(Dispatchers.Default) {
        getUser(2)
    }
}

fun startLongRunningTaskInParallel() {
    GlobalScope.launch {
        val measure = measureTimeMillis {
            val result1Deferred = async { doLongRunningTaskOne() }
            val result2Deferred = async { doLongRunningTaskTwo() }
            val result1 = result1Deferred.await()
            val result2 = result2Deferred.await()
        }
        println("Time: $measure")
    }
}
suspend fun runAsyncParallel4() {
    val measure = measureTimeMillis {
        val result = coroutineScope {
            val request1 = async { getUser(1) }
            val request2 = async { getUser(2) }
            "${request1.await()} ${request2.await()}"
        }
        println(result)
    }
    println("Time: $measure")
}



suspend fun main() {
    //sequential()
    //runBlockingSequential()
    //runAsyncSequential()
    //runAsyncSequential2()
    //runAsyncParallel()
    //startLongRunningTaskInParallel()
    runAsyncParallel4()
    Thread.sleep(5000)
}
