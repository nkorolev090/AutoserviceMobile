package com.project.autoservicemobile.ui.services

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.breakdown.BreakdownRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.project.autoservicemobile.ui.services.models.toServiceUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val breakdownRepository: BreakdownRepository
) : BaseViewModel() {

    val titleText = MutableLiveData<String>().apply {
        value = "Рекомендации"
    }
    val searchInputHint = "Начните вводить название услуги"

    val services = MutableLiveData<RequestResult<List<ServiceUI>>>().apply {
        value = RequestResult.Loading()
    }

    fun getServices(query: String, coroutinesErrorHandler: CoroutinesErrorHandler){
        if (query == "") {
            titleText.postValue("Рекомендации")

            baseRequest(
                services,
                coroutinesErrorHandler,
                request = { breakdownRepository.getAllBreakdowns() },
                mapper = { data -> data.map { list -> list.map { it.toServiceUI() } } }
            )
        } else {
            titleText.postValue("Результаты по запросу: \"$query\"")

            baseRequest(
                services,
                coroutinesErrorHandler,
                request = { breakdownRepository.getBreakdownsByQuery(query) },
                mapper = { data -> data.map { list -> list.map { it.toServiceUI() } } }
            )
        }
    }

    fun onCartBtnClick(service: ServiceUI){
        service.inCart = !service.inCart
    }

    fun onFavoritesBtnClick(service: ServiceUI){
        service.inFavourites = !service.inFavourites
    }
}
