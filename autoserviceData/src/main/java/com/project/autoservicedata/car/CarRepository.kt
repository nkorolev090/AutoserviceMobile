package com.project.autoservicedata.car

import com.project.autoserviceapi.car.CarApi
import com.project.autoserviceapi.car.models.CarDTO
import com.project.autoserviceapi.cart.models.CartDTO
import com.project.autoservicedata.car.models.Car
import com.project.autoservicedata.car.models.toCar
import com.project.autoservicedata.cart.models.Cart
import com.project.autoservicedata.cart.models.toCart
import com.project.common.api.RequestResultAPI
import com.project.common.api.apiRequestFlow
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CarRepository @Inject constructor(
    private val api: CarApi
) {
    suspend fun getCars(): Flow<RequestResult<List<Car>>> {
        return apiRequestFlow {
            api.getCars()
        }.map { it.toCars()}
    }

    private fun RequestResultAPI<List<CarDTO>>.toCars(): RequestResult<List<Car>> =
        when (this) {
            is RequestResultAPI.Success -> {
                if (this.data != null) {
                    RequestResult.Success(this.data.map {
                        it.toCar()
                    })
                } else {
                    RequestResult.Error(
                        code = StatusCodeEnum.NO_CONTENT,
                        message = "result is empty"
                    )
                }
            }

            is RequestResultAPI.Error -> {
                RequestResult.Error(message = this.message, code = this.code)
                //getFromDatabase(city)
                //RequestResult.Error(code = response.code, message = response.message)
            }

            is RequestResultAPI.Exception -> {
                RequestResult.Error(error = this.throwable)
                //getFromDatabase(city)
//                    RequestResult.Error(error = response.throwable)
            }

            is RequestResultAPI.Loading -> {
                RequestResult.Loading()
            }
        }
}