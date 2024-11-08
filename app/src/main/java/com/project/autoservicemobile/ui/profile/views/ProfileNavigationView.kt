package com.project.autoservicemobile.ui.profile.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.project.autoservicemobile.R
import  androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.autoservicemobile.ui.AppTheme
import com.project.autoservicemobile.ui.profile.models.NavigationItemEnum
import com.project.autoservicemobile.ui.profile.models.toImageResource
import com.project.autoservicemobile.ui.profile.models.toTitleResource
import com.project.autoservicemobile.ui.vietnamFamily

@Composable
fun ProfileNavigationView(navItem: NavigationItemEnum) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(corner = CornerSize(18.dp))
            )
            .wrapContentHeight()
            .padding(20.dp, 10.dp, 12.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = navItem.toTitleResource()),
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 16.sp,
            fontFamily = vietnamFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Image(
            painterResource(id = navItem.toImageResource()),
            contentDescription = "",
            modifier = Modifier.size(27.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileNavigationViewPreview(){
    AppTheme {
        ProfileNavigationView(NavigationItemEnum.FAQ)
    }
}