fun main() {
    val stringA = readLine()!!
    val stringB = readLine()!!
    val stringC = readLine()!!
    val separator = readLine()!!
    val result = if (separator == "NO SEPARATOR") {
        concatentate(stringA,stringB,stringC)
    } else {
        concatentate(stringA,stringB,stringC,separator)
    }
    println(result)
}

fun concatentate(stringA : String,stringB : String,stringC : String,separator : String = " ") : String {
    return "$stringA$separator$stringB$separator$stringC"
}