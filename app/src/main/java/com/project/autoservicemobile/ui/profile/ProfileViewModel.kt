package com.project.autoservicemobile.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.profile.models.UserDataUI
import com.project.autoservicemobile.ui.profile.models.toUserDataUI
import com.project.autoservicemobile.ui.registrations.details.RegistrationDetailsBottomSheetDialog
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    userContext: UserContext
) : ViewModel() {
    val personal_infoText = "Информация о пользователе"
    val nameTitleText = "Имя"
    val nameHintText = "Введите имя"
    val surnameTitleText = "Фамилия"
    val surnameHintText = "Введите фамилию"
    val dateTitleText = "Дата рождения"
    val dateHintText = "Введите дату"
    val emailTitleText = "Email"
    val emailHintText = "Введите email"
    val passwordTitleText = "Пароль"
    val passwordHintText = "Введите пароль"
    val logOutBtnText = "Выйти"

    var userData = userContext.userData.map {
        it?.toUserDataUI() ?:
        UserDataUI(
            "-",
            "-",
            "-",
            "-",
            "-",
            "-",
            "-"
        )
    }

    init {
        if(userContext.isAuthorize.value == false)
            openSingBottomSheet()
    }

    fun onLogOutBtnClick(){

    }
    fun onUserDataChange(userData: UserDataUI){

    }

    private fun openSingBottomSheet(){
        val modalBottomSheet = SignInOrUpBottomSheetDialog()
        modalBottomSheet.setCancelable(false)
        modalBottomSheet.show(MAIN.supportFragmentManager, SignInOrUpBottomSheetDialog.TAG)
    }
}