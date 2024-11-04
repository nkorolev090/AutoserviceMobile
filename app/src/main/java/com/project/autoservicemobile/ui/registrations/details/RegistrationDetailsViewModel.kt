package com.project.autoservicemobile.ui.registrations.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicedata.registration.RegistrationRepository
import com.project.autoservicedata.registration.models.RegStatusEnum
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import com.project.autoservicemobile.ui.registrations.models.toRegistrationUI
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationDetailsViewModel @Inject constructor(
    private val _registrationRepository: RegistrationRepository
) : BaseViewModel() {
    val totalText = "Стоимость работ"

    val closed = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun onWarrantyBtnClick(service: ServiceUI) {
        service.inWarranty = !service.inWarranty
    }

    fun onFavoritesBtnClick(service: ServiceUI) {
        service.inFavourites = !service.inFavourites
    }

    fun closeRegistration(registrationId: Int, coroutinesErrorHandler: CoroutinesErrorHandler) =
        baseRequest(
            closed,
            coroutinesErrorHandler,
            request = { _registrationRepository.closeRegistration(registrationId) },
        )
}