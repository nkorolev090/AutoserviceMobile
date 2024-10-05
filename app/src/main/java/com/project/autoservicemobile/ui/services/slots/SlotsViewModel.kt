package com.project.autoservicemobile.ui.services.slots

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicedata.slot.SlotRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.cart.models.SlotUI
import com.project.autoservicemobile.ui.cart.models.toSlotUI
import com.project.autoservicemobile.ui.home.models.toNewsArticleUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SlotsViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val slotRepository: SlotRepository
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

    fun updateSlots(date: String, breakdownId: Int, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        slots,
        coroutinesErrorHandler,
        request = { slotRepository.getSlotsByDateBreakdown(date, breakdownId) },
        mapper = { data -> data.map { list -> list.map { it.toSlotUI() } } }
    )
}