import kotlinx.coroutines.*
import kotlinx.coroutines.future.*
import java.util.concurrent.CompletableFuture

fun main() = runBlocking<Unit> {
    val futureResult: CompletableFuture<String> = CompletableFuture.supplyAsync {
        getUser(1)
    }
    println("Hovedtråd fortsetter å kjøre mens jobben utføres asynkront")
    val result = futureResult.await()
    println("Resultat: $result")
}
