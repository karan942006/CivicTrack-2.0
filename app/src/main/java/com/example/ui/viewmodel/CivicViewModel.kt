package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.Complaint
import com.example.data.local.Notice
import com.example.data.local.Project
import com.example.data.local.Poll
import com.example.data.repository.CivicRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ChatMessage(
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

sealed interface SubmitState {
    object Idle : SubmitState
    object Submitting : SubmitState
    object Success : SubmitState
    data class Error(val message: String) : SubmitState
}

data class CivicAlert(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val message: String,
    val urgency: String, // Low, Medium, High, Critical
    val timestamp: Long = System.currentTimeMillis(),
    val isEmergency: Boolean = false,
    val roleTarget: String? = null
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val points: Int,
    val iconName: String
)

class CivicViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CivicRepository(application)

    // Flow Streams from DB
    val complaints: StateFlow<List<Complaint>> = repository.complaints
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notices: StateFlow<List<Notice>> = repository.notices
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val projects = repository.projects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val polls = repository.polls
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val carbonMetrics = repository.carbonMetrics
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // App Keys & Configs State
    private val _apiKey = MutableStateFlow("")
    val apiKey: StateFlow<String> = _apiKey.asStateFlow()

    private val _firebaseConfig = MutableStateFlow("")
    val firebaseConfig: StateFlow<String> = _firebaseConfig.asStateFlow()

    // Real-Time Alert Stream
    private val _realTimeAlert = MutableStateFlow<CivicAlert?>(null)
    val realTimeAlert: StateFlow<CivicAlert?> = _realTimeAlert.asStateFlow()

    // Dynamic Citizen Points
    private val _citizenPoints = MutableStateFlow(0)
    val citizenPoints: StateFlow<Int> = _citizenPoints.asStateFlow()

    // Earned Achievements
    private val _earnedAchievements = MutableStateFlow<Set<String>>(emptySet())
    val earnedAchievements: StateFlow<Set<String>> = _earnedAchievements.asStateFlow()

    // List of standard gamified achievements
    val achievementsList = listOf(
        Achievement("eco_sentinel", "Eco Sentinel", "Lodge your first public grievance to clean up Ward 4", 100, "EmojiEvents"),
        Achievement("active_elector", "Active Elector", "Vote in a municipal planning survey/poll", 50, "HowToVote"),
        Achievement("green_dialogue", "Green Dialogue", "Ask the AI Municipal Officer a sustainability question", 30, "ChatBubble"),
        Achievement("five_star_grader", "Five-Star Grader", "Rate a resolved complaint with a 5-star rating", 50, "Verified"),
        Achievement("elder_helper", "Elder Helper", "Use Elder Mode to increase accessibility", 25, "Elderly")
    )

    // Dynamic Worker Points
    private val _workerPoints = MutableStateFlow(0)
    val workerPoints: StateFlow<Int> = _workerPoints.asStateFlow()

    // Earned Worker Achievements
    private val _earnedWorkerAchievements = MutableStateFlow<Set<String>>(emptySet())
    val earnedWorkerAchievements: StateFlow<Set<String>> = _earnedWorkerAchievements.asStateFlow()

    // List of Worker achievements
    val workerAchievementsList = listOf(
        Achievement("first_mission", "First Mission Resolved", "Successfully mark your first assigned complaint as resolved", 100, "EmojiEvents"),
        Achievement("eco_builder", "Eco Builder", "Update progress of a municipal development project", 100, "DirectionsCar"),
        Achievement("gold_service", "Gold Service Worker", "Get a flawless 5-star feedback rating on an issue you resolved", 150, "Verified"),
        Achievement("work_veteran", "Workforce Veteran", "Accumulate more than 250 work experience points", 200, "WorkspacePremium")
    )

    // AI Assistant State
    private val _aiMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                content = "Hello! I am your CivicTrack AI assistant. I can answer questions about Sector 4's municipal services, property taxes, environmental carbon goals, and sustainable projects. How can I assist you today?",
                isUser = false
            )
        )
    )
    val aiMessages: StateFlow<List<ChatMessage>> = _aiMessages.asStateFlow()

    private val _aiLoading = MutableStateFlow(false)
    val aiLoading: StateFlow<Boolean> = _aiLoading.asStateFlow()

    // Complaint Submission State
    private val _submitState = MutableStateFlow<SubmitState>(SubmitState.Idle)
    val submitState: StateFlow<SubmitState> = _submitState.asStateFlow()

    // Notice Summaries Map
    private val _noticeSummaries = MutableStateFlow<Map<Int, String>>(emptyMap())
    val noticeSummaries: StateFlow<Map<Int, String>> = _noticeSummaries.asStateFlow()

    private val _summarizingNoticeId = MutableStateFlow<Int?>(null)
    val summarizingNoticeId: StateFlow<Int?> = _summarizingNoticeId.asStateFlow()

    init {
        viewModelScope.launch {
            // Populate db with initial mock content if database is empty
            repository.prepopulateDataIfEmpty()
            // Load credentials
            _apiKey.value = repository.getApiKey()
            _firebaseConfig.value = repository.getFirebaseConfig()

            // Load citizen points
            val ptsStr = repository.getConfig("citizen_points")
            _citizenPoints.value = ptsStr?.toIntOrNull() ?: 0

            // Load earned achievements
            val achsStr = repository.getConfig("earned_achievements")
            if (!achsStr.isNullOrBlank()) {
                _earnedAchievements.value = achsStr.split(",").toSet()
            }

            // Load worker points
            val wPtsStr = repository.getConfig("worker_points")
            _workerPoints.value = wPtsStr?.toIntOrNull() ?: 0

            // Load earned worker achievements
            val wAchsStr = repository.getConfig("earned_worker_achievements")
            if (!wAchsStr.isNullOrBlank()) {
                _earnedWorkerAchievements.value = wAchsStr.split(",").toSet()
            }
        }
    }

    fun triggerRealTimeAlert(alert: CivicAlert) {
        _realTimeAlert.value = alert
    }

    fun dismissRealTimeAlert() {
        _realTimeAlert.value = null
    }

    fun awardPoints(points: Int) {
        viewModelScope.launch {
            val nextPoints = _citizenPoints.value + points
            _citizenPoints.value = nextPoints
            repository.saveConfig("citizen_points", nextPoints.toString())
        }
    }

    fun unlockAchievement(id: String) {
        if (_earnedAchievements.value.contains(id)) return
        viewModelScope.launch {
            val updated = _earnedAchievements.value + id
            _earnedAchievements.value = updated
            repository.saveConfig("earned_achievements", updated.joinToString(","))
            
            // Auto award points for the achievement
            val ach = achievementsList.find { it.id == id }
            if (ach != null) {
                awardPoints(ach.points)
                // Trigger a real-time celebration alert
                triggerRealTimeAlert(CivicAlert(
                    title = "🏆 Achievement Unlocked: ${ach.title}!",
                    message = "Congratulations! You earned the '${ach.title}' badge and received +${ach.points} Citizen XP!",
                    urgency = "Low",
                    isEmergency = false
                ))
            }
        }
    }

    fun enableElderModeBonus() {
        unlockAchievement("elder_helper")
    }

    fun updateApiKey(key: String) {
        viewModelScope.launch {
            repository.saveApiKey(key)
            _apiKey.value = key
        }
    }

    fun updateFirebaseConfig(json: String) {
        viewModelScope.launch {
            repository.saveFirebaseConfig(json)
            _firebaseConfig.value = json
        }
    }

    fun askAi(question: String) {
        if (question.isBlank()) return
        
        // Append user message
        val userMsg = ChatMessage(content = question, isUser = true)
        _aiMessages.update { it + userMsg }
        _aiLoading.value = true

        viewModelScope.launch {
            val responseText = repository.askAiAssistant(question)
            val aiMsg = ChatMessage(content = responseText, isUser = false)
            _aiMessages.update { it + aiMsg }
            _aiLoading.value = false
            
            // Award achievement for talking to AI
            unlockAchievement("green_dialogue")
        }
    }

    fun clearChat() {
        _aiMessages.value = listOf(
            ChatMessage(
                content = "Hello! I am your CivicTrack AI assistant. I can answer questions about Sector 4's municipal services, property taxes, environmental carbon goals, and sustainable projects. How can I assist you today?",
                isUser = false
            )
        )
    }

    fun submitComplaint(title: String, description: String, imageBase64: String?, categoryOverride: String? = null) {
        if (title.isBlank() || description.isBlank()) {
            _submitState.value = SubmitState.Error("Title and description are required.")
            return
        }

        _submitState.value = SubmitState.Submitting
        viewModelScope.launch {
            try {
                val inserted = repository.insertComplaint(title, description, imageBase64, categoryOverride)
                _submitState.value = SubmitState.Success
                
                // Award points & unlock achievement for lodging a complaint
                unlockAchievement("eco_sentinel")

                // Trigger real-time worker/citizen alert for critical complaints
                if (inserted.urgency == "High" || inserted.urgency == "Critical" || inserted.urgency == "Emergency") {
                    triggerRealTimeAlert(CivicAlert(
                        title = "🚨 EMERGENCY REPORTED: Ward 4 Live Grid",
                        message = "New ${inserted.urgency} priority '${inserted.category}' issue: '${inserted.title}'. Dispatch units and Ward Officers have been notified.",
                        urgency = inserted.urgency,
                        isEmergency = true
                    ))
                } else {
                    // Regular alert
                    triggerRealTimeAlert(CivicAlert(
                        title = "📋 Complaint Lodged successfully",
                        message = "Your grievance regarding '${inserted.category}' is registered on our live civic grid with ID #${inserted.id}.",
                        urgency = "Medium",
                        isEmergency = false
                    ))
                }
            } catch (e: Exception) {
                _submitState.value = SubmitState.Error(e.localizedMessage ?: "Unknown compilation error occurred")
            }
        }
    }

    fun resetSubmitState() {
        _submitState.value = SubmitState.Idle
    }

    fun summarizeNotice(noticeId: Int, content: String) {
        if (_noticeSummaries.value.containsKey(noticeId)) return // Already summarized
        
        _summarizingNoticeId.value = noticeId
        viewModelScope.launch {
            val summary = repository.generateNoticeSummary(noticeId, content)
            _noticeSummaries.update { it + (noticeId to summary) }
            _summarizingNoticeId.value = null
        }
    }

    fun voteInPoll(pollId: Int, option: String) {
        viewModelScope.launch {
            repository.submitVote(pollId, option)
            // Award points and unlock achievement
            unlockAchievement("active_elector")
        }
    }

    fun updateComplaint(complaint: Complaint) {
        viewModelScope.launch {
            repository.updateComplaint(complaint)
            
            // Real-time alert when complaint status updates
            triggerRealTimeAlert(CivicAlert(
                title = "🔄 Grievance Live Status Updated",
                message = "The status of ID #${complaint.id} ('${complaint.title}') has been updated to '${complaint.status}'.",
                urgency = "Low",
                isEmergency = false
            ))
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch {
            repository.updateProject(project)
            
            // Broadcast project telemetry change in real-time
            triggerRealTimeAlert(CivicAlert(
                title = "⚡ Live Project Telemetry Update",
                message = "Development Project '${project.name}' progress updated to ${project.progress}%. Target CO2 mitigation: ${project.co2Saved}.",
                urgency = "Medium",
                isEmergency = false
            ))
        }
    }

    fun publishNotice(title: String, content: String, type: String) {
        viewModelScope.launch {
            repository.insertNotice(Notice(
                title = title,
                content = content,
                type = type,
                date = "Today",
                bookmarked = false
            ))
            
            // Trigger emergency broadcast alerts
            if (type == "Emergency" || type == "Alert") {
                triggerRealTimeAlert(CivicAlert(
                    title = "🚨 EMERGENCY BROADCAST: Municipal Corp",
                    message = "$title: $content",
                    urgency = "Critical",
                    isEmergency = true
                ))
            } else {
                triggerRealTimeAlert(CivicAlert(
                    title = "📢 New Municipal Announcement",
                    message = "'$title' has been officially broadcasted to Ward 4 dashboards.",
                    urgency = "Low",
                    isEmergency = false
                ))
            }
        }
    }
}
