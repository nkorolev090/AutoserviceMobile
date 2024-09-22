package com.project.autoservicemobile.ui.login

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicemobile.common.AuthenticatedListener
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentSignInOrUpBinding
import com.project.autoservicemobile.ui.login.signIn.SignInBottomSheetDialog
import com.project.autoservicemobile.ui.login.signUp.SignUpBottomSheetDialog
import com.project.common.data.RequestResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInOrUpBottomSheetDialog(private val authenticatedListener: AuthenticatedListener) : BottomSheetDialogFragment() {

    private var _binding: FragmentSignInOrUpBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val _viewModel: SignInOrUpViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentSignInOrUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        _viewModel.isAuthenticated(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d(TAG, "Error! $message")
            }
        })
    }

    private fun setup(){
        with(binding) {
            titleText.text = _viewModel.title

            signInBtn.text = _viewModel.signInBtn
            signInBtn.setOnClickListener { _ ->
                openSingInBottomSheet()
                dismiss()
            }

            signUpBtn.text = _viewModel.signUpBtn
            signUpBtn.setOnClickListener { _ ->
                openSingUpBottomSheet()
                dismiss()
            }

            goHomeBtn.text = _viewModel.goHomeBtn

            descriptionSignInText.text = _viewModel.signInDescription
            descriptionSignUpText.text = _viewModel.signUpDescription
            descriptionGoHomeText.text = _viewModel.goHomeDescription

            _viewModel.isAuth.observe(viewLifecycleOwner){
                when(it){
                    is RequestResult.Error -> Log.d(TAG, it.message.toString())
                    is RequestResult.Loading -> Log.d(TAG, "Loading")
                    is RequestResult.Success -> {
                        dismiss()
                    }
                }
            }
        }
    }

    private fun openSingInBottomSheet() {
        val modalBottomSheet = SignInBottomSheetDialog(authenticatedListener)
        modalBottomSheet.show(requireActivity().supportFragmentManager, SignInBottomSheetDialog.TAG)
    }

    private fun openSingUpBottomSheet() {
        val modalBottomSheet = SignUpBottomSheetDialog(authenticatedListener)
        modalBottomSheet.show(requireActivity().supportFragmentManager, SignUpBottomSheetDialog.TAG)
    }

    companion object {
        const val TAG = "SignInOrUpBottomSheetDialog"
    }
}