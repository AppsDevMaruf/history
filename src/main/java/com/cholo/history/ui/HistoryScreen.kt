package com.cholo.history.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chalo.core.R
import com.chalo.core.components.DividerSection
import com.chalo.core.components.LoadImage
import com.chalo.core.theme.colorDarkGray
import com.chalo.core.theme.colorDarkText
import com.chalo.core.theme.colorLightGray
import com.chalo.core.theme.colorLightText
import com.chalo.core.theme.colorMediumGray
import com.chalo.core.theme.colorPrimary
import com.chalo.core.theme.titleTextStyle
import com.chalo.core.utils.components.noRippleClickable
import com.chalo.core.utils.formatTime

import com.chalo.ticket.data.TicketBottomSheet
import com.chalo.ticket.data.TicketCategory
import com.chalo.ticket.data.TicketViewModel
import com.cholo.history.domain.model.RideLastLocationRequest
import org.koin.androidx.compose.koinViewModel


@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = koinViewModel(),
    onBack: () -> Unit = {},
    onPrev: () -> Unit = {},
    ticketViewModel: TicketViewModel,
    goNextClick: (TicketCategory) -> Unit,
) {
    val uiState by historyViewModel.uiState.collectAsState()

    val rideList = uiState.rideHistory.orEmpty()
    val isLoading = uiState.isLoading
    val isError = uiState.errorMessage?.isNotEmpty() == true

    LaunchedEffect(Unit) {
        historyViewModel.rideHistory(RideLastLocationRequest())
    }


    Scaffold(
        topBar = { RideHistoryTopBar(onBack) }, containerColor = Color.White

    ) { padding ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = colorPrimary)
                }
            }

            isError -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),

                    ) {
                    Text("${uiState.errorMessage}", color = com.chalo.core.theme.error)
                    Spacer(Modifier.height(4.dp))
                    Button(
                        onClick = { historyViewModel.rideHistory(RideLastLocationRequest()) },
                        colors = ButtonDefaults.buttonColors(containerColor = colorPrimary),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text(
                            "Try Again",
                            color = colorDarkText,
                            style = titleTextStyle,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            rideList.isEmpty() -> {
                EmptyRideHistory(modifier = Modifier.padding(padding), onBack)
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(Color.White),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    items(rideList) { ride ->
                        RideCard(
                            name = ride.userName ?: "",
                            imageUrl = ride.userPhoto ?: "",
                            time = formatTime(ride.createdAt),
                            fare = ride.actualFare,
                            from = ride.pickupAddress,
                            to = ride.destinationAddress,
                            status = ride.status,
                            rating = ride.userRating,
                            rideId = ride.rideId,
                            goNextClick = goNextClick,
                            ticketViewModel = ticketViewModel,
                            onBack = onPrev
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun RideCard(
    name: String,
    imageUrl: String,
    time: String,
    fare: String,
    from: String,
    to: String,
    rating: String?,
    status: String?,
    rideId: String?,
    onBack: () -> Unit,
    goNextClick: (TicketCategory) -> Unit,
    ticketViewModel: TicketViewModel,
) {
    val ticketUiState by ticketViewModel.uiState.collectAsState()
    var showRatingSheet by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    // Handle submit success - navigate back
    LaunchedEffect(ticketUiState.submitSuccess) {
        if (ticketUiState.submitSuccess) {
            onBack()
            showBottomSheet = false
            ticketViewModel.clearSuccess()

        }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, color = colorLightGray, shape = RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {
        Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 8.dp)) {

            if (status == "Cancelled") {
                StatusButton(time = time, fare = fare)
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LoadImage(
                        imageUrl = imageUrl,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = name,
                            style = titleTextStyle,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = colorMediumGray,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = time,
                                style = titleTextStyle,
                                fontSize = 11.sp,
                                fontWeight = FontWeight(400),
                                color = colorMediumGray
                            )
                        }
                    }
                    Text(text = fare, style = titleTextStyle, fontSize = 18.sp)
                }
            }


            DividerSection(modifier = Modifier.padding(0.dp))
            RideLocationRow(label = from, color = colorPrimary)
            RideLocationRow(label = to, color = com.chalo.core.theme.error)

            if (status != "Cancelled") {
                DividerSection(modifier = Modifier.padding(0.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .noRippleClickable {
                                showRatingSheet = false // force reset first
                                showRatingSheet = true  // then show
                            }
                            .clip(RoundedCornerShape(4.dp))
                            .background(colorLightGray)
                            .height(24.dp)
                            .padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Spacer(modifier = Modifier.width(4.dp))
                        if (rating != null) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFF26AF22)
                            )
                            Text(
                                text = rating,
                                color = colorLightText,
                                style = titleTextStyle,
                                fontSize = 12.sp,
                            )
                        } else {
                            Text(
                                text = "No Feedback Given",
                                style = titleTextStyle,
                                fontSize = 12.sp,
                            )
                        }

                    }

                    TextButton(onClick = { }) {
                        Icon(
                            Icons.Default.Star, contentDescription = null, tint = Color(0xFF26AF22)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        if (rating == null) {
                            Text("Give Feedback", style = titleTextStyle, fontSize = 12.sp)
                        } else {
                            Text("Change Feedback", style = titleTextStyle, fontSize = 12.sp)
                        }

                    }
                }
                DividerSection(modifier = Modifier.padding(vertical = 0.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_report),
                            contentDescription = null,
                            tint = colorLightText
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("View Receipt", style = titleTextStyle, fontSize = 12.sp)
                    }

                    TextButton(onClick = {
                        showBottomSheet = true
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_info_circle),
                            contentDescription = null,
                            tint = colorLightText
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Report", style = titleTextStyle, fontSize = 12.sp)
                    }
                }
            }

            if (showRatingSheet) {
                //ShowRatingSheet()
            }


            if (showBottomSheet) {
                rideId?.let {
                    ticketViewModel.updateRideId(rideId)
                    TicketBottomSheet(
                        onDismiss = {
                            onBack()
                            showBottomSheet = false
                        },
                        goNextClick = { category ->
                            // Close bottom sheet
                            showBottomSheet = false
                            goNextClick(category)

                        },
                        ticketViewModel = ticketViewModel
                    )
                }

            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideHistoryTopBar(onBack: () -> Unit) {
    TopAppBar(
        title = { Text("Ride History", style = titleTextStyle, fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun RideLocationRow(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = titleTextStyle, fontSize = 12.sp, fontWeight = FontWeight.Normal)
    }
}

@Composable
private fun StatusButton(
    modifier: Modifier = Modifier, time: String, fare: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween, // This ensures proper spacing
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start // Changed from CenterHorizontally to Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp)) // Increased for pill shape
                    .background(colorLightGray)
                    .height(24.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Cancelled", // Fixed spelling
                    color = colorDarkGray,
                    style = titleTextStyle,
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = colorMediumGray,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = time,
                    style = titleTextStyle,
                    fontSize = 11.sp,
                    fontWeight = FontWeight(400),
                    color = colorMediumGray
                )
            }
        }

        // Fixed fare text alignment
        Text(
            text = fare, style = titleTextStyle, fontSize = 18.sp, textAlign = TextAlign.End
        )
    }
}


/*
@Preview(showBackground = true)
@Composable
fun PreviewHistory() {
    HistoryScreen()
}
*/
