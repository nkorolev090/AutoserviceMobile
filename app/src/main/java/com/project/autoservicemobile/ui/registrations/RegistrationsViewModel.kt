package com.project.autoservicemobile.ui.registrations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicedata.registration.RegistrationRepository
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.cart.models.toSlotUI
import com.project.autoservicemobile.ui.registrations.details.RegistrationDetailsBottomSheetDialog
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import com.project.autoservicemobile.ui.registrations.models.toRegistrationUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationsViewModel  @Inject constructor(
    private val _registrationRepository: RegistrationRepository
) : BaseViewModel() {
//    private var _registrations: List<RegistrationUI> = listOf(
//        RegistrationUI(
//            "Замена масла",
//            "Завершена 30 августа",
//            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
//            "3000$rubleSimbol"
//        ),
//        RegistrationUI(
//            "Замена масла",
//            "Завершена 30 августа",
//            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
//            "3000$rubleSimbol"
//        ),
//        RegistrationUI(
//            "Замена масла",
//            "Завершена 30 августа",
//            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
//            "3000$rubleSimbol"
//        ),
//        RegistrationUI(
//            "Замена масла",
//            "Завершена 30 августа",
//            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
//            "3000$rubleSimbol"
//        ),
//        RegistrationUI(
//            "Замена масла",
//            "Завершена 30 августа",
//            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
//            "3000$rubleSimbol"
//        ),
//        RegistrationUI(
//            "Замена масла",
//            "Завершена 30 августа",
//            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
//            "3000$rubleSimbol"
//        ),
//        RegistrationUI(
//            "Замена масла",
//            "Завершена 30 августа",
//            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
//            "3000$rubleSimbol"
//        )
//        )
    val registrations = MutableLiveData<RequestResult<List<RegistrationUI>>>().apply {
        value = RequestResult.Loading()
    }

    fun updateRegistrations(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        registrations,
        coroutinesErrorHandler,
        request = { _registrationRepository.getRegistrations() },
        mapper = { data -> data.map { list -> list.map { it.toRegistrationUI() } } }
    )
}