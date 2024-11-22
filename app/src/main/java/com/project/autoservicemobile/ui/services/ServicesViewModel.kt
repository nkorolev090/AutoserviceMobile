package com.project.autoservicemobile.ui.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.autoservicedata.breakdown.BreakdownRepository
import com.project.autoservicedata.products.ProductsRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.services.models.ProductUI
import com.project.autoservicemobile.ui.services.models.ServiceUI
import com.project.autoservicemobile.ui.services.models.toProductUI
import com.project.autoservicemobile.ui.services.models.toServiceUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val breakdownRepository: BreakdownRepository,
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    val titleText = MutableLiveData<String>().apply {
        value = "Услуги для вас"
    }
    val searchInputHint = "Начните вводить название услуги"

    val services = MutableLiveData<RequestResult<List<ServiceUI>>>().apply {
        value = RequestResult.Loading()
    }

    val products = MutableLiveData<RequestResult<List<ProductUI>>>().apply{
        value = RequestResult.Loading()
    }

    fun getServices(query: String, coroutinesErrorHandler: CoroutinesErrorHandler){
        if (query == "") {
            titleText.postValue("Услуги для вас")

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

    fun getProducts(query: String, coroutinesErrorHandler: CoroutinesErrorHandler) {
        if (query == "") {
            titleText.postValue("Товары для вас")

            baseRequest(
            products,
            coroutinesErrorHandler,
            request = {productsRepository.getProducts()},
            mapper = { data -> data.map { list -> list.map { it.toProductUI() } } }
        )}
        else{
            titleText.postValue("Результаты по запросу: \"$query\"")

            baseRequest(
                products,
                coroutinesErrorHandler,
                request = {productsRepository.getProductsFromQuery(query)},
                mapper = { data -> data.map { list -> list.map { it.toProductUI() } } }
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
