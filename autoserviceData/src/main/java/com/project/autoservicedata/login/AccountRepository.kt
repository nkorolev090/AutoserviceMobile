package com.project.autoservicedata.login

import android.annotation.SuppressLint
import com.project.autoserviceapi.login.AccountApi
import com.project.autoserviceapi.login.models.SignInRequestData
import com.project.autoserviceapi.login.models.SignUpRequestData
import com.project.autoserviceapi.login.models.UserDataDTO
import com.project.common.data.RequestResult
import com.project.autoservicedata.login.models.SignInData
import com.project.autoservicedata.login.models.SignUpData
import com.project.autoservicedata.login.models.UserData
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicedata.token.TokenManager
import com.project.common.api.RequestResultAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val api: AccountApi,
    private val tokenManager: TokenManager,
    private val userContext: UserContext
) {
    suspend fun logIn(data: SignInData): Flow<RequestResult<Boolean>> {
        return com.project.common.api.apiRequestFlow {
            api.loginResponse(data.toSignInRequestData())
        }.map { response ->
            when (response) {
                is RequestResultAPI.Success -> {
//                    saveNetResponseToCache(response.data)
                    if (response.data.token != null && response.data.user != null) {
                        tokenManager.saveToken(response.data.token!!)
                        userContext.setUserData(response.data.user!!.toUserData())

                        RequestResult.Success(true)
                    } else {
                        RequestResult.Error(message = "token is null")
                    }

                }

                is RequestResultAPI.Error -> {
                    RequestResult.Error(message = response.message, code = response.code)
                    //getFromDatabase(city)
                    //RequestResult.Error(code = response.code, message = response.message)
                }

                is RequestResultAPI.Exception -> {
                    RequestResult.Error(error = response.throwable)
                    //getFromDatabase(city)
//                    RequestResult.Error(error = response.throwable)
                }

                is RequestResultAPI.Loading -> {
                    RequestResult.Loading()
                }
            }
        }
    }

    suspend fun logUp(data: SignUpData): Flow<RequestResult<Boolean>> {
        return com.project.common.api.apiRequestFlow {
            api.logupResponse(data.toSignUpRequestData())
        }.map { response ->
            when (response) {
                is RequestResultAPI.Success -> {
//                    saveNetResponseToCache(response.data)
                    if (response.data.token != null && response.data.user != null) {
                        tokenManager.saveToken(response.data.token!!)
                        userContext.setUserData(response.data.user!!.toUserData())

                        RequestResult.Success(true)
                    } else {
                        RequestResult.Error(message = "token is null")
                    }

                }

                is RequestResultAPI.Error -> {
                    RequestResult.Error(message = response.message, code = response.code)
                    //getFromDatabase(city)
                    //RequestResult.Error(code = response.code, message = response.message)
                }

                is RequestResultAPI.Exception -> {
                    RequestResult.Error(error = response.throwable)
                    //getFromDatabase(city)
//                    RequestResult.Error(error = response.throwable)
                }

                is RequestResultAPI.Loading -> {
                    RequestResult.Loading()
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun isAuthenticated(): Flow<RequestResult<Boolean>> {
        val token = tokenManager.getToken()
            ?: return flowOf(RequestResult.Error(message = "token is null"))

        return com.project.common.api.apiRequestFlow {
            api.isAuthenticatedResponse(token)
        }.map { response ->
            when (response) {
                is RequestResultAPI.Success -> {
//                    saveNetResponseToCache(response.data)
                    if (response.data.user != null) {
                        userContext.setUserData(response.data.user!!.toUserData())

                       RequestResult.Success(true)
                    } else {
                        RequestResult.Error(message = "userData is null")
                    }
                }

                is RequestResultAPI.Error -> {
                    RequestResult.Error(message = response.message, code = response.code)
                    //getFromDatabase(city)
                    //RequestResult.Error(code = response.code, message = response.message)
                }

                is RequestResultAPI.Exception -> {
                    RequestResult.Error(error = response.throwable)
                    //getFromDatabase(city)
//                    RequestResult.Error(error = response.throwable)
                }

                is RequestResultAPI.Loading -> {
                    RequestResult.Loading()
                }
            }
        }
    }

    suspend fun logOff(){
        tokenManager.deleteToken()
        userContext.clearUserDataCache()
    }
}

private fun SignUpData.toSignUpRequestData(): SignUpRequestData {
return SignUpRequestData(
    name = this.name,
    email = this.email,
    password = this.password,
    passwordApply = this.password
)
}

private fun UserDataDTO.toUserData(): UserData {
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

private fun SignInData.toSignInRequestData(): SignInRequestData {
return SignInRequestData(
    email = this.email,
    password = this.password,
    rememberMe = true
)
}