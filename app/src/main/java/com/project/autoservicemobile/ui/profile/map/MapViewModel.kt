package com.project.autoservicemobile.ui.profile.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.autoservicedata.login.AccountRepository
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicemobile.common.BaseViewModel
import com.project.autoservicemobile.common.CoroutinesErrorHandler
import com.project.autoservicemobile.ui.profile.models.UserDataUI
import com.project.autoservicemobile.ui.profile.models.toUserDataUI
import com.project.common.data.RequestResult
import com.project.common.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val userContext: UserContext
) : BaseViewModel() {
}