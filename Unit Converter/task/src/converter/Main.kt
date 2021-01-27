package converter


fun main() {
    print("Enter a number and a measure of length: ")
    val input = readLine()!!.split(" ")
    val amount = input[0].toDouble()
    val isSingular = amount == 1.0
    val unit = input[1].toLowerCase()
    val result = when (unit) {
        "m","meter","meters" -> buildResult(amount,if (isSingular) "meter" else "meters",amount)
        "km","kilometer","kilometers" -> buildResult(amount,if (isSingular) "kilometer" else "kilometers",amount * 1000)
        "cm","centimeter","centimeters" -> buildResult(amount,if (isSingular) "centimeter" else "centimeters",amount / 100)
        "mm","millimeter","millimeters" -> buildResult(amount,if (isSingular) "millimeter" else "millimeters",amount / 1000)
        "mi","mile","miles" -> buildResult(amount,if (isSingular) "mile" else "miles",amount * 1609.35)
        "yd","yard","yards" -> buildResult(amount,if (isSingular) "yard" else "yards",amount * 0.9144)
        "ft","foot","feet" -> buildResult(amount,if (isSingular) "foot" else "feet",amount * 0.3048)
        "in","inch","inches" -> buildResult(amount,if (isSingular) "inch" else "inches",amount * 0.0254)
        else -> "unknown"
    }
    println(result)
}

fun buildResult(providedAmount : Double,providedUnit: String,amount : Double) : String {
    return "$providedAmount $providedUnit is $amount ${if (amount == 1.0) "meter" else "meters"}"
}