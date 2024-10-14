package com.project.autoservicemobile.ui.profile.userdata

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.autoservicemobile.common.AuthenticatedListener
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.databinding.FragmentUserDataBinding
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.profile.models.UserDataUI
import com.project.common.data.RequestResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDataFragment : Fragment(), AuthenticatedListener {
    private var _binding: FragmentUserDataBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val _viewModel: UserDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDataBinding.inflate(inflater, container, false)
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

    override fun onPause() {
        super.onPause()
        _viewModel.isAuth.postValue(RequestResult.Loading())
    }
    override fun onAuthenticated() {
        _viewModel.updateUserData(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                Log.d("SignInBottomSheetDialog", "Error! $message")
            }
        })
    }
    private fun setup() {
        with(binding){
            personalInfoText.text = _viewModel.personal_infoText

            nameTitle.text = _viewModel.nameTitleText
            nameLayout.hint = _viewModel.nameHintText
            surnameTitle.text = _viewModel.surnameTitleText
            surnameLayout.hint = _viewModel.surnameHintText
            emailTitle.text = _viewModel.emailTitleText
            emailLayout.hint = _viewModel.emailHintText
            dateTitle.text = _viewModel.dateTitleText
            dateLayout.hint = _viewModel.dateHintText
            passwordTitle.text = _viewModel.passwordTitleText
            passwordLayout.hint = _viewModel.passwordHintText

            nameInput.setOnKeyListener(onKeyListener)
            surnameInput.setOnKeyListener(onKeyListener)
            emailInput.setOnKeyListener(onKeyListener)
            dateInput.setOnKeyListener(onKeyListener)
            passwordInput.setOnKeyListener(onKeyListener)

            logOutBtn.text = _viewModel.logOutBtnText
            logOutBtn.setOnClickListener {
                _viewModel.logOff()
            }
        }

        with(_viewModel){
            userData.observe(viewLifecycleOwner) {
                when(it){
                    is RequestResult.Error -> {
                        Log.d("ProfileFragment", it.message.toString())

                        setUserDataFields(
                            UserDataUI(
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                            )
                        )
                    }
                    is RequestResult.Loading -> {
                        Log.d("ProfileFragment", "Loading")

                        setUserDataFields(
                            UserDataUI(
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                            )
                        )
                    }
                    is RequestResult.Success -> setUserDataFields(it.data)
                }
            }

            isAuth.observe(viewLifecycleOwner){
                if (it is RequestResult.Error) {
                    openSignBottomSheet()
                }
                if(it is RequestResult.Success){
                    _viewModel.updateUserData(object : CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("SignInBottomSheetDialog", "Error! $message")
                        }
                    })
                }
            }
        }
    }

    private fun setUserDataFields(userdata: UserDataUI){
        with(binding){
            nameInput.setText(userdata.name)
            surnameInput.setText(userdata.surname)
            emailInput.setText(userdata.email)
            dateInput.setText(userdata.birthDate)
            passwordInput.setText(userdata.password)}
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
        val modalBottomSheet = SignInOrUpBottomSheetDialog(this)
        modalBottomSheet.setCancelable(false)
        modalBottomSheet.show(
            requireActivity().supportFragmentManager,
            SignInOrUpBottomSheetDialog.TAG
        )
    }
}