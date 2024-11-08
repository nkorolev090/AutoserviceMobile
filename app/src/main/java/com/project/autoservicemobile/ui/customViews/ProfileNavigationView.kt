package com.project.autoservicemobile.ui.customViews

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView
import androidx.core.content.withStyledAttributes
import com.project.autoservicemobile.R
import  androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.autoservicemobile.ui.AppTheme
import com.project.autoservicemobile.ui.vietnamFamily
import kotlin.math.round

class ProfileNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {

    var navigationData: MutableState<String> = mutableStateOf("A string")

    init {
        context.withStyledAttributes(attrs, R.styleable.ProfileNavigationView) {
            navigationData.value = getString(R.styleable.ProfileNavigationView_textTitle) ?: ""
        }
    }

    @Composable
    override fun Content() {
        AppTheme {
            ComposeProfileNavigationView(title = navigationData.value)
        }
    }
}

@Composable
fun ComposeProfileNavigationView(title: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondaryContainer, shape = RoundedCornerShape( corner = CornerSize(10.dp))),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 16.sp,
            fontFamily = vietnamFamily,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileNavigationViewPreview(){
    AppTheme {
        ComposeProfileNavigationView(title = "Часто задаваемые вопросы")
    }
}