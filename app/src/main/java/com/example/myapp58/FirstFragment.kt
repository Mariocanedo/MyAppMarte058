package com.example.myapp58

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.myapp58.ViewModel.MarsViewModel
import com.example.myapp58.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment()  {



    private lateinit var _binding: FragmentFirstBinding
    private val viewModel : MarsViewModel by activityViewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
  //  private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return _binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    /********************* PARTE 1 SIN CORRUTINAS NI DAO*******************/
//     viewModel.liveDatafromInternet.observe(viewLifecycleOwner, Observer {
//          it?.let{
//
//              _binding.textviewFirst.text = it.toString()
//          }
//      })


        /************  PARTE 2este metódo funciona despiues de la actualización de las corrutinas*******/
 viewModel.allTerrains.observe(viewLifecycleOwner, Observer {
         it?.let {

            _binding.textviewFirst.text=it.toString()
       }
     })

        _binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
      //  _binding = null
    }
}