package pt.apt.trabalho01unidade03.UI.Proximidade

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
import pt.apt.trabalho01unidade03.databinding.FragmentProximidadeBinding


class ProximidadeFragment : Fragment(), SensorEventListener {
    lateinit var binding: FragmentProximidadeBinding;

    lateinit var sensorManager: SensorManager;
    var sensor: Sensor? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_proximidade, container, false);

        setHasOptionsMenu(true);
        setSensor();

        return binding.root;
    }

    fun setSensor(){
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_PROXIMITY){
            val distance = event.values[0];
            binding.textViewDistancia.text = "Distancia = ${distance}";
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        sensor?.also { proximity ->
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_FASTEST)
        }

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this);
    }

}