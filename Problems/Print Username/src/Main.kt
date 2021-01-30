fun main() {
    val input = readLine()!!
    if (input == "HIDDEN") greet() else greet(input)
}

fun greet(name : String = "secret user") {
    println("Hello, $name!")
}