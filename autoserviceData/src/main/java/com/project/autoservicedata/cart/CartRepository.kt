package com.project.autoservicedata.cart

import com.project.autoserviceapi.cart.CartApi
import com.project.autoserviceapi.cart.models.CartDTO
import com.project.autoservicedata.breakdown.models.Breakdown
import com.project.autoservicedata.cart.models.Cart
import com.project.autoservicedata.cart.models.toCart
import com.project.common.api.RequestResultAPI
import com.project.common.api.apiRequestFlow
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val api: CartApi
) {
    fun getCart(): Flow<RequestResult<Cart>> {
        return apiRequestFlow {
            api.getCart()
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