package com.project.autoservicedata.login

import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.models.SignInData
import com.project.autoservicedata.login.models.User
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    suspend fun logIn(data: SignInData): RequestResult<User> {
        return repository.logIn(data)
    }
}