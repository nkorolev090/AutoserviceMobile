package com.project.autoservicemobile.ui.services

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.breakdown.BreakdownRepository
import com.project.autoservicedata.breakdown.models.Breakdown
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.rubleSimbol
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val breakdownRepository: BreakdownRepository
) : BaseViewModel() {

    val titleText: String = "Рекомендации"
    val searchInputHint = "Начните вводить название услуги"

    val services = MutableLiveData<RequestResult<List<ServiceUI>>>().apply {
        value = RequestResult.Loading()
    }

    fun updateServices(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        services,
        coroutinesErrorHandler,
        request = { breakdownRepository.getAllBreakdowns() },
        mapper = { data -> data.map { list -> list.map { it.toServiceUI() } } }
    )

    fun onCartBtnClick(service: ServiceUI){
        service.inCart = !service.inCart
    }

    fun onFavoritesBtnClick(service: ServiceUI){
        service.inFavourites = !service.inFavourites
    }

    fun searchServices(searchString: String){

    }
}

private fun Breakdown.toServiceUI(): ServiceUI {
    return ServiceUI(
        title = this.title,
        priceText = this.price.toString() + rubleSimbol,
        imageUrl = this.imageUrl,
        warrantyText = this.warranty.toWarrantyText()
    )
}

private fun Int.toWarrantyText(): String {
    val postfix = when{
        this == 0 -> "Не предусмотрена"
        this in 10..14 || this % 10 in 5..9 -> "Лет"
        this % 10 == 1 -> "Год"
        this % 10 in 2..4 -> "Года"
        else -> "Ошибка"
    }

    return "$this $postfix"
}
