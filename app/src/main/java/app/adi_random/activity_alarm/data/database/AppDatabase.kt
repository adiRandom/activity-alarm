package app.adi_random.activity_alarm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.adi_random.activity_alarm.data.dao.AlarmDao
import app.adi_random.activity_alarm.data.entities.AlarmEntity


@Database(entities = [AlarmEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getAlarmDao(): AlarmDao
}