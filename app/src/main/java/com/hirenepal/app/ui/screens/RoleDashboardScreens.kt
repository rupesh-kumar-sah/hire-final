package com.hirenepal.app.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hirenepal.app.R
import com.hirenepal.app.model.BookingItem
import com.hirenepal.app.model.PublicUser
import com.hirenepal.app.model.UserRole
import com.hirenepal.app.ui.theme.HireCardBorder
import com.hirenepal.app.ui.theme.HireGreen
import com.hirenepal.app.ui.theme.HireGreenDark
import com.hirenepal.app.ui.theme.HireInk
import com.hirenepal.app.ui.theme.HireMint
import com.hirenepal.app.ui.theme.HireMuted
import com.hirenepal.app.ui.theme.HireOrange
import com.hirenepal.app.ui.theme.HireSurface

@Composable
fun WorkerDashboardScreen(
    user: PublicUser,
    available: Boolean,
    bookings: List<BookingItem>,
    onToggleAvailable: () -> Unit,
    onOpenChat: () -> Unit,
    onSwitchToCustomerHome: () -> Unit,
    onLogout: () -> Unit
) {
    BackHandler(onBack = onSwitchToCustomerHome)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HireSurface)
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(colors = listOf(HireGreenDark, HireGreen))
                )
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "WORKER SPACE",
                            style = MaterialTheme.typography.labelSmall,
                            color = HireMint
                        )
                        Text(
                            text = "Hello, ${user.name.substringBefore(" ")}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                    }
                    IconButton(
                        onClick = onLogout,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.16f))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Sign out",
                            tint = Color.White
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (available) "● Available for work" else "○ Currently unavailable",
                                fontWeight = FontWeight.Bold,
                                color = if (available) HireGreen else HireMuted
                            )
                            Text(
                                text = if (available) {
                                    "You can receive nearby job requests"
                                } else {
                                    "New requests are paused"
                                },
                                fontSize = 12.sp,
                                color = HireMuted
                            )
                        }
                        Switch(
                            checked = available,
                            onCheckedChange = { onToggleAvailable() },
                            colors = SwitchDefaults.colors(checkedTrackColor = HireGreen)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, HireCardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "08",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF2563EB)
                        )
                        Text("New requests", fontWeight = FontWeight.Bold)
                        Text("3 need a reply", fontSize = 12.sp, color = HireMuted)
                    }
                }
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, HireCardBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "NPR 38K",
                            style = MaterialTheme.typography.headlineMedium,
                            color = HireGreen
                        )
                        Text("This month", fontWeight = FontWeight.Bold)
                        Text("+18% from last month", fontSize = 12.sp, color = HireMuted)
                    }
                }
            }

            Text("Upcoming jobs", style = MaterialTheme.typography.titleLarge)

            bookings.take(2).forEach { job ->
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, HireCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.house),
                            contentDescription = job.service,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(68.dp)
                                .clip(RoundedCornerShape(14.dp))
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = HireMint
                            ) {
                                Text(
                                    text = job.status.uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = HireGreenDark,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${job.service} · ${job.customerName}",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "📍 ${job.address} · ${job.timeSlot}",
                                fontSize = 12.sp,
                                color = HireMuted
                            )
                        }
                    }
                }
            }

            // Weekly Earnings Bar Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = HireGreenDark,
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
                            text = "WEEKLY EARNINGS",
                            style = MaterialTheme.typography.labelSmall,
                            color = HireMint
                        )
                        Text(
                            text = "NPR 12,450",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                        Text(
                            text = "↑ 14.2% from last week",
                            fontSize = 12.sp,
                            color = HireMint
                        )
                    }
                    Row(
                        modifier = Modifier.height(64.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        listOf(0.35f, 0.55f, 0.43f, 0.75f, 0.58f, 0.91f, 0.68f).forEach { ratio ->
                            Box(
                                modifier = Modifier
                                    .width(8.dp)
                                    .fillMaxHeight(ratio)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(HireOrange)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onOpenChat,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Client Messages", fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = onSwitchToCustomerHome,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HireOrange),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Home, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Explore App", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AdminDashboardScreen(
    user: PublicUser,
    users: List<PublicUser>,
    bookings: List<BookingItem>,
    onVerifyWorker: (String) -> Unit,
    onLogout: () -> Unit
) {
    var activeTab by remember { mutableStateOf("home") }
    var searchQuery by remember { mutableStateOf("") }

    BackHandler {
        if (activeTab != "home") activeTab = "home" else onLogout()
    }

    val workerUsers = remember(users, searchQuery) {
        users.filter {
            it.role == UserRole.Worker &&
                (searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true))
        }
    }

    val totalRevenue = remember(bookings) {
        bookings.sumOf { it.totalPrice }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                listOf(
                    Triple("home", "Home", Icons.Default.Home),
                    Triple("workers", "Workers", Icons.Default.VerifiedUser),
                    Triple("bookings", "Bookings", Icons.Default.CalendarMonth),
                    Triple("reports", "Reports", Icons.Default.BarChart),
                    Triple("settings", "More", Icons.Default.Settings)
                ).forEach { (tabKey, label, icon) ->
                    NavigationBarItem(
                        selected = activeTab == tabKey,
                        onClick = { activeTab = tabKey },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = HireGreen,
                            selectedTextColor = HireGreen,
                            indicatorColor = HireMint
                        )
                    )
                }
            }
        },
        containerColor = HireSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            // Topbar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HireGreenDark)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Home, contentDescription = null, tint = HireOrange)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "HiréNepal Admin",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = HireGreen
                    ) {
                        Text(
                            text = "♛ Super Admin",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Sign out",
                            tint = Color.White
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (activeTab) {
                    "home" -> {
                        Text(
                            text = "Welcome back, ${user.name.substringBefore(" ")}.",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AdminStatTile(
                                value = "${users.size}",
                                label = "Total users",
                                bg = Color(0xFFE0F2FE),
                                modifier = Modifier.weight(1f)
                            )
                            AdminStatTile(
                                value = "${users.count { it.role == UserRole.Worker }}",
                                label = "Workers",
                                bg = HireMint,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AdminStatTile(
                                value = "${bookings.size}",
                                label = "Bookings",
                                bg = Color(0xFFFEF3C7),
                                modifier = Modifier.weight(1f)
                            )
                            AdminStatTile(
                                value = "NPR %,d".format(totalRevenue),
                                label = "Revenue",
                                bg = Color(0xFFEDE9FE),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, HireCardBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text("Quick actions", style = MaterialTheme.typography.titleMedium)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Button(
                                        onClick = { activeTab = "workers" },
                                        colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Verify workers", fontWeight = FontWeight.Bold)
                                    }
                                    Button(
                                        onClick = { activeTab = "bookings" },
                                        colors = ButtonDefaults.buttonColors(containerColor = HireOrange),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("All bookings", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    "workers" -> {
                        Text("Workers management", style = MaterialTheme.typography.headlineMedium)
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search worker...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                        workerUsers.forEach { workerUser ->
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = Color.White,
                                border = BorderStroke(1.dp, HireCardBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${workerUser.name} ${if (workerUser.verified) "✓" else ""}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "${workerUser.service} · ${workerUser.city}",
                                            fontSize = 13.sp,
                                            color = HireMuted
                                        )
                                    }
                                    if (workerUser.verified) {
                                        Surface(
                                            shape = RoundedCornerShape(50),
                                            color = HireMint
                                        ) {
                                            Text(
                                                text = "Verified",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = HireGreenDark,
                                                modifier = Modifier.padding(
                                                    horizontal = 12.dp,
                                                    vertical = 6.dp
                                                )
                                            )
                                        }
                                    } else {
                                        Button(
                                            onClick = { onVerifyWorker(workerUser.id) },
                                            colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Approve", fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    "bookings" -> {
                        Text("Bookings management", style = MaterialTheme.typography.headlineMedium)
                        bookings.forEach { b ->
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = Color.White,
                                border = BorderStroke(1.dp, HireCardBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(b.reference, fontWeight = FontWeight.ExtraBold)
                                        Text("${b.service} · ${b.workerName}", color = HireMuted)
                                        Text(
                                            "NPR %,d".format(b.totalPrice),
                                            fontWeight = FontWeight.Bold,
                                            color = HireGreen
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(50),
                                        color = HireMint
                                    ) {
                                        Text(
                                            text = b.status,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = HireGreenDark,
                                            modifier = Modifier.padding(
                                                horizontal = 12.dp,
                                                vertical = 6.dp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    "reports" -> {
                        Text("Reports & analytics", style = MaterialTheme.typography.headlineMedium)
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = HireGreenDark,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "TOTAL PLATFORM VOLUME",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = HireMint
                                )
                                Text(
                                    text = "NPR 4,850,000",
                                    style = MaterialTheme.typography.displaySmall,
                                    color = Color.White
                                )
                                Text(
                                    text = "↑ 14.2% vs previous 30 days",
                                    color = HireMint,
                                    fontSize = 13.sp
                                )
                            }
                        }
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, HireCardBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text("Top locations", style = MaterialTheme.typography.titleMedium)
                                listOf(
                                    "Kathmandu" to 0.35f,
                                    "Lalitpur" to 0.28f,
                                    "Bhaktapur" to 0.21f,
                                    "Kirtipur" to 0.16f
                                ).forEach { (city, share) ->
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(city, fontWeight = FontWeight.SemiBold)
                                            Text("${(share * 100).toInt()}%", fontWeight = FontWeight.Bold)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(8.dp)
                                                .clip(CircleShape)
                                                .background(HireSurface)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(share)
                                                    .height(8.dp)
                                                    .clip(CircleShape)
                                                    .background(HireGreen)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    else -> {
                        Text("Settings & security", style = MaterialTheme.typography.headlineMedium)
                        listOf(
                            "General settings",
                            "Payment settings (NPR)",
                            "Notification settings",
                            "Two-factor authentication (Active)",
                            "Audit logs",
                            "Database backup"
                        ).forEach { setting ->
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = Color.White,
                                border = BorderStroke(1.dp, HireCardBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(setting, fontWeight = FontWeight.Bold)
                                    Icon(
                                        imageVector = Icons.Default.VerifiedUser,
                                        contentDescription = null,
                                        tint = HireGreen
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminStatTile(
    value: String,
    label: String,
    bg: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = bg,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = HireInk
            )
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = HireMuted
            )
        }
    }
}
