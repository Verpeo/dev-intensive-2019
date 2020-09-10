package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}


fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND) : Date{
    var time = this.time
    time += when (units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits{
    SECOND {
        override fun plural(num: Int): String {
            return "${num} ${when (num_declension(num)){1->"секунда";2->"секунды";3->"секунд" else -> ""}}"
        }
    },
    MINUTE {
        override fun plural(num: Int): String {
            return "${num} ${when (num_declension(num)){1->"минута";2->"минуты";3->"минут" else -> ""}}"
        }
    },
    HOUR {
        override fun plural(num: Int): String {
            return "${num} ${when (num_declension(num)){1->"час";2->"часа";3->"часов" else -> ""}}"
        }
    },
    DAY {
        override fun plural(num: Int): String {
            return "${num} ${when (num_declension(num)){1->"день";2->"дня";3->"дней" else -> ""}}"
        }
    };
    abstract fun plural(num:Int):String
}

private fun num_declension(num:Int):Int{
    return if (num%100 in 11 .. 19)  3
    else {
        when (num % 10) {
            1 -> 1
            2, 3, 4 -> 2
            else -> 3
        }
    }
}


fun Date.humanizeDiff(date:Date = Date()): String{
    //val curDate = Date()
    var diff = (date.time - this.time) / 1000
    if (diff>=0) {
        return when {
            diff in 0..1 -> "только что"
            diff in 2..45 -> "несколько секунд назад"
            diff in 46..75 -> "минуту назад"
            diff > 75 && diff <= 45 * 60 -> "${TimeUnits.MINUTE.plural(diff.toInt() / 60)} назад"
            diff > 45 * 60 && diff <= 75 * 60 -> "час назад"
            diff > 75 * 60 && diff <= 22 * 60 * 60 -> "${TimeUnits.HOUR.plural(diff.toInt() / 60 / 60)} назад"
            diff > 22 * 60 * 60 && diff <= 26 * 60 * 60 -> "день назад"
            diff > 26 * 60 * 60 && diff <= 360 * 24 * 60 * 60 -> "${TimeUnits.DAY.plural(diff.toInt() / 60 / 60 / 24)} назад"
            diff > 360 * 24 * 60 * 60 -> "более года назад"
            else -> ""
        }
    }
    else {
        diff *= -1
        return when {
            diff in 0..1 -> "только что"
            diff in 2..45 -> "через несколько секунд"
            diff in 46..75 -> "через минуту"
            diff > 75 && diff <= 45 * 60 -> "через ${TimeUnits.MINUTE.plural(diff.toInt() / 60)}"
            diff > 45 * 60 && diff <= 75 * 60 -> "через час"
            diff > 75 * 60 && diff <= 22 * 60 * 60 -> "через ${TimeUnits.HOUR.plural(diff.toInt() / 60 / 60)}"
            diff > 22 * 60 * 60 && diff <= 26 * 60 * 60 -> "через день"
            diff > 26 * 60 * 60 && diff <= 360 * 24 * 60 * 60 -> "через ${TimeUnits.DAY.plural(diff.toInt() / 60 / 60 / 24)}"
            diff > 360 * 24 * 60 * 60 -> "более чем через год"
            else -> ""
        }
    }
    //return "$diff"
}