package com.project.autoservicedata.car

import com.project.autoserviceapi.car.CarApi
import com.project.autoservicedata.car.models.Car
import com.project.autoservicedata.car.models.toCar
import com.project.common.api.apiRequestFlow
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import com.project.common.data.toRequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CarRepository @Inject constructor(
    private val api: CarApi
) {
    fun getCars(): Flow<RequestResult<List<Car>>> {
        return apiRequestFlow {
            api.getCars()
        }.map {
            it.toRequestResult(
                { result ->
                    if (result.data.isNotEmpty()) {
                        RequestResult.Success(result.data.map { car ->
                            car.toCar()
                        })
                    } else {
                        RequestResult.Error(
                            code = StatusCodeEnum.NO_CONTENT,
                            message = "result is empty"
                        )
                    }
                }
            )
        }
    }

    fun getDefaultCar(): Flow<RequestResult<Car>> {
        return apiRequestFlow {
            api.getDefaultCars()
        }.map {
            it.toRequestResult(
                { result ->
                    if (result.data != null) {
                        RequestResult.Success(result.data!!.toCar())
                    } else {
                        RequestResult.Error(
                            code = StatusCodeEnum.NO_CONTENT,
                            message = "result is empty"
                        )
                    }
                }
            )
        }
    }
}