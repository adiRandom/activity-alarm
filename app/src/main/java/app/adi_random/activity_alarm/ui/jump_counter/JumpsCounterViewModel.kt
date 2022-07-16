package app.adi_random.activity_alarm.ui.jump_counter

import android.hardware.SensorEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs

class JumpsCounterViewModel : ViewModel() {

    companion object {
        const val ACCELERATION_JUMP_UP_THRESHOLD = 20f
        const val ACCELERATION_JUMP_DOWN_THRESHOLD = 10f
        const val ROTATION_DIFFERENCE = 1.5f
        const val ROTATION_BASELINE_EPSILON = 0.1f
        const val ACCELERATION_BASELINE_EPSILON = 0.1f
        private const val Z_LINEAR_ACCELERATION_BASELINE: Float = 0f
        private const val Y_ROTATION_VECTOR_BASELINE = -0.6f
    }

    enum class Positions {
        RESTING_HANDS_DOWN,
        HALF_JUMP_HANDS_UP,
        DONE_JUMP_AWAITING_HANDS_UP,
        RESTING_HANDS_UP,
        HALF_JUMP_HANDS_DOWN,
        COOLDOWN,
        DONE_JUMPING_AWAITING_HANDS_DOWN
    }

    private val _jumps = MutableStateFlow<Int>(0)
    val jumps = _jumps.asStateFlow()


    // TODO: Configure from alarm set screen
    private val _targetJumps = MutableStateFlow(0)
    val targetJumps = _targetJumps.asStateFlow()

    private val _userPosition = MutableStateFlow<Positions>(Positions.RESTING_HANDS_DOWN)
    val userPosition = _userPosition.asStateFlow()


    fun onLinearAccelerationChange(sensorEvent: SensorEvent?) {

        viewModelScope.launch {
            if (sensorEvent == null) {
                return@launch
            }

            val zAcceleration = sensorEvent.values[2]

            if (_userPosition.value == Positions.RESTING_HANDS_DOWN &&
                zAcceleration - Z_LINEAR_ACCELERATION_BASELINE >= ACCELERATION_JUMP_UP_THRESHOLD
            ) {
                _userPosition.emit(Positions.HALF_JUMP_HANDS_UP)

                // zAcceleration is now negative
            } else if (_userPosition.value == Positions.HALF_JUMP_HANDS_UP &&
                zAcceleration + Z_LINEAR_ACCELERATION_BASELINE >= ACCELERATION_JUMP_DOWN_THRESHOLD
            ) {
                _userPosition.emit(Positions.DONE_JUMP_AWAITING_HANDS_UP)
            } else if (_userPosition.value == Positions.RESTING_HANDS_UP &&
                zAcceleration - Z_LINEAR_ACCELERATION_BASELINE >= ACCELERATION_JUMP_UP_THRESHOLD
            ) {
                _userPosition.emit(Positions.HALF_JUMP_HANDS_DOWN)
            } else if (_userPosition.value == Positions.HALF_JUMP_HANDS_DOWN &&
                zAcceleration + Z_LINEAR_ACCELERATION_BASELINE >= ACCELERATION_JUMP_DOWN_THRESHOLD
            ) {
                _userPosition.emit( Positions.DONE_JUMPING_AWAITING_HANDS_DOWN)
            } else if (_userPosition.value == Positions.COOLDOWN
                && abs(zAcceleration - Z_LINEAR_ACCELERATION_BASELINE) <= ACCELERATION_BASELINE_EPSILON
            ) {
                _userPosition.emit(Positions.RESTING_HANDS_DOWN)
            }
        }

    }

    fun onRotationVectorChange(sensorEvent: SensorEvent?) {
        viewModelScope.launch {
            if (sensorEvent == null) {
                return@launch
            }

            val yRotation = sensorEvent.values[1]


            if (_userPosition.value == Positions.DONE_JUMP_AWAITING_HANDS_UP &&
                yRotation - Y_ROTATION_VECTOR_BASELINE >= ROTATION_DIFFERENCE
            ) {
                _userPosition.emit( Positions.RESTING_HANDS_UP)
            } else if (_userPosition.value == Positions.DONE_JUMPING_AWAITING_HANDS_DOWN &&
                abs(yRotation - Y_ROTATION_VECTOR_BASELINE) <= ROTATION_BASELINE_EPSILON
            ) {
                _userPosition.emit(Positions.COOLDOWN)
                _jumps.run {
                    emit(value + 1)
                }
            }

        }
    }

}