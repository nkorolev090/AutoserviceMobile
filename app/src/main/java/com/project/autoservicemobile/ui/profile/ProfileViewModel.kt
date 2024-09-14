package com.project.autoservicemobile.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.common.map
import com.project.autoservicedata.login.AccountUseCase
import com.project.autoservicedata.login.models.UserData
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.login.SignInOrUpBottomSheetDialog
import com.project.autoservicemobile.ui.login.models.toSignInData
import com.project.autoservicemobile.ui.profile.models.UserDataUI
import com.project.autoservicemobile.ui.profile.models.toUserDataUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
    private val userContext: UserContext
) : BaseViewModel() {

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

    var userData = MutableLiveData<RequestResult<UserDataUI>>().apply {
        value = RequestResult.Loading()
    }

    var isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }
    fun updateUserData(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        userData,
        coroutinesErrorHandler,
        request = { userContext.getUserData() },
        mapper = { data -> data.map{it.toUserDataUI()}}
    )

    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { accountUseCase.isAuthenticated() },
    )
//    : Boolean{
//        var result = false
//        viewModelScope. {
//            withContext(Dispatchers.IO) {
//                val requestResult = accountUseCase.isAuthenticated()
//                result = when(requestResult){
//                    is RequestResult.Error -> false
//                    is RequestResult.Loading -> false
//                    is RequestResult.Success -> true
//                }
//            }
//        }
//        return result
//    }

    fun onLogOutBtnClick(){

    }
    fun onUserDataChange(userData: UserDataUI){

    }
}