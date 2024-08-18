package com.project.autoservicemobile.ui.registrations.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import com.project.autoservicemobile.ui.services.models.ServiceUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationDetailsViewModel @Inject constructor() : ViewModel() {
    val titleText = "Запись"
    val totalText = "Итого"

    private val _registration = RegistrationUI(
        "Запись от 20 августа",
        "Завершена 30 августа",
        "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
        "3000$rubleSimbol",
        listOf(
            ServiceUI(
                "Замена тормозных колодок",
                "2500₽",
                "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
                inFavourites = false,
                inCart = false
            ),
            ServiceUI(
                "Замена тормозных колодок",
                "2500₽",
                "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
                inFavourites = false,
                inCart = false
            ),
            ServiceUI(
                "Замена тормозных колодок",
                "2500₽",
                "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
                inFavourites = false,
                inCart = false
            ),
            ServiceUI(
                "Замена тормозных колодок",
                "2500₽",
                "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
                inFavourites = false,
                inCart = false
            )
        )
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