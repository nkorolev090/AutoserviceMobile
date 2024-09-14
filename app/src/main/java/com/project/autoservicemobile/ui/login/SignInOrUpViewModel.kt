package com.project.autoservicemobile.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.AccountUseCase
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicedata.token.TokenManager
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.login.signIn.SignInBottomSheetDialog
import com.project.autoservicemobile.ui.login.signUp.SignUpBottomSheetDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInOrUpViewModel @Inject constructor(
    private val userContext: UserContext,
    private val accountUseCase: AccountUseCase
) : BaseViewModel() {
    val title = "Присоединяйтесь к нам!"
    val signInDescription = "Войдите в существующий аккаунт"
    val signInBtn = "Войти"
    val signUpDescription = "Или зарегистрируйте новый аккаунт"
    val signUpBtn = "Зарегистрироваться"

    var isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }
    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { accountUseCase.isAuthenticated() },
    )
    fun openSingInBottomSheet() {
        val modalBottomSheet = SignInBottomSheetDialog()
        modalBottomSheet.show(MAIN.supportFragmentManager, SignInBottomSheetDialog.TAG)

    }

    fun openSingUpBottomSheet() {
        val modalBottomSheet = SignUpBottomSheetDialog()
        modalBottomSheet.show(MAIN.supportFragmentManager, SignUpBottomSheetDialog.TAG)
    }
}