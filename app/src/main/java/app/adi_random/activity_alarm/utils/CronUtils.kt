package app.adi_random.activity_alarm.utils

import com.cronutils.model.Cron
import com.cronutils.model.definition.CronDefinition
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.parser.CronParser


object CronUtils {
    val cronDefinition =
        CronDefinitionBuilder
            .defineCron()
            .withSeconds().and()
            .withMinutes().and()
            .withHours().and()
            .withDayOfWeek().and()
            .instance()

    fun parseString(cron: String):Cron {
        return CronParser(cronDefinition).run {
            parse(cron)
        }
    }
}
