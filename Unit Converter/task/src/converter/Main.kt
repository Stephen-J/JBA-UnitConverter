package converter

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    print("Enter a number of kilometers: ")
    val km = scanner.nextInt()
    print("$km kilometers is ${km * 1000} meters\n")
}