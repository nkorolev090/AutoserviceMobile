package com.project.autoservicedata.login

import com.project.autoserviceapi.login.LogInApi
import com.project.autoserviceapi.login.RequestResultAPI
import com.project.autoserviceapi.login.models.AuthorizationResponse
import com.project.autoserviceapi.login.models.SignInRequestData
import com.project.autoserviceapi.login.models.UserDTO
import com.project.autoserviceapi.utils.apiRequestFlow
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.models.SignInData
import com.project.autoservicedata.login.models.User
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicedata.token.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LogInApi,
    private val tokenManager: TokenManager,
    private val userContext: UserContext
) {
    fun logIn(data: SignInData): Flow<RequestResult<Boolean>> {
        return apiRequestFlow {
            api.loginResponse(data.toSignInRequestData())
        }.map { response ->
            when(response){
                is RequestResultAPI.Success->{
//                    saveNetResponseToCache(response.data)
                    if(response.data.token != null && response.data.user != null){
                        tokenManager.saveToken(response.data.token!!)
                        userContext.setUserData(response.data.user!!.toUser())

                        RequestResult.Success(true)
                    }
                    else{
                        RequestResult.Error(message = "token is null")
                    }

                }
                is RequestResultAPI.Error->{
                    RequestResult.Error(message = response.message, code = response.code)
                    //getFromDatabase(city)
                    //RequestResult.Error(code = response.code, message = response.message)
                }
                is RequestResultAPI.Exception-> {
                    RequestResult.Error(error = response.throwable)
                    //getFromDatabase(city)
//                    RequestResult.Error(error = response.throwable)
                }
                is RequestResultAPI.Loading -> {
                    RequestResult.Loading()
                }
            }
        }
//        val response = handleApi { api.loginResponse(data.toSignInRequestData()) }
//
//        return when(response){
//            is RequestResultAPI.Success->{
////                saveNetResponseToCache(response.data)
//                RequestResult.Success(response.data.toUser())}
//            is RequestResultAPI.Error->{
//                RequestResult.Error(message = response.message, code = response.code)
//                //getFromDatabase(city)
//                //RequestResult.Error(code = response.code, message = response.message)
//            }
//            is RequestResultAPI.Exception-> {
//                RequestResult.Error(error = response.throwable)
//                //getFromDatabase(city)
////                RequestResult.Error(error = response.throwable)
//            }
//        }
    }
}

private fun UserDTO.toUser(): User {
return User(
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
