package app.adi_random.activity_alarm

import android.app.Application
import app.adi_random.activity_alarm.dependencies.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class AlarmApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@AlarmApplication)
            // use modules
            modules(dataModule)
        }

    }
}