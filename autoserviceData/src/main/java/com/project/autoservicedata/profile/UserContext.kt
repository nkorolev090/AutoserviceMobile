package com.project.autoservicedata.profile

import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.models.UserData
import com.project.autoservicedatabase.AutoserviceDatabase
import com.project.autoservicedatabase.models.UserDataDBO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class UserContext @Inject constructor(
    private val database: AutoserviceDatabase
) {
    private var _isAuthorize = false

    suspend fun setUserData(data: UserData) {
        database.userDataDao.insert(data.toUserDataDBO())
        _isAuthorize = true
//        CoroutineScope(Dispatchers.Main)
//            .launch{
//                _userData.value = data
//                _isAuthorize.value = true
//            }
    }

    suspend fun getUserData(): Flow<RequestResult<UserData>> = flow {
        emit(RequestResult.Loading())

        if (_isAuthorize) {
            val userData = database.userDataDao.getAll().firstOrNull()
            if (userData == null) {
                emit(RequestResult.Error(message = "cached user data empty"))
            } else {
                emit(RequestResult.Success(userData.toUserData()))
            }
        }
    }
}

private fun UserDataDBO.toUserData() : UserData {
    return UserData(
        id = this.id,
        name = this.name,
        surname = this.surname,
        midname = this.midname,
        email = this.email,
        userName = this.userName,
        phoneNumber = this.phoneNumber,
    )
}

private fun UserData.toUserDataDBO(): UserDataDBO {
    return UserDataDBO(
        id = this.id,
        name = this.name,
        surname = this.surname,
        midname = this.midname,
        email = this.email,
        userName = this.userName,
        phoneNumber = this.phoneNumber,
        requestDateTime = LocalDateTime.now()
    )
}
