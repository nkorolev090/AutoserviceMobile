package com.project.autoservicemobile.ui.login.signIn

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicemobile.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: FragmentSignInBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val _viewModel: SignInViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    private fun setup(){
        binding.titleText.text = _viewModel.title
        binding.emailLayout.hint = _viewModel.emailHintText
        binding.passwordLayout.hint = _viewModel.passwordHintText

        binding.signInBtn.text = _viewModel.signInBtnText
        binding.signInBtn.setOnClickListener(View.OnClickListener {
            _viewModel.onSignInBtnClick()
        })
    }

    companion object {
        const val TAG = "SignInBottomSheetDialog"
    }
}