package com.project.autoservicedata.breakdown

import com.project.autoserviceapi.breakdown.BreakdownApi
import com.project.autoserviceapi.breakdown.models.BreakdownDTO
import com.project.autoservicedata.breakdown.models.Breakdown
import com.project.common.api.RequestResultAPI
import com.project.common.api.apiRequestFlow
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BreakdownRepository @Inject constructor(
    private val api: BreakdownApi
) {
    fun getAllBreakdowns():  Flow<RequestResult<List<Breakdown>>>{
            return apiRequestFlow {
                api.getAllBreakdowns()
            }.map { response ->
                when(response){
                    is RequestResultAPI.Success -> {
                        if (response.data.isEmpty().not()){
                            RequestResult.Success(response.data.map { it.toBreakdown() })
                        }
                        else{
                            RequestResult.Error(code = StatusCodeEnum.NO_CONTENT ,message = "result is empty")
                        }
                    }
                    is RequestResultAPI.Error -> {
                        RequestResult.Error(message = response.message, code = response.code)
                        //getFromDatabase(city)
                        //RequestResult.Error(code = response.code, message = response.message)
                    }

                    is RequestResultAPI.Exception -> {
                        RequestResult.Error(error = response.throwable)
                        //getFromDatabase(city)
//                    RequestResult.Error(error = response.throwable)
                    }

                    is RequestResultAPI.Loading -> {
                        RequestResult.Loading()
                    }
                }
            }
    }
}

private fun BreakdownDTO.toBreakdown(): Breakdown {
    return Breakdown(
        id = this.id,
        title = this.title,
        info = this.info,
        price = this.price,
        warranty = this.warranty,
        imageUrl = this.imageUrl
    )
}
