package com.project.firebaseapi.station

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.project.firebaseapi.station.models.StationDTO
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StationManager @Inject constructor(private val db: FirebaseFirestore) {

    suspend fun getStations() = db
        .collection(COLLECTION_PATH)
        .get()
        .await()
            .documents
            .map { it.toObject<StationDTO>() }

    companion object {
        private const val COLLECTION_PATH = "Stations"
    }
}