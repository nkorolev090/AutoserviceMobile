package com.project.autoservicemobile.ui.login.signIn

import android.content.DialogInterface
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicemobile.common.AuthenticatedListener
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentSignInBinding
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.login.models.SignInDataUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInBottomSheetDialog(private val authenticatedListener: AuthenticatedListener) : BottomSheetDialogFragment() {

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
       with(binding){
           titleText.text = _viewModel.title
           emailLayout.hint = _viewModel.emailHintText
           passwordLayout.hint = _viewModel.passwordHintText

           signInBtn.text = _viewModel.signInBtnText
           signInBtn.setOnClickListener(View.OnClickListener {
               _viewModel.signIn(
                   SignInDataUI(
                       emailInput.text.toString(),
                       passwordInput.text.toString()),
                   object: CoroutinesErrorHandler {
                       override fun onError(message: String) {
                           Log.d("SignInBottomSheetDialog", "Error! $message")
                       }
                   })
           })
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(_viewModel.isAuthorize.value !is RequestResult.Success)
            openSingInOrUpBottomSheet()
    }


    private fun openSingInOrUpBottomSheet() {
        val modalBottomSheet = SignInOrUpBottomSheetDialog(authenticatedListener)
        modalBottomSheet.setCancelable(false)
        modalBottomSheet.show(requireActivity().supportFragmentManager, SignInOrUpBottomSheetDialog.TAG)
    }
    companion object {
        const val TAG = "SignInBottomSheetDialog"
    }
}