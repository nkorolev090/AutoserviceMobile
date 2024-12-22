package com.project.autoservicedata.product

import com.project.autoservicedata.product.models.Product
import com.project.autoservicedata.product.models.toProduct
import com.project.common.api.RequestResultAPI
import com.project.common.data.RequestResult
import com.project.common.data.StatusCodeEnum
import com.project.common.firebase.firetoreRequestFlow
import com.project.firebaseapi.good.GoodManager
import com.project.firebaseapi.good.models.ProductDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val goodManager: GoodManager) {

    fun getProducts(): Flow<RequestResult<List<Product>>> {
        return firetoreRequestFlow {
            goodManager.getGoods()
        }.map { it.toProductList() }
    }

    fun getProductsFromQuery(query: String): Flow<RequestResult<List<Product>>> {
        return firetoreRequestFlow {
            goodManager.getGoodsFromQuery(query)
        }.map { it.toProductList() }
    }

    private fun RequestResultAPI<List<ProductDTO?>>.toProductList(): RequestResult<List<Product>> =
        when (this) {
            is RequestResultAPI.Success -> {
                if (this.data.isEmpty().not()) {
                    RequestResult.Success(this.data.mapNotNull { it.toProduct() })
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

