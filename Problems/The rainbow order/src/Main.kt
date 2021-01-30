enum class Rainbow {
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    BLUE,
    INDIGO,
    VIOLET;

    companion object {
        fun findIndex(colorName: String): Int {
            var index = 0
            for ((i, color) in values().withIndex()) {
                if (color.name == colorName.toUpperCase()) index = i
            }
            return ++index
        }
    }
}

fun main() {
    val color = readLine()!!
    println(Rainbow.findIndex(color))
}