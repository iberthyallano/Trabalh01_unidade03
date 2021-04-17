package pt.apt.trabalho01unidade03.UI.Acelerometro

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
import androidx.databinding.DataBindingUtil
import pt.apt.trabalho01unidade03.R
import pt.apt.trabalho01unidade03.databinding.FragmentAcelerometroBinding

class AcelerometroFragment : Fragment(), SensorEventListener {
    lateinit var binding: FragmentAcelerometroBinding;

    lateinit var sensorManager: SensorManager;
    var sensor: Sensor? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_acelerometro, container, false);

        setHasOptionsMenu(true);
        setSensor();

        return binding.root;
    }

    fun setSensor(){
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            val alpha = 0.8f

            var gravity = arrayOf(0.0f, 0.0f, 0.0f);

            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

            binding.textViewX.text = (event.values[0] - gravity[0]).toString();
            binding.textViewY.text = (event.values[1] - gravity[1]).toString();
            binding.textViewZ.text = (event.values[2] - gravity[2]).toString();
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