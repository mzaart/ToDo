package mzaart.com.todo.utils

import java.text.SimpleDateFormat
import java.util.*

fun unixTime2String(time: Long): String {
    val diff = time - System.currentTimeMillis()

    return when {
        diff < 7*24*3600*1000 -> SimpleDateFormat("EEEE")
        diff/1000 < 365*24*3600 -> SimpleDateFormat("dd MMM")
        else -> SimpleDateFormat("dd/MM/yyyy")
    }.format(Date(time))
}
