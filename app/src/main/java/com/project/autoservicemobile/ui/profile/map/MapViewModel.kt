package com.project.autoservicemobile.ui.profile.map

import androidx.lifecycle.MutableLiveData
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
    private val stationRepository: StationRepository
) : BaseViewModel() {
    public val stations = MutableLiveData<RequestResult<List<StationUI>>>().apply {
        value = RequestResult.Loading()
    }

    public fun LoadStations(coroutinesErrorHandler: CoroutinesErrorHandler) {
        baseRequest(
            liveData = stations,
            errorHandler = coroutinesErrorHandler,
            request = { stationRepository.getStations() },
            mapper = { data -> data.map { items -> items.map { it.toStationUI() } } }
        )
    }
}