package converter


val lengthUnits = listOf("m","meter","meters",
    "km","kilometer","kilometers",
    "cm","centimeter","centimeters",
    "mm","millimeter","millimeters",
    "mi","mile","miles",
    "yd","yard","yards",
    "ft","foot","feet",
    "in","inch","inches")

val weightUnits = listOf("g","gram","grams",
    "kg","kilogram","kilograms",
    "mg","milligram","milligrams",
    "lb","pound","pounds",
    "oz","ounce","ounces")

const val unknownUnit = "???"

fun unitWord(unit : String,singular : Boolean = true) : String{
    val result = when (unit) {
        "m","meter","meters" -> if (singular) "meter" else "meters"
        "km","kilometer","kilometers" -> if (singular) "kilometer" else "kilometers"
        "cm","centimeter","centimeters" -> if (singular) "centimeter" else "centimeters"
        "mm","millimeter","millimeters" -> if (singular) "millimeter" else "millimeters"
        "mi","mile","miles" -> if (singular) "mile" else "miles"
        "yd","yard","yards" -> if (singular) "yard" else "yards"
        "ft","foot","feet" -> if (singular) "foot" else "feet"
        "in","inch","inches" -> if (singular) "inch" else "inches"
        "g","gram","grams" -> if (singular) "gram" else "grams"
        "kg","kilogram","kilograms" -> if (singular) "kilogram" else "kilograms"
        "mg","milligram","milligrams" -> if (singular) "milligram" else "milligrams"
        "lb","pound","pounds" -> if (singular) "pound" else "pounds"
        "oz","ounce","ounces" -> if (singular) "ounce" else "ounces"
        else -> unknownUnit
    }
    return result
}



fun handleLengthConversion(providedAmount : Double,providedUnit : String,requestedUnit : String) : Double {
    val isSingular = providedAmount == 1.0
    val result = metersToLengthUnit(convertToMeters(providedAmount,providedUnit),requestedUnit)
    return result
}

fun convertToMeters(amount : Double,unit : String) : Double {
    val result = when (unit) {
        "m","meter","meters" -> amount
        "km","kilometer","kilometers" -> amount * 1000
        "cm","centimeter","centimeters" -> amount / 100
        "mm","millimeter","millimeters" -> amount / 1000
        "mi","mile","miles" -> amount * 1609.35
        "yd","yard","yards" -> amount * 0.9144
        "ft","foot","feet" -> amount * 0.3048
        "in","inch","inches" -> amount * 0.0254
        else -> 0.0
    }
    return result
}

fun metersToLengthUnit(amount : Double,unit : String) : Double {
    val result = when (unit) {
        "m","meter","meters" -> amount
        "km","kilometer","kilometers" -> amount / 1000
        "cm","centimeter","centimeters" -> amount * 100
        "mm","millimeter","millimeters" -> amount * 1000
        "mi","mile","miles" -> amount / 1609.35
        "yd","yard","yards" -> amount / 0.9144
        "ft","foot","feet" -> amount / 0.3048
        "in","inch","inches" -> amount / 0.0254
        else -> 0.0
    }
    return result
}

fun handleWeightConversion(providedAmount: Double,providedUnit: String,requestedUnit: String) : Double {
    val isSingular = providedAmount == 1.0
    val result = gramsToWeightUnit(convertToGrams(providedAmount,providedUnit),requestedUnit)
    return result
}

fun convertToGrams(amount : Double,unit : String) : Double {
    val result = when (unit) {
        "g","gram","grams" -> amount
        "kg","kilogram","kilograms" -> amount * 1000
        "mg","milligram","milligrams" -> amount / 1000
        "lb","pound","pounds" -> amount * 453.592
        "oz","ounce","ounces" -> amount * 28.3495
        else -> Double.NaN
    }
    return result
}

fun gramsToWeightUnit(amount : Double,unit : String) : Double {
    val result = when (unit) {
        "g","gram","grams" -> amount
        "kg","kilogram","kilograms" -> amount / 1000
        "mg","milligram","milligrams" -> amount * 1000
        "lb","pound","pounds" -> amount / 453.592
        "oz","ounce","ounces" -> amount / 28.3495
        else -> Double.NaN
    }
    return result
}


fun buildResult(providedAmount : Double,providedUnit: String,amount : Double,requestedUnit : String) : String {
    return "$providedAmount ${unitWord(providedUnit,providedAmount == 1.0)} is $amount ${unitWord(requestedUnit,amount == 1.0)}"
}

fun main() {
    do {
        print("Enter what you want to convert (or exit): ")
        val input = readLine()!!.split(" ")
        if (input.size == 4) {
            val providedAmount = input[0].toDouble()
            val providedUnit = unitWord(input[1].toLowerCase())
            val requestedUnit = unitWord(input[3].toLowerCase())
            if (providedUnit == unknownUnit || requestedUnit == unknownUnit
                || (providedUnit in lengthUnits && requestedUnit in weightUnits)
                || (providedUnit in weightUnits && requestedUnit in lengthUnits)) {
                println("Conversion from ${unitWord(providedUnit,false)} to ${unitWord(requestedUnit,false)} is impossible")
            } else {
                val result = when (requestedUnit) {
                    in weightUnits -> handleWeightConversion(providedAmount,providedUnit,requestedUnit)
                    in lengthUnits -> handleLengthConversion(providedAmount,providedUnit,requestedUnit)
                    else -> Double.NaN
                }
                println(buildResult(providedAmount,providedUnit,result,requestedUnit))
            }
            println()
        } else if (input.size != 1) {
            println("Invalid Input")
            println()
        }
    } while (input.size != 1 && input[0].toLowerCase() != "exit")

}