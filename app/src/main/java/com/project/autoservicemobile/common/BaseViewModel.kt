package com.project.autoservicemobile.common
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class BaseViewModel : ViewModel() {
    private var mJob: Job? = null

    protected fun <T, E> baseRequest(liveData: MutableLiveData<E>, errorHandler: CoroutinesErrorHandler, request: suspend () -> Flow<T>, mapper: (T) -> E) {
        mJob = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            viewModelScope.launch(Dispatchers.Main) {
                errorHandler.onError(error.localizedMessage ?: "Error occured! Please try again.")
            }
        }){
            request().map { mapper(it) }.collect {
                withContext(Dispatchers.Main) {
                    liveData.value = it
                }
            }
        }
    }

    protected fun <T> baseRequest(liveData: MutableLiveData<T>, errorHandler: CoroutinesErrorHandler, request: suspend () -> Flow<T>) {
        mJob = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            viewModelScope.launch(Dispatchers.Main) {
                errorHandler.onError(error.localizedMessage ?: "Error occured! Please try again.")
            }
        }){
            request().collect {
                withContext(Dispatchers.Main) {
                    liveData.value = it
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }
}

interface CoroutinesErrorHandler {
    fun onError(message:String)
}