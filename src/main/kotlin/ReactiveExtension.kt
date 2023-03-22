import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay

fun main() {
    val observable: Observable<String> = Observable.fromCallable {
        getUser(1)
    }
    observable.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .subscribe(
            { result -> println("Suksess: $result") },
            { error -> println("Feil: ${error.message}") }
        )
    println("Hovedtråd fortsetter å kjøre mens jobben utføres asynkront")
    Thread.sleep(5000)
}
