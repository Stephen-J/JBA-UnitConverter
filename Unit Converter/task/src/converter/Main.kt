package converter

enum class MUnitType {
    LENGTH,
    TEMP,
    WEIGHT,
    UNK
}

enum class MUnit(val type : MUnitType,val singularDisplay : String,val multipleDisplay : String) {
    M(MUnitType.LENGTH,"meter","meters"),
    KM(MUnitType.LENGTH,"kilometer","kilometers"),
    CM(MUnitType.LENGTH,"centimeter","centimeters"),
    MM(MUnitType.LENGTH,"millimeter","millimeters"),
    MI(MUnitType.LENGTH,"mile","miles"),
    YD(MUnitType.LENGTH,"yard","yards"),
    FT(MUnitType.LENGTH,"foot","feet"),
    IN(MUnitType.LENGTH,"inch","inches"),

    G(MUnitType.WEIGHT,"gram","grams"),
    KG(MUnitType.WEIGHT,"kilogram","kilograms"),
    MG(MUnitType.WEIGHT,"milligram","milligrams"),
    LB(MUnitType.WEIGHT,"pound","pounds"),
    OZ(MUnitType.WEIGHT,"ounce","ounces"),

    C(MUnitType.TEMP,"degree Celsius","degrees Celsius"),
    F(MUnitType.TEMP,"degree Fahrenheit","degrees Fahrenheit"),
    K(MUnitType.TEMP,"kelvin","kelvins"),

    UNK(MUnitType.UNK,"???","???"), // valid parse but unknown unit
    NAU(MUnitType.UNK,"",""); // Not a Unit , used for parse errors

    companion object {
        fun fromToken(token : String) = when (token) {
            "m","meter","meters" -> M
            "km","kilometer","kilometers" -> KM
            "cm","centimeter","centimeters" -> CM
            "mm","millimeter","millimeters" -> MM
            "mi","mile","miles" -> MI
            "yd","yard","yards" -> YD
            "ft","foot","feet" -> FT
            "in","inch","inches" -> IN
            "g","gram","grams" -> G
            "kg","kilogram","kilograms" -> KG
            "mg","milligram","milligrams" -> MG
            "lb","pound","pounds" -> LB
            "oz","ounce","ounces" -> OZ
            "degree celsius","degrees celsius","celsius","dc","c" -> C
            "degree fahrenheit","degrees fahrenheit","fahrenheit","df","f" -> F
            "kelvin","kelvins","k" -> K
            else -> UNK
        }

        fun displayName(amount : Double,unit : MUnit) = if (amount == 1.0) unit.singularDisplay else unit.multipleDisplay
        fun errorName(unit : MUnit) = displayName(100.0,unit)
    }
}

fun convertToMeters(amount: Double, unit: MUnit): Double {
    return when (unit) {
        MUnit.M -> amount
        MUnit.KM -> amount * 1000
        MUnit.CM -> amount / 100
        MUnit.MM -> amount / 1000
        MUnit.MI -> amount * 1609.35
        MUnit.YD -> amount * 0.9144
        MUnit.FT -> amount * 0.3048
        MUnit.IN -> amount * 0.0254
        else -> Double.NaN
    }
}

fun metersToLengthUnit(amount: Double, unit: MUnit): Double {
    return when (unit) {
        MUnit.M -> amount
        MUnit.KM -> amount / 1000
        MUnit.CM -> amount * 100
        MUnit.MM -> amount * 1000
        MUnit.MI -> amount / 1609.35
        MUnit.YD -> amount / 0.9144
        MUnit.FT -> amount / 0.3048
        MUnit.IN -> amount / 0.0254
        else -> 0.0
    }
}

fun convertToGrams(amount: Double, unit: MUnit): Double {
    return when (unit) {
        MUnit.G -> amount
        MUnit.KG -> amount * 1000
        MUnit.MG -> amount / 1000
        MUnit.LB -> amount * 453.592
        MUnit.OZ -> amount * 28.3495
        else -> Double.NaN
    }
}

fun gramsToWeightUnit(amount: Double, unit: MUnit): Double {
    return when (unit) {
        MUnit.G -> amount
        MUnit.KG -> amount / 1000
        MUnit.MG -> amount * 1000
        MUnit.LB -> amount / 453.592
        MUnit.OZ -> amount / 28.3495
        else -> Double.NaN
    }
}



fun parseAmount(input : List<String>) : Pair<Double,List<String>> {
    var result = Double.NaN
    try {
        if (input.size > 1) result = input.first().toDouble()
    }catch(ex : Exception) {
        // ignore all exceptions
    }
    return Pair(result,input.drop(1))
}

fun parseUnit(input : List<String>) : Pair<MUnit,List<String>> {
    return if (input.isNotEmpty()) {
        if ((input.first() == "degree" || input.first() == "degrees") && input.size >= 2) {
            Pair(MUnit.fromToken(input.subList(0,2).joinToString(" ")),input.drop(2))
        } else {
            Pair(MUnit.fromToken(input.first()),input.drop(1))
        }
    } else {
        Pair(MUnit.NAU,input)
    }
}

fun handleTempConversion(amount: Double, fromUnit: MUnit, toUnit: MUnit): Double {
    return when (fromUnit) {
        MUnit.C -> when (toUnit) {
            MUnit.C -> amount
            MUnit.F -> amount * (9.0 / 5.0) + 32.0
            MUnit.K -> amount + 273.15
            else -> Double.NaN
        }
        MUnit.F -> when (toUnit) {
            MUnit.C -> (amount - 32.0) * (5.0 / 9.0)
            MUnit.F -> amount
            MUnit.K -> (amount + 459.67) * (5.0 / 9.0)
            else -> Double.NaN
        }
        MUnit.K -> when (toUnit) {
            MUnit.C -> amount - 273.15
            MUnit.F -> amount * (9.0 / 5.0) - 459.67
            MUnit.K -> amount
            else -> Double.NaN
        }
        else -> Double.NaN
    }
}

fun handleConversion(amount : Double,fromUnit : MUnit,toUnit : MUnit) : Double {
    var result : Double = Double.NaN
    if (fromUnit.type == toUnit.type){
        result = when (fromUnit.type) {
            MUnitType.TEMP -> handleTempConversion(amount,fromUnit,toUnit)
            MUnitType.WEIGHT -> gramsToWeightUnit(convertToGrams(amount,fromUnit),toUnit)
            MUnitType.LENGTH -> metersToLengthUnit(convertToMeters(amount,fromUnit),toUnit)
            else -> Double.NaN
        }
    }
    return result
}

fun isExit(input : List<String>) = input.size == 1 && input[0] == "exit"

fun main() {
    do {
        print("Enter what you want to convert (or exit): ")
        val input = readLine()!!.toLowerCase().split(" ")
        val (amount,amountParsed) = parseAmount(input)
        val (fromUnit,fromUnitParsed) = parseUnit(amountParsed)
        val fromIgnore = fromUnitParsed.drop(1)
        val (toUnit,_) = parseUnit(fromIgnore)

        if (!isExit(input)) {
            when {
                amount.isNaN() || fromUnit == MUnit.NAU || toUnit == MUnit.NAU -> println("Parse error")
                fromUnit == MUnit.UNK || toUnit == MUnit.UNK || fromUnit.type != toUnit.type  -> {
                    println("Conversion from ${MUnit.errorName(fromUnit)} to ${MUnit.errorName(toUnit)} is impossible")
                }
                amount < 0 && fromUnit.type == MUnitType.LENGTH -> println("Length shouldn't be negative")
                amount < 0 && fromUnit.type == MUnitType.WEIGHT -> println("Weight shouldn't be negative")
                fromUnit.type == toUnit.type -> {
                    val result = handleConversion(amount, fromUnit, toUnit)
                    println("$amount ${MUnit.displayName(amount,fromUnit)} is $result ${MUnit.displayName(result,toUnit)}")
                }

            }
            println()
        }
    } while (!isExit(input))
}