package app.adi_random.activity_alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.adi_random.activity_alarm.ui.jump_counter.JumpsCounterFragment
import app.adi_random.activity_alarm.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, JumpsCounterFragment.newInstance())
                .commitNow()
        }
    }
}