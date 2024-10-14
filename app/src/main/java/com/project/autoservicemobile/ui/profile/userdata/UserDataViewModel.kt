package com.project.autoservicemobile.ui.profile.userdata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.profile.models.UserDataUI
import com.project.autoservicemobile.ui.profile.models.toUserDataUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
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

    val userData = MutableLiveData<RequestResult<UserDataUI>>().apply {
        value = RequestResult.Loading()
    }

    val isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { accountRepository.isAuthenticated() },
    )

    fun updateUserData(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        userData,
        coroutinesErrorHandler,
        request = { userContext.getUserData() },
        mapper = { data -> data.map{it.toUserDataUI()}}
    )

    fun logOff(){
        userData.postValue(RequestResult.Error())
        isAuth.postValue(RequestResult.Error())
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                accountRepository.logOff()
            }
        }
    }

    fun onUserDataChange(userData: UserDataUI){

    }
}