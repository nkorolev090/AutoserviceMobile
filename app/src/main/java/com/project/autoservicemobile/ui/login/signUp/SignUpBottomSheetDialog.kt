package com.project.autoservicemobile.ui.login.signUp

import android.content.DialogInterface
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicemobile.common.AuthenticatedListener
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentSignUpBinding
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.login.models.SignUpDataUI
import com.project.common.data.RequestResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpBottomSheetDialog(private val authenticatedListener: AuthenticatedListener) : BottomSheetDialogFragment() {

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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(_viewModel.isAuthorize.value !is RequestResult.Success)
            openSingInOrUpBottomSheet()
    }

    private fun setup(){
        with(binding){
            titleText.text = _viewModel.title

            nameLayout.hint = _viewModel.nameHintText
            emailLayout.hint = _viewModel.emailHintText
            passwordLayout.hint = _viewModel.passwordHintText
            passwordApplyLayout.hint = _viewModel.passwordApplyHintText

            signUpBtn.text = _viewModel.signUpBtnText
            signUpBtn.setOnClickListener {
                _viewModel.signUp(
                    SignUpDataUI(
                        binding.nameInput.text.toString(),
                        binding.emailInput.text.toString(),
                        binding.passwordInput.text.toString()
                    ),
                    object : CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("SignInBottomSheetDialog", "Error! $message")
                        }
                    })
            }
        }

        _viewModel.isAuthorize.observe(viewLifecycleOwner) {
            var loggingText: String
            when(it) {
                is RequestResult.Error -> loggingText = "Code: ${it.code}, ${it.message}"
                is RequestResult.Loading -> loggingText = "Loading"
                is RequestResult.Success -> {
                    loggingText = "Authorize"
                    authenticatedListener.onAuthenticated()
                    dismiss()
                }
            }
            Log.d("SignInBottomSheetDialog", loggingText)
        }
    }

    private fun openSingInOrUpBottomSheet() {
        val modalBottomSheet = SignInOrUpBottomSheetDialog(authenticatedListener)
        modalBottomSheet.setCancelable(false)
        modalBottomSheet.show(requireActivity().supportFragmentManager, SignInOrUpBottomSheetDialog.TAG)
    }

    companion object {
        const val TAG = "SignUpBottomSheetDialog"
    }
}