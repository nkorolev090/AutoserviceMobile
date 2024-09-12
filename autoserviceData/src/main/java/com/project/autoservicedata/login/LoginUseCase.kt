package com.project.autoservicedata.login

import com.project.autoservicedata.common.RequestResult
import com.project.autoservicedata.login.models.SignInData
import com.project.autoservicedata.login.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository,
) {
    fun logIn(data: SignInData): Flow<RequestResult<Boolean>> {
        return repository.logIn(data)
    }
}