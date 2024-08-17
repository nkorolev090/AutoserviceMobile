package com.project.autoservicemobile.ui.registrations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI

class RegistrationsViewModel : ViewModel() {
    private var _registrations: List<RegistrationUI> = listOf(
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg"
        )
        )
    val registrations = MutableLiveData<List<RegistrationUI>>().apply {
        value = _registrations
    }

    public fun onGoToHomeClick(){
        MAIN.navController.navigate(R.id.action_registrationsFragment_to_navigation_home)
    }
}