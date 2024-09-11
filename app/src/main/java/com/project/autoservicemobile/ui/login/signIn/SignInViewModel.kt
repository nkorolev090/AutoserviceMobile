package com.project.autoservicemobile.ui.login.signIn

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.LoginUseCase
import com.project.autoservicedata.login.models.SignInData
import com.project.autoservicemobile.ui.login.models.SignInDataUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCase: LoginUseCase
) : ViewModel() {
    val title = "C возвращением!"
    val emailHintText = "Введите email"
    val passwordHintText = "Введите пароль повторно"
    val signInBtnText = "Войти"

    var signInData = MutableLiveData<SignInDataUI>().apply {
        value = SignInDataUI(
            "string@string",
            "S!1tring",
        )
    }

    public fun onSignInBtnClick(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var result = useCase.logIn(signInData.value!!.toSignInData())
                if(result is RequestResult.Success){
                    Log.d(TAG, result.data.userName ?: "null")
                }
            }
        }
    }

    companion object {
        const val TAG = "SignInViewModel"
    }
}

private fun SignInDataUI.toSignInData(): SignInData {
return SignInData(
    email = this.email,
    password = this.password
)
}
