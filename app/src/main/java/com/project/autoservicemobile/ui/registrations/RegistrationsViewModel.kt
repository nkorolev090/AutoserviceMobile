package com.project.autoservicemobile.ui.registrations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicemobile.MAIN
import com.project.autoservicemobile.R
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.registrations.details.RegistrationDetailsBottomSheetDialog
import com.project.autoservicemobile.ui.registrations.models.RegistrationUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationsViewModel  @Inject constructor() : ViewModel() {
    private var _registrations: List<RegistrationUI> = listOf(
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
            "3000$rubleSimbol"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
            "3000$rubleSimbol"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
            "3000$rubleSimbol"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
            "3000$rubleSimbol"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
            "3000$rubleSimbol"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
            "3000$rubleSimbol"
        ),
        RegistrationUI(
            "Замена масла",
            "Завершена 30 августа",
            "https://part4usa.ru/upload/iblock/593/3logakj6ro9o2w3r08uvhr1gqvpg4kxv.jpg",
            "3000$rubleSimbol"
        )
        )
    val registrations = MutableLiveData<List<RegistrationUI>>().apply {
        value = _registrations
    }

    public fun onGoToHomeClick(){
        MAIN.navController.navigate(R.id.action_registrationsFragment_to_navigation_home)
    }

    public fun openRegistrationDetails(registration: RegistrationUI){
        val modalBottomSheet = RegistrationDetailsBottomSheetDialog()
        modalBottomSheet.show(MAIN.supportFragmentManager, RegistrationDetailsBottomSheetDialog.TAG)
    }
}