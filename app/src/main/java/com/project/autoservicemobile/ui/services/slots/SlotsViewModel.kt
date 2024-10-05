package com.project.autoservicemobile.ui.services.slots

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.cart.models.SlotUI
import com.project.common.data.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SlotsViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {
    val title = "Выберете дату и время"

    val isAuth = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    val slots = MutableLiveData<RequestResult<List<SlotUI>>>().apply {
        value = RequestResult.Success(
            listOf(
                SlotUI(
                    id = 0,
                    service = null,
                    mechanicId = 0,
                    mechanicNameText = "Иванов. И.И.",
                    startTimeText = "10:00",
                    startDateText = "10.10.2020",
                    registrationId = 0
                ),
                SlotUI(
                    id = 0,
                    service = null,
                    mechanicId = 0,
                    mechanicNameText = "Иванов. И.И.",
                    startTimeText = "10:00",
                    startDateText = "10.10.2020",
                    registrationId = 0
                ),
                SlotUI(
                    id = 0,
                    service = null,
                    mechanicId = 0,
                    mechanicNameText = "Иванов. И.И.",
                    startTimeText = "10:00",
                    startDateText = "10.10.2020",
                    registrationId = 0
                ),
                SlotUI(
                    id = 0,
                    service = null,
                    mechanicId = 0,
                    mechanicNameText = "Иванов. И.И.",
                    startTimeText = "10:00",
                    startDateText = "10.10.2020",
                    registrationId = 0
                )
            )
        )
    }

    var selectedSlot: SlotUI? = null

    fun isAuthenticated(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        isAuth,
        coroutinesErrorHandler,
        request = { accountRepository.isAuthenticated() },
    )

}