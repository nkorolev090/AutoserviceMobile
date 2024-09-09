package com.project.autoservicemobile.ui.login

import androidx.lifecycle.ViewModel
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.ui.login.signIn.SignInBottomSheetDialog
import com.project.autoservicemobile.ui.login.signUp.SignUpBottomSheetDialog

class SignInOrUpViewModel : ViewModel() {
    val title = "Присоединяйтесь к нам!"
    val signInDescription = "Войдите в существующий аккаунт"
    val signInBtn = "Войти"
    val signUpDescription = "Или зарегистрируйте новый аккаунт"
    val signUpBtn = "Зарегистрироваться"

    public fun openSingInBottomSheet(){
        val modalBottomSheet = SignInBottomSheetDialog()
        modalBottomSheet.show(MAIN.supportFragmentManager, SignInOrUpBottomSheetDialog.TAG)
    }

    public fun openSingUpBottomSheet(){
        val modalBottomSheet = SignUpBottomSheetDialog()
        modalBottomSheet.show(MAIN.supportFragmentManager, SignInOrUpBottomSheetDialog.TAG)
    }
}