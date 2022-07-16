package app.adi_random.activity_alarm.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val cronExpression: String,
    val name: String?,
    val isEnabled: Boolean,
    val qrDisarmData: String?,
    val jumpingJacksGoal: Int?
)