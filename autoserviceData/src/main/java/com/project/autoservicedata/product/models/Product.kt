package com.project.autoservicedata.product.models

import com.project.firebaseapi.good.models.ProductDTO

data class Product(
    var article: String,
    var brand: String,
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
            this.brand!!,
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

