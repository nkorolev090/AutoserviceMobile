package com.project.autoservicedata.deviceToken

import com.project.autoserviceapi.deviceToken.DeviceTokenApi
import com.project.autoservicedata.car.models.Car
import com.project.autoservicedata.car.models.toCar
import com.project.common.api.apiRequestFlow
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import com.project.common.data.toRequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeviceTokenRepository @Inject constructor(
    private val api: DeviceTokenApi
) {
    fun saveDeviceToken(deviceToken: String): Flow<RequestResult<Boolean>> {
        return apiRequestFlow {
            api.saveDeviceToken(deviceToken)
        }.map {
            it.toRequestResult(
                { result ->
                    if (result.data != null) {
                        RequestResult.Success(true)
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