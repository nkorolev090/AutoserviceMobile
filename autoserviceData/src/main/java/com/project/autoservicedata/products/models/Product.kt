package com.project.autoservicedata.products.models

import com.project.firebaseapi.goods.models.ProductDTO

data class Product(
    var article: String,
    var forAuto: String,
    var imageUrl: String,
    var price: Double,
    var title: String,
    var url: String,
)

fun ProductDTO?.toProduct(): Product? =
    try{
        Product(
            this!!.article!!,
            this.forAuto!!,
            this.imageUrl!!,
            this.price!!,
            this.title!!,
            this.url!!
        )
    }
    catch (ex: NullPointerException){
        null
    }
