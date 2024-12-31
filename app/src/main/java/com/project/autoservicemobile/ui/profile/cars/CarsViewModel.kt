package com.project.autoservicemobile.ui.profile.cars

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.car.CarRepository
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.profile.models.CarUI
import com.project.autoservicemobile.ui.profile.models.toCarUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val carRepository: CarRepository,
    private val accountRepository: AccountRepository
): BaseViewModel() {

    val cars = MutableLiveData<RequestResult<List<CarUI>>>().apply {
        value = RequestResult.Loading()
    }

    val defaultCar = MutableLiveData<RequestResult<CarUI>>().apply {
        value = RequestResult.Loading()
    }

    val defaultCarUpdated = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun getCars(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        cars,
        coroutinesErrorHandler,
        request = { carRepository.getCars() },
        mapper = { result -> result.map { it.map { car -> car.toCarUI() } }}
    )

    fun getDefaultCar(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        defaultCar,
        coroutinesErrorHandler,
        request = { carRepository.getDefaultCar() },
        mapper = {data -> data.map { it.toCarUI() }}
    )

    fun setDefaultCar(selectedCar: CarUI, coroutinesErrorHandler: CoroutinesErrorHandler)= baseRequest(
        defaultCarUpdated,
        coroutinesErrorHandler,
        request = { accountRepository.updateDefaultCar(selectedCar.id) },
    )
}