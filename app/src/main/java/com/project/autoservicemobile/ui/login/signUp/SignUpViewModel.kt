package com.project.autoservicemobile.ui.login.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicemobile.ui.login.models.SignUpDataUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    val title = "Добро пожаловать!"
    val nameHintText = "Введите имя"
    val emailHintText = "Введите email"
    val passwordApplyHintText = "Введите пароль"
    val passwordHintText = "Введите пароль повторно"
    val signUpBtnText = "Зарегистрироваться"

    var userData = MutableLiveData<SignUpDataUI>().apply {
        value = SignUpDataUI(
            "-",
            "-",
            "-",
        )
    }

    public fun onSignUpBtnClick(){

    }
}