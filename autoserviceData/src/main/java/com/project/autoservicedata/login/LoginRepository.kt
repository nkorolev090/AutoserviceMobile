package com.project.autoservicedata.login

import com.project.autoserviceapi.login.LogInApi
import com.project.autoserviceapi.login.RequestResultAPI
import com.project.autoserviceapi.login.handleApi
import com.project.autoserviceapi.login.models.AuthorizationResponse
import com.project.autoserviceapi.login.models.SignInRequestData
import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.models.SignInData
import com.project.autoservicedata.login.models.User
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LogInApi,
) {
    suspend fun logIn(data: SignInData): RequestResult<User> {
        val response = handleApi { api.loginResponse(data.toSignInRequestData()) }

        return when(response){
            is RequestResultAPI.Success->{
//                saveNetResponseToCache(response.data)
                RequestResult.Success(response.data.toUser())}
            is RequestResultAPI.Error->{
                RequestResult.Error(message = response.message, code = response.code)
                //getFromDatabase(city)
                //RequestResult.Error(code = response.code, message = response.message)
            }
            is RequestResultAPI.Exception-> {
                RequestResult.Error(error = response.throwable)
                //getFromDatabase(city)
//                RequestResult.Error(error = response.throwable)
            }
        }
    }
}

private fun AuthorizationResponse.toUser(): User {
return User(
    id = this.user!!.id,
    name = this.user!!.name,
    surname = this.user!!.surname,
    midname = this.user!!.midname,
    email = this.user!!.email,
    userName = this.user!!.userName,
    phoneNumber = this.user!!.phoneNumber,
)
}

private fun SignInData.toSignInRequestData(): SignInRequestData {
return SignInRequestData(
    email = this.email,
    password = this.password,
    rememberMe = true
)
}
