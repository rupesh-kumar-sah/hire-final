package com.hirenepal.app.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hirenepal.app.R
import com.hirenepal.app.model.AppScreen
import com.hirenepal.app.model.NotificationItem
import com.hirenepal.app.model.ServiceCategory
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
fun HireBottomNav(
    activeScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val navItems = listOf(
            Triple("Home", Icons.Default.Home, AppScreen.Home),
            Triple("Bookings", Icons.Default.CalendarMonth, AppScreen.Confirmed),
            Triple("Messages", Icons.AutoMirrored.Filled.Chat, AppScreen.Chat),
            Triple("Profile", Icons.Default.Person, AppScreen.Account)
        )
        navItems.forEach { (label, icon, screen) ->
            val selected = activeScreen == screen
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(screen) },
                icon = {
                    if (label == "Messages") {
                        BadgedBox(
                            badge = {
                                Badge(containerColor = HireOrange, contentColor = Color.White) {
                                    Text("2")
                                }
                            }
                        ) {
                            Icon(icon, contentDescription = label)
                        }
                    } else {
                        Icon(icon, contentDescription = label)
                    }
                },
                label = {
                    Text(
                        text = label,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = HireGreen,
                    selectedTextColor = HireGreen,
                    indicatorColor = HireMint,
                    unselectedIconColor = HireMuted,
                    unselectedTextColor = HireMuted
                ),
                modifier = Modifier.testTag("bottom_nav_${label.lowercase()}")
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    selectedCity: String,
    cities: List<String>,
    searchQuery: String,
    categories: List<ServiceCategory>,
    featuredWorker: WorkerInfo,
    notifications: List<NotificationItem>,
    onSearchChange: (String) -> Unit,
    onCitySelect: (String) -> Unit,
    onSelectCategory: (String) -> Unit,
    onSeeAllWorkers: () -> Unit,
    onOpenFeaturedWorker: () -> Unit,
    onNavigateBottom: (AppScreen) -> Unit
) {
    var showAssuranceNote by remember { mutableStateOf(false) }
    var activeSheet by remember { mutableStateOf<String?>(null) }

    val visibleCategories = remember(searchQuery, categories) {
        if (searchQuery.isBlank()) categories
        else categories.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        bottomBar = {
            HireBottomNav(activeScreen = AppScreen.Home, onNavigate = onNavigateBottom)
        },
        containerColor = HireSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(HireGreenDark, HireGreen)
                        )
                    )
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            onClick = { activeSheet = "location" },
                            shape = RoundedCornerShape(50),
                            color = Color.White.copy(alpha = 0.14f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f)),
                            modifier = Modifier.testTag("home_location_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Location",
                                    tint = HireOrange,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(
                                        text = "Your location",
                                        fontSize = 10.sp,
                                        color = Color.White.copy(alpha = 0.75f)
                                    )
                                    Text(
                                        text = selectedCity,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }

                        IconButton(
                            onClick = { activeSheet = "notifications" },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.14f))
                                .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                                .testTag("home_notifications_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1.2f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = Color.White.copy(alpha = 0.16f)
                            ) {
                                Text(
                                    text = "✦ Trusted local experts",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = HireMint,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = buildAnnotatedString {
                                    append("Home care,\n")
                                    withStyle(
                                        SpanStyle(
                                            color = HireOrange,
                                            fontStyle = FontStyle.Italic
                                        )
                                    ) {
                                        append("handled.")
                                    }
                                },
                                style = MaterialTheme.typography.displaySmall,
                                color = Color.White
                            )
                            Text(
                                text = "Book skilled professionals for every task, big or small.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.painter),
                                contentDescription = "Featured professional",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(96.dp)
                                    .clip(RoundedCornerShape(22.dp))
                                    .border(2.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(22.dp))
                            )
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "4.9",
                                        fontWeight = FontWeight.ExtraBold,
                                        color = HireInk,
                                        fontSize = 13.sp
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "★★★★★",
                                        color = HireOrange,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }

                    // Search Box
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchChange,
                        placeholder = { Text("What service do you need?") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = HireMuted)
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = onSeeAllWorkers,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(HireGreen)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Search",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = HireOrange,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("home_search_input")
                    )
                }
            }

            // Content body
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "WHAT WE DO",
                            style = MaterialTheme.typography.labelSmall,
                            color = HireGreen
                        )
                        Text(
                            text = "Popular services",
                            style = MaterialTheme.typography.titleLarge,
                            color = HireInk
                        )
                    }
                    TextButton(
                        onClick = onSeeAllWorkers,
                        modifier = Modifier.testTag("home_see_all_services")
                    ) {
                        Text("See all", fontWeight = FontWeight.Bold, color = HireGreen)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = HireGreen,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // 2x2 Grid of Services
                val displayed = visibleCategories.ifEmpty { categories }.take(4)
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    displayed.chunked(2).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowItems.forEach { service ->
                                ServiceCategoryCard(
                                    service = service,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onSelectCategory(service.name) }
                                )
                            }
                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

                // Hiré Assurance Card
                Surface(
                    onClick = { showAssuranceNote = !showAssuranceNote },
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFF6F2E9),
                    border = BorderStroke(1.dp, Color(0xFFE7DFCE)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = HireGreen,
                                modifier = Modifier.size(46.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.VerifiedUser,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "HIRÉ ASSURANCE",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = HireGreen
                                )
                                Text(
                                    text = "Quality work, guaranteed",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = HireInk
                                )
                                Text(
                                    text = "Every pro is identity verified",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = HireMuted
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = HireMuted
                            )
                        }
                        AnimatedVisibility(visible = showAssuranceNote) {
                            Text(
                                text = "Your payment stays protected until the work is complete.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = HireGreenDark,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                    }
                }

                // Top professionals near you
                Column {
                    Text(
                        text = "NEAR YOU",
                        style = MaterialTheme.typography.labelSmall,
                        color = HireGreen
                    )
                    Text(
                        text = "Top professionals",
                        style = MaterialTheme.typography.titleLarge,
                        color = HireInk
                    )
                }

                Surface(
                    onClick = onOpenFeaturedWorker,
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, HireCardBorder),
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_featured_pro_card")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box {
                            Image(
                                painter = painterResource(id = featuredWorker.imageRes),
                                contentDescription = featuredWorker.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(88.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = HireGreen,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 4.dp)
                            ) {
                                Text(
                                    text = "AVAILABLE",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = HireOrange,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${featuredWorker.rating}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = " (${featuredWorker.reviewsCount})",
                                    color = HireMuted,
                                    fontSize = 12.sp
                                )
                            }
                            Text(
                                text = "${featuredWorker.name} ✓",
                                style = MaterialTheme.typography.titleMedium,
                                color = HireInk
                            )
                            Text(
                                text = "${featuredWorker.role} specialist",
                                style = MaterialTheme.typography.bodyMedium,
                                color = HireMuted
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = HireMuted,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = " ${featuredWorker.distanceKm}",
                                        fontSize = 12.sp,
                                        color = HireMuted
                                    )
                                }
                                Text(
                                    text = "NPR %,d/day".format(featuredWorker.dailyRate),
                                    fontWeight = FontWeight.ExtraBold,
                                    color = HireGreen,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (activeSheet != null) {
        ModalBottomSheet(
            onDismissRequest = { activeSheet = null },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 12.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (activeSheet == "location") {
                    Text(
                        text = "SERVICE AREA",
                        style = MaterialTheme.typography.labelSmall,
                        color = HireGreen
                    )
                    Text(
                        text = "Choose your location",
                        style = MaterialTheme.typography.titleLarge
                    )
                    cities.forEach { place ->
                        val isSelected = place == selectedCity
                        Surface(
                            onClick = {
                                onCitySelect(place)
                                activeSheet = null
                            },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) HireMint.copy(alpha = 0.5f) else HireSurface,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) HireGreen else HireCardBorder
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = HireGreen
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = place,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = HireGreen
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "WHAT'S NEW",
                        style = MaterialTheme.typography.labelSmall,
                        color = HireGreen
                    )
                    Text(
                        text = "Notifications",
                        style = MaterialTheme.typography.titleLarge
                    )
                    notifications.forEach { item ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = HireSurface,
                            border = BorderStroke(1.dp, HireCardBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (item.isSuccess) HireMint else Color(0xFFFFF0E2),
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = if (item.isSuccess) Icons.Default.Check else Icons.Default.Star,
                                            contentDescription = null,
                                            tint = if (item.isSuccess) HireGreen else HireOrange,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(item.title, fontWeight = FontWeight.Bold)
                                    Text(
                                        item.body,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = HireMuted
                                    )
                                    Text(
                                        item.timeAgo,
                                        fontSize = 11.sp,
                                        color = HireMuted,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ServiceCategoryCard(
    service: ServiceCategory,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        border = BorderStroke(1.dp, HireCardBorder),
        shadowElevation = 1.dp,
        modifier = modifier.testTag("service_card_${service.name.lowercase()}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(service.accentHex).copy(alpha = 0.14f),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = service.symbol,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(service.accentHex)
                    )
                }
            }
            Text(
                text = service.name,
                style = MaterialTheme.typography.titleMedium,
                color = HireInk
            )
            Text(
                text = "From NPR ${service.startingPrice}",
                style = MaterialTheme.typography.bodyMedium,
                color = HireMuted,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun WorkersScreen(
    workers: List<WorkerInfo>,
    selectedFilter: String,
    sortLowestPrice: Boolean,
    savedWorkerIds: Set<String>,
    onBack: () -> Unit,
    onFilterChange: (String) -> Unit,
    onToggleSort: () -> Unit,
    onToggleSave: (String) -> Unit,
    onViewProfile: (String) -> Unit,
    onBookNow: (String) -> Unit
) {
    BackHandler(onBack = onBack)

    var showSearch by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val filteredWorkers = remember(workers, selectedFilter, sortLowestPrice, query) {
        var list = workers.filter { worker ->
            val matchesQuery = query.isBlank() ||
                worker.name.contains(query, ignoreCase = true) ||
                worker.role.contains(query, ignoreCase = true)
            val matchesTab = when (selectedFilter) {
                "Top rated" -> worker.rating >= 4.8
                "Available" -> worker.available
                else -> true
            }
            matchesQuery && matchesTab
        }
        if (sortLowestPrice) {
            list = list.sortedBy { it.dailyRate }
        }
        list
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Surface(
                color = Color.White,
                tonalElevation = 6.dp,
                border = BorderStroke(1.dp, HireCardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            val next = when (selectedFilter) {
                                "All" -> "Available"
                                "Available" -> "Top rated"
                                else -> "All"
                            }
                            onFilterChange(next)
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("☷ Filter: $selectedFilter", fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = onToggleSort,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (sortLowestPrice) HireGreen else Color(0xFFEEF3F0),
                            contentColor = if (sortLowestPrice) Color.White else HireInk
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("workers_sort_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapVert,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (sortLowestPrice) "Lowest price" else "Sort by price",
                            fontWeight = FontWeight.Bold
                        )
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, HireCardBorder, CircleShape)
                        .testTag("workers_back_button")
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "34 professionals nearby",
                        style = MaterialTheme.typography.bodyMedium,
                        color = HireMuted,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Verified Professionals",
                        style = MaterialTheme.typography.titleLarge,
                        color = HireInk
                    )
                }
                IconButton(
                    onClick = { showSearch = !showSearch },
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, HireCardBorder, CircleShape)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search professionals")
                }
            }

            AnimatedVisibility(visible = showSearch) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Search a professional") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Top rated", "Available").forEach { tab ->
                    val selected = selectedFilter == tab
                    FilterChip(
                        selected = selected,
                        onClick = { onFilterChange(tab) },
                        label = { Text(tab, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = HireGreen,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredWorkers, key = { it.id }) { worker ->
                    val isSaved = worker.id in savedWorkerIds
                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, HireCardBorder),
                        shadowElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onViewProfile(worker.id) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box {
                                    Image(
                                        painter = painterResource(id = worker.imageRes),
                                        contentDescription = worker.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(92.dp)
                                            .clip(RoundedCornerShape(18.dp))
                                    )
                                    if (worker.rating >= 4.9) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = HireOrange,
                                            modifier = Modifier
                                                .align(Alignment.TopStart)
                                                .padding(6.dp)
                                        ) {
                                            Text(
                                                text = "TOP PRO",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = Color.White,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${worker.name} ✓",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = HireInk
                                        )
                                        IconButton(
                                            onClick = { onToggleSave(worker.id) },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                contentDescription = "Save ${worker.name}",
                                                tint = if (isSaved) Color(0xFFE11D48) else HireMuted,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = worker.role,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = HireMuted
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = HireOrange,
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Text(
                                            text = " ${worker.rating} ",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                        Text(
                                            text = "(${worker.reviewsCount} reviews)",
                                            color = HireMuted,
                                            fontSize = 12.sp
                                        )
                                    }
                                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                        Text(
                                            text = "✓ Verified",
                                            fontSize = 12.sp,
                                            color = HireGreen,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = "◷ ${worker.yearsExp} years",
                                            fontSize = 12.sp,
                                            color = HireMuted
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "📍 ${worker.area}",
                                            fontSize = 12.sp,
                                            color = HireMuted
                                        )
                                        Text(
                                            text = "NPR %,d/day".format(worker.dailyRate),
                                            fontWeight = FontWeight.ExtraBold,
                                            color = HireGreen,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { onViewProfile(worker.id) },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("View profile", fontWeight = FontWeight.Bold)
                                }
                                Button(
                                    onClick = { onBookNow(worker.id) },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("book_now_${worker.id}")
                                ) {
                                    Text("Book now", fontWeight = FontWeight.Bold)
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
fun WorkerProfileScreen(
    worker: WorkerInfo,
    isSaved: Boolean,
    onBack: () -> Unit,
    onToggleSave: () -> Unit,
    onMessageWorker: () -> Unit,
    onBookWorker: () -> Unit
) {
    BackHandler(onBack = onBack)

    var selectedTab by remember { mutableStateOf("About") }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Surface(
                color = Color.White,
                tonalElevation = 8.dp,
                border = BorderStroke(1.dp, HireCardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onMessageWorker,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, HireCardBorder, RoundedCornerShape(14.dp))
                            .testTag("profile_chat_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Chat,
                            contentDescription = "Message ${worker.name}",
                            tint = HireInk
                        )
                    }
                    Button(
                        onClick = onBookWorker,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("profile_book_button")
                    ) {
                        Text(
                            text = "Book ${worker.name.substringBefore(" ")}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
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
                .padding(bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                Image(
                    painter = painterResource(id = worker.imageRes),
                    contentDescription = worker.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.35f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.35f))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = onToggleSave,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.35f))
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Save professional",
                            tint = if (isSaved) Color(0xFFFF4D6D) else Color.White
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = HireGreen,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(18.dp)
                ) {
                    Text(
                        text = "HIRÉ VERIFIED",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${worker.name} ✓",
                            style = MaterialTheme.typography.headlineMedium,
                            color = HireInk
                        )
                        Text(
                            text = "${worker.role} specialist",
                            style = MaterialTheme.typography.bodyLarge,
                            color = HireMuted
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFFFF8EB),
                        border = BorderStroke(1.dp, Color(0xFFFDE68A))
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = HireOrange,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = " ${worker.rating}",
                                    fontWeight = FontWeight.ExtraBold,
                                    color = HireInk
                                )
                            }
                            Text(
                                text = "${worker.reviewsCount} reviews",
                                fontSize = 11.sp,
                                color = HireMuted
                            )
                        }
                    }
                }

                // Quick facts row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickFactTile(
                        title = "Verified",
                        subtitle = "Identity checked",
                        modifier = Modifier.weight(1f)
                    )
                    QuickFactTile(
                        title = "${worker.yearsExp} years",
                        subtitle = "Experience",
                        modifier = Modifier.weight(1f)
                    )
                    QuickFactTile(
                        title = worker.distanceKm,
                        subtitle = "From you",
                        modifier = Modifier.weight(1f)
                    )
                }

                // Profile Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(HireSurface)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("About", "Portfolio", "Reviews").forEach { tab ->
                        val active = selectedTab == tab
                        Surface(
                            onClick = { selectedTab = tab },
                            shape = RoundedCornerShape(10.dp),
                            color = if (active) Color.White else Color.Transparent,
                            shadowElevation = if (active) 1.dp else 0.dp,
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = tab,
                                    fontWeight = FontWeight.Bold,
                                    color = if (active) HireGreenDark else HireMuted
                                )
                            }
                        }
                    }
                }

                when (selectedTab) {
                    "About" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Careful work. Beautiful finish.",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = worker.bio,
                                style = MaterialTheme.typography.bodyLarge,
                                color = HireMuted
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                worker.skills.forEach { skill ->
                                    Surface(
                                        shape = RoundedCornerShape(50),
                                        color = HireMint.copy(alpha = 0.6f)
                                    ) {
                                        Text(
                                            text = skill,
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

                    "Portfolio" -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            listOf(
                                R.drawable.house,
                                R.drawable.painter,
                                R.drawable.carpenter
                            ).forEach { imgRes ->
                                Image(
                                    painter = painterResource(id = imgRes),
                                    contentDescription = "Portfolio project",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(100.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                )
                            }
                        }
                    }

                    "Reviews" -> {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = HireSurface,
                            border = BorderStroke(1.dp, HireCardBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Sunita K.", fontWeight = FontWeight.Bold)
                                    Text("★★★★★", color = HireOrange)
                                }
                                Text(
                                    text = "“Careful, punctual, and the finish looks wonderful. Highly recommended.”",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = HireMuted
                                )
                            }
                        }
                    }
                }

                // Quote card
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
                            Text(
                                text = "STARTING FROM",
                                style = MaterialTheme.typography.labelSmall,
                                color = HireMuted
                            )
                            Text(
                                text = "NPR %,d / day".format(worker.dailyRate),
                                style = MaterialTheme.typography.titleMedium,
                                color = HireGreen
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "NEXT AVAILABLE",
                                style = MaterialTheme.typography.labelSmall,
                                color = HireMuted
                            )
                            Text(
                                text = "Tomorrow, 9:00 AM",
                                style = MaterialTheme.typography.titleMedium,
                                color = HireInk
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickFactTile(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = HireSurface,
        border = BorderStroke(1.dp, HireCardBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = HireInk)
            Text(text = subtitle, fontSize = 11.sp, color = HireMuted)
        }
    }
}
