package com.project.autoservicemobile.ui.profile

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicedata.registration.RegistrationRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.profile.models.ProfileDataUI
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import com.project.autoservicemobile.ui.registrations.models.toRegistrationUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val _accountRepository: AccountRepository,
    private val _registrationRepository: RegistrationRepository
) : BaseViewModel() {

    val registrations = MutableLiveData<RequestResult<List<RegistrationUI>>>().apply {
        value = RequestResult.Loading()
    }

    val profileData = MutableLiveData<RequestResult<ProfileDataUI>>().apply {
//        value = RequestResult.Success(
//            ProfileDataUI(
//                "9 услуг",
//                "1 автомобиль"
//            )
//        )
        value = RequestResult.Loading()
    }

    fun updateRegistrations(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        registrations,
        coroutinesErrorHandler,
        request = { _registrationRepository.getRegistrations() },
        mapper = { data -> data.map { list -> list.map { it.toRegistrationUI() } } }
    )

    val isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { _accountRepository.isAuthenticated() },
    )
}