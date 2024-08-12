package com.project.autoservicemobile.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicemobile.ui.services.models.ServiceUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private var _cartItems: List<ServiceUI> = listOf(
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
    val cartItems = MutableLiveData<List<ServiceUI>>().apply {
        value = _cartItems
    }
}