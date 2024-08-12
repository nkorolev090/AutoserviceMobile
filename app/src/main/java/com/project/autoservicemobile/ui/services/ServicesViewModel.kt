package com.project.autoservicemobile.ui.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicemobile.ui.services.models.ServiceUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor() : ViewModel() {

    val titleText: String = "Рекомендации"
    private var _services: List<ServiceUI> = listOf(
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
        ),
    )
    val services = MutableLiveData<List<ServiceUI>>().apply {
        value = _services
    }

    fun onCartBtnClick(service: ServiceUI){
        service.inCart = !service.inCart
    }

    fun onFavoritesBtnClick(service: ServiceUI){
        service.inFavourites = !service.inFavourites
    }
}