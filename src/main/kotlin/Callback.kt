interface Callback {
    fun onSuccess(result: String)
    fun onFailure(error: Exception)
}

fun main() {
    performAsyncOperation(object : Callback {
        override fun onSuccess(result: String) {
            println("Suksess: $result")
        }
        override fun onFailure(error: Exception) {
            println("Feil: ${error.message}")
        }
    })
    println("Hovedtråd fortsetter å kjøre mens jobben utføres asynkront")
}

fun performAsyncOperation(callback: Callback) {
    Thread {
        println("Kjører request")
        val result = getUser()
        if (result.isNotEmpty()) {
            callback.onSuccess(result)
        } else {
            callback.onFailure(Exception("Resultatet var tomt"))
        }
    }.start()
}

