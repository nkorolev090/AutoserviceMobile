package com.project.autoservicedata.registration

import com.project.autoserviceapi.cart.models.SlotDTO
import com.project.autoserviceapi.registrations.RegistrationApi
import com.project.autoserviceapi.registrations.models.RegistrationDTO
import com.project.autoservicedata.cart.models.Slot
import com.project.autoservicedata.cart.models.toSlot
import com.project.autoservicedata.registration.models.Registration
import com.project.autoservicedata.registration.models.toRegistration
import com.project.common.api.RequestResultAPI
import com.project.common.api.apiRequestFlow
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    private val api: RegistrationApi
) {

    suspend fun getRegistrations(): Flow<RequestResult<List<Registration>>> {
        return apiRequestFlow {
            api.getRegistrations()
        }.map { it.toRegistrationList() }
    }

    private fun RequestResultAPI<List<RegistrationDTO>>.toRegistrationList(): RequestResult<List<Registration>> =
        when (this) {
            is RequestResultAPI.Success -> {
                if (this.data.isEmpty().not()) {
                    RequestResult.Success(this.data.map { it.toRegistration() })
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
