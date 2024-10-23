package com.project.autoservicemobile.ui.profile.cars

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentCarsBinding
import com.project.autoservicemobile.databinding.FragmentProfileBinding
import com.project.autoservicemobile.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarsFragment : Fragment() {

    private val _viewModel: CarsViewModel by viewModels()

    private var _binding: FragmentCarsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    override fun onResume() {
        super.onResume()

        _viewModel.updateCars(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("CarsFragment", "Error! $message")
            }
        })
    }

    private fun setup() {
        with(binding) {
        }
    }
}