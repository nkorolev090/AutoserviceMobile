package com.project.autoservicemobile.ui.services.views

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.project.autoservicemobile.ui.AppTheme
import com.project.autoservicemobile.ui.profile.models.NavigationItemEnum
import com.project.autoservicemobile.ui.services.models.ProductUI
import com.project.autoservicemobile.ui.services.models.toTitleResource
import com.project.autoservicemobile.ui.vietnamFamily

class ProductsList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {

    val productsList: MutableState<List<ProductUI>> =
        mutableStateOf(
            listOf()
        )

    @Composable
    override fun Content() {
        AppTheme {
            ProductsRows(products = productsList.value)
        }
    }
}

@Composable
private fun ProductsRows(products: List<ProductUI>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        for (i in products.indices step 2) {
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                ) {
                    for (j in 0..1) {
                        if (i + j in products.indices)
                            ProductCard(product = products[i + j])
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductCard(product: ProductUI) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(20.dp)
            )
            .size(190.dp, 300.dp)
            .padding(10.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(product.imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Text(
                text = product.priceText,
                color = MaterialTheme.colorScheme.surface,
                fontSize = 16.sp,
                fontFamily = vietnamFamily,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(top = 5.dp)
            )

            Text(
                text = product.brandText,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 10.sp,
                fontFamily = vietnamFamily,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(top = 5.dp, start = 5.dp)
            )
        }

        Text(
            text = product.titleText,
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 16.sp,
            fontFamily = vietnamFamily,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .padding(top = 5.dp)
        )

        Text(
            text = product.articleText,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 14.sp,
            fontFamily = vietnamFamily,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .padding(top = 5.dp)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun prevProductCard() {
    AppTheme {
        ProductCard(
            product =
            ProductUI(
                "арт. 099899",
                "LADA 2109",
                "https://cdn1.ozone.ru/s3/multimedia-0/6688034280.jpg",
                "20000,90 P",
                "Карбюратор для LADA 2109",
                "LuzarAutooooo",
                "https://www.drive2.ru/l/620359304871946625/?ysclid=m3ct4wjzx4256648975"
            )
        )
    }
}
