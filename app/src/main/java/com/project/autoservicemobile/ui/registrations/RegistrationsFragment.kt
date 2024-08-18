package com.project.autoservicemobile.ui.registrations

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.autoservicemobile.R
import com.project.autoservicemobile.databinding.FragmentProfileBinding
import com.project.autoservicemobile.databinding.FragmentRegistrationsBinding
import com.project.autoservicemobile.ui.cart.CartRecyclerAdapter

class RegistrationsFragment : Fragment() {

    private var _binding: FragmentRegistrationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: RegistrationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    private fun setup(){
        binding.appBar.setLeftOnClickListener(View.OnClickListener {
            _viewModel.onGoToHomeClick()
        })

        binding.registrationsRecycler.layoutManager = LinearLayoutManager(context)
        _viewModel.registrations.observe(viewLifecycleOwner){
            binding.registrationsRecycler.adapter = RegistrationsRecyclerAdapter(it
            ) { item ->
                _viewModel.openRegistrationDetails(item)
            }
        }
    }
}