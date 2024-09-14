package com.project.autoservicemobile.ui.login.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.AccountUseCase
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.login.models.SignInDataUI
import com.project.autoservicemobile.ui.login.models.toSignInData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase
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
            request = { accountUseCase.logIn(signInDataUI.toSignInData()) }
        )
}
