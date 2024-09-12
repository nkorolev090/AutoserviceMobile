package com.project.autoservicemobile.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.viewModelScope
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicedata.token.TokenManager
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.ui.login.signIn.SignInBottomSheetDialog
import com.project.autoservicemobile.ui.login.signUp.SignUpBottomSheetDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInOrUpViewModel @Inject constructor(
    userContext: UserContext
) : ViewModel() {
    val title = "Присоединяйтесь к нам!"
    val signInDescription = "Войдите в существующий аккаунт"
    val signInBtn = "Войти"
    val signUpDescription = "Или зарегистрируйте новый аккаунт"
    val signUpBtn = "Зарегистрироваться"

    val isAuthorize = userContext.isAuthorize

    fun openSingInBottomSheet(){
        val modalBottomSheet = SignInBottomSheetDialog()
        modalBottomSheet.show(MAIN.supportFragmentManager, SignInBottomSheetDialog.TAG)

    }

    fun openSingUpBottomSheet(){
        val modalBottomSheet = SignUpBottomSheetDialog()
        modalBottomSheet.show(MAIN.supportFragmentManager, SignUpBottomSheetDialog.TAG)
    }
}