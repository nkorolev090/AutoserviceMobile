package com.project.autoservicemobile.ui.services.views

import android.content.Context
import android.util.AttributeSet
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.project.autoservicemobile.common.models.VibrateTypeEnum
import com.project.autoservicemobile.common.vibrate
import com.project.autoservicemobile.ui.AppTheme
import com.project.autoservicemobile.ui.services.models.SearchItemEnum
import com.project.autoservicemobile.ui.services.models.toTitleResource
import com.project.autoservicemobile.ui.vietnamFamily

class SearchSliderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {

    var mutableSearchList: MutableState<List<SearchItemEnum>> =
        mutableStateOf(
            listOf()
        )

    private val selected: MutableState<SearchItemEnum>  =
        mutableStateOf(
            SearchItemEnum.PRODUCTS
        )

    val mutableSelected = MutableLiveData<SearchItemEnum>().apply {
        value = SearchItemEnum.PRODUCTS
    }

    @Composable
    override fun Content() {
        AppTheme {
            SearchButtonsRow(
                items = mutableSearchList.value,
                selectedItem = selected.value
            ) { onClick() }
        }
    }

    private fun onClick(){
        vibrate(context, VibrateTypeEnum.MEDIUM_WAVE)
        selected.value = when(selected.value){
             SearchItemEnum.PRODUCTS -> SearchItemEnum.SERVICES
            SearchItemEnum.SERVICES -> SearchItemEnum.PRODUCTS
        }

        mutableSelected.postValue(selected.value)
    }
}

@Composable
private fun SearchButtonsRow(items: List<SearchItemEnum>, selectedItem: SearchItemEnum, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .background(
                MaterialTheme.colorScheme.onSecondaryContainer,
                RoundedCornerShape(30.dp)
            )
            .width(IntrinsicSize.Min),
    ) {
        val activeColor = MaterialTheme.colorScheme.secondaryContainer
        val inactiveColor = MaterialTheme.colorScheme.onSecondaryContainer
        val activeTextColor = MaterialTheme.colorScheme.tertiary
        val inactiveTextColor = MaterialTheme.colorScheme.tertiaryContainer

        for (item in items) {
            var colorState by remember {
                mutableStateOf(
                    if(item==selectedItem){
                        activeColor
                    }else{
                        inactiveColor
                    }
                )
            }

            var colorTextState by remember {
                mutableStateOf(
                    if(item==selectedItem){
                        activeTextColor
                    }else{
                        inactiveTextColor
                    }
                )
            }

            val animatedColor: Color by animateColorAsState(
                targetValue = colorState,
                animationSpec = tween(400)
            )

            val animatedTextColor: Color by animateColorAsState(
                targetValue = colorTextState,
                animationSpec = tween(400)
            )

            colorState = if(item==selectedItem){
                activeColor
            }else{
                inactiveColor
            }

            colorTextState = if(item==selectedItem){
                activeTextColor
            }else{
                inactiveTextColor
            }

                Text(
                    text = stringResource(id = item.toTitleResource()),
                    color = animatedTextColor,
                    fontSize = 16.sp,
                    fontFamily = vietnamFamily,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(7.dp, 7.dp)
                        .background(
                            animatedColor,
                            RoundedCornerShape(20.dp)
                            )
                        .weight(1f)
                        .width(IntrinsicSize.Min)
                        .padding(10.dp, 5.dp)
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun searchSliderPreview() {
    AppTheme {
        SearchButtonsRow(
            items = listOf(
                SearchItemEnum.SERVICES,
                SearchItemEnum.PRODUCTS
            ), selectedItem = SearchItemEnum.PRODUCTS
        ){}
    }
}
