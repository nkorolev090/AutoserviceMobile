package com.project.autoservicemobile.ui.profile.map

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicedata.login.models.UserData
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicedata.station.StationRepository
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.profile.map.models.StationUI
import com.project.autoservicemobile.ui.profile.map.models.toStationUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val stationRepository: StationRepository,
    private val accountRepository: AccountRepository,
    private val userContext: UserContext
) : BaseViewModel() {
    val stations = MutableLiveData<RequestResult<List<StationUI>>>().apply {
        value = RequestResult.Loading()
    }

    val user = MutableLiveData<RequestResult<UserData>>().apply {
        value = RequestResult.Loading()
    }

    val stationUpdated = MutableLiveData<RequestResult<Boolean>>().apply {
        value = RequestResult.Loading()
    }

    fun loadStations(coroutinesErrorHandler: CoroutinesErrorHandler) {
        baseRequest(
            liveData = stations,
            errorHandler = coroutinesErrorHandler,
            request = { stationRepository.getStations() },
            mapper = { data -> data.map { items -> items.map { it.toStationUI() } } }
        )
    }

    fun loadUserData(coroutinesErrorHandler: CoroutinesErrorHandler) {
        baseRequest(
            liveData = user,
            errorHandler = coroutinesErrorHandler,
            request = { userContext.getUserData() }
        )
    }

    fun updateDefaultStation(id: Int, coroutinesErrorHandler: CoroutinesErrorHandler){
        baseRequest(
            liveData = stationUpdated,
            errorHandler = coroutinesErrorHandler,
            request = { accountRepository.updateDefaultStation(id) }
        )
    }
}