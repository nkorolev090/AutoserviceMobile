package com.project.autoservicedata.login

import android.annotation.SuppressLint
import com.project.autoserviceapi.deviceToken.DeviceTokenApi
import com.project.autoserviceapi.login.AccountApi
import com.project.autoserviceapi.login.models.AuthorizationResponseDTO
import com.project.autoserviceapi.login.models.ClientDTO
import com.project.autoserviceapi.login.models.SignInRequestData
import com.project.autoserviceapi.login.models.SignUpRequestData
import com.project.autoserviceapi.login.models.UserDataDTO
import com.project.autoservicedata.login.models.Client
import com.project.common.data.RequestResult
import com.project.autoservicedata.login.models.SignInData
import com.project.autoservicedata.login.models.SignUpData
import com.project.autoservicedata.login.models.UserData
import com.project.autoservicedata.profile.UserContext
import com.project.token.TokenManager
import com.project.common.api.RequestResultAPI
import com.project.common.api.apiRequestFlow
import com.project.common.data.StatusCodeEnum
import com.project.common.data.toRequestResult
import com.project.deviceToken.DeviceTokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val api: AccountApi,
    private val tokenManager: TokenManager,
    private val deviceTokenManager: DeviceTokenManager,
    private val tokenApi: DeviceTokenApi,
    private val userContext: UserContext
) {
    suspend fun logIn(data: SignInData): Flow<RequestResult<Boolean>> {
        return apiRequestFlow {
            api.loginResponse(data.toSignInRequestData())
        }.map { it.toAuthorizationRequestResult() }
    }

    suspend fun logUp(data: SignUpData): Flow<RequestResult<Boolean>> {
        return apiRequestFlow {
            api.logupResponse(data.toSignUpRequestData())
        }.map { it.toAuthorizationRequestResult() }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun isAuthenticated(): Flow<RequestResult<Boolean>> {
        val token = tokenManager.getToken()

        return apiRequestFlow {
            api.isAuthenticatedResponse(token)
        }.map { response ->
            when (response) {
                is RequestResultAPI.Success -> {
//                    saveNetResponseToCache(response.data)
                    if (response.data.user != null) {
                        userContext.setUserData(response.data.user!!.toUserData())

                        RequestResult.Success(true)
                    } else {
                        RequestResult.Error(
                            code = StatusCodeEnum.NO_CONTENT,
                            message = "userData is null"
                        )
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

    suspend fun logOff() {
        tokenManager.deleteToken()
        userContext.clearUserDataCache()
    }

    suspend fun updateDefaultStation(stationId: Int): Flow<RequestResult<Boolean>> {
        val token = tokenManager.getToken()
        return apiRequestFlow { api.updateDefaultStation(token, stationId) }
            .map { result ->
                result.toRequestResult(successAction = {
                    if(it.data.defaultStationId == stationId){
                        userContext.updateClient(it.data.defaultStationId, it.data.id)
                        RequestResult.Success(true)
                    }else{
                        RequestResult.Error(code = StatusCodeEnum.NO_CONTENT)
                    }
                })
        }
    }


    private suspend fun RequestResultAPI<AuthorizationResponseDTO>.toAuthorizationRequestResult(): RequestResult<Boolean> {
        return when (this) {
            is RequestResultAPI.Success -> {
//                    saveNetResponseToCache(response.data)
                if (this.data.token != null && this.data.user != null) {
                    tokenManager.saveToken(this.data.token!!)
                    userContext.setUserData(this.data.user!!.toUserData())

                    val deviceToken = deviceTokenManager.getToken()
                    if (deviceToken != null) {
                        tokenApi.saveDeviceToken(deviceToken)
                    }

                    RequestResult.Success(true)
                } else {
                    RequestResult.Error(message = "token is null")
                }

            }

            is RequestResultAPI.Error -> {
                RequestResult.Error(message = this.message, code = this.code)
                //getFromDatabase(city)
                //RequestResult.Error(code = response.code, message = response.message)
            }

            is RequestResultAPI.Exception -> {
                RequestResult.Error(error = this.throwable)
                //getFromDatabase(city)
//                    RequestResult.Error(error = response.throwable)
            }

            is RequestResultAPI.Loading -> {
                RequestResult.Loading()
            }
        }
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
        client = this.client?.toClient()
    )
}

private fun ClientDTO.toClient(): Client =
    Client(
        id = id,
        discountName = discountName,
        discountPoints = discountPoints,
        defaultStationId = defaultStationId,
        birthday = birthday
    )

private fun SignInData.toSignInRequestData(): SignInRequestData {
    return SignInRequestData(
        email = this.email,
        password = this.password,
        rememberMe = true
    )
}