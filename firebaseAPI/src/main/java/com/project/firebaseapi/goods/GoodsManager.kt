package com.project.firebaseapi.goods

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject
import com.project.firebaseapi.goods.models.ProductDTO
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoodsManager @Inject constructor(private val db: FirebaseFirestore) {

    suspend fun getGoods() = db
        .collection(COLLECTION_PATH)
        .get()
        .await()
        .documents
        .map { it.toObject<ProductDTO>() }

    companion object {
        private const val COLLECTION_PATH = "Goods"
    }
}