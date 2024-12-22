package com.project.firebaseapi.good

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.project.firebaseapi.good.models.ProductDTO
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoodManager @Inject constructor(private val db: FirebaseFirestore) {

    suspend fun getGoods() = db
        .collection(COLLECTION_PATH)
        .get()
        .await()
            .documents
            .map { it.toObject<ProductDTO>() }

    suspend fun getGoodsFromQuery(query: String) = db
        .collection(COLLECTION_PATH)
        .orderBy("title")
        .startAt(query)
        .endAt("$query~")//mb
        .get()
        .await()
        .documents
        .map { it.toObject<ProductDTO>() }

    companion object {
        private const val COLLECTION_PATH = "Goods"
    }
}