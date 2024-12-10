package com.project.autoservicedata.cart

import com.project.autoservicedata.cart.models.Slot
import com.project.common.data.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val _cartRepository: CartRepository
) {

    fun addSlotToCart(slotId: Int, breakdownId: Int): Flow<RequestResult<Boolean>>
        = _cartRepository.addSlotToCart(slotId, breakdownId).map {
            when(it){
                is RequestResult.Success -> {
                    val result = it.data.cartItems.find { cartItem ->  cartItem.slot.id == slotId}
                    if(result != null){
                        RequestResult.Success(true)
                    }
                    else{
                        RequestResult.Error(message = "slot is not added")
                    }
                }
                is RequestResult.Error -> RequestResult.Error()
                is RequestResult.Loading -> RequestResult.Loading()
            }
        }
    }
