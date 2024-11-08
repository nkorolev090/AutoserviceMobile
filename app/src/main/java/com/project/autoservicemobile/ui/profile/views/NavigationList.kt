package com.project.autoservicemobile.ui.profile.views

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.unit.dp
import com.project.autoservicemobile.ui.AppTheme
import com.project.autoservicemobile.ui.profile.models.NavigationItemEnum

class NavigationList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {
    
    val navigationMutableList: MutableState<List<NavigationItemEnum>> =
        mutableStateOf(
            listOf()
        )
    
    private val navigationList get() = navigationMutableList.value
    @Composable
    override fun Content() {
        AppTheme {
            Column(
                modifier = Modifier
                    .padding(0.dp, 10.dp,0.dp,0.dp)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                    .padding(10.dp, 10.dp,10.dp,0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (item in navigationList){
                    ProfileNavigationView(navItem = item)
                    if (navigationList.indexOf(item) != navigationList.lastIndex){
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}