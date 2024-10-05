package com.project.autoservicedata.slot

import com.project.autoserviceapi.cart.models.SlotDTO
import com.project.autoserviceapi.slot.SlotApi
import com.project.autoservicedata.cart.models.Slot
import com.project.autoservicedata.cart.models.toSlot
import com.project.common.api.RequestResultAPI
import com.project.common.api.apiRequestFlow
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SlotRepository @Inject constructor(
    private val api: SlotApi
) {
    fun getSlotsByDateBreakdown(date: String, breakdownId: Int): Flow<RequestResult<List<Slot>>> {
        return apiRequestFlow {
            api.getSlotsByDateBreakdown(date, breakdownId)
        }.map { it.toBreakdownList() }
    }


    private fun RequestResultAPI<List<SlotDTO>>.toBreakdownList(): RequestResult<List<Slot>> =
        when (this) {
            is RequestResultAPI.Success -> {
                if (this.data.isEmpty().not()) {
                    RequestResult.Success(this.data.map { it.toSlot() })
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
