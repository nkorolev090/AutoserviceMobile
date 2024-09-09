package com.project.autoservicemobile.ui.login.signUp

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicemobile.databinding.FragmentSignUpBinding

class SignUpBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: FragmentSignUpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val _viewModel: SignUpViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val bottomSheetBehavior = BottomSheetBehavior.from(binding.root)
//        bottomSheetBehavior.skipCollapsed = true
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        setup()

        return root;
    }

    private fun setup(){

    }
    companion object {
        const val TAG = "SignUpBottomSheetDialog"
    }
}