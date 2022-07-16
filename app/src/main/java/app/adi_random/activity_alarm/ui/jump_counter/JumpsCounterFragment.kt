package app.adi_random.activity_alarm.ui.jump_counter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.adi_random.activity_alarm.databinding.FragmentJumpsCounterBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JumpsCounterFragment : Fragment() {

    companion object {
        fun newInstance() = JumpsCounterFragment()
    }

    private val viewModel by viewModels<JumpsCounterViewModel>()
    private lateinit var binding: FragmentJumpsCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJumpsCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sensorManager =
            requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val linearAccelerationSensor =
            sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        val rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        sensorManager.registerListener(object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                viewModel.onLinearAccelerationChange(event)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }, linearAccelerationSensor, SensorManager.SENSOR_DELAY_NORMAL)

        sensorManager.registerListener(object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                viewModel.onRotationVectorChange(event)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }
        }, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.jumps.collect {
                        binding.textView.text = it.toString()
                    }
                }

                launch {
                    viewModel.userPosition.collect {
                        binding.textView2.text = it.name
                    }
                }

            }

        }
    }
}

