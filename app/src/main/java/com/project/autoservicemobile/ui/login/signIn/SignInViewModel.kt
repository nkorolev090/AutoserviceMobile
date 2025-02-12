package com.project.autoservicemobile.ui.login.signIn

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.login.models.SignInDataUI
import com.project.autoservicemobile.ui.login.models.toSignInData
import com.project.common.data.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {
    val title = "C возвращением!"
    val emailHintText = "Введите email"
    val passwordHintText = "Введите пароль повторно"
    val signInBtnText = "Войти"

    private val _isAuthorize = MutableLiveData<RequestResult<Boolean>>()
    val isAuthorize = _isAuthorize
    fun signIn(signInDataUI: SignInDataUI, coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(
            _isAuthorize,
            coroutinesErrorHandler,
            request = { accountRepository.logIn(signInDataUI.toSignInData()) }
        )
}
