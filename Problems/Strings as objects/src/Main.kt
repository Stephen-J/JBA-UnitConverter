fun main() {
    val input = readLine()!!
    val result = when {
        input.isBlank() -> input
        input.isEmpty() -> input
        input.first() == 'i' -> (input.drop(1).toInt() + 1).toString()
        input.first() == 's' -> input.drop(1).reversed()
        else -> input
    }
    println(result)
}