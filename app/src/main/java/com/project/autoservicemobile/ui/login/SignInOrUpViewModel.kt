package com.project.autoservicemobile.ui.login

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicedata.login.AccountUseCase
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInOrUpViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {
    val title = "Присоединяйтесь к нам!"
    val signInDescription = "Войдите в существующий аккаунт"
    val signInBtn = "Войти"
    val signUpDescription = "Или зарегистрируйте новый аккаунт"
    val signUpBtn = "Зарегистрироваться"
    val goHomeDescription = "Или продолжите без регистрации"
    val goHomeBtn = "На главную"

    val isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { accountRepository.isAuthenticated() },
    )
}