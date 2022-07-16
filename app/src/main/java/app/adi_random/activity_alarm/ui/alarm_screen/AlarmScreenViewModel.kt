package app.adi_random.activity_alarm.ui.alarm_screen

import androidx.lifecycle.ViewModel

class AlarmScreenViewModel : ViewModel() {

    lateinit var alarmQrCodeData:String

    fun tryDisarmQr(qrData:String?):Boolean{
        return qrData == alarmQrCodeData
    }

}
