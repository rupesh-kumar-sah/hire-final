package com.hirenepal.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hirenepal.app.model.AppScreen
import com.hirenepal.app.ui.screens.AccountScreen
import com.hirenepal.app.ui.screens.AdminChallengeScreen
import com.hirenepal.app.ui.screens.AdminDashboardScreen
import com.hirenepal.app.ui.screens.AuthScreen
import com.hirenepal.app.ui.screens.BookingScreen
import com.hirenepal.app.ui.screens.ChatScreen
import com.hirenepal.app.ui.screens.ConfirmedScreen
import com.hirenepal.app.ui.screens.HomeScreen
import com.hirenepal.app.ui.screens.RegisterScreen
import com.hirenepal.app.ui.screens.SplashScreen
import com.hirenepal.app.ui.screens.WorkerDashboardScreen
import com.hirenepal.app.ui.screens.WorkerProfileScreen
import com.hirenepal.app.ui.screens.WorkersScreen
import com.hirenepal.app.ui.theme.HireInk
import com.hirenepal.app.ui.theme.HireMint
import com.hirenepal.app.ui.theme.HireNepalTheme
import com.hirenepal.app.viewmodel.HireNepalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HireNepalTheme {
                HireNepalAppRoot()
            }
        }
    }
}

@Composable
fun HireNepalAppRoot(viewModel: HireNepalViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (state.currentScreen) {
            AppScreen.Splash -> {
                SplashScreen(
                    onGetStarted = { viewModel.navigateTo(AppScreen.Register) },
                    onLogin = { viewModel.navigateTo(AppScreen.Auth) },
                    onExploreGuest = { viewModel.navigateTo(AppScreen.Home) }
                )
            }

            AppScreen.Auth -> {
                AuthScreen(
                    errorText = state.authError,
                    onBack = { viewModel.navigateBack() },
                    onLoginSubmit = { email, password, role ->
                        viewModel.login(email, password, role)
                    },
                    onCreateAccount = { viewModel.navigateTo(AppScreen.Register) }
                )
            }

            AppScreen.Register -> {
                RegisterScreen(
                    cities = viewModel.cities,
                    services = viewModel.categories.map { it.name },
                    errorText = state.authError,
                    onBackToSplash = { viewModel.navigateTo(AppScreen.Splash) },
                    onGoToSignIn = { viewModel.navigateTo(AppScreen.Auth) },
                    onRegisterSubmit = { name, phone, email, password, city, role, service ->
                        viewModel.register(name, phone, email, password, city, role, service)
                    }
                )
            }

            AppScreen.AdminChallenge -> {
                AdminChallengeScreen(
                    errorText = state.authError,
                    onBackToAuth = { viewModel.navigateTo(AppScreen.Auth) },
                    onVerify = { linuxAnswer, dateAnswer ->
                        viewModel.verifyAdminChallenge(linuxAnswer, dateAnswer)
                    }
                )
            }

            AppScreen.WorkerDashboard -> {
                val workerUser = state.currentUser ?: state.users.first()
                WorkerDashboardScreen(
                    user = workerUser,
                    available = state.workerAvailable,
                    bookings = state.bookings,
                    onToggleAvailable = { viewModel.toggleWorkerAvailability() },
                    onOpenChat = { viewModel.navigateTo(AppScreen.Chat) },
                    onSwitchToCustomerHome = { viewModel.navigateTo(AppScreen.Home) },
                    onLogout = { viewModel.logout() }
                )
            }

            AppScreen.AdminDashboard -> {
                val adminUser = state.currentUser ?: state.users.last()
                AdminDashboardScreen(
                    user = adminUser,
                    users = state.users,
                    bookings = state.bookings,
                    onVerifyWorker = { viewModel.verifyWorkerAccount(it) },
                    onLogout = { viewModel.logout() }
                )
            }

            AppScreen.Home -> {
                HomeScreen(
                    selectedCity = state.selectedCity,
                    cities = viewModel.cities,
                    searchQuery = state.searchQuery,
                    categories = viewModel.categories,
                    featuredWorker = state.workers.first(),
                    notifications = state.notifications,
                    onSearchChange = { viewModel.setSearchQuery(it) },
                    onCitySelect = { viewModel.setCity(it) },
                    onSelectCategory = { viewModel.selectCategoryAndOpenWorkers(it) },
                    onSeeAllWorkers = { viewModel.navigateTo(AppScreen.Workers) },
                    onOpenFeaturedWorker = {
                        viewModel.openWorkerProfile(state.workers.first().id)
                    },
                    onNavigateBottom = { viewModel.navigateTo(it) }
                )
            }

            AppScreen.Workers -> {
                WorkersScreen(
                    workers = state.workers,
                    selectedFilter = state.workerFilter,
                    sortLowestPrice = state.sortLowestPrice,
                    savedWorkerIds = state.savedWorkerIds,
                    onBack = { viewModel.navigateBack() },
                    onFilterChange = { viewModel.setWorkerFilter(it) },
                    onToggleSort = { viewModel.toggleSortLowestPrice() },
                    onToggleSave = { viewModel.toggleSavedWorker(it) },
                    onViewProfile = { viewModel.openWorkerProfile(it) },
                    onBookNow = { viewModel.openBookingForWorker(it) }
                )
            }

            AppScreen.Profile -> {
                val worker = state.selectedWorker
                WorkerProfileScreen(
                    worker = worker,
                    isSaved = worker.id in state.savedWorkerIds,
                    onBack = { viewModel.navigateBack() },
                    onToggleSave = { viewModel.toggleSavedWorker(worker.id) },
                    onMessageWorker = { viewModel.navigateTo(AppScreen.Chat) },
                    onBookWorker = { viewModel.navigateTo(AppScreen.Booking) }
                )
            }

            AppScreen.Booking -> {
                BookingScreen(
                    worker = state.selectedWorker,
                    onBack = { viewModel.navigateBack() },
                    onSubmitBooking = { details, rooms, dateLabel, timeSlot, address, photos ->
                        viewModel.submitBooking(details, rooms, dateLabel, timeSlot, address, photos)
                    }
                )
            }

            AppScreen.Confirmed -> {
                ConfirmedScreen(
                    booking = state.activeBooking,
                    onBackHome = { viewModel.navigateTo(AppScreen.Home) },
                    onReschedule = { viewModel.navigateTo(AppScreen.Booking) },
                    onCancelBooking = { viewModel.cancelActiveBooking() },
                    onOpenChat = { viewModel.navigateTo(AppScreen.Chat) },
                    onFindAnotherPro = { viewModel.navigateTo(AppScreen.Workers) },
                    onNavigateBottom = { viewModel.navigateTo(it) }
                )
            }

            AppScreen.Chat -> {
                val worker = state.selectedWorker
                val msgs = state.messagesByWorker[worker.id]
                    ?: state.messagesByWorker["w1"].orEmpty()
                ChatScreen(
                    worker = worker,
                    messages = msgs,
                    isTyping = state.isWorkerTyping,
                    onBack = { viewModel.navigateBack() },
                    onSendMessage = { viewModel.sendChatMessage(it) },
                    onCallNotice = {
                        viewModel.showToast("Calling ${worker.name} (+977 9851098765)…")
                    },
                    onNavigateBottom = { viewModel.navigateTo(it) }
                )
            }

            AppScreen.Account -> {
                AccountScreen(
                    user = state.currentUser,
                    walletBalance = state.walletBalance,
                    savedCount = state.savedWorkerIds.size,
                    bookingsCount = state.bookings.count { it.status != "Cancelled" },
                    onTopUp = { viewModel.topUpWallet(1000) },
                    onOpenBookings = { viewModel.navigateTo(AppScreen.Confirmed) },
                    onOpenFavourites = { viewModel.navigateTo(AppScreen.Workers) },
                    onLogout = { viewModel.logout() },
                    onNavigateBottom = { viewModel.navigateTo(it) }
                )
            }
        }

        AnimatedVisibility(
            visible = state.toastMessage.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(bottom = 88.dp, start = 20.dp, end = 20.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = HireInk,
                shadowElevation = 6.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = HireMint,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = state.toastMessage,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
