package com.project.autoservicemobile.ui.loyalty

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.autoservicemobile.ui.loyalty.models.PointUI
import com.project.autoservicemobile.ui.loyalty.models.RewardUI
import com.project.autoservicemobile.ui.services.models.ServiceUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoyaltyViewModel @Inject constructor() : ViewModel() {

    private var _points: List<PointUI> = listOf(
        PointUI(
            "Rides & Drives",
            "Every \$1 spent earns 1 point",
            "200"
        ),

        PointUI(
            "Gift Cards",
            "Every \$1 spent earns 1 point",
            "200"
        ),

        PointUI(
            "Merchandise",
            "Every \$2 spent earns 1 point",
            "200"
        ),

    )
    val points = MutableLiveData<List<PointUI>>().apply {
        value = _points
    }

    private var _rewards: List<RewardUI> = listOf(
        RewardUI(
            "Награда за первое посещение",
            "500 баллов",
            "https://images.unsplash.com/photo-1723519248300-a9c2f25a0574?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),
        RewardUI(
            "Награда за друга",
            "2500 баллов",
            "https://images.unsplash.com/photo-1722882856121-6daf82c6fac5?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),
        RewardUI(
            "Каждое 5 замена масла",
            "200 баллов",
            "https://plus.unsplash.com/premium_photo-1680528142979-7fc43eb3113a?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        ),

        )
    val rewards = MutableLiveData<List<RewardUI>>().apply {
        value = _rewards
    }

}