enum class Currency(val displayName : String) {
    EURO("Euro"),
    FRANC("CFA Franc"),
    ECD("Eastern Caribbean Dollar"),
    CAD("Canadian Dollar"),
    AUD("Australian Dollar"),
    REAL("Brazilian Dollar"),
    UNKNOWN("Unknown")
}
enum class Country(val currency : Currency) {
    GERMANY(Currency.EURO),
    MALI(Currency.FRANC),
    DOMINICA(Currency.ECD),
    CANADA(Currency.CAD),
    SPAIN(Currency.EURO),
    AUSTRALIA(Currency.AUD),
    BRAZIL(Currency.REAL),
    SENEGAL(Currency.FRANC),
    FRANCE(Currency.EURO),
    GRENADA(Currency.ECD),
    KIRIBATI(Currency.AUD);

    companion object {
        fun findCurrency(countryName : String) : Currency {
            val result =  values().find {
                it.name == countryName.toUpperCase()
            }
            return result?.currency ?: Currency.UNKNOWN
        }
    }
}

fun main() {
    val (currency1,currency2) = readLine()!!.split(" ").map {Country.findCurrency(it)}
    println(if (currency1 == Currency.UNKNOWN || currency2 == Currency.UNKNOWN) false else currency1 == currency2)
}