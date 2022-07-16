package app.adi_random.activity_alarm.dependencies

import androidx.room.Room
import app.adi_random.activity_alarm.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app-database").build()
    }
}