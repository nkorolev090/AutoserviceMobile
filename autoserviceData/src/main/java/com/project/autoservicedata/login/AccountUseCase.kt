package com.project.autoservicedata.login

import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.models.SignInData
import com.project.autoservicedata.login.models.SignUpData
import com.project.autoservicedata.login.models.UserData
import com.project.autoservicedata.profile.UserContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val repository: AccountRepository,
    private val userContext: UserContext
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