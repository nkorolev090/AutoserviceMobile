package com.project.autoservicedata.login

import com.project.common.data.RequestResult
import com.project.autoservicedata.login.models.SignInData
import com.project.autoservicedata.login.models.SignUpData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend fun logIn(data: SignInData): Flow<RequestResult<Boolean>> {
        return repository.logIn(data)
    }

    suspend fun logUp(data: SignUpData): Flow<RequestResult<Boolean>> {
        return repository.logUp(data)
    }

    suspend fun isAuthenticated(): Flow<RequestResult<Boolean>> {
        return repository.isAuthenticated()
    }

    suspend fun logOff(){
        repository.logOff()
    }

//    flow {
//        emit(RequestResult.Loading())
//
//        withTimeoutOrNull(20000L) {
//            userContext.getUserData().collect {
//                emit(RequestResult.Success(it))
//            }
//        } ?: repository.isAuthenticated().collect { result ->
//            emit(result)
//        }
//    }
}