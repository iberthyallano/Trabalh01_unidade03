package pt.apt.trabalho01unidade03.UI.Home

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import pt.apt.trabalho01unidade03.R
import pt.apt.trabalho01unidade03.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        binding.buttonAcelerometro.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToAcelerometroFragment());
        }

        binding.buttonLuz.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToLuzFragment());
        }

        binding.buttonGiroscopio.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToGiroscopioFragment());
        }

        binding.buttonProximidade.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToProximidadeFragment());
        }

        setHasOptionsMenu(true);

        return binding.root;
    }
}