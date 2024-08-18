package com.project.autoservicemobile.ui.registrations.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicemobile.databinding.FragmentRegistrationDetailsBinding
import com.project.autoservicemobile.ui.registrations.RegistrationsRecyclerAdapter

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
        //return inflater.inflate(R.layout.fragment_registration_details, container, false)
    }

    private fun setup(){
        binding.totalText.text = _viewModel.totalText
        binding.totalValueText.text = _viewModel.registration.value!!.price

        binding.regServicesRecycler.layoutManager = LinearLayoutManager(context)
        _viewModel.registration.observe(viewLifecycleOwner){
            binding.textTitle.text = it.registrationTitle
            binding.completeOnText.text = it.completedDate
            binding.regServicesRecycler.adapter = RegServicesRecyclerAdapter(it.services.orEmpty(),
                {item -> _viewModel.onWarrantyBtnClick(item)},
                {item -> _viewModel.onFavoritesBtnClick(item)})
        }
    }
    companion object {
        const val TAG = "ModalBottomSheet"
    }
}