package app.adi_random.activity_alarm.utils

import com.cronutils.model.Cron
import com.cronutils.model.definition.CronDefinition
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.parser.CronParser


object CronUtils {
    val cronDefinition =
        CronDefinitionBuilder
            .defineCron()
            .withMinutes().and()
            .withHours().and()
            .withDayOfWeek().and()
            .instance()

    fun parseString(cron: String): Cron {
        return CronParser(cronDefinition).run {
            parse(cron)
        }
    }
}


fun Cron.getHour(): Int {
    val tokens = asString().split(" ")
    return tokens[1].toInt()
}


fun Cron.getMinute(): Int {
    val tokens = asString().split(" ")
    return tokens[0].toInt()
}

fun Cron.getRepeatingDays(): List<Int> {
    val tokens = asString().split(" ")
    return tokens[2].split(",").map(String::toInt)
}