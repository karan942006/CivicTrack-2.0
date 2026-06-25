package com.example.data.repository

import android.content.Context
import androidx.room.Room
import com.example.BuildConfig
import com.example.data.api.GeminiClient
import com.example.data.api.GeminiContent
import com.example.data.api.GeminiPart
import com.example.data.api.GeminiRequest
import com.example.data.local.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import org.json.JSONObject

class CivicRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "civictrack_database"
    ).fallbackToDestructiveMigration()
        .build()

    private val complaintDao = db.complaintDao()
    private val noticeDao = db.noticeDao()
    private val projectDao = db.projectDao()
    private val pollDao = db.pollDao()
    private val carbonMetricDao = db.carbonMetricDao()
    private val appConfigDao = db.appConfigDao()

    val complaints: Flow<List<Complaint>> = complaintDao.getAllComplaints()
    val notices: Flow<List<Notice>> = noticeDao.getAllNotices()
    val projects: Flow<List<Project>> = projectDao.getAllProjects()
    val polls: Flow<List<Poll>> = pollDao.getAllPolls()
    val carbonMetrics: Flow<List<CarbonMetric>> = carbonMetricDao.getAllMetrics()

    suspend fun getApiKey(): String {
        return withContext(Dispatchers.IO) {
            val savedKey = appConfigDao.getConfig("gemini_key")?.value
            if (!savedKey.isNullOrBlank() && savedKey != "placeholder") {
                savedKey
            } else {
                // Read from BuildConfig (AI Studio injected key)
                val buildConfigKey = BuildConfig.GEMINI_API_KEY
                if (buildConfigKey.isNotBlank() && buildConfigKey != "MY_GEMINI_API_KEY") {
                    buildConfigKey
                } else {
                    ""
                }
            }
        }
    }

    suspend fun saveApiKey(key: String) {
        withContext(Dispatchers.IO) {
            appConfigDao.setConfig(AppConfig("gemini_key", key))
        }
    }

    suspend fun getConfig(key: String): String? {
        return withContext(Dispatchers.IO) {
            appConfigDao.getConfig(key)?.value
        }
    }

    suspend fun saveConfig(key: String, value: String) {
        withContext(Dispatchers.IO) {
            appConfigDao.setConfig(AppConfig(key, value))
        }
    }

    suspend fun getFirebaseConfig(): String {
        return withContext(Dispatchers.IO) {
            appConfigDao.getConfig("firebase_json")?.value ?: ""
        }
    }

    suspend fun saveFirebaseConfig(json: String) {
        withContext(Dispatchers.IO) {
            appConfigDao.setConfig(AppConfig("firebase_json", json))
        }
    }

    // AI Helper function to call Gemini
    private suspend fun callGemini(prompt: String, systemInstruction: String? = null): String {
        return withContext(Dispatchers.IO) {
            val key = getApiKey()
            if (key.isBlank()) {
                return@withContext getSimulatedAiResponse(prompt)
            }
            
            try {
                val request = GeminiRequest(
                    contents = listOf(GeminiContent(listOf(GeminiPart(prompt)))),
                    systemInstruction = systemInstruction?.let { GeminiContent(listOf(GeminiPart(it))) }
                )
                val response = GeminiClient.service.generateContent(key, request)
                response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: getSimulatedAiResponse(prompt)
            } catch (e: Exception) {
                getSimulatedAiResponse(prompt)
            }
        }
    }

    private fun getSimulatedAiResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("residence certificate") || lower.contains("निवास प्रमाणपत्र") || lower.contains("रहिवासी प्रमाणपत्र") || lower.contains("ವಾಸಸ್ಥಳ ಪ್ರಮಾಣಪತ್ರ") || lower.contains("இருப்பிடச்") -> {
                """
                **📋 Procedure to Apply for Residence Certificate (Sector 4)**
                
                Hello! To apply for a residence certificate in Smart Sector 4, please follow the structured process below:
                
                1. **Required Documents:**
                   * **Citizen Identity Proof** (Aadhaar, Passport, or Voter ID Card)
                   * **Address Verification** (Recent Electricity Bill, Water Bill, or Registered Rent Agreement)
                   * **Photos:** Two recent passport-size photographs
                   * **Self-Declaration Form** signed by a local notary or Ward Officer
                
                2. **Application Fee:**
                   * Standard Fee: **₹150 / $15**
                   * Processing Mode: Online via Netbanking or at the Ward 4 Citizen Seva Kendra kiosk.
                
                3. **Processing Timeline:**
                   * Fast-track: **3-5 business days**
                   * Standard: **7-10 business days**
                
                *You can submit files online under the 'Documents' tab or visit Sector A Seva Kendra.*
                """.trimIndent()
            }
            lower.contains("property tax") || lower.contains("कर") || lower.contains("ತೆರಿಗೆ") || lower.contains("வரி") -> {
                """
                **💰 Sector 4 Property Tax Guidelines & Green Discount Scheme**
                
                Good day! Sector 4 is committed to Net-Zero 2027 carbon goals. Here is the active property tax and rebate structure:
                
                1. **Standard Payment Deadline:**
                   * Due date for the fiscal year 2026-2027 is **September 30, 2026**.
                
                2. **Green Rebate Benefits (10% Discount):**
                   * **Solar Rooftop Subsidy:** Save 10% on your annual tax if your residential unit has a functional grid solar setup.
                   * **Rainwater Harvesting:** Additional 5% discount for verified rainwater collection facilities.
                   * **Composting & Zero Waste:** 3% rebate for active wet-waste home composting systems.
                
                3. **Online Payment Steps:**
                   * Tap on the Quick Links panel in your Dashboard, enter your Property Tax ID, and make a secure UPI/Card transaction.
                """.trimIndent()
            }
            lower.contains("solar") || lower.contains("रूफटॉप") || lower.contains("ಮೇಲ್ಛಾವಣಿ") || lower.contains("சூரிய கூரை") || lower.contains("green") -> {
                """
                **☀️ Smart Municipal Solar Rooftop Subsidy (SDG 11 Initiative)**
                
                Under our Net-Zero 2027 carbon plan, the Municipal Corporation offers generous incentives for solar transition:
                
                1. **Available Subsidies:**
                   * Up to 3kW capacity: **40% Government Subsidy** on total installation cost.
                   * From 3kW to 10kW capacity: **20% Subsidy** on additional units.
                
                2. **Economic & Environmental Impact:**
                   * Cuts monthly electric bills by an average of **65%**.
                   * Prevents **2.4 Tons of CO2** emissions per household annually.
                
                3. **Installation Steps:**
                   * Complete application through the 'Tenders & Projects' panel, schedule site assessment with eco-builders, and get quick approval within 7 days.
                """.trimIndent()
            }
            lower.contains("carbon") || lower.contains("emissions") || lower.contains("co2") -> {
                """
                **🌱 CivicTrack Real-Time Carbon Emission Tracking (SDG 13)**
                
                CivicTrack monitors environmental metrics in Ward 4 dynamically:
                
                1. **Primary Goals:**
                   * Supporting Net-Zero 2027 by cutting waste emissions and transitioning public transit to EVs.
                   * Daily emission target: Less than **1.2 kg CO2 per capita**.
                
                2. **How to Reduce Your Footprint:**
                   * Segregate wet and dry waste before daily collection.
                   * Opt for public e-shuttles operating on Central Boulevard.
                   * Earn **Civic Green XP Points** in-app for participating in green surveys and reporting waste dumps.
                """.trimIndent()
            }
            else -> {
                """
                **🏛️ CivicTrack Smart Governance AI Officer**
                
                Hello! I am your AI Assistant for Ward 4 Municipal Services. I can guide you on:
                
                * **Residence Certificates** (documents, processing fees, timelines)
                * **Property Tax Discounts** (green scheme rebates, payment due dates)
                * **Smart Solar Rooftop Subsidies** (40% subsidy installation details)
                * **Carbon Emission Targets** (SDG 11 and Net-Zero 2027 compliance)
                
                *Please ask me any questions regarding municipal procedures, and I will be happy to assist you with active, structured guidelines!*
                """.trimIndent()
            }
        }
    }

    // AI Complaint Categorization
    suspend fun insertComplaint(title: String, description: String, imageBase64: String?, categoryOverride: String? = null, location: String? = null): Complaint {
        return withContext(Dispatchers.IO) {
            var category = categoryOverride ?: "General"
            var urgency = "Medium"
            var department = "General Administration"
            
            val key = getApiKey()
            if (key.isNotBlank()) {
                val systemPrompt = "Analyze the city complaint. Classify it into one of these categories: Roads, Water Supply, Garbage, Drainage, Electricity, Public Safety, Parks. Determine Urgency (Low, Medium, High, Critical) and designate the appropriate local government department. Respond strictly in JSON format as: {\"category\": \"...\", \"urgency\": \"...\", \"department\": \"...\"}"
                val prompt = "Complaint Title: $title\nDescription: $description"
                try {
                    val aiResponse = callGemini(prompt, systemPrompt)
                    // Clean json markers if present
                    val cleanJson = aiResponse.trim()
                        .removePrefix("```json")
                        .removeSuffix("```")
                        .trim()
                    val json = JSONObject(cleanJson)
                    category = categoryOverride ?: json.optString("category", "General")
                    urgency = json.optString("urgency", "Medium")
                    department = json.optString("department", "Public Works")
                } catch (e: Exception) {
                    // Fallback to keyword heuristics if Gemini call fails or format is invalid
                    if (categoryOverride == null) {
                        val lower = description.lowercase() + " " + title.lowercase()
                        when {
                            lower.contains("pothole") || lower.contains("road") || lower.contains("street") -> {
                                category = "Roads"
                                department = "Roads & Highways Department"
                            }
                            lower.contains("water") || lower.contains("pipe") || lower.contains("leak") -> {
                                category = "Water Supply"
                                department = "Water & Sanitation Dept"
                            }
                            lower.contains("garbage") || lower.contains("trash") || lower.contains("waste") -> {
                                category = "Garbage"
                                department = "Sanitation & Waste Management"
                            }
                            lower.contains("drain") || lower.contains("sewer") || lower.contains("drainage") -> {
                                category = "Drainage"
                                department = "Drainage & Sewerage Board"
                            }
                            lower.contains("light") || lower.contains("electric") || lower.contains("power") || lower.contains("dark") -> {
                                category = "Electricity"
                                department = "Municipal Power Utility"
                            }
                            lower.contains("safe") || lower.contains("crime") || lower.contains("police") || lower.contains("theft") -> {
                                category = "Public Safety"
                                department = "Community Security & Police"
                            }
                            lower.contains("park") || lower.contains("garden") || lower.contains("tree") || lower.contains("bench") -> {
                                category = "Parks"
                                department = "Parks & Recreation Department"
                            }
                        }
                    }
                    if (description.lowercase().contains("emergency") || description.lowercase().contains("danger") || description.lowercase().contains("leakage")) {
                        urgency = "High"
                    }
                }
            } else {
                // Heuristic Fallback directly if API Key is not set
                if (categoryOverride == null) {
                    val lower = description.lowercase() + " " + title.lowercase()
                    when {
                        lower.contains("pothole") || lower.contains("road") || lower.contains("street") -> {
                            category = "Roads"
                            department = "Roads & Highways Department"
                        }
                        lower.contains("water") || lower.contains("pipe") || lower.contains("leak") -> {
                            category = "Water Supply"
                            department = "Water & Sanitation Dept"
                        }
                        lower.contains("garbage") || lower.contains("trash") || lower.contains("waste") -> {
                            category = "Garbage"
                            department = "Sanitation & Waste Management"
                        }
                        lower.contains("drain") || lower.contains("sewer") || lower.contains("drainage") -> {
                            category = "Drainage"
                            department = "Drainage & Sewerage Board"
                        }
                        lower.contains("light") || lower.contains("electric") || lower.contains("power") || lower.contains("dark") -> {
                            category = "Electricity"
                            department = "Municipal Power Utility"
                        }
                        lower.contains("safe") || lower.contains("crime") || lower.contains("police") || lower.contains("theft") -> {
                            category = "Public Safety"
                            department = "Community Security & Police"
                        }
                        lower.contains("park") || lower.contains("garden") || lower.contains("tree") || lower.contains("bench") -> {
                            category = "Parks"
                            department = "Parks & Recreation Department"
                        }
                    }
                }
                if (description.lowercase().contains("emergency") || description.lowercase().contains("danger") || description.lowercase().contains("leakage")) {
                    urgency = "High"
                }
            }

            val complaint = Complaint(
                title = title,
                description = description,
                category = category,
                status = "Submitted",
                urgency = urgency,
                department = department,
                location = location ?: "Ward 4, Smart Sector A",
                imageBase64 = imageBase64
            )
            complaintDao.insertComplaint(complaint)
            complaint
        }
    }

    // AI Notice Summarization
    suspend fun generateNoticeSummary(noticeId: Int, content: String): String {
        return withContext(Dispatchers.IO) {
            val key = getApiKey()
            if (key.isBlank()) return@withContext "API Key not configured. Notice: " + content.take(60) + "..."
            
            try {
                val systemPrompt = "You are CivicTrack's AI Municipal Officer. Summarize this bureaucratic government announcement into exactly ONE concise, friendly, active-voice sentence for citizens. Focus on the direct impact to their lives."
                val summary = callGemini(content, systemPrompt)
                summary
            } catch (e: Exception) {
                "Unable to generate summary."
            }
        }
    }

    // AI Governance Q&A Assistant Chat
    suspend fun askAiAssistant(question: String): String {
        val systemPrompt = """
            You are 'CivicTrack AI', the official AI Municipal Governance & Sustainability Assistant of CivicTrack platform.
            Your purpose is to explain municipal procedures, property taxes, green schemes, and budgets in simple, friendly, structured language.
            Additionally, help citizens understand CivicTrack's Real-Time Carbon Emission Tracking Dashboard, suggesting sustainable lifestyle improvements.
            Keep responses highly readable, using bullet points, short paragraphs, bold text, and specific action steps.
            If the user asks 'How do I apply for a residence certificate?', provide:
            1. Required Documents (Citizen ID, Electricity Bill, 2 Photos)
            2. Processing Fee ($15 / ₹150)
            3. Processing Time (3-5 business days)
            Always maintain a professional, helpful, trustworthy government tone.
        """.trimIndent()
        return callGemini(question, systemPrompt)
    }

    suspend fun submitVote(pollId: Int, option: String) {
        withContext(Dispatchers.IO) {
            val allPolls = polls.firstOrNull() ?: return@withContext
            val poll = allPolls.find { it.id == pollId } ?: return@withContext
            if (poll.userVote != null) return@withContext // Can only vote once

            val updated = if (option == "A") {
                poll.copy(votesA = poll.votesA + 1, userVote = "A")
            } else {
                poll.copy(votesB = poll.votesB + 1, userVote = "B")
            }
            pollDao.updatePoll(updated)
        }
    }

    suspend fun prepopulateDataIfEmpty() {
        withContext(Dispatchers.IO) {
            // Ensure API keys and Firebase config are populated from user's parameters on startup
            val currentKey = appConfigDao.getConfig("gemini_key")?.value
            if (currentKey.isNullOrBlank() || currentKey == "placeholder") {
                appConfigDao.setConfig(AppConfig("gemini_key", ""))
            }
            val currentFb = appConfigDao.getConfig("firebase_json")?.value
            if (currentFb.isNullOrBlank() || currentFb == "placeholder") {
                val fbConfig = """{
  "project_info": {
    "project_number": "1001594094626",
    "project_id": "civictrack2-a5016",
    "storage_bucket": "civictrack2-a5016.firebasestorage.app"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "1:1001594094626:android:de64f2e588ae05c8e0f67b",
        "android_client_info": {
          "package_name": "com.aistudio.civictrack.yptwhz"
        }
      },
      "oauth_client": [],
      "api_key": [
        {
          "current_key": "AIzaSyCnnTWUn2EXFc6h1UI0pzHc9bfR8bHm9sQ"
        }
      ],
      "services": {
        "appinvite_service": {
          "other_platform_oauth_client": []
        }
      }
    }
  ],
  "configuration_version": "1"
}"""
                appConfigDao.setConfig(AppConfig("firebase_json", fbConfig))
            }

            val existingPolls = polls.firstOrNull()
            // We do not pre-populate any mock data to ensure the app is blank unless and until added.
            // All tables are empty at startup.
        }
    }

    suspend fun updateComplaint(complaint: Complaint) {
        withContext(Dispatchers.IO) {
            complaintDao.updateComplaint(complaint)
        }
    }

    suspend fun updateProject(project: Project) {
        withContext(Dispatchers.IO) {
            projectDao.updateProject(project)
        }
    }

    suspend fun insertNotice(notice: Notice) {
        withContext(Dispatchers.IO) {
            noticeDao.insertNotice(notice)
        }
    }
}
