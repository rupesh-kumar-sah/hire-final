package com.hirenepal.app.ui.screens

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hirenepal.app.R
import com.hirenepal.app.model.AppScreen
import com.hirenepal.app.model.BookingItem
import com.hirenepal.app.model.ChatMessage
import com.hirenepal.app.model.PublicUser
import com.hirenepal.app.model.WorkerInfo
import com.hirenepal.app.ui.theme.HireCardBorder
import com.hirenepal.app.ui.theme.HireGreen
import com.hirenepal.app.ui.theme.HireGreenDark
import com.hirenepal.app.ui.theme.HireInk
import com.hirenepal.app.ui.theme.HireMint
import com.hirenepal.app.ui.theme.HireMuted
import com.hirenepal.app.ui.theme.HireOrange
import com.hirenepal.app.ui.theme.HireSurface

@Composable
fun BookingScreen(
    worker: WorkerInfo,
    onBack: () -> Unit,
    onSubmitBooking: (
        details: String,
        rooms: Int,
        dateLabel: String,
        timeSlot: String,
        address: String,
        photoUris: List<String>
    ) -> Unit
) {
    BackHandler(onBack = onBack)

    var details by remember { mutableStateOf("") }
    var rooms by remember { mutableIntStateOf(2) }
    var selectedDate by remember { mutableStateOf("Tomorrow") }
    var selectedSlot by remember { mutableStateOf("9:00 AM") }
    var address by remember { mutableStateOf("Bansbari, Kathmandu 44600") }
    var selectedPhotos by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(3)
    ) { uris ->
        if (uris.isNotEmpty()) {
            selectedPhotos = uris
        }
    }

    val totalEstimate = rooms * worker.dailyRate

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Surface(
                color = Color.White,
                tonalElevation = 8.dp,
                border = BorderStroke(1.dp, HireCardBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✦ ESTIMATED TOTAL",
                            style = MaterialTheme.typography.labelSmall,
                            color = HireGreen
                        )
                        Text(
                            text = "NPR %,d".format(totalEstimate),
                            style = MaterialTheme.typography.titleLarge,
                            color = HireInk
                        )
                    }
                    Button(
                        onClick = {
                            onSubmitBooking(
                                details,
                                rooms,
                                selectedDate,
                                selectedSlot,
                                address,
                                selectedPhotos.map { it.toString() }
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("booking_submit_button")
                    ) {
                        Text("Review booking", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, HireCardBorder, CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "STEP 1 OF 2",
                        style = MaterialTheme.typography.labelSmall,
                        color = HireMuted
                    )
                    Text(
                        text = "Book your service",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Spacer(modifier = Modifier.width(48.dp))
            }

            // Job details
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "What needs ${worker.serviceCategory.lowercase()}?",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Tell ${worker.name.substringBefore(" ")} a little about the job.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = HireMuted
                )
                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    placeholder = { Text("e.g. Two bedrooms and one hallway...") },
                    minLines = 3,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("booking_details_input")
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Add photos (${selectedPhotos.size})", fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "Helps get a better estimate",
                        style = MaterialTheme.typography.bodyMedium,
                        color = HireMuted,
                        fontSize = 12.sp
                    )
                }
            }

            // Room Stepper
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = HireSurface,
                border = BorderStroke(1.dp, HireCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("How many rooms / areas?", fontWeight = FontWeight.Bold)
                        Text(
                            "Calculates starting estimate",
                            fontSize = 12.sp,
                            color = HireMuted
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = { if (rooms > 1) rooms-- },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(1.dp, HireCardBorder, CircleShape)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease rooms")
                        }
                        Text(
                            text = "$rooms ${if (rooms == 1) "room" else "rooms"}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp
                        )
                        IconButton(
                            onClick = { if (rooms < 8) rooms++ },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(HireGreen)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase rooms",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            // Date selector
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Choose a day", style = MaterialTheme.typography.titleMedium)
                Text(
                    "${worker.name.substringBefore(" ")} is available on these dates.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = HireMuted
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf(
                        "FRIDAY" to "Tomorrow",
                        "SATURDAY" to "Sat, 16",
                        "SUNDAY" to "Sun, 17"
                    ).forEach { (dayLabel, dateText) ->
                        val active = selectedDate == dateText
                        Surface(
                            onClick = { selectedDate = dateText },
                            shape = RoundedCornerShape(16.dp),
                            color = if (active) HireMint.copy(alpha = 0.55f) else Color.White,
                            border = BorderStroke(
                                width = if (active) 2.dp else 1.dp,
                                color = if (active) HireGreen else HireCardBorder
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = dayLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = HireMuted
                                )
                                Text(
                                    text = dateText,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = HireInk
                                )
                            }
                        }
                    }
                }
            }

            // Time slots
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Preferred time", style = MaterialTheme.typography.titleMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("9:00 AM", "11:30 AM", "2:00 PM", "4:30 PM").forEach { slot ->
                        val active = selectedSlot == slot
                        Surface(
                            onClick = { selectedSlot = slot },
                            shape = RoundedCornerShape(12.dp),
                            color = if (active) HireGreen else HireSurface,
                            border = BorderStroke(
                                1.dp,
                                if (active) HireGreen else HireCardBorder
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(vertical = 12.dp)
                            ) {
                                Text(
                                    text = slot,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (active) Color.White else HireInk
                                )
                            }
                        }
                    }
                }
            }

            // Service address
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Service address", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    leadingIcon = {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = HireGreen)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmedScreen(
    booking: BookingItem?,
    onBackHome: () -> Unit,
    onReschedule: () -> Unit,
    onCancelBooking: () -> Unit,
    onOpenChat: () -> Unit,
    onFindAnotherPro: () -> Unit,
    onNavigateBottom: (AppScreen) -> Unit
) {
    BackHandler(onBack = onBackHome)

    var showCancelModal by remember { mutableStateOf(false) }
    val isCancelled = booking?.status == "Cancelled"

    Scaffold(
        bottomBar = {
            HireBottomNav(activeScreen = AppScreen.Confirmed, onNavigate = onNavigateBottom)
        },
        containerColor = HireSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isCancelled) {
                Spacer(modifier = Modifier.height(32.dp))
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFFEE2E2),
                    modifier = Modifier.size(72.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cancelled",
                            tint = Color(0xFFB91C1C),
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                Text(
                    text = "BOOKING CANCELLED",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFB91C1C)
                )
                Text(
                    text = "Your booking was cancelled.",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "No payment was taken. You can find another professional at any time.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = HireMuted,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = onFindAnotherPro,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("Find another professional", fontWeight = FontWeight.Bold)
                }
                TextButton(onClick = onBackHome) {
                    Text("Back to home", color = HireMuted, fontWeight = FontWeight.Bold)
                }
            } else {
                Surface(
                    shape = CircleShape,
                    color = HireMint,
                    modifier = Modifier.size(74.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Surface(
                            shape = CircleShape,
                            color = HireGreen,
                            modifier = Modifier.size(52.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Confirmed",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "BOOKING CONFIRMED",
                    style = MaterialTheme.typography.labelSmall,
                    color = HireGreen
                )
                Text(
                    text = "You’re all set.",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Your service is confirmed for ${booking?.dateLabel ?: "Friday"} at ${booking?.timeSlot ?: "9:00 AM"}.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = HireMuted,
                    textAlign = TextAlign.Center
                )

                // Ticket Card
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, HireCardBorder),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(
                                    id = booking?.workerImageRes ?: R.drawable.painter
                                ),
                                contentDescription = booking?.workerName ?: "Ramesh Thapa",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(14.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "YOUR PROFESSIONAL",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = HireMuted
                                )
                                Text(
                                    text = "${booking?.workerName ?: "Ramesh Thapa"} ✓",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = booking?.workerRole ?: "House painting specialist",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = HireMuted,
                                    fontSize = 12.sp
                                )
                            }
                            IconButton(
                                onClick = onOpenChat,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(HireMint.copy(alpha = 0.5f))
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Chat,
                                    contentDescription = "Chat",
                                    tint = HireGreen
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("DATE", style = MaterialTheme.typography.labelSmall, color = HireMuted)
                                Text(booking?.dateLabel ?: "Friday, 15 Nov", fontWeight = FontWeight.Bold)
                            }
                            Column {
                                Text("TIME", style = MaterialTheme.typography.labelSmall, color = HireMuted)
                                Text(booking?.timeSlot ?: "9:00 AM", fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("ESTIMATE", style = MaterialTheme.typography.labelSmall, color = HireMuted)
                                Text(
                                    "NPR %,d".format(booking?.totalPrice ?: 3000),
                                    fontWeight = FontWeight.ExtraBold,
                                    color = HireGreen
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = HireGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = booking?.address ?: "Bansbari, Kathmandu",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = HireSurface,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Booking ID", fontSize = 12.sp, color = HireMuted)
                                Text(
                                    text = booking?.reference ?: "#HN-2048",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = HireInk
                                )
                            }
                        }
                    }
                }

                // Timeline
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, HireCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        listOf(
                            Triple("Request sent", "Mon, 10 Nov · 9:00 AM", true),
                            Triple("Quotation received", "Mon, 10 Nov · 9:15 AM", true),
                            Triple("Confirmed", "Mon, 10 Nov · 9:30 AM", true),
                            Triple("In progress", "Your professional is on the way", false),
                            Triple("Completed", "Payment released after approval", false)
                        ).forEachIndexed { index, (title, subtitle, done) ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (done) HireGreen else HireSurface,
                                    border = BorderStroke(
                                        1.dp,
                                        if (done) HireGreen else HireCardBorder
                                    ),
                                    modifier = Modifier.size(26.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        if (done) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        } else {
                                            Text(
                                                text = "${index + 1}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = HireMuted
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(subtitle, fontSize = 12.sp, color = HireMuted)
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = onBackHome,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("confirmed_back_home_button")
                ) {
                    Text("Back to home", fontWeight = FontWeight.Bold)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = onReschedule) {
                        Text("Reschedule booking", fontWeight = FontWeight.Bold, color = HireGreen)
                    }
                    TextButton(onClick = { showCancelModal = true }) {
                        Text(
                            "Cancel booking",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB91C1C)
                        )
                    }
                }
            }
        }
    }

    if (showCancelModal) {
        ModalBottomSheet(
            onDismissRequest = { showCancelModal = false },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Cancel this booking?", style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "${booking?.workerName ?: "Ramesh"} will be notified and this time slot will become available.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = HireMuted,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        showCancelModal = false
                        onCancelBooking()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB91C1C)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Yes, cancel booking", fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = { showCancelModal = false },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Keep my booking", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun ChatScreen(
    worker: WorkerInfo,
    messages: List<ChatMessage>,
    isTyping: Boolean,
    onBack: () -> Unit,
    onSendMessage: (String) -> Unit,
    onCallNotice: () -> Unit,
    onNavigateBottom: (AppScreen) -> Unit
) {
    BackHandler(onBack = onBack)

    var draft by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            Column {
                Surface(
                    color = Color.White,
                    border = BorderStroke(1.dp, HireCardBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = draft,
                            onValueChange = { draft = it },
                            placeholder = { Text("Type a message...") },
                            singleLine = true,
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("chat_message_input")
                        )
                        IconButton(
                            onClick = {
                                if (draft.isNotBlank()) {
                                    onSendMessage(draft)
                                    draft = ""
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(HireGreen)
                                .testTag("chat_send_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send message",
                                tint = Color.White
                            )
                        }
                    }
                }
                HireBottomNav(activeScreen = AppScreen.Chat, onNavigate = onNavigateBottom)
            }
        },
        containerColor = HireSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HireGreenDark)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = worker.imageRes),
                    contentDescription = worker.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = worker.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "● Online · ${worker.role}",
                        color = HireMint,
                        fontSize = 12.sp
                    )
                }
                IconButton(onClick = onCallNotice) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call ${worker.name}",
                        tint = Color.White
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color.White,
                            border = BorderStroke(1.dp, HireCardBorder)
                        ) {
                            Text(
                                text = "TODAY",
                                style = MaterialTheme.typography.labelSmall,
                                color = HireMuted,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                items(messages, key = { it.id }) { msg ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (msg.mine) Arrangement.End else Arrangement.Start
                    ) {
                        Surface(
                            shape = RoundedCornerShape(
                                topStart = 18.dp,
                                topEnd = 18.dp,
                                bottomStart = if (msg.mine) 18.dp else 4.dp,
                                bottomEnd = if (msg.mine) 4.dp else 18.dp
                            ),
                            color = if (msg.mine) HireGreen else Color.White,
                            border = if (msg.mine) null else BorderStroke(1.dp, HireCardBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = msg.text,
                                    color = if (msg.mine) Color.White else HireInk,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = if (msg.mine) "${msg.time} ✓✓" else msg.time,
                                    fontSize = 10.sp,
                                    color = if (msg.mine) Color.White.copy(alpha = 0.75f) else HireMuted,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                }

                if (isTyping) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, HireCardBorder)
                        ) {
                            Text(
                                text = "${worker.name.substringBefore(" ")} is typing…",
                                fontSize = 12.sp,
                                color = HireMuted,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                item {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, HireCardBorder),
                        modifier = Modifier.width(220.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.house),
                                contentDescription = "Example house for painting",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Text(
                                text = "Site reference photo · 9:25 AM",
                                fontSize = 11.sp,
                                color = HireMuted,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AccountScreen(
    user: PublicUser?,
    walletBalance: Int,
    savedCount: Int,
    bookingsCount: Int,
    onTopUp: () -> Unit,
    onOpenBookings: () -> Unit,
    onOpenFavourites: () -> Unit,
    onLogout: () -> Unit,
    onNavigateBottom: (AppScreen) -> Unit
) {
    BackHandler {
        onNavigateBottom(AppScreen.Home)
    }

    Scaffold(
        bottomBar = {
            HireBottomNav(activeScreen = AppScreen.Account, onNavigate = onNavigateBottom)
        },
        containerColor = HireSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(colors = listOf(HireGreenDark, HireGreen))
                    )
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.painter),
                        contentDescription = user?.name ?: "Rupesh Sah",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(84.dp)
                            .clip(CircleShape)
                            .border(3.dp, Color.White, CircleShape)
                    )
                    Text(
                        text = user?.name ?: "Rupesh Sah",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                    Text(
                        text = user?.email ?: "rupesh@gmail.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color.White.copy(alpha = 0.16f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = null,
                                tint = HireMint,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Verified member",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Wallet Card
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, HireCardBorder),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "HIRÉ WALLET",
                                style = MaterialTheme.typography.labelSmall,
                                color = HireGreen
                            )
                            Text(
                                text = "NPR %,d".format(walletBalance),
                                style = MaterialTheme.typography.headlineMedium,
                                color = HireInk
                            )
                        }
                        Button(
                            onClick = onTopUp,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                            modifier = Modifier.testTag("wallet_topup_button")
                        ) {
                            Text("Top up +1,000", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                AccountMenuRow(
                    icon = Icons.Default.CalendarMonth,
                    label = "My bookings",
                    detail = "$bookingsCount active",
                    onClick = onOpenBookings
                )
                AccountMenuRow(
                    icon = Icons.Default.Favorite,
                    label = "Favourite professionals",
                    detail = "$savedCount saved",
                    onClick = onOpenFavourites
                )
                AccountMenuRow(
                    icon = Icons.Default.Star,
                    label = "My reviews",
                    detail = "4 reviews",
                    onClick = onOpenFavourites
                )
                AccountMenuRow(
                    icon = Icons.Default.Notifications,
                    label = "Notifications",
                    detail = "Enabled",
                    onClick = {}
                )
                AccountMenuRow(
                    icon = Icons.Default.VerifiedUser,
                    label = "Help & support",
                    detail = "24/7",
                    onClick = {}
                )

                Surface(
                    onClick = onLogout,
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFFF1F2),
                    border = BorderStroke(1.dp, Color(0xFFFECDD3)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("account_logout_button")
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = null,
                            tint = Color(0xFFE11D48)
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Text(
                            text = "Sign out",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE11D48),
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color(0xFFE11D48)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AccountMenuRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    detail: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, HireCardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = HireMint.copy(alpha = 0.5f),
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = HireGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = HireInk,
                modifier = Modifier.weight(1f)
            )
            if (detail.isNotEmpty()) {
                Text(
                    text = detail,
                    fontSize = 12.sp,
                    color = HireMuted,
                    modifier = Modifier.padding(end = 6.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = HireMuted
            )
        }
    }
}
