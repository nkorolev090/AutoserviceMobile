package com.project.autoservicemobile.ui.registrations.details

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.databinding.FragmentRegistrationDetailsBinding
import com.project.autoservicemobile.ui.registrations.RegistrationsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationDetailsBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: FragmentRegistrationDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val _viewModel: RegistrationDetailsViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentRegistrationDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    private fun setup(){
        binding.totalText.text = _viewModel.totalText
        binding.totalValueText.text = _viewModel.registration.value!!.price

        binding.regServicesRecycler.layoutManager = LinearLayoutManager(context)
        _viewModel.registration.observe(viewLifecycleOwner){
            binding.textTitle.text = it.registrationTitle
            binding.completeOnText.text = it.statusTitle
            //binding.regServicesRecycler.adapter = RegServicesRecyclerAdapter(it.services.orEmpty(),
//                {item -> _viewModel.onWarrantyBtnClick(item)},
//                {item -> _viewModel.onFavoritesBtnClick(item)})
        }
    }
    companion object {
        const val TAG = "RegistrationDetailsBottomSheetDialog"
    }
}