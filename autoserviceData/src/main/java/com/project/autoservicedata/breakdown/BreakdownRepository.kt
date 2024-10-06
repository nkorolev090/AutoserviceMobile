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
    suspend fun getAllBreakdowns(): Flow<RequestResult<List<Breakdown>>> {
        return apiRequestFlow {
            api.getAllBreakdowns()
        }.map { it.toBreakdownList() }
    }

    suspend fun getBreakdownsByQuery(query: String): Flow<RequestResult<List<Breakdown>>> {
        return apiRequestFlow {
            api.getBreakdownsByQuery(query)
        }.map { it.toBreakdownList() }
    }

    private fun RequestResultAPI<List<BreakdownDTO>>.toBreakdownList(): RequestResult<List<Breakdown>> =
        when (this) {
            is RequestResultAPI.Success -> {
                if (this.data.isEmpty().not()) {
                    RequestResult.Success(this.data.map { it.toBreakdown() })
                } else {
                    RequestResult.Error(
                        code = StatusCodeEnum.NO_CONTENT,
                        message = "result is empty"
                    )
                }
            }

            is RequestResultAPI.Error -> {
                RequestResult.Error(message = this.message, code = this.code)
                //getFromDatabase(city)
                //RequestResult.Error(code = response.code, message = response.message)
            }

            is RequestResultAPI.Exception -> {
                RequestResult.Error(error = this.throwable)
                //getFromDatabase(city)
//                    RequestResult.Error(error = response.throwable)
            }

            is RequestResultAPI.Loading -> {
                RequestResult.Loading()
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
