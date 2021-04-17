package pt.apt.trabalho01unidade03.UI.Luz

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
import pt.apt.trabalho01unidade03.databinding.FragmentLuzBinding

class LuzFragment : Fragment() , SensorEventListener {

        lateinit var binding: FragmentLuzBinding;

        lateinit var sensorManager: SensorManager
        var sensor: Sensor? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_luz, container, false);
            setHasOptionsMenu(true);

            setSensor()

            return binding.root;
        }

        private fun setSensor() {
            sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
                val luz = event.values[0]

                binding.sensor.text = "Sensor: $luz\n${grauDeLuminosidade(luz)}"
                binding.circularProgressBar.setProgressWithAnimation(luz)
            }
        }

        private fun grauDeLuminosidade(luz: Float): String {
            return when (luz.toInt()) {
                0 -> "Não há luz"
                in 1..60 -> "Pouco iluminado"
                in 61..5000 -> "Normal"
                in 5001..25000 -> "Muito iluminado"
                else -> "Você vai ficar cego de tanta luz"
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            return
        }

        override fun onResume() {
            super.onResume()
            // Register a listener for the sensor.
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        }


        override fun onPause() {
            super.onPause()
            sensorManager.unregisterListener(this)
        }

}