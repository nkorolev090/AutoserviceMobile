package com.project.autoservicedata.cart

import com.project.autoserviceapi.cart.CartApi
import com.project.autoserviceapi.cart.models.CartDTO
import com.project.autoserviceapi.cart.models.SlotDTO
import com.project.autoservicedata.breakdown.models.Breakdown
import com.project.autoservicedata.cart.models.Cart
import com.project.autoservicedata.cart.models.Slot
import com.project.autoservicedata.cart.models.toCart
import com.project.autoservicedata.cart.models.toSlot
import com.project.common.api.RequestResultAPI
import com.project.common.api.apiRequestFlow
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import com.project.common.data.asDateTimeFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val api: CartApi
) {
    suspend fun getCart(): Flow<RequestResult<Cart>> {
        return apiRequestFlow {
            api.getCart()
        }.map { it.toCart() }
    }

    suspend fun addSlotToCart(slotId: Int, breakdownId: Int): Flow<RequestResult<Cart>> {
        return apiRequestFlow {
            api.addCartItem(slotId, breakdownId)
        }.map { it.toCart() }
    }

    suspend fun removeBreakdownFromCart(breakdownId: Int): Flow<RequestResult<Cart>> {
        return apiRequestFlow {
            api.removeCartItem(breakdownId)
        }.map { it.toCart() }
    }

    private fun RequestResultAPI<CartDTO>.toCart(): RequestResult<Cart> =
        when (this) {
            is RequestResultAPI.Success -> {
                if (this.data != null) {
                    RequestResult.Success(this.data.toCart())
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

private fun Slot.toSlotDTO(): SlotDTO {
    return SlotDTO(
        id = this.id,
        breakdownId = this.breakdownId,
        breakdownName = this.breakdownName,
        breakdownWarranty = this.breakdownWarranty,
        cost = this.cost,
        mechanicId = this.mechanicId,
        mechanicName = this.mechanicName,
        startDate = "${this.startDateTime?.dayOfMonth}.${this.startDateTime?.monthValue}.${this.startDateTime?.year}",
        startTime = "${this.startDateTime?.hour}:${this.startDateTime?.minute}",
        finishDate = "${this.finishDateTime?.dayOfMonth}.${this.finishDateTime?.monthValue}.${this.finishDateTime?.year}",
        finishTime = "${this.finishDateTime?.hour}:${this.finishDateTime?.minute}",
        registrationId = registrationId
    )
}
