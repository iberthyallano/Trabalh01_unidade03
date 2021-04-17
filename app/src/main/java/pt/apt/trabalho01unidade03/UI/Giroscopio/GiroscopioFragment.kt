package pt.apt.trabalho01unidade03.UI.Giroscopio

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Half.EPSILON
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import pt.apt.trabalho01unidade03.R
import pt.apt.trabalho01unidade03.databinding.FragmentGiroscopioBinding
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class GiroscopioFragment : Fragment(), SensorEventListener{

    lateinit var binding: FragmentGiroscopioBinding;

    lateinit var sensorManager: SensorManager;
    var sensor: Sensor? = null;

    private val NS2S = 1.0f / 1000000000.0f
    private val deltaRotationVector = FloatArray(4) { 0f }
    private var timestamp: Float = 0f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_giroscopio, container, false);

        setHasOptionsMenu(true);
        setSensor();

        return binding.root;
    }

    fun setSensor(){
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_GYROSCOPE){
            if (timestamp != 0f && event != null) {
                val dT = (event.timestamp - timestamp) * NS2S

                var axisX: Float = event.values[0]
                var axisY: Float = event.values[1]
                var axisZ: Float = event.values[2]

                val omegaMagnitude: Float = sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ)

                if (omegaMagnitude > EPSILON) {
                    axisX /= omegaMagnitude
                    axisY /= omegaMagnitude
                    axisZ /= omegaMagnitude
                }

                val thetaOverTwo: Float = omegaMagnitude * dT / 2.0f
                val sinThetaOverTwo: Float = sin(thetaOverTwo)
                val cosThetaOverTwo: Float = cos(thetaOverTwo)
                deltaRotationVector[0] = sinThetaOverTwo * axisX
                deltaRotationVector[1] = sinThetaOverTwo * axisY
                deltaRotationVector[2] = sinThetaOverTwo * axisZ
                deltaRotationVector[3] = cosThetaOverTwo

                binding.textViewX.text = (sinThetaOverTwo * axisX).toString()
                binding.textViewY.text = (sinThetaOverTwo * axisY).toString()
                binding.textViewZ.text = (sinThetaOverTwo * axisZ).toString()
            }
            timestamp = event?.timestamp?.toFloat() ?: 0f
            val deltaRotationMatrix = FloatArray(9) { 0f }
            SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this);
    }

}