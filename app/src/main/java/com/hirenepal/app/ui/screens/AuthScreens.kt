package com.hirenepal.app.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hirenepal.app.R
import com.hirenepal.app.model.AppScreen
import com.hirenepal.app.model.UserRole
import com.hirenepal.app.ui.theme.HireCardBorder
import com.hirenepal.app.ui.theme.HireGreen
import com.hirenepal.app.ui.theme.HireGreenDark
import com.hirenepal.app.ui.theme.HireInk
import com.hirenepal.app.ui.theme.HireMint
import com.hirenepal.app.ui.theme.HireMuted
import com.hirenepal.app.ui.theme.HireOrange

@Composable
fun SplashScreen(
    onGetStarted: () -> Unit,
    onLogin: () -> Unit,
    onExploreGuest: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFEAF8FF),
                        Color(0xFFD8EDF0),
                        HireGreen
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 6.dp,
                    modifier = Modifier.size(68.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "HiréNepal Logo",
                            tint = HireGreen,
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = HireOrange, fontWeight = FontWeight.ExtraBold)) {
                            append("Hiré")
                        }
                        withStyle(SpanStyle(color = HireInk, fontWeight = FontWeight.ExtraBold)) {
                            append("Nepal")
                        }
                    },
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = "Find · Hire · Trust",
                    style = MaterialTheme.typography.bodyMedium,
                    color = HireMuted,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Trusted Local Professionals\nfor Your Every Need",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = HireInk
                )
            }

            CardHeroPreview()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onGetStarted,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HireOrange,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("splash_get_started_button")
                ) {
                    Text("Get Started", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }

                OutlinedButton(
                    onClick = onLogin,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White.copy(alpha = 0.14f),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("splash_login_button")
                ) {
                    Text("Login", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                TextButton(
                    onClick = onExploreGuest,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .testTag("splash_explore_guest_button")
                ) {
                    Text(
                        text = "Browse services as guest →",
                        color = Color.White.copy(alpha = 0.9f),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun CardHeroPreview() {
    Surface(
        shape = RoundedCornerShape(28.dp),
        shadowElevation = 12.dp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.painter),
                contentDescription = "Trusted local professional",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.65f)
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = HireGreen,
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "2,000+ Verified Pros",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Kathmandu · Lalitpur · Bhaktapur · Kirtipur",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AuthScreen(
    errorText: String,
    onBack: () -> Unit,
    onLoginSubmit: (email: String, password: String, role: UserRole) -> Unit,
    onCreateAccount: () -> Unit
) {
    BackHandler(onBack = onBack)

    var portal by remember { mutableStateOf(UserRole.Customer) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F9F7))
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    .background(Color.White)
                    .border(1.dp, HireCardBorder, CircleShape)
                    .testTag("auth_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Home, contentDescription = null, tint = HireGreen)
                Spacer(modifier = Modifier.width(6.dp))
                Text("HiréNepal", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(48.dp))
        }

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, HireCardBorder),
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "WELCOME BACK",
                    style = MaterialTheme.typography.labelSmall,
                    color = HireGreen
                )
                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.headlineMedium,
                    color = HireInk
                )
                Text(
                    text = "Choose how you want to use HiréNepal today.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = HireMuted
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFEEF3F0))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    PortalTabButton(
                        selected = portal == UserRole.Customer,
                        label = "Hire a pro",
                        modifier = Modifier.weight(1f),
                        onClick = { portal = UserRole.Customer }
                    )
                    PortalTabButton(
                        selected = portal == UserRole.Worker,
                        label = "Find work",
                        modifier = Modifier.weight(1f),
                        onClick = { portal = UserRole.Worker }
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email address") },
                    placeholder = { Text("you@example.com") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("auth_email_input")
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    placeholder = { Text("Enter your password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("auth_password_input")
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Quick Demo Accounts:",
                        style = MaterialTheme.typography.labelSmall,
                        color = HireMuted
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DemoPillButton(
                            label = "Customer",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                portal = UserRole.Customer
                                email = "rupesh@gmail.com"
                                password = "password123"
                            }
                        )
                        DemoPillButton(
                            label = "Worker",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                portal = UserRole.Worker
                                email = "ramesh@hirenepal.com"
                                password = "password123"
                            }
                        )
                        DemoPillButton(
                            label = "Admin",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                email = "admin@hirenepal.com"
                                password = "password123"
                            }
                        )
                    }
                }

                if (errorText.isNotEmpty()) {
                    Surface(
                        color = Color(0xFFFEE2E2),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = errorText,
                            color = Color(0xFFB91C1C),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Button(
                    onClick = { onLoginSubmit(email, password, portal) },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("auth_submit_button")
                ) {
                    Text("Sign in", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }

                OutlinedButton(
                    onClick = onCreateAccount,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("auth_create_account_button")
                ) {
                    Text("Create your account", fontWeight = FontWeight.Bold, color = HireGreen)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.VerifiedUser,
                contentDescription = null,
                tint = HireGreen,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Secure login · Your information is protected",
                style = MaterialTheme.typography.bodyMedium,
                color = HireMuted
            )
        }
    }
}

@Composable
private fun PortalTabButton(
    selected: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = if (selected) Color.White else Color.Transparent,
        shadowElevation = if (selected) 2.dp else 0.dp,
        modifier = modifier.height(42.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = if (selected) HireGreenDark else HireMuted
            )
        }
    }
}

@Composable
private fun DemoPillButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = HireMint.copy(alpha = 0.55f),
        modifier = modifier.height(36.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = HireGreenDark
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    cities: List<String>,
    services: List<String>,
    errorText: String,
    onBackToSplash: () -> Unit,
    onGoToSignIn: () -> Unit,
    onRegisterSubmit: (
        name: String,
        phone: String,
        email: String,
        password: String,
        city: String,
        role: UserRole,
        service: String
    ) -> Unit
) {
    var step by remember { mutableIntStateOf(1) }
    var role by remember { mutableStateOf(UserRole.Customer) }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("+977 ") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var city by remember { mutableStateOf(cities.firstOrNull() ?: "Kathmandu") }
    var service by remember { mutableStateOf(services.firstOrNull() ?: "Painting") }
    var cityExpanded by remember { mutableStateOf(false) }
    var serviceExpanded by remember { mutableStateOf(false) }

    BackHandler {
        if (step == 2) step = 1 else onBackToSplash()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F9F7))
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { if (step == 2) step = 1 else onBackToSplash() },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, HireCardBorder, CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "STEP $step OF 2",
                    style = MaterialTheme.typography.labelSmall,
                    color = HireMuted
                )
                Text(
                    text = "Create account",
                    style = MaterialTheme.typography.titleMedium,
                    color = HireInk
                )
            }
            TextButton(onClick = onGoToSignIn) {
                Text("Sign in", fontWeight = FontWeight.Bold, color = HireGreen)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(HireGreen)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(if (step == 2) HireGreen else Color(0xFFD5DFDA))
            )
        }

        if (step == 1) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, HireCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "CHOOSE YOUR PATH",
                        style = MaterialTheme.typography.labelSmall,
                        color = HireGreen
                    )
                    Text(
                        text = "How will you use HiréNepal?",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "You can update your profile later, but your workspace is tailored to this choice.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = HireMuted
                    )

                    RoleChoiceCard(
                        selected = role == UserRole.Customer,
                        kicker = "I WANT TO",
                        title = "Hire a professional",
                        subtitle = "Find verified experts for home services",
                        icon = Icons.Default.Search,
                        onClick = { role = UserRole.Customer }
                    )

                    RoleChoiceCard(
                        selected = role == UserRole.Worker,
                        kicker = "I WANT TO",
                        title = "Offer my services",
                        subtitle = "Grow your business and receive local jobs",
                        icon = Icons.Default.VerifiedUser,
                        onClick = { role = UserRole.Worker }
                    )

                    Button(
                        onClick = { step = 2 },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("register_continue_button")
                    ) {
                        Text("Continue", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
            }
        } else {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, HireCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = if (role == UserRole.Worker) "WORKER PROFILE" else "YOUR DETAILS",
                        style = MaterialTheme.typography.labelSmall,
                        color = HireGreen
                    )
                    Text(
                        text = "Tell us about yourself",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = if (role == UserRole.Worker) {
                            "Your profile will be reviewed before it goes live."
                        } else {
                            "Book trusted help in just a few taps."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = HireMuted
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full name") },
                        placeholder = { Text("Your name") },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("register_name_input")
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone") },
                        placeholder = { Text("+977 98...") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email address") },
                        placeholder = { Text("you@example.com") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("register_email_input")
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        placeholder = { Text("At least 8 characters") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("register_password_input")
                    )

                    ExposedDropdownMenuBox(
                        expanded = cityExpanded,
                        onExpandedChange = { cityExpanded = !cityExpanded }
                    ) {
                        OutlinedTextField(
                            value = city,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("City") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = cityExpanded)
                            },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = cityExpanded,
                            onDismissRequest = { cityExpanded = false }
                        ) {
                            cities.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item) },
                                    onClick = {
                                        city = item
                                        cityExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    if (role == UserRole.Worker) {
                        ExposedDropdownMenuBox(
                            expanded = serviceExpanded,
                            onExpandedChange = { serviceExpanded = !serviceExpanded }
                        ) {
                            OutlinedTextField(
                                value = service,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Main service") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = serviceExpanded)
                                },
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                    .fillMaxWidth()
                            )
                            ExposedDropdownMenu(
                                expanded = serviceExpanded,
                                onDismissRequest = { serviceExpanded = false }
                            ) {
                                services.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(item) },
                                        onClick = {
                                            service = item
                                            serviceExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    if (errorText.isNotEmpty()) {
                        Surface(
                            color = Color(0xFFFEE2E2),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = errorText,
                                color = Color(0xFFB91C1C),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            onRegisterSubmit(name, phone, email, password, city, role, service)
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("register_submit_button")
                    ) {
                        Text("Create account", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Composable
private fun RoleChoiceCard(
    selected: Boolean,
    kicker: String,
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = if (selected) HireMint.copy(alpha = 0.4f) else Color(0xFFF8FAF8),
        border = androidx.compose.foundation.BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) HireGreen else HireCardBorder
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (selected) HireGreen else Color.White,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (selected) Color.White else HireGreen
                    )
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = kicker,
                    style = MaterialTheme.typography.labelSmall,
                    color = HireMuted
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = HireInk
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = HireMuted
                )
            }
            Surface(
                shape = CircleShape,
                color = if (selected) HireGreen else Color.Transparent,
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (selected) HireGreen else HireCardBorder
                ),
                modifier = Modifier.size(24.dp)
            ) {
                if (selected) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminChallengeScreen(
    errorText: String,
    onBackToAuth: () -> Unit,
    onVerify: (linuxAnswer: String, dateAnswer: String) -> Unit
) {
    BackHandler(onBack = onBackToAuth)

    var linuxAnswer by remember { mutableStateOf("Linus Torvalds") }
    var dateAnswer by remember { mutableStateOf("2026") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(HireGreenDark, Color(0xFF042920))
                )
            )
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White.copy(alpha = 0.14f),
            modifier = Modifier.size(64.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.VerifiedUser,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "SECURE ADMIN ACCESS",
            style = MaterialTheme.typography.labelSmall,
            color = HireMint
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "One final security check.",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Answer both account recovery questions to open the operations panel.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp, bottom = 22.dp)
        )

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                OutlinedTextField(
                    value = linuxAnswer,
                    onValueChange = { linuxAnswer = it },
                    label = { Text("Q1: Who created the Linux kernel?") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = dateAnswer,
                    onValueChange = { dateAnswer = it },
                    label = { Text("Q2: What is the verification year?") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorText.isNotEmpty()) {
                    Text(
                        text = errorText,
                        color = Color(0xFFB91C1C),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Button(
                    onClick = { onVerify(linuxAnswer, dateAnswer) },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HireGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("admin_verify_button")
                ) {
                    Text("Verify and continue", fontWeight = FontWeight.Bold)
                }
                TextButton(
                    onClick = onBackToAuth,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Back to sign in", color = HireMuted)
                }
            }
        }
    }
}
