package app.adi_random.activity_alarm.ui.alarm_screen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.viewModels
import app.adi_random.activity_alarm.R
import app.adi_random.activity_alarm.databinding.FragmentAlarmScreenBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class AlarmScreenFragment : Fragment() {

    companion object {
        fun newInstance() = AlarmScreenFragment()
    }

    private lateinit var binding:FragmentAlarmScreenBinding
    private val viewModel by viewModels<AlarmScreenViewModel>()
    private lateinit var qrScannerLauncher: ActivityResultLauncher<ScanOptions>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmScreenBinding.inflate(inflater,container,false
        )

        binding.onLaunchQrPress = View.OnClickListener {
            val options = ScanOptions()
            options.setOrientationLocked(false)
            qrScannerLauncher.launch(options)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        qrScannerLauncher = registerForActivityResult(ScanContract()){
            onScanQrForDisarm(it.contents)
        }

        binding.button
    }

    private fun onScanQrForDisarm(data:String?){
        if(viewModel.tryDisarmQr(data)){
            // TODO: dismiss alarm
            Toast.makeText(requireContext(),"Valid",Toast.LENGTH_SHORT).show()
        }else{
            //TODO: Show error banner
            Toast.makeText(requireContext(),"Invalid",Toast.LENGTH_SHORT).show()
        }

    }


}