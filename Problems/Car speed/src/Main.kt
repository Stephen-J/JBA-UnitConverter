fun main(args: Array<String>) {
    val speed = readLine()!!.toInt()
    val limit = readLine()!!
    val result = if (limit == "no limit") checkLimit(speed) else checkLimit(speed,limit.toInt())
    println(result)

}

fun checkLimit(speed : Int,limit : Int = 60) : String {
    return if (speed > limit) {
        "Exceeds the limit by ${speed - limit} kilometers per hour"
    } else "Within the limit"
}