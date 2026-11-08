package com.hirenepal.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hirenepal.app.R
import com.hirenepal.app.model.AppScreen
import com.hirenepal.app.model.BookingItem
import com.hirenepal.app.model.ChatMessage
import com.hirenepal.app.model.NotificationItem
import com.hirenepal.app.model.PublicUser
import com.hirenepal.app.model.ServiceCategory
import com.hirenepal.app.model.UserRole
import com.hirenepal.app.model.UserStatus
import com.hirenepal.app.model.WorkerInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HireNepalUiState(
    val currentScreen: AppScreen = AppScreen.Splash,
    val backStack: List<AppScreen> = emptyList(),
    val currentUser: PublicUser? = null,
    val pendingAdminUser: PublicUser? = null,
    val authError: String = "",
    val selectedCity: String = "Kathmandu",
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val workerFilter: String = "All",
    val sortLowestPrice: Boolean = false,
    val savedWorkerIds: Set<String> = setOf("w1"),
    val workers: List<WorkerInfo> = emptyList(),
    val selectedWorkerId: String = "w1",
    val users: List<PublicUser> = emptyList(),
    val bookings: List<BookingItem> = emptyList(),
    val activeBookingId: String = "b1",
    val messagesByWorker: Map<String, List<ChatMessage>> = emptyMap(),
    val isWorkerTyping: Boolean = false,
    val workerAvailable: Boolean = true,
    val walletBalance: Int = 2500,
    val toastMessage: String = "",
    val notifications: List<NotificationItem> = emptyList()
) {
    val selectedWorker: WorkerInfo
        get() = workers.find { it.id == selectedWorkerId } ?: workers.first()

    val activeBooking: BookingItem?
        get() = bookings.find { it.id == activeBookingId } ?: bookings.firstOrNull()
}

class HireNepalViewModel : ViewModel() {

    val categories = listOf(
        ServiceCategory("Painting", "◒", 1500, 128, 0xFFEE8B39),
        ServiceCategory("Plumbing", "⌁", 900, 96, 0xFF2563EB),
        ServiceCategory("Electrical", "ϟ", 1100, 84, 0xFFD97706),
        ServiceCategory("Cleaning", "✦", 800, 142, 0xFF006C4D),
        ServiceCategory("Carpentry", "⌂", 1400, 61, 0xFF7C3AED)
    )

    val cities = listOf("Kathmandu", "Lalitpur", "Bhaktapur", "Kirtipur")

    private val initialWorkers = listOf(
        WorkerInfo(
            id = "w1",
            name = "Ramesh Thapa",
            role = "House Painter",
            serviceCategory = "Painting",
            rating = 4.9,
            reviewsCount = 124,
            yearsExp = "6+",
            area = "Kathmandu",
            distanceKm = "1.2 km",
            dailyRate = 1500,
            available = true,
            verified = true,
            bio = "Professional painter with 6+ years of experience in interior and exterior painting. Known for clean lines, tidy workspaces, and reliable service.",
            skills = listOf("Interior", "Exterior", "Wall finish", "Waterproofing"),
            imageRes = R.drawable.painter
        ),
        WorkerInfo(
            id = "w2",
            name = "Suman Gurung",
            role = "Master Electrician",
            serviceCategory = "Electrical",
            rating = 4.8,
            reviewsCount = 89,
            yearsExp = "8+",
            area = "Lalitpur",
            distanceKm = "2.4 km",
            dailyRate = 1200,
            available = true,
            verified = true,
            bio = "Certified residential and commercial electrician specializing in panel upgrades, smart lighting,inverter wiring, and fault diagnostics.",
            skills = listOf("Wiring", "Inverter setup", "Circuit breaker", "Lighting"),
            imageRes = R.drawable.electrician
        ),
        WorkerInfo(
            id = "w3",
            name = "Binod Shrestha",
            role = "Custom Carpenter & Finisher",
            serviceCategory = "Carpentry",
            rating = 4.7,
            reviewsCount = 76,
            yearsExp = "5+",
            area = "Bhaktapur",
            distanceKm = "3.8 km",
            dailyRate = 1300,
            available = false,
            verified = true,
            bio = "Skilled woodworker crafting modular wardrobes, kitchen cabinets, door repairs, and bespoke furniture polishing across Kathmandu Valley.",
            skills = listOf("Modular kitchen", "Door repair", "Wood polish", "Custom shelves"),
            imageRes = R.drawable.carpenter
        ),
        WorkerInfo(
            id = "w4",
            name = "Anita Sharma",
            role = "Deep Home Cleaner",
            serviceCategory = "Cleaning",
            rating = 4.9,
            reviewsCount = 104,
            yearsExp = "4+",
            area = "Kathmandu",
            distanceKm = "1.8 km",
            dailyRate = 1000,
            available = true,
            verified = true,
            bio = "Eco-friendly full home, sofa, and post-renovation deep cleaning specialist with professional sanitization equipment.",
            skills = listOf("Deep cleaning", "Sofa shampoo", "Kitchen degrease"),
            imageRes = R.drawable.cleaner
        )
    )

    private val initialUsers = listOf(
        PublicUser(
            id = "u1",
            name = "Rupesh Sah",
            email = "rupesh@gmail.com",
            phone = "+977 9841223344",
            city = "Kathmandu",
            role = UserRole.Customer,
            status = UserStatus.Active,
            verified = true
        ),
        PublicUser(
            id = "u2",
            name = "Ramesh Thapa",
            email = "ramesh@hirenepal.com",
            phone = "+977 9851098765",
            city = "Kathmandu",
            role = UserRole.Worker,
            status = UserStatus.Active,
            service = "Painting",
            verified = true
        ),
        PublicUser(
            id = "u3",
            name = "Suman Gurung",
            email = "suman@hirenepal.com",
            phone = "+977 9801122334",
            city = "Lalitpur",
            role = UserRole.Worker,
            status = UserStatus.Pending,
            service = "Electrical",
            verified = false
        ),
        PublicUser(
            id = "u4",
            name = "Binod Shrestha",
            email = "binod@hirenepal.com",
            phone = "+977 9849988776",
            city = "Bhaktapur",
            role = UserRole.Worker,
            status = UserStatus.Active,
            service = "Carpentry",
            verified = true
        ),
        PublicUser(
            id = "u_admin",
            name = "Aarav Adhikari",
            email = "admin@hirenepal.com",
            phone = "+977 9851000001",
            city = "Kathmandu",
            role = UserRole.Customer,
            status = UserStatus.Active,
            verified = true,
            isAppAdmin = true
        )
    )

    private val initialBookings = listOf(
        BookingItem(
            id = "b1",
            reference = "#HN-2048",
            customerName = "Rupesh Sah",
            workerId = "w1",
            workerName = "Ramesh Thapa",
            workerRole = "House painting specialist",
            workerImageRes = R.drawable.painter,
            service = "Painting",
            details = "Two bedrooms and one hallway interior repainting.",
            rooms = 2,
            dateLabel = "Friday, 15 Nov",
            timeSlot = "9:00 AM",
            address = "Bansbari, Kathmandu",
            totalPrice = 3000,
            status = "Confirmed"
        ),
        BookingItem(
            id = "b2",
            reference = "#HN-1046",
            customerName = "Sunita Karki",
            workerId = "w2",
            workerName = "Suman Gurung",
            workerRole = "Master Electrician",
            workerImageRes = R.drawable.electrician,
            service = "Electrical",
            details = "Full inverter backup wiring and distribution board check.",
            rooms = 1,
            dateLabel = "Saturday, 16 Nov",
            timeSlot = "11:30 AM",
            address = "Jhamsikhel, Lalitpur",
            totalPrice = 2400,
            status = "In progress"
        ),
        BookingItem(
            id = "b3",
            reference = "#HN-1045",
            customerName = "Prakash Malla",
            workerId = "w3",
            workerName = "Binod Shrestha",
            workerRole = "Custom Carpenter & Finisher",
            workerImageRes = R.drawable.carpenter,
            service = "Carpentry",
            details = "Bedroom wardrobe sliding track replacement.",
            rooms = 3,
            dateLabel = "Monday, 11 Nov",
            timeSlot = "2:00 PM",
            address = "Suryabinayak, Bhaktapur",
            totalPrice = 5200,
            status = "Completed"
        )
    )

    private val initialMessages = mapOf(
        "w1" to listOf(
            ChatMessage("m1", false, "Namaste! How can I help with your home project?", "9:20 AM"),
            ChatMessage("m2", true, "Can you do house painting this weekend?", "9:22 AM"),
            ChatMessage("m3", false, "Yes, I am available. I can visit tomorrow for inspection.", "9:24 AM")
        )
    )

    private val initialNotifications = listOf(
        NotificationItem(
            id = "n1",
            title = "Booking confirmed",
            body = "Ramesh accepted your painting request for Friday at 9:00 AM.",
            timeAgo = "2 minutes ago",
            isSuccess = true
        ),
        NotificationItem(
            id = "n2",
            title = "20% off your next service",
            body = "Use code HOME20 on any electrical or cleaning booking before Sunday.",
            timeAgo = "Today",
            isSuccess = false
        )
    )

    private val _uiState = MutableStateFlow(
        HireNepalUiState(
            workers = initialWorkers,
            users = initialUsers,
            bookings = initialBookings,
            messagesByWorker = initialMessages,
            notifications = initialNotifications
        )
    )
    val uiState: StateFlow<HireNepalUiState> = _uiState.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _uiState.update { state ->
            if (state.currentScreen == screen) state
            else state.copy(
                currentScreen = screen,
                backStack = state.backStack + state.currentScreen,
                authError = ""
            )
        }
    }

    fun navigateBack(): Boolean {
        val currentStack = _uiState.value.backStack
        if (currentStack.isEmpty()) {
            if (_uiState.value.currentScreen != AppScreen.Splash && _uiState.value.currentScreen != AppScreen.Home) {
                _uiState.update { it.copy(currentScreen = AppScreen.Home, authError = "") }
                return true
            }
            return false
        }
        val previous = currentStack.last()
        _uiState.update {
            it.copy(
                currentScreen = previous,
                backStack = currentStack.dropLast(1),
                authError = ""
            )
        }
        return true
    }

    fun clearAuthError() {
        _uiState.update { it.copy(authError = "") }
    }

    fun login(emailInput: String, passwordInput: String, portal: UserRole) {
        val email = emailInput.trim().lowercase()
        if (email.isEmpty() || passwordInput.isEmpty()) {
            _uiState.update { it.copy(authError = "Enter your email and password.") }
            return
        }
        if (passwordInput.length < 4) {
            _uiState.update { it.copy(authError = "The email or password is incorrect.") }
            return
        }

        val existingUser = _uiState.value.users.find { it.email.lowercase() == email }
        val user = existingUser ?: PublicUser(
            id = "u_${System.currentTimeMillis()}",
            name = email.substringBefore("@").replaceFirstChar { it.uppercase() },
            email = email,
            role = portal,
            status = UserStatus.Active,
            verified = portal == UserRole.Customer,
            isAppAdmin = email.startsWith("admin")
        )

        if (user.status == UserStatus.Suspended) {
            _uiState.update { it.copy(authError = "This account is currently suspended.") }
            return
        }

        if (user.isAppAdmin) {
            _uiState.update {
                it.copy(
                    pendingAdminUser = user,
                    currentScreen = AppScreen.AdminChallenge,
                    backStack = it.backStack + it.currentScreen,
                    authError = ""
                )
            }
            return
        }

        completeAuthentication(user)
    }

    fun register(
        name: String,
        phone: String,
        emailInput: String,
        passwordInput: String,
        city: String,
        role: UserRole,
        service: String
    ) {
        val trimmedName = name.trim()
        val trimmedEmail = emailInput.trim().lowercase()
        if (trimmedName.length < 2) {
            _uiState.update { it.copy(authError = "Enter your full name.") }
            return
        }
        if (!trimmedEmail.contains("@")) {
            _uiState.update { it.copy(authError = "Enter a valid email address.") }
            return
        }
        if (passwordInput.length < 8) {
            _uiState.update { it.copy(authError = "Use at least 8 characters for your password.") }
            return
        }

        val newUser = PublicUser(
            id = "u_${System.currentTimeMillis()}",
            name = trimmedName,
            email = trimmedEmail,
            phone = phone.ifBlank { "+977 9841000000" },
            city = city,
            role = role,
            status = if (role == UserRole.Worker) UserStatus.Pending else UserStatus.Active,
            service = service,
            verified = false,
            isAppAdmin = false
        )

        _uiState.update { state ->
            val updatedWorkers = if (role == UserRole.Worker) {
                val worker = WorkerInfo(
                    id = "w_${System.currentTimeMillis()}",
                    name = trimmedName,
                    role = "$service Specialist",
                    serviceCategory = service,
                    rating = 4.8,
                    reviewsCount = 12,
                    yearsExp = "3+",
                    area = city,
                    distanceKm = "1.5 km",
                    dailyRate = 1200,
                    available = true,
                    verified = false,
                    bio = "Dedicated $service professional serving $city with reliable, high-quality workmanship.",
                    skills = listOf(service, "Residential", "Inspection"),
                    imageRes = R.drawable.painter
                )
                listOf(worker) + state.workers
            } else {
                state.workers
            }
            state.copy(
                users = listOf(newUser) + state.users,
                workers = updatedWorkers,
                selectedCity = city
            )
        }
        completeAuthentication(newUser)
    }

    fun verifyAdminChallenge(linuxAnswer: String, yearAnswer: String) {
        val linuxNorm = linuxAnswer.trim().lowercase()
        val yearNorm = yearAnswer.trim()
        val linuxOk = linuxNorm.contains("linus") || linuxNorm.contains("torvalds")
        val yearOk = yearNorm == "2026" || yearNorm == "1991" || yearNorm == "2025"

        if (!linuxOk || !yearOk) {
            _uiState.update {
                it.copy(authError = "Hint: Linus Torvalds & 2026 (or 1991).")
            }
            return
        }

        val admin = _uiState.value.pendingAdminUser ?: initialUsers.last()
        completeAuthentication(admin)
    }

    private fun completeAuthentication(user: PublicUser) {
        val target = when {
            user.isAppAdmin -> AppScreen.AdminDashboard
            user.role == UserRole.Worker -> AppScreen.WorkerDashboard
            else -> AppScreen.Home
        }
        _uiState.update {
            it.copy(
                currentUser = user,
                pendingAdminUser = null,
                currentScreen = target,
                backStack = emptyList(),
                authError = ""
            )
        }
    }

    fun logout() {
        _uiState.update {
            it.copy(
                currentUser = null,
                pendingAdminUser = null,
                currentScreen = AppScreen.Auth,
                backStack = listOf(AppScreen.Splash),
                authError = ""
            )
        }
    }

    fun setCity(city: String) {
        _uiState.update { it.copy(selectedCity = city) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectCategoryAndOpenWorkers(category: String) {
        _uiState.update {
            it.copy(
                selectedCategory = category,
                currentScreen = AppScreen.Workers,
                backStack = it.backStack + it.currentScreen
            )
        }
    }

    fun setWorkerFilter(filter: String) {
        _uiState.update { it.copy(workerFilter = filter) }
    }

    fun toggleSortLowestPrice() {
        _uiState.update { it.copy(sortLowestPrice = !it.sortLowestPrice) }
    }

    fun toggleSavedWorker(workerId: String) {
        _uiState.update { state ->
            val next = if (workerId in state.savedWorkerIds) {
                state.savedWorkerIds - workerId
            } else {
                state.savedWorkerIds + workerId
            }
            state.copy(savedWorkerIds = next)
        }
    }

    fun openWorkerProfile(workerId: String) {
        _uiState.update {
            it.copy(
                selectedWorkerId = workerId,
                currentScreen = AppScreen.Profile,
                backStack = it.backStack + it.currentScreen
            )
        }
    }

    fun openBookingForWorker(workerId: String) {
        _uiState.update {
            it.copy(
                selectedWorkerId = workerId,
                currentScreen = AppScreen.Booking,
                backStack = it.backStack + it.currentScreen
            )
        }
    }

    fun submitBooking(
        details: String,
        rooms: Int,
        dateLabel: String,
        timeSlot: String,
        address: String,
        photoUris: List<String>
    ) {
        val state = _uiState.value
        val worker = state.selectedWorker
        val newId = "b_${System.currentTimeMillis()}"
        val refNumber = (2049 + state.bookings.size)
        val booking = BookingItem(
            id = newId,
            reference = "#HN-$refNumber",
            customerName = state.currentUser?.name ?: "Rupesh Sah",
            workerId = worker.id,
            workerName = worker.name,
            workerRole = worker.role,
            workerImageRes = worker.imageRes,
            service = worker.serviceCategory,
            details = details.ifBlank { "${worker.serviceCategory} service for $rooms room(s)" },
            rooms = rooms,
            dateLabel = dateLabel,
            timeSlot = timeSlot,
            address = address,
            totalPrice = rooms * worker.dailyRate,
            status = "Confirmed",
            photoUris = photoUris
        )
        _uiState.update {
            it.copy(
                bookings = listOf(booking) + it.bookings,
                activeBookingId = newId,
                currentScreen = AppScreen.Confirmed,
                backStack = it.backStack + it.currentScreen
            )
        }
    }

    fun cancelActiveBooking() {
        val activeId = _uiState.value.activeBookingId
        _uiState.update { state ->
            state.copy(
                bookings = state.bookings.map {
                    if (it.id == activeId) it.copy(status = "Cancelled") else it
                }
            )
        }
    }

    fun sendChatMessage(text: String) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return
        val workerId = _uiState.value.selectedWorkerId
        val userMsg = ChatMessage(
            id = "m_${System.currentTimeMillis()}",
            mine = true,
            text = trimmed,
            time = "Now"
        )
        _uiState.update { state ->
            val currentList = state.messagesByWorker[workerId] ?: initialMessages["w1"].orEmpty()
            state.copy(
                messagesByWorker = state.messagesByWorker + (workerId to (currentList + userMsg)),
                isWorkerTyping = true
            )
        }
        viewModelScope.launch {
            delay(900)
            val reply = ChatMessage(
                id = "m_${System.currentTimeMillis()}_reply",
                mine = false,
                text = "Dhanyabad! I have noted that and will bring all required tools on time.",
                time = "Now"
            )
            _uiState.update { state ->
                val currentList = state.messagesByWorker[workerId].orEmpty()
                state.copy(
                    messagesByWorker = state.messagesByWorker + (workerId to (currentList + reply)),
                    isWorkerTyping = false
                )
            }
        }
    }

    fun toggleWorkerAvailability() {
        _uiState.update { it.copy(workerAvailable = !it.workerAvailable) }
    }

    fun verifyWorkerAccount(userId: String) {
        _uiState.update { state ->
            state.copy(
                users = state.users.map { user ->
                    if (user.id == userId) user.copy(verified = true, status = UserStatus.Active)
                    else user
                }
            )
        }
        showToast("Worker verified and activated")
    }

    fun topUpWallet(amount: Int = 1000) {
        _uiState.update { it.copy(walletBalance = it.walletBalance + amount) }
        showToast("NPR %,d added to your Hiré Wallet".format(amount))
    }

    fun showToast(message: String) {
        _uiState.update { it.copy(toastMessage = message) }
        viewModelScope.launch {
            delay(2400)
            _uiState.update { state ->
                if (state.toastMessage == message) state.copy(toastMessage = "") else state
            }
        }
    }
}
