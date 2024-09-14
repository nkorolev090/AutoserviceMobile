package com.project.autoservicemobile.ui.login.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.AccountUseCase
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.login.models.SignInDataUI
import com.project.autoservicemobile.ui.login.models.SignUpDataUI
import com.project.autoservicemobile.ui.login.models.toSignInData
import com.project.autoservicemobile.ui.login.models.toSignUpData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase
) : BaseViewModel() {
    val title = "Добро пожаловать!"
    val nameHintText = "Введите имя"
    val emailHintText = "Введите email"
    val passwordApplyHintText = "Введите пароль"
    val passwordHintText = "Введите пароль повторно"
    val signUpBtnText = "Зарегистрироваться"

    private val _isAuthorize = MutableLiveData<RequestResult<Boolean>>()
    val isAuthorize = _isAuthorize

    fun signUp(signUpDataUI: SignUpDataUI, coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(
            _isAuthorize,
            coroutinesErrorHandler,
            request = { accountUseCase.logUp(signUpDataUI.toSignUpData()) }
        )
}