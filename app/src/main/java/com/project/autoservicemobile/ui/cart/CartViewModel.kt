package com.project.autoservicemobile.ui.cart

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.car.CarRepository
import com.project.autoservicedata.cart.CartRepository
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicedata.registration.RegistrationRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.cart.models.CartItemUI
import com.project.autoservicemobile.ui.cart.models.CartUI
import com.project.autoservicemobile.ui.cart.models.toCartUI
import com.project.autoservicemobile.ui.cart.models.toSlot
import com.project.autoservicemobile.ui.profile.models.CarUI
import com.project.autoservicemobile.ui.profile.models.toCarUI
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val cartRepository: CartRepository,
    private val registrationRepository: RegistrationRepository,
    private val carRepository: CarRepository,
): BaseViewModel() {

//    val cartItems = MutableLiveData<RequestResult<List<CartItemUI>>>().apply {
//        value = RequestResult.Loading()
//    }

    val defaultCar = MutableLiveData<RequestResult<CarUI>>().apply {
        value = RequestResult.Loading()
    }

    val cart = MutableLiveData<RequestResult<CartUI>>().apply {
        value = RequestResult.Loading()
    }

    val createdRegistration = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    val promocode = ""

    val promocodeHintText = "Введите промокод"
    val applyBtnText = "Применить"
    val createRegBtnText = "Записться"
    val subTotalText = "Сумма"
    val saleText = "Скидка"
    val totalText = "Итого"

    val isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { accountRepository.isAuthenticated() },
    )

    fun updateCart(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        cart,
        coroutinesErrorHandler,
        request = { cartRepository.getCart() },
        mapper = {data -> data.map { it.toCartUI() }}
    )

    fun updateDefaultCar(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
    defaultCar,
    coroutinesErrorHandler,
    request = { carRepository.getDefaultCar() },
    mapper = {data -> data.map { it.toCarUI() }}
    )

    fun removeBreakdownFromCart(breakdownId: Int, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        cart,
        coroutinesErrorHandler,
        request = { cartRepository.removeBreakdownFromCart(breakdownId) },
        mapper = {data -> data.map { it.toCartUI() }}
    )

    fun createRegistration(coroutinesErrorHandler: CoroutinesErrorHandler) {
        val carId = defaultCar.value?.data?.id
        val slots = cart.value?.data?.cartItems?.map { it.slot.toSlot() }

        if(carId == null || slots.isNullOrEmpty()){
            return
        }

        baseRequest(
            createdRegistration,
            coroutinesErrorHandler,
            request = { registrationRepository.createRegistration(carId!!, slots!!) },
        )
    }
    fun onApplyPromocodeClick(){

    }
}