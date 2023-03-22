import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

const val DELAY_IN_MS = 2000

fun getUser(user: Int = 1): String {
    println("getUser($user) - ${Thread.currentThread().name}")
    val requestUrl = "https://reqres.in/api/users/$user"
    val delayUrl = URL("https://app.requestly.io/delay/$DELAY_IN_MS/$requestUrl")


    val conn = delayUrl.openConnection() as HttpURLConnection
    conn.requestMethod = "GET"
    var response = ""

    BufferedReader(InputStreamReader(conn.inputStream)).use { br ->
        var line: String?
        while (br.readLine().also { line = it } != null) {
            response += line
        }
    }
    println("Result $user: $response")
    return response
}


fun main() {
    val request = getUser()
}
