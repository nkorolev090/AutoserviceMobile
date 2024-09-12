package com.project.autoservicedata.profile

import android.service.autofill.UserData
import androidx.lifecycle.MutableLiveData
import com.project.autoservicedata.login.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserContext {
    private  val _isAuthorize = MutableLiveData<Boolean>().apply {
        value = false
    }

    val isAuthorize = _isAuthorize

    private val _userData = MutableLiveData<User?>().apply {
        value = null
    }
    val userData = _userData

    fun setUserData (data: User){
        CoroutineScope(Dispatchers.Main)
            .launch{
                withContext(Dispatchers.Main) {
                _userData.value = data
                _isAuthorize.value = true
                }
            }
    }
    // add cache
}