package com.project.autoservicemobile.ui.login.signUp

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicemobile.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        setup()

        return root;
    }

    private fun setup(){
        binding.titleText.text = _viewModel.title

        binding.nameLayout.hint = _viewModel.nameHintText
        binding.emailLayout.hint = _viewModel.emailHintText
        binding.passwordLayout.hint = _viewModel.passwordHintText
        binding.passwordApplyLayout.hint = _viewModel.passwordApplyHintText

        binding.signUpBtn.text = _viewModel.signUpBtnText
        binding.signUpBtn.setOnClickListener(View.OnClickListener {
            _viewModel.onSignUpBtnClick()
        })
    }
    companion object {
        const val TAG = "SignUpBottomSheetDialog"
    }
}