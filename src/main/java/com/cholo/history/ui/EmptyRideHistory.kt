package com.cholo.history.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chalo.core.theme.colorDarkText
import com.chalo.core.theme.colorMediumGray
import com.chalo.core.theme.colorPrimary
import com.chalo.core.theme.headingTextStyle
import com.chalo.core.theme.titleTextStyle


@Composable
fun EmptyRideHistory(modifier: Modifier = Modifier, onBack: () -> Unit,) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(com.chalo.core.R.drawable.ic_no_ride_car),
            contentDescription = null,
            modifier = Modifier
                .size(240.dp)
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No Rides Yet",
            style = headingTextStyle,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Once you start riding with EVO, your eco-friendly trip history will appear here.",
            style = titleTextStyle,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = colorMediumGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onBack() },
            colors = ButtonDefaults.buttonColors(containerColor = colorPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text("Book Your First Ride", color = colorDarkText, style = titleTextStyle, fontWeight = FontWeight.Normal, fontSize = 14.sp)
        }
    }
}

@Composable
@Preview
private fun PreviewEmptyRideHistory () {
    EmptyRideHistory(onBack = {})

}
