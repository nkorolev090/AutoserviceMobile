package com.project.autoservicemobile.ui.cart

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.project.common.data.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val accountRepository: AccountRepository
): BaseViewModel() {

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

    val promocode = ""

    val promocodeHintText = "Введите промокод"
    val applyBtnText = "Применить"
    val createRegBtnText = "Записться"
    val subTotalText = "Сумма"
    val saleText = "Скидка"
    val totalText = "Итого"
    val subTotalValueText = "777 $rubleSimbol"
    val saleValueText = "-30 $rubleSimbol"
    val totalValueText = "747 $rubleSimbol"

    val isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { accountRepository.isAuthenticated() },
    )

    fun onApplyPromocodeClick(){

    }

    fun onCreateRegistrationClick(){

    }
}