package com.project.autoservicedata.profile

import com.project.autoservicedata.login.models.Client
import com.project.common.data.RequestResult
import com.project.autoservicedata.login.models.UserData
import com.project.autoservicedatabase.AutoserviceDatabase
import com.project.autoservicedatabase.models.ClientDBO
import com.project.autoservicedatabase.models.UserDataDBO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class UserContext @Inject constructor(
    private val database: AutoserviceDatabase
) {
 //   private var _isAuthorize = false

//    val userData = MutableLiveData<UserData?>().apply {
//        value = null
//    }

    suspend fun setUserData(data: UserData) {
        if(data.client != null){
            database.clientDao.insert(data.client.toClientDBO())
            database.userDataDao.insert(data.toUserDataDBO())
        }
    }

    suspend fun setClient(client: Client){
        database.clientDao.insert(client.toClientDBO())
    }

    suspend fun updateClientStation(stationId: Int?, id: Int){
        database.clientDao.updateStation(stationId, id)
    }

    suspend fun updateClientCar(carId: Int?, id: Int){
        database.clientDao.updateCar(carId, id)
    }

    fun getUserData(): Flow<RequestResult<UserData>> = flow {
        emit(RequestResult.Loading())

            val userData = database.userDataDao.getAll().firstOrNull()
            if (userData?.clientId == null) {
                emit(RequestResult.Error(message = "cached user data empty"))
            } else {
                val clientDBO = database.clientDao.get(userData.clientId!!).firstOrNull()
                if(clientDBO == null){
                    emit(RequestResult.Error(message = "cached client data empty"))
                }
                else{
                    emit(RequestResult.Success(userData.toUserData(clientDBO)))
                }
        }
    }
    suspend fun updateUserData() {
        val userDataDb = database.userDataDao.getAll().firstOrNull()
    }

    suspend fun clearUserDataCache(){
        database.userDataDao.clean()
        database.clientDao.clean()
    }
}

private fun UserDataDBO.toUserData(clientDBO: ClientDBO?) : UserData {
    return UserData(
        id = this.id,
        name = this.name,
        surname = this.surname,
        midname = this.midname,
        email = this.email,
        userName = this.userName,
        phoneNumber = this.phoneNumber,
        client = clientDBO?.toClient()
    )
}

private fun ClientDBO.toClient(): Client =
    Client(
        id = id,
        discountName = discountName,
        discountPoints = discountPoints,
        defaultStationId = defaultStationId,
        defaultCarId = defaultCarId,
        birthday = birthday
    )

private fun UserData.toUserDataDBO(): UserDataDBO {
    return UserDataDBO(
        id = this.id,
        name = this.name,
        surname = this.surname,
        midname = this.midname,
        email = this.email,
        userName = this.userName,
        phoneNumber = this.phoneNumber,
        requestDateTime = LocalDateTime.now(),
        clientId = this.client?.id
    )
}

private fun Client.toClientDBO(): ClientDBO=
    ClientDBO(
        id = id,
        discountName = discountName,
        discountPoints = discountPoints,
        defaultStationId = defaultStationId,
        defaultCarId = defaultCarId,
        birthday = birthday
    )
