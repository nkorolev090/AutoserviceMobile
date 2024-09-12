package com.project.autoservicemobile.ui.login.signIn

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.common.map
import com.project.autoservicedata.login.LoginUseCase
import com.project.autoservicedata.login.models.User
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.login.models.SignInDataUI
import com.project.autoservicemobile.ui.login.models.toSignInData
import com.project.autoservicemobile.ui.profile.models.UserDataUI
import com.project.autoservicemobile.ui.profile.models.toUserDataUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCase: LoginUseCase
) : BaseViewModel() {
    val title = "C возвращением!"
    val emailHintText = "Введите email"
    val passwordHintText = "Введите пароль повторно"
    val signInBtnText = "Войти"

    private val _isAuthorize = MutableLiveData<RequestResult<Boolean>>()
    val isAuthorize = _isAuthorize
    fun signIn(signInDataUI: SignInDataUI, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _isAuthorize,
        coroutinesErrorHandler,
        request = { useCase.logIn(signInDataUI.toSignInData())}
    )

    companion object {
        const val TAG = "SignInViewModel"
    }
}
