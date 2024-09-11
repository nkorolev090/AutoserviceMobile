package com.project.autoservicemobile.ui.login

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicemobile.databinding.FragmentSignInOrUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInOrUpBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: FragmentSignInOrUpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val _viewModel: SignInOrUpViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentSignInOrUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    private fun setup(){
        binding.titleText.text = _viewModel.title

        binding.signInBtn.text = _viewModel.signInBtn
        binding.signInBtn.setOnClickListener(View.OnClickListener {_ ->
            _viewModel.openSingInBottomSheet()
        })

        binding.signUpBtn.text = _viewModel.signUpBtn
        binding.signUpBtn.setOnClickListener(View.OnClickListener {_ ->
            _viewModel.openSingUpBottomSheet()
        })

        binding.descriptionSignInText.text = _viewModel.signInDescription
        binding.descriptionSignUpText.text = _viewModel.signUpDescription
    }
    companion object {
        const val TAG = "SignInOrUpBottomSheetDialog"
    }
}