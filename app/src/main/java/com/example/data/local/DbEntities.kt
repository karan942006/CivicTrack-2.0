package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "complaints")
data class Complaint(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: String,
    val status: String, // Submitted, Assigned, Under Review, In Progress, Resolved, Closed
    val urgency: String, // Low, Medium, High, Critical
    val department: String,
    val location: String,
    val timestamp: Long = System.currentTimeMillis(),
    val imageBase64: String? = null,
    val rating: Int = 0, // 0 means unrated, 1-5 for stars
    val feedbackText: String? = null,
    val ratingComment: String? = null,   // citizen's text comment on the rating
    val resolvedImageBase64: String? = null,
    val resolutionNote: String? = null,  // worker's written resolution summary
    val workerName: String? = null,
    val workerInfo: String? = null,
    val isTipped: Boolean = false,
    val tipAmount: Double = 0.0,
    val governmentPayout: Double = 0.0,
    val payoutStatus: String? = null
)

@Entity(tableName = "notices")
data class Notice(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val type: String, // Announcement, Tender, Emergency, Alert
    val date: String,
    val summary: String? = null,
    val bookmarked: Boolean = false,
    val targetLocation: String? = "All"
)

@Entity(tableName = "projects")
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val budget: String,
    val contractor: String,
    val progress: Int, // 0 to 100
    val startDate: String,
    val endDate: String,
    val co2Saved: String, // e.g. "45.2 Tons/yr"
    val description: String
)

@Entity(tableName = "polls")
data class Poll(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val optionA: String,
    val optionB: String,
    val votesA: Int,
    val votesB: Int,
    val userVote: String? = null // "A", "B", or null
)

@Entity(tableName = "carbon_metrics")
data class CarbonMetric(
    @PrimaryKey val source: String, // e.g. "Grid Energy", "Transport", "Waste", "Water"
    val co2Value: Double, // in Tons CO2/day
    val percentageChange: Double, // positive or negative
    val totalGoal: Double // target threshold
)

@Entity(tableName = "app_config")
data class AppConfig(
    @PrimaryKey val key: String, // "gemini_key" or "firebase_json"
    val value: String
)
