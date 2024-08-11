package com.project.autoservicemobile.ui.loyalty

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.autoservicemobile.R
import com.project.autoservicemobile.databinding.FragmentCartBinding
import com.project.autoservicemobile.databinding.FragmentLoyaltyBinding
import com.project.autoservicemobile.databinding.FragmentServicesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoyaltyFragment : Fragment() {

    private var _binding: FragmentLoyaltyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: LoyaltyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoyaltyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}