package com.project.autoservicedata.station

import android.util.Log
import com.project.autoservicedata.station.models.Station
import com.project.autoservicedata.station.models.toStation
import com.project.common.api.RequestResultAPI
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import com.project.common.firebase.firetoreRequestFlow
import com.project.firebaseapi.station.StationManager
import com.project.firebaseapi.station.models.StationDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StationRepository @Inject constructor(private val stationManager: StationManager) {

    fun getStations(): Flow<RequestResult<List<Station>>> {
        return firetoreRequestFlow {
            stationManager.getStations()
        }.map { it.toProductList() }
    }

    private fun RequestResultAPI<List<StationDTO?>>.toProductList(): RequestResult<List<Station>> =
        when (this) {
            is RequestResultAPI.Success -> {
                if (this.data.isEmpty().not()) {
                    Log.w("STATION_REPO", this.data.toString())
                    RequestResult.Success(this.data.mapNotNull { it?.toStation() })
                } else {
                    RequestResult.Error(
                        code = StatusCodeEnum.NO_CONTENT,
                        message = "result is empty"
                    )
                }
            }

            is RequestResultAPI.Error -> {
                RequestResult.Error(message = this.message, code = this.code)
            }

            is RequestResultAPI.Exception -> {
                RequestResult.Error(error = this.throwable)
            }

            is RequestResultAPI.Loading -> {
                RequestResult.Loading()
            }
        }
}

