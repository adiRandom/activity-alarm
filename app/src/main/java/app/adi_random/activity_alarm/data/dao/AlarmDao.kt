package app.adi_random.activity_alarm.data.dao

import androidx.room.*
import app.adi_random.activity_alarm.data.entities.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm")
    suspend fun getAll(): Flow<List<AlarmEntity>>

    @Update
    fun updateAlarm(alarm: AlarmEntity)

    @Delete
    fun deleteAlarm(alarm: AlarmEntity)

    @Insert
    fun insertAlarm(alarm: AlarmEntity)
}