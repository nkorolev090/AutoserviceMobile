package com.project.autoservicemobile.ui.services.slots

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.cart.CartRepository
import com.project.autoservicedata.cart.CartUseCase
import com.project.autoservicedata.cart.models.Slot
import com.project.autoservicedata.slot.SlotRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.cart.models.SlotUI
import com.project.autoservicemobile.ui.cart.models.toSlotUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SlotsViewModel @Inject constructor(
//    private val _accountRepository: AccountRepository,
    private val _slotRepository: SlotRepository,
    private val _cartUseCase: CartUseCase
) : BaseViewModel() {
    val title = "Выберете дату и время"

    val slots = MutableLiveData<RequestResult<List<SlotUI>>>().apply {
        value = RequestResult.Loading()
//        value = RequestResult.Success(
//            listOf(
//                SlotUI(
//                    id = 0,
//                    service = null,
//                    mechanicId = 0,
//                    mechanicNameText = "Иванов. И.И.",
//                    startTimeText = "10:00",
//                    startDateText = "10.10.2020",
//                    registrationId = 0
//                ),
//                SlotUI(
 //                   id = 0,
//                    service = null,
//                    mechanicId = 0,
//                    mechanicNameText = "Иванов. И.И.",
//                    startTimeText = "10:00",
//                    startDateText = "10.10.2020",
//                    registrationId = 0
//                ),
//                SlotUI(
//                    id = 0,
//                    service = null,
//                    mechanicId = 0,
//                    mechanicNameText = "Иванов. И.И.",
//                    startTimeText = "10:00",
//                    startDateText = "10.10.2020",
//                    registrationId = 0
//                ),
//                SlotUI(
//                    id = 0,
//                    service = null,
//                    mechanicId = 0,
//                    mechanicNameText = "Иванов. И.И.",
//                    startTimeText = "10:00",
//                    startDateText = "10.10.2020",
//                    registrationId = 0
//                )
//            )
//        )
    }

    var selectedSlot: SlotUI? = null
    var hasSlotAddError = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun updateSlots(date: String, breakdownId: Int, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        slots,
        coroutinesErrorHandler,
        request = { _slotRepository.getSlotsByDateBreakdown(date, breakdownId) },
        mapper = { data -> data.map { list -> list.map { it.toSlotUI() } } }
    )

    fun addSlotToCart(slot: SlotUI, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        hasSlotAddError,
        coroutinesErrorHandler,
        request = { _cartUseCase.addSlotToCart(slot.id, slot.service!!.id) }
    )
}