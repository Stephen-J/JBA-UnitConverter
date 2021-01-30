import kotlin.math.pow

fun main() {
    val param = readLine()!!
    val input = readLine()!!.toDouble()
    val result = when (param) {
        "amount" -> calcAmount(startingAmount = input)
        "percent" -> calcAmount(yearlyPercent = input)
        "years" -> calcAmount(years = input)
        else -> calcAmount()
    }
    println(result.toInt())
}

fun calcAmount(startingAmount : Double = 1000.0,yearlyPercent : Double = 5.0,years : Double = 10.0) : Double {
    return startingAmount * (1 + yearlyPercent / 100).pow(years)
}

