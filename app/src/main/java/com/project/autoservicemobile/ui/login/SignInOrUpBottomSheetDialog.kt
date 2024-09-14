package com.project.autoservicemobile.ui.login

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicemobile.common.CoroutinesErrorHandler
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
    ): View{
        _binding = FragmentSignInOrUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setup()

        return root;
    }

    override fun onResume() {
        super.onResume()
        _viewModel.isAuthenticated(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("SignInBottomSheetDialog", "Error! $message")
            }
        })
    }

    private fun setup(){
        with(binding) {
            titleText.text = _viewModel.title

            signInBtn.text = _viewModel.signInBtn
            signInBtn.setOnClickListener(View.OnClickListener {_ ->
                _viewModel.openSingInBottomSheet()
            })

            signUpBtn.text = _viewModel.signUpBtn
            signUpBtn.setOnClickListener(View.OnClickListener {_ ->
                _viewModel.openSingUpBottomSheet()
            })

            descriptionSignInText.text = _viewModel.signInDescription
            descriptionSignUpText.text = _viewModel.signUpDescription

            _viewModel.isAuth.observe(viewLifecycleOwner){
                when(it){
                    is RequestResult.Error -> Log.d("ProfileFragment", it.message.toString())
                    is RequestResult.Loading -> Log.d("ProfileFragment", "Loading")
                    is RequestResult.Success -> {
                        dismiss()
                    }
                }
            }

        }
    }
    companion object {
        const val TAG = "SignInOrUpBottomSheetDialog"
    }
}