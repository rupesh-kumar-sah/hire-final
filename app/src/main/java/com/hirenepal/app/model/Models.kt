package com.hirenepal.app.model

import androidx.annotation.DrawableRes

enum class AppScreen {
    Splash,
    Auth,
    Register,
    AdminChallenge,
    WorkerDashboard,
    AdminDashboard,
    Home,
    Workers,
    Profile,
    Booking,
    Chat,
    Confirmed,
    Account
}

enum class UserRole(val label: String) {
    Customer("customer"),
    Worker("worker")
}

enum class UserStatus(val label: String) {
    Active("Active"),
    Pending("Pending"),
    Suspended("Suspended")
}

data class PublicUser(
    val id: String,
    val name: String,
    val email: String,
    val phone: String = "+977 9841000000",
    val city: String = "Kathmandu",
    val role: UserRole = UserRole.Customer,
    val status: UserStatus = UserStatus.Active,
    val service: String = "Painting",
    val verified: Boolean = false,
    val isAppAdmin: Boolean = false
)

data class ServiceCategory(
    val name: String,
    val symbol: String,
    val startingPrice: Int,
    val prosCount: Int,
    val accentHex: Long
)

data class WorkerInfo(
    val id: String,
    val name: String,
    val role: String,
    val serviceCategory: String,
    val rating: Double,
    val reviewsCount: Int,
    val yearsExp: String,
    val area: String,
    val distanceKm: String,
    val dailyRate: Int,
    val available: Boolean,
    val verified: Boolean,
    val bio: String,
    val skills: List<String>,
    @DrawableRes val imageRes: Int
)

data class BookingItem(
    val id: String,
    val reference: String,
    val customerName: String,
    val workerId: String,
    val workerName: String,
    val workerRole: String,
    @DrawableRes val workerImageRes: Int,
    val service: String,
    val details: String,
    val rooms: Int,
    val dateLabel: String,
    val timeSlot: String,
    val address: String,
    val totalPrice: Int,
    val status: String,
    val photoUris: List<String> = emptyList()
)

data class ChatMessage(
    val id: String,
    val mine: Boolean,
    val text: String,
    val time: String
)

data class NotificationItem(
    val id: String,
    val title: String,
    val body: String,
    val timeAgo: String,
    val isSuccess: Boolean
)
