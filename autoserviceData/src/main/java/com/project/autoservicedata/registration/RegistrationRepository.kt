package com.project.autoservicedata.registration

import com.project.autoserviceapi.registrations.RegistrationApi
import com.project.autoserviceapi.registrations.models.RegistrationDTO
import com.project.autoserviceapi.registrations.models.RegistrationDataDTO
import com.project.autoservicedata.cart.models.Slot
import com.project.autoservicedata.cart.models.toSlotDTO
import com.project.autoservicedata.registration.models.Registration
import com.project.autoservicedata.registration.models.toRegistration
import com.project.common.api.RequestResultAPI
import com.project.common.api.apiRequestFlow
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import com.project.common.data.toRequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    private val api: RegistrationApi
) {

    fun getRegistrations(): Flow<RequestResult<List<Registration>>> {
        return apiRequestFlow {
            api.getRegistrations()
        }.map {
            it.toRequestResult(
                { result ->
                    if (result.data.isEmpty().not()) {
                        RequestResult.Success(result.data.map { dto -> dto.toRegistration() }
                            .reversed())
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

    fun createRegistration(): Flow<RequestResult<Boolean>> {
        return apiRequestFlow {
            api.createRegistration()
        }.map {
            it.toRequestResult(
                {
                    RequestResult.Success(true)
                }
            )
        }
    }

    fun closeRegistration(registrationId: Int): Flow<RequestResult<Boolean>> {
        return apiRequestFlow {
            api.closeRegistration(registrationId)
        }.map {
            it.toRequestResult(
                {
                    RequestResult.Success(true) //mapping statusCode!!!
                }
            )
        }
    }
}
