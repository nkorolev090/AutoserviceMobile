package com.project.autoservicemobile.ui.registrations.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicedata.registration.models.RegStatusEnum
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import com.project.autoservicemobile.ui.services.models.ServiceUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationDetailsViewModel @Inject constructor() : ViewModel() {
    val totalText = "Стоимость работ"

    private val _registration = RegistrationUI(
        "Запись от 20 августа",
        "Завершена 30 августа",
        "3000$rubleSimbol",
        RegStatusEnum.APPROVED,
        ""
    )

    var registration = MutableLiveData<RegistrationUI>().apply {
        value = _registration
    }

    fun onWarrantyBtnClick(service: ServiceUI){
        service.inWarranty = !service.inWarranty
    }

    fun onFavoritesBtnClick(service: ServiceUI){
        service.inFavourites = !service.inFavourites
    }
}