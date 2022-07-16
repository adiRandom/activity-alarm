package app.adi_random.activity_alarm.models

import androidx.room.PrimaryKey
import app.adi_random.activity_alarm.data.entities.AlarmEntity
import app.adi_random.activity_alarm.utils.CronUtils
import app.adi_random.activity_alarm.utils.getHour
import app.adi_random.activity_alarm.utils.getMinute
import app.adi_random.activity_alarm.utils.getRepeatingDays
import com.cronutils.model.Cron
import com.cronutils.model.field.CronFieldName

class Alarm(
    val name: String?,
    val isEnabled: Boolean,
    val qrDisarmData: String?,
    val jumpingJacksGoal: Int?,
    private val cronExpression: String
) {
    val hour: Number
    val minute: Number
    val repeatsOn: List<Int>
    private val cron: Cron = CronUtils.parseString(cronExpression)

    init {
        hour = cron.getHour()
        minute = cron.getMinute()
        repeatsOn = cron.getRepeatingDays()
    }

    constructor(alarmEntity: AlarmEntity) : this(
        alarmEntity.name,
        alarmEntity.isEnabled,
        alarmEntity.qrDisarmData,
        alarmEntity.jumpingJacksGoal,
        alarmEntity.cronExpression
    )

    fun asEntity(): AlarmEntity =
        AlarmEntity(null, cronExpression, name, isEnabled, qrDisarmData, jumpingJacksGoal)
}