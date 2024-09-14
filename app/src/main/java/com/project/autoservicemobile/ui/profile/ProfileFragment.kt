package com.project.autoservicemobile.ui.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentProfileBinding
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.profile.models.UserDataUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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

    private fun setup() {
        binding.personalInfoText.text = _viewModel.personal_infoText

        binding.nameTitle.text = _viewModel.nameTitleText
        binding.nameLayout.hint = _viewModel.nameHintText
        binding.surnameTitle.text = _viewModel.surnameTitleText
        binding.surnameLayout.hint = _viewModel.surnameHintText
        binding.emailTitle.text = _viewModel.emailTitleText
        binding.emailLayout.hint = _viewModel.emailHintText
        binding.dateTitle.text = _viewModel.dateTitleText
        binding.dateLayout.hint = _viewModel.dateHintText
        binding.passwordTitle.text = _viewModel.passwordTitleText
        binding.passwordLayout.hint = _viewModel.passwordHintText

        binding.nameInput.setOnKeyListener(onKeyListener)
        binding.surnameInput.setOnKeyListener(onKeyListener)
        binding.emailInput.setOnKeyListener(onKeyListener)
        binding.dateInput.setOnKeyListener(onKeyListener)
        binding.passwordInput.setOnKeyListener(onKeyListener)

        binding.logOutBtn.text = _viewModel.logOutBtnText
        binding.logOutBtn.setOnClickListener(View.OnClickListener {
            _viewModel.onLogOutBtnClick()
        })

        _viewModel.userData.observe(viewLifecycleOwner) {
           when(it){
               is RequestResult.Error -> Log.d("ProfileFragment", it.message.toString())
               is RequestResult.Loading -> Log.d("ProfileFragment", "Loading")
               is RequestResult.Success -> {
                   binding.nameInput.setText(it.data.name)
                   binding.surnameInput.setText(it.data.surname)
                   binding.emailInput.setText(it.data.email)
                   binding.dateInput.setText(it.data.birthDate)
                   binding.passwordInput.setText(it.data.password)
               }
           }
        }

        _viewModel.isAuth.observe(viewLifecycleOwner){
            when (it) {
                is RequestResult.Error -> openSignBottomSheet()
                is RequestResult.Loading -> {}
                is RequestResult.Success -> _viewModel.updateUserData(
                    object : CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("SignInBottomSheetDialog", "Error! $message")
                        }
                    }
                )
            }
        }
    }

    private val onKeyListener = View.OnKeyListener { v, keyCode, event ->
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
//            _viewModel.onUserDataChange(
//                UserDataUI(
//                    binding.nameInput.text.toString(),
//                    binding.surnameInput.text.toString(),
//                    binding.emailInput.text.toString(),
//                    binding.dateInput.text.toString(),
//                    binding.passwordInput.text.toString()
//                )
//            )
        }
        false
    }

    private fun openSignBottomSheet() {
        val modalBottomSheet = SignInOrUpBottomSheetDialog()
        modalBottomSheet.setCancelable(false)
        modalBottomSheet.show(
            requireActivity().supportFragmentManager,
            SignInOrUpBottomSheetDialog.TAG
        )
    }
}