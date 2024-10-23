package com.project.autoservicemobile.ui.profile

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.profile.models.ProfileDataUI
import com.project.autoservicemobile.ui.profile.models.UserDataUI
import com.project.autoservicemobile.ui.profile.models.toUserDataUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val userContext: UserContext
) : BaseViewModel() {

    val profileData = MutableLiveData<RequestResult<ProfileDataUI>>().apply {
        value = RequestResult.Success(
            ProfileDataUI(
                "9 услуг",
                "1 автомобиль"
            )
        )
    }

    val isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { accountRepository.isAuthenticated() },
    )
//
//    fun updateUserData(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
//        userData,
//        coroutinesErrorHandler,
//        request = { userContext.getUserData() },
//        mapper = { data -> data.map{it.toUserDataUI()}}
//    )
}