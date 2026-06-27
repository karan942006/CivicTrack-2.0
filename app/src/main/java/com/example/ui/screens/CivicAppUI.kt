package com.example.ui.screens



import android.widget.Toast

import androidx.compose.animation.*

import androidx.compose.animation.core.*

import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.foundation.background

import androidx.compose.foundation.border

import androidx.compose.foundation.clickable

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.layout.ExperimentalLayoutApi

import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.text.KeyboardActions

import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.*

import androidx.compose.material.icons.outlined.*

import androidx.compose.material.icons.rounded.*

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip

import androidx.compose.ui.draw.shadow

import androidx.compose.ui.draw.rotate

import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.platform.testTag

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.text.input.PasswordVisualTransformation

import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.data.local.CarbonMetric

import com.example.data.local.Complaint

import com.example.data.local.Notice

import com.example.data.local.Poll

import com.example.data.local.Project

import com.example.ui.theme.MyApplicationTheme

import com.example.ui.viewmodel.ChatMessage

import com.example.ui.viewmodel.CivicViewModel

import com.example.ui.viewmodel.SubmitState

import kotlinx.coroutines.delay

import kotlinx.coroutines.launch

import android.graphics.Bitmap

import android.graphics.BitmapFactory

import android.graphics.Canvas

import android.graphics.Paint

import android.util.Base64

import android.webkit.JavascriptInterface

import android.webkit.WebView

import android.webkit.WebViewClient

import androidx.compose.ui.graphics.ImageBitmap

import androidx.compose.ui.graphics.asImageBitmap

import androidx.compose.ui.viewinterop.AndroidView



enum class Language(val displayName: String) {

    ENGLISH("English"),

    HINDI("हिंदी"),

    MARATHI("मराठी"),

    KANNADA("ಕನ್ನಡ"),

    TAMIL("தமிழ்")

}



data class SuggestionItem(

    val id: Int,

    val title: String,

    val description: String,

    val votes: Int,

    val funded: Boolean = false

)



data class EmergencyAlertItem(

    val id: Int,

    val title: String,

    val type: String, // Fire, Ambulance, Flood, Earthquake, Police, Road Accident

    val urgency: String, // Low, Medium, High, Critical

    val status: String // Awaiting Dispatch, Units Dispatched!

)



data class CertificateApplication(

    val id: String,

    val type: String, // Birth Certificate, Death Certificate, Residence Certificate, Character Certificate, Income Certificate

    val applicantName: String,

    val details: String,

    val status: String, // Pending Approval, Approved

    val trackingNo: String

)



object Localization {

    val dictionary = mapOf(

        Language.ENGLISH to mapOf(

            "app_name" to "CivicTrack",

            "dashboard" to "Dashboard",

            "ai_officer" to "AI Officer",

            "complaints" to "Complaints",

            "transparency" to "Tenders & Projects",

            "polls" to "Polls",

            "services" to "Services",

            "login" to "Login",

            "logout" to "Logout",

            "role" to "ROLE",

            "carbon_index" to "Sector 4 Carbon Emission Index",

            "co2_saved" to "CO2 Saved",

            "target" to "Target",

            "trend" to "Trend",

            "recent_notices" to "Municipal Notices & Emergency Alerts",

            "active_projects" to "Active Development Projects",

            "democratic_polls" to "Participatory Planning Polls",

            "file_grievance" to "File a Municipal Grievance",

            "title" to "Title",

            "description" to "Description",

            "category" to "Category",

            "urgency" to "Urgency",

            "submit" to "Submit",

            "submitting" to "Submitting...",

            "success" to "Success",

            "error" to "Error",

            "emergency_sos" to "EMERGENCY SOS",

            "ask_ai" to "Ask AI Officer",

            "type_question" to "Type your question here...",

            "phone_number" to "Mobile Number & OTP",

            "email" to "Email Address",

            "password" to "Password",

            "switch_to_phone" to "Switch to Phone OTP",

            "switch_to_email" to "Switch to Email & Password",

            "select_role" to "Select Portal Access Role",

            "notice_summary" to "AI Notice Summary",

            "budget" to "Budget",

            "contractor" to "Contractor",

            "progress" to "Progress",

            "timeline" to "Timeline",

            "total_votes" to "Total Votes",

            "voted" to "Voted",

            "vote_button" to "Cast Vote",

            "carbon_warning" to "Daily carbon threshold reduction is in progress to support Net-Zero 2027.",

            "sos_triggered" to "SOS TRIGGERED",

            "sos_sub_text" to "Pressing this immediately notifies municipal emergency response.",

            "setup_title" to "CivicTrack Setup & APIs",

            "setup_desc" to "Configure runtime secrets. These are securely saved locally on this device.",

            "api_key_label" to "Gemini AI API Key",

            "firebase_label" to "Firebase configuration (JSON String)"

        ),

        Language.HINDI to mapOf(

            "app_name" to "सिविकट्रैक",

            "dashboard" to "डैशबोर्ड",

            "ai_officer" to "एआई अधिकारी",

            "complaints" to "शिकायतें",

            "transparency" to "निविदाएं और परियोजनाएं",

            "polls" to "मतदान",

            "services" to "सेवाएं",

            "login" to "लॉगिन",

            "logout" to "लॉगआउट",

            "role" to "भूमिका",

            "carbon_index" to "सेक्टर 4 कार्बन उत्सर्जन सूचकांक",

            "co2_saved" to "बचाया गया CO2",

            "target" to "लक्ष्य",

            "trend" to "प्रवृत्ति",

            "recent_notices" to "नगर पालिका सूचनाएं और आपातकालीन अलर्ट",

            "active_projects" to "सक्रिय विकास परियोजनाएं",

            "democratic_polls" to "सहभागी योजना मतदान",

            "file_grievance" to "नगरपालिका शिकायत दर्ज करें",

            "title" to "शीर्षक",

            "description" to "विवरण",

            "category" to "श्रेणी",

            "urgency" to "तात्कालिकता",

            "submit" to "जमा करें",

            "submitting" to "जमा किया जा रहा है...",

            "success" to "सफलता",

            "error" to "त्रुटि",

            "emergency_sos" to "आपातकालीन एसओएस",

            "ask_ai" to "एआई अधिकारी से पूछें",

            "type_question" to "अपना प्रश्न यहाँ टाइप करें...",

            "phone_number" to "मोबाइल नंबर और ओटीपी",

            "email" to "ईमेल पता",

            "password" to "पासवर्ड",

            "switch_to_phone" to "फोन ओटीपी पर जाएं",

            "switch_to_email" to "ईमेल और पासवर्ड पर जाएं",

            "select_role" to "पोर्टल एक्सेस भूमिका चुनें",

            "notice_summary" to "एआई नोटिस सारांश",

            "budget" to "बजट",

            "contractor" to "ठेकेदार",

            "progress" to "प्रगति",

            "timeline" to "समय सीमा",

            "total_votes" to "कुल वोट",

            "voted" to "वोट दिया",

            "vote_button" to "वोट डालें",

            "carbon_warning" to "नेट-ज़ीरो 2027 का समर्थन करने के लिए दैनिक कार्बन सीमा में कमी प्रगति पर है।",

            "sos_triggered" to "एसओएस ट्रिगर",

            "sos_sub_text" to "इसे दबाने से तुरंत नगर पालिका आपातकालीन प्रतिक्रिया को सूचित किया जाता है।",

            "setup_title" to "सिविकट्रैक सेटअप और एपीआई",

            "setup_desc" to "रनटाइम रहस्यों को कॉन्फ़िगर करें। ये इस डिवाइस पर स्थानीय रूप से सुरक्षित रूप से सहेजे गए हैं।",

            "api_key_label" to "जेमिनी एआई एपीआई कुंजी",

            "firebase_label" to "फायरबेस कॉन्फ़िगरेशन (JSON स्ट्रिंग)"

        ),

        Language.MARATHI to mapOf(

            "app_name" to "सिविकट्रॅक",

            "dashboard" to "डॅशबोर्ड",

            "ai_officer" to "एआय अधिकारी",

            "complaints" to "तक्रारी",

            "transparency" to "निविदा आणि प्रकल्प",

            "polls" to "मतदान",

            "services" to "सेवा",

            "login" to "लॉगिन",

            "logout" to "लॉगआउट",

            "role" to "भूमिका",

            "carbon_index" to "सेक्टर ४ कार्बन उत्सर्जन निर्देशांक",

            "co2_saved" to "बचत केलेले CO2",

            "target" to "लक्ष्य",

            "trend" to "कल",

            "recent_notices" to "महानगरपालिका सूचना आणि आपत्कालीन इशारे",

            "active_projects" to "सक्रिय विकास प्रकल्प",

            "democratic_polls" to "सहभागी नियोजन मतदान",

            "file_grievance" to "महानगरपालिका तक्रार नोंदवा",

            "title" to "शीर्षक",

            "description" to "वर्णन",

            "category" to "वर्ग",

            "urgency" to "तातडी",

            "submit" to "सादर करा",

            "submitting" to "सादर करत आहे...",

            "success" to "यश",

            "error" to "त्रुटी",

            "emergency_sos" to "आपातकालीन एसओएस",

            "ask_ai" to "एआय अधिकाऱ्याला विचारा",

            "type_question" to "तुमचा प्रश्न येथे टाईप करा...",

            "phone_number" to "मोबाईल नंबर आणि ओटीपी",

            "email" to "ईमेल पत्ता",

            "password" to "पासवर्ड",

            "switch_to_phone" to "फोन ओटीपीवर जा",

            "switch_to_email" to "ईमेल आणि पासवर्डवर जा",

            "select_role" to "पोर्टल प्रवेश भूमिका निवडा",

            "notice_summary" to "एआय नोटीस सारांश",

            "budget" to "बजेट",

            "contractor" to "कंत्राटदार",

            "progress" to "प्रगती",

            "timeline" to "वेळापत्रक",

            "total_votes" to "एकूण मते",

            "voted" to "मतदान केले",

            "vote_button" to "मत नोंदवा",

            "carbon_warning" to "नेट-झिरो २०२७ ला पाठिंबा देण्यासाठी दैनिक कार्बन मर्यादा कमी करण्याचे काम सुरू आहे.",

            "sos_triggered" to "एसओएस ट्रिगर झाला",

            "sos_sub_text" to "हे दाबल्यास तात्काळ मनपा आपत्कालीन प्रतिसाद यंत्रणेला सूचित केले जाते.",

            "setup_title" to "सिविकट्रॅक सेटअप आणि एपीआय",

            "setup_desc" to "रनटाइम गुपिते कॉन्फिगर करा. हे या उपकरणावर सुरक्षितपणे सेव्ह केले आहेत.",

            "api_key_label" to "जेमिनी एआई एपीआई की",

            "firebase_label" to "फायरबेस कॉन्फिगरेशन (JSON स्ट्रिंग)"

        ),

        Language.KANNADA to mapOf(

            "app_name" to "ಸಿವಿಕ್ ಟ್ರ್ಯಾಕ್",

            "dashboard" to "ಡ್ಯಾಶ್‌ಬೋರ್ಡ್",

            "ai_officer" to "ಎಐ ಅಧಿಕಾರಿ",

            "complaints" to "ದೂರುಗಳು",

            "transparency" to "ಟೆಂಡರ್‌ಗಳು ಮತ್ತು ಯೋಜನೆಗಳು",

            "polls" to "ಮತದಾನ",

            "services" to "ಸೇವೆಗಳು",

            "login" to "ಲಾಗಿನ್",

            "logout" to "ಲಾಗ್ಔಟ್",

            "role" to "ಪಾತ್ರ",

            "carbon_index" to "ಸೆಕ್ಟರ್ 4 ಕಾರ್ಬನ್ ಹೊರಸೂಸುವಿಕೆ ಸೂಚ್ಯಂಕ",

            "co2_saved" to "ಉಳಿಸಿದ CO2",

            "target" to "ಗುರಿ",

            "trend" to "ಪ್ರವೃತ್ತಿ",

            "recent_notices" to "ಪುರಸಭೆಯ ಸೂಚನೆಗಳು ಮತ್ತು ತುರ್ತು ಎಚ್ಚರಿಕೆಗಳು",

            "active_projects" to "ಸಕ್ರಿಯ ಅಭಿವೃದ್ಧಿ ಯೋಜನೆಗಳು",

            "democratic_polls" to "ಭಾಗವಹಿಸುವಿಕೆಯ ಯೋಜನೆ ಮತದಾನ",

            "file_grievance" to "ಪುರಸಭೆಯ ದೂರು ದಾಖಲಿಸಿ",

            "title" to "ಶೀರ್ಷಿಕೆ",

            "description" to "ವಿವರಣೆ",

            "category" to "ವರ್ಗ",

            "urgency" to "ತುರ್ತು",

            "submit" to "ಸಲ್ಲಿಸಿ",

            "submitting" to "ಸಲ್ಲಿಸಲಾಗುತ್ತಿದೆ...",

            "success" to "ಯಶಸ್ಸು",

            "error" to "ದೋಷ",

            "emergency_sos" to "ತುರ್ತು ಎಸ್ಒಎಸ್",

            "ask_ai" to "ಎಐ ಅಧಿಕಾರಿಯನ್ನು ಕೇಳಿ",

            "type_question" to "ನಿಮ್ಮ ಪ್ರಶ್ನೆಯನ್ನು ಇಲ್ಲಿ ಟೈಪ್ ಮಾಡಿ...",

            "phone_number" to "ಮೊಬೈಲ್ ಸಂಖ್ಯೆ ಮತ್ತು ಒಟಿಪಿ",

            "email" to "ಇಮೇಲ್ ವಿಳಾಸ",

            "password" to "ಪಾಸ್ವರ್ಡ್",

            "switch_to_phone" to "ಫೋನ್ ಒಟಿಪಿಗೆ ಬದಲಾಯಿಸಿ",

            "switch_to_email" to "ಇಮೇಲ್ ಮತ್ತು ಪಾಸ್ವರ್ಡ್ಗೆ ಬದಲಾಯಿಸಿ",

            "select_role" to "ಪೋರ್ಟಲ್ ಪ್ರವೇಶ ಪಾತ್ರವನ್ನು ಆಯ್ಕೆಮಾಡಿ",

            "notice_summary" to "ಎಐ ನೋಟಿಸ್ ಸಾರಾಂಶ",

            "budget" to "ಬಜೆಟ್",

            "contractor" to "ಗುತ್ತಿಗೆದಾರ",

            "progress" to "ಪ್ರಗತಿ",

            "timeline" to "ಸಮಯದ ಮಿತಿ",

            "total_votes" to "ಒಟ್ಟು ಮತಗಳು",

            "voted" to "ಮತ ಚಲಾಯಿಸಲಾಗಿದೆ",

            "vote_button" to "ಮತ ಚಲಾಯಿಸಿ",

            "carbon_warning" to "ನೆಟ್-ಶೂನ್ಯ 2027 ಅನ್ನು ಬೆಂಬಲಿಸಲು ದೈನಂದิน ಕಾರ್ಬನ್ ಮಿತಿ ಕಡಿತವು ಪ್ರಗತಿಯಲ್ಲಿದೆ.",

            "sos_triggered" to "ಎಸ್ಒಎಸ್ ಪ್ರಚೋದಿಸಿದೆ",

            "sos_sub_text" to "ಇದನ್ನು ಒತ್ತುವುದರಿಂದ ತಕ್ಷಣವೇ ಪುರಸಭೆಯ ತುರ್ತು ಪ್ರತಿಕ್ರಿಯೆಗೆ ಸೂಚನೆ ನೀಡಲಾಗುತ್ತದೆ.",

            "setup_title" to "ಸಿವಿಕ್ ಟ್ರ್ಯಾಕ್ ಸೆಟಪ್ ಮತ್ತು ಎಪಿಐಗಳು",

            "setup_desc" to "ರನ್‌ಟೈಮ್ ರಹಸ್ಯಗಳನ್ನು ಕಾನ್ಫಿಗರ್ ಮಾಡಿ. ಇವುಗಳನ್ನು ಈ ಸಾಧನದಲ್ಲಿ ಸ್ಥಳীয়ವಾಗಿ ಸುರಕ್ಷಿತವಾಗಿ ಉಳಿಸಲಾಗಿದೆ.",

            "api_key_label" to "ಜೆಮಿನಿ ಎಐ ಎಪಿಐ ಕೀ",

            "firebase_label" to "ಫೈರ್ಬೇಸ್ ಕಾನ್ಫಿಗರೇಶನ್ (JSON ಸ್ಟ್ರಿಂಗ್)"

        ),

        Language.TAMIL to mapOf(

            "app_name" to "சிவிக்ட்ராக்",

            "dashboard" to "டாஷ்போர்டு",

            "ai_officer" to "ஏஐ அதிகாரி",

            "complaints" to "புகார்கள்",

            "transparency" to "டெண்டர்கள் மற்றும் திட்டங்கள்",

            "polls" to "வாக்கெடுப்பு",

            "services" to "சேவைகள்",

            "login" to "உள்நுழை",

            "logout" to "வெளியேறு",

            "role" to "பங்கு",

            "carbon_index" to "செக்டர் 4 கார்பன் உமிழ்வு குறியீடு",

            "co2_saved" to "சேமிக்கப்பட்ட CO2",

            "target" to "இலக்கு",

            "trend" to "போக்கு",

            "recent_notices" to "நகராட்சி அறிவிப்புகள் மற்றும் அவசர எச்சரிக்கைகள்",

            "active_projects" to "செயலில் உள்ள வளர்ச்சி திட்டங்கள்",

            "democratic_polls" to "பங்கேற்பு திட்டமிடல் வாக்கெடுப்புகள்",

            "file_grievance" to "நகாரட்சி புகார் பதிவு செய்யவும்",

            "title" to "தலைப்பு",

            "description" to "விளக்கம்",

            "category" to "வகை",

            "urgency" to "அவசியம்",

            "submit" to "சமர்ப்பி",

            "submitting" to "சமர்ப்பிக்கப்படுகிறது...",

            "success" to "வெற்றி",

            "error" to "பிழை",

            "emergency_sos" to "அவசர எஸ்ஓஎஸ்",

            "ask_ai" to "ஏஐ அதிகாரியிடம் கேளுங்கள்",

            "type_question" to "உங்கள் கேள்வியை இங்கே தட்டச்சு செய்யவும்...",

            "phone_number" to "மொபைல் எண் மற்றும் OTP",

            "email" to "மின்னஞ்சல் முகவரி",

            "password" to "கடவுச்சொல்",

            "switch_to_phone" to "தொலைபேசி OTP-க்கு மாறவும்",

            "switch_to_email" to "மின்னஞ்சல் மற்றும் கடவுச்சொல்லுக்கு மாறவும்",

            "select_role" to "போர்டல் அணுகல் பாத்திரத்தைத் தேர்ந்தெடுக்கவும்",

            "notice_summary" to "ஏஐ அறிவிப்பு சுருக்கம்",

            "budget" to "பட்ஜெட்",

            "contractor" to "ஒப்பந்தக்காரர்",

            "progress" to "முன்னேற்றம்",

            "timeline" to "காலக்கெடு",

            "total_votes" to "மொத்த வாக்குகள்",

            "voted" to "வாக்களித்தேன்",

            "vote_button" to "வாக்களிக்கவும்",

            "carbon_warning" to "நெட்-ஜீரோ 2027-ஐ ஆதரிக்க தினசரி கார்பன் வரம்பு குறைப்பு செயல்பாட்டில் உள்ளது.",

            "sos_triggered" to "எஸ்ஓஎஸ் தூண்டப்பட்டது",

            "sos_sub_text" to "இதை அழுத்தினால் உடனடியாக நகராட்சி அவசர உதவிக்கு தகவல் அனுப்பப்படும்.",

            "setup_title" to "சிவிக்ட்ராக் அமைப்பு மற்றும் ஏபிஐக்கள்",

            "setup_desc" to "ரன்டைம் ரகசியங்களை உள்ளமைக்கவும். இவை சாதனத்தில் உள்ளூரில் பாதுகாப்பாக சேமிக்கப்படுகின்றன.",

            "api_key_label" to "ஜெமினி ஏஐ ஏபிஐ விசை",

            "firebase_label" to "ஃபயர்பேஸ் உள்ளமைவு (JSON சரம்)"

        )

    )



    fun translate(key: String, lang: Language): String {

        return dictionary[lang]?.get(key) ?: dictionary[Language.ENGLISH]?.get(key) ?: key

    }

}



// Screen Enumeration

enum class NavItem(val title: String) {

    DASHBOARD("Dashboard"),

    AI_ASSISTANT("AI Officer"),

    COMPLAINTS("Complaints"),

    TRANSPARENCY("Tenders & Projects"),

    SERVICES("Services"),

    COMMUNITY("Polls")

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun CivicAppUI(

    viewModel: CivicViewModel = viewModel()

) {

    var isLoggedIn by remember { mutableStateOf(false) }

    var currentRole by remember { mutableStateOf("Citizen") } // Role-based Simulation

    var selectedLanguage by remember { mutableStateOf(Language.ENGLISH) }

    var isElderMode by remember { mutableStateOf(false) }

    var showSetupSheet by remember { mutableStateOf(false) }

    val context = androidx.compose.ui.platform.LocalContext.current

    

    var sharedSuggestions by remember { mutableStateOf(listOf(

        SuggestionItem(1, "Senior Community Center Ramp", "Install wheelchair ramp at the community center entrance", 14),

        SuggestionItem(2, "Hybrid Solar Streetlights", "Install automatic solar streetlights on Sector 4 Main Road", 28)

    )) }



    var sharedEmergencyAlerts by remember { mutableStateOf(listOf(

        EmergencyAlertItem(1, "Fire at Block B", "Fire", "Critical", "Awaiting Dispatch"),

        EmergencyAlertItem(2, "Accident on Main St", "Road Accident", "High", "Awaiting Dispatch")

    )) }



    var sharedCertificates by remember { mutableStateOf(listOf(

        CertificateApplication("BC-9923", "Birth Certificate", "Aarav Kumar Sharma", "Father: Rajesh Sharma, Mother: Sunita Sharma, DOB: 12/04/2026", "Pending Approval", "TXN100234"),

        CertificateApplication("IC-1048", "Income Certificate", "Rekha Devi", "Annual Income: Rs. 1,80,000, Occupation: Agriculture", "Pending Approval", "TXN100456")

    )) }



    val apiKey by viewModel.apiKey.collectAsState()

    val firebaseConfig by viewModel.firebaseConfig.collectAsState()



    MyApplicationTheme {

        Box(modifier = Modifier.fillMaxSize()) {

            if (!isLoggedIn) {

                SplashLoginScreen(

                    onLoginSuccess = { role ->

                        currentRole = role

                        isLoggedIn = true

                    },

                    onOpenSetup = {},

                    apiKey = apiKey,

                    firebaseConfig = firebaseConfig,

                    selectedLanguage = selectedLanguage,

                    onLanguageChange = { selectedLanguage = it },

                    isElderMode = isElderMode,

                    onElderModeChange = { 

                        isElderMode = it

                        if (it) viewModel.enableElderModeBonus()

                    }

                )

            } else {

                MainWorkspace(

                    currentRole = currentRole,

                    onChangeRole = { currentRole = it },

                    onLogout = { isLoggedIn = false },

                    onOpenSetup = {},

                    viewModel = viewModel,

                    selectedLanguage = selectedLanguage,

                    onLanguageChange = { selectedLanguage = it },

                    isElderMode = isElderMode,

                    onElderModeChange = { 

                        isElderMode = it

                        if (it) viewModel.enableElderModeBonus()

                    },

                    sharedSuggestions = sharedSuggestions,

                    onUpdateSuggestions = { sharedSuggestions = it },

                    sharedEmergencyAlerts = sharedEmergencyAlerts,

                    onUpdateEmergencyAlerts = { sharedEmergencyAlerts = it },

                    sharedCertificates = sharedCertificates,

                    onUpdateCertificates = { sharedCertificates = it }

                )

            }

        }

    }

}



@Composable

fun <T> IosSegmentedControl(

    items: List<T>,

    selectedItem: T,

    onItemSelected: (T) -> Unit,

    labelProvider: (T) -> String,

    modifier: Modifier = Modifier

) {

    Row(

        modifier = modifier

            .fillMaxWidth()

            .clip(RoundedCornerShape(10.dp))

            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f))

            .padding(2.dp),

        horizontalArrangement = Arrangement.Center,

        verticalAlignment = Alignment.CenterVertically

    ) {

        items.forEach { item ->

            val isSelected = item == selectedItem

            Box(

                modifier = Modifier

                    .weight(1f)

                    .clip(RoundedCornerShape(8.dp))

                    .background(if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent)

                    .clickable { onItemSelected(item) }

                    .padding(vertical = 8.dp, horizontal = 4.dp),

                contentAlignment = Alignment.Center

            ) {

                Text(

                    text = labelProvider(item),

                    fontSize = 11.sp,

                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,

                    color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),

                    maxLines = 1,

                    textAlign = TextAlign.Center

                )

            }

        }

    }

}



// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun SplashLoginScreen(

    onLoginSuccess: (String) -> Unit,

    onOpenSetup: () -> Unit,

    apiKey: String,

    firebaseConfig: String,

    selectedLanguage: Language = Language.ENGLISH,

    onLanguageChange: (Language) -> Unit = {},

    isElderMode: Boolean = false,

    onElderModeChange: (Boolean) -> Unit = {}

) {

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var phoneOtp by remember { mutableStateOf("") }

    var otpCode by remember { mutableStateOf("") }

    var isOtpSent by remember { mutableStateOf(false) }

    var isPhoneMode by remember { mutableStateOf(false) }

    var selectedRole by remember { mutableStateOf("Citizen") }

    

    var emailError by remember { mutableStateOf<String?>(null) }

    var passwordError by remember { mutableStateOf<String?>(null) }

    var phoneError by remember { mutableStateOf<String?>(null) }

    var otpError by remember { mutableStateOf<String?>(null) }



    val context = LocalContext.current

    val isDark = isSystemInDarkTheme()



    val rolesList = listOf("Citizen", "Worker", "Admin")



    Box(

        modifier = Modifier

            .fillMaxSize()

            .background(MaterialTheme.colorScheme.background)

            .padding(20.dp),

        contentAlignment = Alignment.Center

    ) {

        Column(

            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier

                .fillMaxWidth()

                .verticalScroll(rememberScrollState())

                .padding(bottom = 24.dp)

        ) {

            // Language Selection using custom Segmented Control

            Text(

                text = "Language / भाषा / ಕನ್ನಡ / தமிழ்",

                style = MaterialTheme.typography.labelSmall,

                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),

                fontWeight = FontWeight.Bold,

                modifier = Modifier.padding(bottom = 6.dp)

            )

            IosSegmentedControl(

                items = Language.values().toList(),

                selectedItem = selectedLanguage,

                onItemSelected = onLanguageChange,

                labelProvider = { it.displayName },

                modifier = Modifier.padding(bottom = 16.dp)

            )



            // App Branded Logo (Sleek minimalist iOS Icon)

            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = com.example.R.mipmap.ic_launcher),
                contentDescription = "CivicTrack Logo",
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(1.dp, RoundedCornerShape(16.dp))
            )



            Spacer(modifier = Modifier.height(12.dp))



            Text(

                text = "CivicTrack",

                style = MaterialTheme.typography.titleLarge.copy(

                    fontWeight = FontWeight.Bold,

                    fontSize = 24.sp

                ),

                color = MaterialTheme.colorScheme.onSurface

            )



            Text(

                text = "AI-Powered Smart City Platform",

                style = MaterialTheme.typography.bodyMedium,

                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),

                textAlign = TextAlign.Center

            )



            Spacer(modifier = Modifier.height(24.dp))



            // Login Panel (Clean iOS White/Black Rounded Card)

            Card(

                modifier = Modifier

                    .fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),

                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),

                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),

                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)

            ) {

                Column(

                    modifier = Modifier.padding(20.dp),

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Text(

                        text = if (isPhoneMode) Localization.translate("phone_number", selectedLanguage) else "Secure Email Login",

                        style = MaterialTheme.typography.titleMedium,

                        fontWeight = FontWeight.Bold,

                        color = MaterialTheme.colorScheme.onSurface

                    )



                    Spacer(modifier = Modifier.height(16.dp))



                    if (isPhoneMode) {

                        OutlinedTextField(

                            value = phoneOtp,

                            onValueChange = { 

                                phoneOtp = it

                                phoneError = null

                            },

                            label = { Text(Localization.translate("phone_number", selectedLanguage)) },

                            placeholder = { Text("+91 98765 43210") },

                            leadingIcon = { Icon(Icons.Rounded.Phone, contentDescription = "Phone") },

                            enabled = !isOtpSent,

                            isError = phoneError != null,

                            colors = OutlinedTextFieldDefaults.colors(

                                focusedTextColor = MaterialTheme.colorScheme.onSurface,

                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

                                focusedBorderColor = Color(0xFF007AFF),

                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),

                                focusedContainerColor = Color.Transparent,

                                unfocusedContainerColor = Color.Transparent

                            ),

                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),

                            modifier = Modifier.fillMaxWidth().testTag("phone_input")

                        )

                        if (phoneError != null) {

                            Text(

                                text = phoneError ?: "",

                                color = MaterialTheme.colorScheme.error,

                                style = MaterialTheme.typography.labelSmall,

                                modifier = Modifier.align(Alignment.Start).padding(top = 4.dp, start = 8.dp)

                            )

                        }



                        if (!isOtpSent) {

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(

                                onClick = {

                                    if (phoneOtp.length < 10) {

                                        phoneError = "Please enter a valid 10-digit phone number"

                                    } else {

                                        phoneError = null

                                        isOtpSent = true

                                        Toast.makeText(context, "OTP sent! Use test code: 123456", Toast.LENGTH_LONG).show()

                                    }

                                },

                                modifier = Modifier.fillMaxWidth().height(44.dp),

                                shape = RoundedCornerShape(10.dp),

                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34C759))

                            ) {

                                Text("Send Verification OTP", fontWeight = FontWeight.SemiBold)

                            }

                        } else {

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(

                                value = otpCode,

                                onValueChange = { 

                                    otpCode = it

                                    otpError = null

                                },

                                label = { Text("Enter 6-Digit OTP") },

                                placeholder = { Text("123456") },

                                leadingIcon = { Icon(Icons.Rounded.Pin, contentDescription = "OTP") },

                                isError = otpError != null,

                                colors = OutlinedTextFieldDefaults.colors(

                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,

                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

                                    focusedBorderColor = Color(0xFF007AFF),

                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),

                                    focusedContainerColor = Color.Transparent,

                                    unfocusedContainerColor = Color.Transparent

                                ),

                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                                modifier = Modifier.fillMaxWidth().testTag("otp_input")

                            )

                            if (otpError != null) {

                                Text(

                                    text = otpError ?: "",

                                    color = MaterialTheme.colorScheme.error,

                                    style = MaterialTheme.typography.labelSmall,

                                    modifier = Modifier.align(Alignment.Start).padding(top = 4.dp, start = 8.dp)

                                )

                            }

                            

                            TextButton(

                                onClick = { isOtpSent = false },

                                modifier = Modifier.align(Alignment.End)

                            ) {

                                Text("Edit Phone Number", color = Color(0xFF007AFF), fontSize = 11.sp, fontWeight = FontWeight.Bold)

                            }

                        }

                    } else {

                        OutlinedTextField(

                            value = email,

                            onValueChange = { 

                                email = it

                                emailError = null

                            },

                            label = { Text(Localization.translate("email", selectedLanguage)) },

                            placeholder = { Text("citizen@civictrack.org") },

                            leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = "Email") },

                            isError = emailError != null,

                            colors = OutlinedTextFieldDefaults.colors(

                                focusedTextColor = MaterialTheme.colorScheme.onSurface,

                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

                                focusedBorderColor = Color(0xFF007AFF),

                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),

                                focusedContainerColor = Color.Transparent,

                                unfocusedContainerColor = Color.Transparent

                            ),

                            modifier = Modifier.fillMaxWidth().testTag("email_input")

                        )

                        if (emailError != null) {

                            Text(

                                text = emailError ?: "",

                                color = MaterialTheme.colorScheme.error,

                                style = MaterialTheme.typography.labelSmall,

                                modifier = Modifier.align(Alignment.Start).padding(top = 4.dp, start = 8.dp)

                            )

                        }



                        Spacer(modifier = Modifier.height(12.dp))



                        OutlinedTextField(

                            value = password,

                            onValueChange = { 

                                password = it

                                passwordError = null

                            },

                            label = { Text(Localization.translate("password", selectedLanguage)) },

                            leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = "Lock") },

                            visualTransformation = PasswordVisualTransformation(),

                            isError = passwordError != null,

                            colors = OutlinedTextFieldDefaults.colors(

                                focusedTextColor = MaterialTheme.colorScheme.onSurface,

                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

                                focusedBorderColor = Color(0xFF007AFF),

                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),

                                focusedContainerColor = Color.Transparent,

                                unfocusedContainerColor = Color.Transparent

                            ),

                            modifier = Modifier.fillMaxWidth().testTag("password_input")

                        )

                        if (passwordError != null) {

                            Text(

                                text = passwordError ?: "",

                                color = MaterialTheme.colorScheme.error,

                                style = MaterialTheme.typography.labelSmall,

                                modifier = Modifier.align(Alignment.Start).padding(top = 4.dp, start = 8.dp)

                            )

                        }

                    }



                    Spacer(modifier = Modifier.height(16.dp))



                    // Role selector simulator using iOS Segmented Controls

                    Text(

                        text = Localization.translate("select_role", selectedLanguage),

                        style = MaterialTheme.typography.labelSmall,

                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),

                        modifier = Modifier.align(Alignment.Start).padding(bottom = 6.dp)

                    )



                    IosSegmentedControl(

                        items = rolesList,

                        selectedItem = selectedRole,

                        onItemSelected = { selectedRole = it },

                        labelProvider = { it }

                    )



                    Spacer(modifier = Modifier.height(20.dp))



                    Button(

                        onClick = {

                            if (isPhoneMode) {

                                var hasError = false

                                if (phoneOtp.length < 10) {

                                    phoneError = "Phone number must be at least 10 digits"

                                    hasError = true

                                } else {

                                    phoneError = null

                                }

                                

                                if (!isOtpSent) {

                                    phoneError = "Please request OTP first"

                                    hasError = true

                                } else if (otpCode != "123456" && otpCode.length != 6) {

                                    otpError = "Invalid 6-digit OTP. Use test code: 123456"

                                    hasError = true

                                } else {

                                    otpError = null

                                }



                                if (!hasError) {

                                    onLoginSuccess(selectedRole)

                                    Toast.makeText(context, "Logged in as $selectedRole via SMS OTP", Toast.LENGTH_SHORT).show()

                                }

                            } else {

                                var hasError = false

                                if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                                    emailError = "Please enter a valid email address"

                                    hasError = true

                                } else {

                                    emailError = null

                                }



                                if (password.length < 6) {

                                    passwordError = "Password must be at least 6 characters"

                                    hasError = true

                                } else {

                                    passwordError = null

                                }



                                if (!hasError) {

                                    val matchedRole = when {

                                        email == "citizen@civictrack.org" && password == "citizen123" -> "Citizen"

                                        email == "worker@civictrack.org" && password == "worker123" -> "Worker"

                                        email == "admin@civictrack.org" && password == "admin123" -> "Admin"

                                        else -> null

                                    }

                                    if (matchedRole == null) {

                                        emailError = "Invalid email or password"

                                        hasError = true

                                    } else {

                                        onLoginSuccess(matchedRole)

                                        Toast.makeText(context, "Logged in as $matchedRole", Toast.LENGTH_SHORT).show()

                                    }

                                }

                            }

                        },

                        modifier = Modifier

                            .fillMaxWidth()

                            .height(46.dp)

                            .testTag("login_button"),

                        shape = RoundedCornerShape(10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)) // Cupertino Blue

                    ) {

                        Icon(Icons.Rounded.Login, contentDescription = "Login")

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(Localization.translate("login", selectedLanguage), fontWeight = FontWeight.SemiBold)

                    }



                    Spacer(modifier = Modifier.height(16.dp))



                    Text(

                        text = if (isPhoneMode) Localization.translate("switch_to_email", selectedLanguage) else Localization.translate("switch_to_phone", selectedLanguage),

                        style = MaterialTheme.typography.bodySmall,

                        color = Color(0xFF007AFF),

                        fontWeight = FontWeight.Bold,

                        modifier = Modifier

                            .clickable { 

                                isPhoneMode = !isPhoneMode

                                emailError = null

                                passwordError = null

                                phoneError = null

                                otpError = null

                            }

                            .padding(8.dp)

                    )

                }

            }



            Spacer(modifier = Modifier.height(24.dp))



            // Professional, Secure Quick-Fill Demo Cards

            Text(

                text = "💡 Quick Access Demo Accounts",

                style = MaterialTheme.typography.labelSmall,

                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),

                fontWeight = FontWeight.Bold,

                modifier = Modifier.padding(bottom = 8.dp)

            )



            LazyRow(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {

                val demoUsers = listOf(

                    Triple("Citizen", "citizen@civictrack.org", "citizen123"),

                    Triple("Worker", "worker@civictrack.org", "worker123"),

                    Triple("Admin", "admin@civictrack.org", "admin123")

                )

                items(demoUsers) { (roleName, demoEmail, demoPass) ->

                    Card(

                        onClick = {

                            email = demoEmail

                            password = demoPass

                            selectedRole = roleName

                            isPhoneMode = false

                            emailError = null

                            passwordError = null

                            Toast.makeText(context, "Pre-filled $roleName credentials", Toast.LENGTH_SHORT).show()

                        },

                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),

                        shape = RoundedCornerShape(12.dp),

                        modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f), RoundedCornerShape(12.dp))

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Text(roleName, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)

                            Text(demoEmail, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))

                        }

                    }

                }

            }

        }

    }

}



// =========================================================================

// SETUP DIALOG / SHEET (Credential Input)

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun SetupCredentialsSheet(

    initialKey: String,

    initialConfig: String,

    onSave: (String, String) -> Unit,

    onDismiss: () -> Unit,

    selectedLanguage: Language = Language.ENGLISH

) {

    var keyInput by remember { mutableStateOf(initialKey) }

    var configInput by remember { mutableStateOf(initialConfig) }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {

            Button(

                onClick = { onSave(keyInput, configInput) },

                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))

            ) {

                Text(Localization.translate("submit", selectedLanguage))

            }

        },

        dismissButton = {

            TextButton(onClick = onDismiss) {

                Text("Cancel", color = Color.White)

            }

        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Key, contentDescription = "Key", tint = Color(0xFFF59E0B))

                Spacer(modifier = Modifier.width(8.dp))

                Text(Localization.translate("setup_title", selectedLanguage), color = Color.White, fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(

                    text = Localization.translate("setup_desc", selectedLanguage),

                    style = MaterialTheme.typography.bodySmall,

                    color = Color(0xFF94A3B8)

                )



                Spacer(modifier = Modifier.height(16.dp))



                OutlinedTextField(

                    value = keyInput,

                    onValueChange = { keyInput = it },

                    label = { Text(Localization.translate("api_key_label", selectedLanguage)) },

                    placeholder = { Text("AIzaSy...") },

                    colors = OutlinedTextFieldDefaults.colors(

                        focusedTextColor = Color.White,

                        unfocusedTextColor = Color.White,

                        focusedContainerColor = Color(0xFF0F172A),

                        unfocusedContainerColor = Color(0xFF0F172A)

                    ),

                    modifier = Modifier.fillMaxWidth().testTag("gemini_key_input")

                )



                Spacer(modifier = Modifier.height(12.dp))



                OutlinedTextField(

                    value = configInput,

                    onValueChange = { configInput = it },

                    label = { Text(Localization.translate("firebase_label", selectedLanguage)) },

                    placeholder = { Text("{\n  \"apiKey\": \"...\",\n  \"projectId\": \"...\"\n}") },

                    colors = OutlinedTextFieldDefaults.colors(

                        focusedTextColor = Color.White,

                        unfocusedTextColor = Color.White,

                        focusedContainerColor = Color(0xFF0F172A),

                        unfocusedContainerColor = Color(0xFF0F172A)

                    ),

                    minLines = 4,

                    modifier = Modifier.fillMaxWidth().testTag("firebase_json_input")

                )

            }

        },

        containerColor = Color(0xFF1E293B),

        shape = RoundedCornerShape(28.dp)

    )

}



// =========================================================================

// 2. MAIN WORKSPACE WITH BOTTOM NAV & SHEETS

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun MainWorkspace(

    currentRole: String,

    onChangeRole: (String) -> Unit,

    onLogout: () -> Unit,

    onOpenSetup: () -> Unit,

    viewModel: CivicViewModel,

    selectedLanguage: Language,

    onLanguageChange: (Language) -> Unit,

    isElderMode: Boolean = false,

    onElderModeChange: (Boolean) -> Unit = {},

    sharedSuggestions: List<SuggestionItem>,

    onUpdateSuggestions: (List<SuggestionItem>) -> Unit,

    sharedEmergencyAlerts: List<EmergencyAlertItem>,

    onUpdateEmergencyAlerts: (List<EmergencyAlertItem>) -> Unit,

    sharedCertificates: List<CertificateApplication>,

    onUpdateCertificates: (List<CertificateApplication>) -> Unit

) {

    var selectedTab by remember { mutableStateOf(NavItem.DASHBOARD) }

    var showSosNotification by remember { mutableStateOf<String?>(null) }

    var activeSosDispatch by remember { mutableStateOf(false) }

    var showLanguageMenu by remember { mutableStateOf(false) }

    var showSettingsDialog by remember { mutableStateOf(false) }

    var showProfileDialog by remember { mutableStateOf(false) }

    var showSmartCityHub by remember { mutableStateOf(false) }

    var customSosContact by remember { mutableStateOf("112") }

    var pushNotificationsEnabled by remember { mutableStateOf(true) }

    var textScaleMultiplier by remember { mutableStateOf(1.0f) }

    var isSyncingDatabase by remember { mutableStateOf(false) }

    var lastSyncTime by remember { mutableStateOf("Just now") }



    val realTimeAlert by viewModel.realTimeAlert.collectAsState()

    val context = LocalContext.current



    // Launch dispatch timer if SOS active

    LaunchedEffect(activeSosDispatch) {

        if (activeSosDispatch) {

            viewModel.triggerRealTimeAlert(

                com.example.ui.viewmodel.CivicAlert(

                    title = "🚨 EMERGENCY PANIC SOS BROADCAST",

                    message = "SOS Alert triggered by Senior Citizen at Sector 4, Lane B! Life-support emergency units required immediately.",

                    urgency = "Critical",

                    isEmergency = true

                )

            )

            delay(4000)

            activeSosDispatch = false

        }

    }



    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(

                            imageVector = Icons.Rounded.EnergySavingsLeaf,

                            contentDescription = "Leaf",

                            tint = Color(0xFF34C759), // iOS Green

                            modifier = Modifier.size(22.dp)

                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(

                            text = Localization.translate("app_name", selectedLanguage),

                            fontWeight = FontWeight.SemiBold, // Clean Apple display Weight

                            fontSize = 19.sp,

                            letterSpacing = (-0.5).sp

                        )

                    }

                },

                actions = {

                    Box {

                        IconButton(onClick = { showLanguageMenu = true }) {

                            Icon(

                                imageVector = Icons.Rounded.Translate,

                                contentDescription = "Language",

                                tint = MaterialTheme.colorScheme.primary // iOS Accent

                            )

                        }

                        DropdownMenu(

                            expanded = showLanguageMenu,

                            onDismissRequest = { showLanguageMenu = false },

                            modifier = Modifier

                                .background(MaterialTheme.colorScheme.surface)

                                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), RoundedCornerShape(12.dp))

                        ) {

                            Language.values().forEach { lang ->

                                DropdownMenuItem(

                                    text = { 

                                        Text(

                                            text = lang.displayName, 

                                            color = MaterialTheme.colorScheme.onSurface, 

                                            fontWeight = FontWeight.Medium,

                                            fontSize = 14.sp

                                        ) 

                                    },

                                    onClick = {

                                        onLanguageChange(lang)

                                        showLanguageMenu = false

                                    }

                                )

                            }

                        }

                    }

                    IconButton(onClick = { showProfileDialog = true }) {

                        Icon(

                            imageVector = Icons.Rounded.AccountCircle,

                            contentDescription = "Profile",

                            tint = MaterialTheme.colorScheme.primary

                        )

                    }

                    IconButton(onClick = { showSettingsDialog = true }) {

                        Icon(

                            imageVector = Icons.Rounded.Settings,

                            contentDescription = "Settings",

                            tint = MaterialTheme.colorScheme.primary

                        )

                    }

                    IconButton(onClick = onLogout) {

                        Icon(

                            imageVector = Icons.Rounded.ExitToApp,

                            contentDescription = "Logout",

                            tint = Color(0xFFFF3B30) // iOS Red

                        )

                    }

                },

                navigationIcon = {

                    // Role badge indicator that toggles role simulation on tap (iOS Style Pill badge)

                    Box(

                        modifier = Modifier

                            .padding(start = 12.dp)

                            .clip(RoundedCornerShape(10.dp))

                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))

                            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)), RoundedCornerShape(10.dp))

                            .clickable {

                                // Cycle through roles on click for easy judging testing!

                                val roles = listOf("Citizen", "Worker", "Admin")

                                val nextIndex = (roles.indexOf(currentRole) + 1) % roles.size

                                onChangeRole(roles[nextIndex])

                            }

                            .padding(horizontal = 8.dp, vertical = 4.dp),

                        contentAlignment = Alignment.Center

                    ) {

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            Text("ROLE", fontSize = 7.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                            Text(currentRole, fontSize = 9.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)

                        }

                    }

                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(

                    containerColor = MaterialTheme.colorScheme.surface

                )

            )

        },

        bottomBar = {

            NavigationBar(

                containerColor = MaterialTheme.colorScheme.surface,

                tonalElevation = 8.dp

            ) {

                NavItem.values().forEach { tab ->

                    val selected = selectedTab == tab

                    val icon = when (tab) {

                        NavItem.DASHBOARD -> if (selected) Icons.Rounded.Dashboard else Icons.Outlined.Dashboard

                        NavItem.AI_ASSISTANT -> if (selected) Icons.Rounded.SupportAgent else Icons.Outlined.SupportAgent

                        NavItem.COMPLAINTS -> if (selected) Icons.Rounded.ReportProblem else Icons.Outlined.ReportProblem

                        NavItem.TRANSPARENCY -> if (selected) Icons.Rounded.ReceiptLong else Icons.Outlined.ReceiptLong

                        NavItem.SERVICES -> if (selected) Icons.Rounded.Widgets else Icons.Outlined.Widgets

                        NavItem.COMMUNITY -> if (selected) Icons.Rounded.HowToVote else Icons.Outlined.HowToVote

                    }

                    val tabLabel = when (tab) {

                        NavItem.DASHBOARD -> Localization.translate("dashboard", selectedLanguage)

                        NavItem.AI_ASSISTANT -> Localization.translate("ai_officer", selectedLanguage)

                        NavItem.COMPLAINTS -> Localization.translate("complaints", selectedLanguage)

                        NavItem.TRANSPARENCY -> Localization.translate("transparency", selectedLanguage)

                        NavItem.SERVICES -> Localization.translate("services", selectedLanguage)

                        NavItem.COMMUNITY -> Localization.translate("polls", selectedLanguage)

                    }

                    NavigationBarItem(

                        selected = selected,

                        onClick = { selectedTab = tab },

                        icon = { Icon(icon, contentDescription = tabLabel) },

                        label = { Text(tabLabel, fontSize = 10.sp, maxLines = 1) },

                        colors = NavigationBarItemDefaults.colors(

                            selectedIconColor = MaterialTheme.colorScheme.primary, // iOS Blue

                            unselectedIconColor = Color(0xFF8E8E93), // iOS Gray

                            selectedTextColor = MaterialTheme.colorScheme.primary,

                            unselectedTextColor = Color(0xFF8E8E93),

                            indicatorColor = Color.Transparent // iOS style Tab Bar has no heavy background capsule

                        )

                    )

                }

            }

        }

    ) { innerPadding ->

        Box(

            modifier = Modifier

                .fillMaxSize()

                .padding(innerPadding)

        ) {

            // Main views routing

            AnimatedContent(

                targetState = selectedTab,

                transitionSpec = {

                    fadeIn(animationSpec = tween(220)) togetherWith fadeOut(animationSpec = tween(220))

                }

            ) { targetTab ->

                when (targetTab) {

                    NavItem.DASHBOARD -> DashboardScreen(

                        currentRole = currentRole,

                        onTriggerSos = { activeSosDispatch = true },

                        onNavigateToAi = { selectedTab = NavItem.AI_ASSISTANT },

                        viewModel = viewModel,

                        selectedLanguage = selectedLanguage,

                        isElderMode = isElderMode,

                        onLaunchSmartCityHub = { showSmartCityHub = true }

                    )

                    NavItem.AI_ASSISTANT -> AiAssistantScreen(viewModel = viewModel, selectedLanguage = selectedLanguage, isElderMode = isElderMode)

                    NavItem.COMPLAINTS -> ComplaintsScreen(

                        viewModel = viewModel,

                        currentRole = currentRole,

                        selectedLanguage = selectedLanguage,

                        isElderMode = isElderMode

                    )

                    NavItem.TRANSPARENCY -> TransparencyScreen(
                        viewModel = viewModel,
                        currentRole = currentRole,
                        selectedLanguage = selectedLanguage,
                        isElderMode = isElderMode
                    )

                    NavItem.SERVICES -> ServicesScreen(

                        viewModel = viewModel,

                        currentRole = currentRole,

                        selectedLanguage = selectedLanguage,

                        isElderMode = isElderMode,

                        sharedCertificates = sharedCertificates,

                        onUpdateCertificates = onUpdateCertificates,

                        sharedEmergencyAlerts = sharedEmergencyAlerts,

                        onUpdateEmergencyAlerts = onUpdateEmergencyAlerts,

                        sharedSuggestions = sharedSuggestions,

                        onUpdateSuggestions = onUpdateSuggestions,

                        onLaunchSmartCityHub = { showSmartCityHub = true }

                    )

                    NavItem.COMMUNITY -> CommunityPollsScreen(
                        viewModel = viewModel,
                        currentRole = currentRole,
                        selectedLanguage = selectedLanguage,
                        isElderMode = isElderMode
                    )

                }

            }



            // Dispatch HUD Loading Overlay

            if (activeSosDispatch) {

                Box(

                    modifier = Modifier

                        .fillMaxSize()

                        .background(Color.Black.copy(alpha = 0.85f)),

                    contentAlignment = Alignment.Center

                ) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        CircularProgressIndicator(color = Color(0xFFEF4444), strokeWidth = 5.dp)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(

                            "CRITICAL SOS TRIGGERED",

                            style = MaterialTheme.typography.titleMedium,

                            fontWeight = FontWeight.ExtraBold,

                            color = Color(0xFFEF4444),

                            letterSpacing = 1.sp

                        )

                        Text(

                            "Attaching GPS: Lat 40.7128, Long -74.0060",

                            style = MaterialTheme.typography.bodySmall,

                            color = Color.White

                        )

                        Text(

                            "Broadcasting to Emergency Operators...",

                            style = MaterialTheme.typography.bodySmall,

                            color = Color(0xFF94A3B8)

                        )

                    }

                }

            }



            // Real-time Alert Notification Banner

            realTimeAlert?.let { alert ->

                Card(

                    modifier = Modifier

                        .align(Alignment.TopCenter)

                        .padding(16.dp)

                        .fillMaxWidth()

                        .shadow(16.dp, RoundedCornerShape(16.dp))

                        .border(

                            2.dp,

                            if (alert.isEmergency) Color(0xFFEF4444) else Color(0xFF10B981),

                            RoundedCornerShape(16.dp)

                        ),

                    colors = CardDefaults.cardColors(

                        containerColor = if (alert.isEmergency) Color(0xFF7F1D1D) else Color(0xFF0F172A)

                    ),

                    shape = RoundedCornerShape(16.dp)

                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Icon(

                                imageVector = if (alert.isEmergency) Icons.Rounded.Warning else Icons.Rounded.NotificationsActive,

                                contentDescription = "Alert Icon",

                                tint = if (alert.isEmergency) Color(0xFFFCA5A5) else Color(0xFF10B981),

                                modifier = Modifier.size(24.dp)

                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(

                                    text = alert.title,

                                    color = Color.White,

                                    fontSize = 14.sp,

                                    fontWeight = FontWeight.Bold

                                )

                                Text(

                                    text = "Broadcasted Live • Just now",

                                    color = Color(0xFF94A3B8),

                                    fontSize = 9.sp

                                )

                            }

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(

                            text = alert.message,

                            color = Color(0xFFE2E8F0),

                            fontSize = 12.sp,

                            lineHeight = 16.sp

                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(

                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement = Arrangement.End,

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            if (alert.isEmergency) {

                                Button(

                                    onClick = {

                                        viewModel.dismissRealTimeAlert()

                                        Toast.makeText(context, "GPS Signal Intercepted! Dispatch unit sent.", Toast.LENGTH_LONG).show()

                                    },

                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),

                                    shape = RoundedCornerShape(8.dp),

                                    modifier = Modifier.padding(end = 8.dp)

                                ) {

                                    Text("Dispatch First Responders", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)

                                }

                            }

                            TextButton(

                                onClick = { viewModel.dismissRealTimeAlert() }

                            ) {

                                Text(

                                    text = if (alert.isEmergency) "Acknowledge" else "Great!",

                                    color = if (alert.isEmergency) Color(0xFFFCA5A5) else Color(0xFF10B981),

                                    fontSize = 11.sp,

                                    fontWeight = FontWeight.Bold

                                )

                            }

                        }

                    }

                }

            }



            showSosNotification?.let { msg ->

                Card(

                    modifier = Modifier

                        .align(Alignment.BottomCenter)

                        .padding(16.dp)

                        .fillMaxWidth()

                        .shadow(12.dp),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981)),

                    shape = RoundedCornerShape(12.dp)

                ) {

                    Row(

                        modifier = Modifier.padding(16.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Icon(Icons.Rounded.Verified, contentDescription = "OK", tint = Color.White)

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(

                            text = msg,

                            color = Color.White,

                            style = MaterialTheme.typography.bodyMedium,

                            modifier = Modifier.weight(1f)

                        )

                        Text(

                            "DISMISS",

                            color = Color.White,

                            fontWeight = FontWeight.Bold,

                            fontSize = 11.sp,

                            modifier = Modifier

                                .clickable { showSosNotification = null }

                                .padding(8.dp)

                        )

                    }

                }

            }



            // SETTINGS PREFERENCES DIALOG

            if (showSettingsDialog) {

                val coroutineScope = rememberCoroutineScope()

                androidx.compose.ui.window.Dialog(onDismissRequest = { showSettingsDialog = false }) {

                    Card(

                        modifier = Modifier

                            .fillMaxWidth()

                            .padding(vertical = 24.dp),

                        shape = RoundedCornerShape(20.dp),

                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),

                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),

                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

                    ) {

                        Column(

                            modifier = Modifier

                                .fillMaxWidth()

                                .padding(20.dp)

                                .verticalScroll(rememberScrollState())

                        ) {

                            // Header

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween,

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Icon(

                                        imageVector = Icons.Rounded.Settings,

                                        contentDescription = "Settings",

                                        tint = MaterialTheme.colorScheme.primary,

                                        modifier = Modifier.size(24.dp)

                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(

                                        text = "System Preferences",

                                        style = MaterialTheme.typography.titleMedium,

                                        fontWeight = FontWeight.Bold,

                                        color = MaterialTheme.colorScheme.onSurface

                                    )

                                }

                                IconButton(onClick = { showSettingsDialog = false }) {

                                    Icon(

                                        imageVector = Icons.Rounded.Close,

                                        contentDescription = "Close",

                                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

                                    )

                                }

                            }



                            Spacer(modifier = Modifier.height(16.dp))



                            // SECTION 1: Accessibility (Elder Mode + Display Scaling)

                            Text(

                                text = "ACCESSIBILITY & USABILITY",

                                style = MaterialTheme.typography.labelSmall,

                                color = MaterialTheme.colorScheme.primary,

                                fontWeight = FontWeight.Bold,

                                modifier = Modifier.padding(bottom = 8.dp)

                            )



                            // Elder Mode Row

                            Row(

                                modifier = Modifier

                                    .fillMaxWidth()

                                    .clip(RoundedCornerShape(12.dp))

                                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f))

                                    .clickable { onElderModeChange(!isElderMode) }

                                    .padding(12.dp),

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Icon(

                                    imageVector = Icons.Rounded.Accessibility,

                                    contentDescription = "Elder Mode",

                                    tint = if (isElderMode) Color(0xFFFF9500) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),

                                    modifier = Modifier.size(24.dp)

                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(

                                        text = "Elder Friendly UI",

                                        style = MaterialTheme.typography.bodyMedium,

                                        fontWeight = FontWeight.SemiBold,

                                        color = MaterialTheme.colorScheme.onSurface

                                    )

                                    Text(

                                        text = "Larger elements & enhanced readability",

                                        style = MaterialTheme.typography.labelSmall,

                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

                                    )

                                }

                                Switch(

                                    checked = isElderMode,

                                    onCheckedChange = { onElderModeChange(it) },

                                    colors = SwitchDefaults.colors(

                                        checkedThumbColor = Color.White,

                                        checkedTrackColor = Color(0xFF34C759)

                                    )

                                )

                            }



                            Spacer(modifier = Modifier.height(8.dp))



                            // Display Scale Slider

                            Row(

                                modifier = Modifier

                                    .fillMaxWidth()

                                    .clip(RoundedCornerShape(12.dp))

                                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f))

                                    .padding(12.dp),

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Icon(

                                    imageVector = Icons.Rounded.FormatSize,

                                    contentDescription = "Display Scale",

                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),

                                    modifier = Modifier.size(24.dp)

                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(

                                        text = "UI Text Scaling",

                                        style = MaterialTheme.typography.bodyMedium,

                                        fontWeight = FontWeight.SemiBold,

                                        color = MaterialTheme.colorScheme.onSurface

                                    )

                                    Slider(

                                        value = textScaleMultiplier,

                                        onValueChange = { textScaleMultiplier = it },

                                        valueRange = 0.8f..1.4f,

                                        steps = 2,

                                        colors = SliderDefaults.colors(

                                            thumbColor = MaterialTheme.colorScheme.primary,

                                            activeTrackColor = MaterialTheme.colorScheme.primary

                                        )

                                    )

                                    Text(

                                        text = "Scale Factor: ${String.format("%.1f", textScaleMultiplier)}x",

                                        style = MaterialTheme.typography.labelSmall,

                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

                                    )

                                }

                            }



                            Spacer(modifier = Modifier.height(16.dp))



                            // SECTION 2: Safety & Emergency Contacts

                            Text(

                                text = "SAFETY & PANIC ALERTS",

                                style = MaterialTheme.typography.labelSmall,

                                color = MaterialTheme.colorScheme.primary,

                                fontWeight = FontWeight.Bold,

                                modifier = Modifier.padding(bottom = 8.dp)

                            )



                            OutlinedTextField(

                                value = customSosContact,

                                onValueChange = { customSosContact = it },

                                label = { Text("Primary SOS Hotline") },

                                placeholder = { Text("e.g. 112") },

                                leadingIcon = { Icon(Icons.Rounded.Phone, contentDescription = "SOS Number") },

                                modifier = Modifier.fillMaxWidth(),

                                colors = OutlinedTextFieldDefaults.colors(

                                    focusedBorderColor = MaterialTheme.colorScheme.primary,

                                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)

                                )

                            )



                            Spacer(modifier = Modifier.height(8.dp))



                            Row(

                                modifier = Modifier

                                    .fillMaxWidth()

                                    .clip(RoundedCornerShape(12.dp))

                                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f))

                                    .clickable { pushNotificationsEnabled = !pushNotificationsEnabled }

                                    .padding(12.dp),

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Icon(

                                    imageVector = Icons.Rounded.Notifications,

                                    contentDescription = "Notifications",

                                    tint = MaterialTheme.colorScheme.primary,

                                    modifier = Modifier.size(24.dp)

                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(

                                        text = "Emergency Push Alerts",

                                        style = MaterialTheme.typography.bodyMedium,

                                        fontWeight = FontWeight.SemiBold,

                                        color = MaterialTheme.colorScheme.onSurface

                                    )

                                    Text(

                                        text = "Notify immediately on disaster broadcasts",

                                        style = MaterialTheme.typography.labelSmall,

                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

                                    )

                                }

                                Switch(

                                    checked = pushNotificationsEnabled,

                                    onCheckedChange = { pushNotificationsEnabled = it },

                                    colors = SwitchDefaults.colors(

                                        checkedThumbColor = Color.White,

                                        checkedTrackColor = Color(0xFF34C759)

                                    )

                                )

                            }



                            Spacer(modifier = Modifier.height(16.dp))



                            // SECTION 3: Offline Sync Engine

                            Text(

                                text = "OFFLINE SYNC STATUS",

                                style = MaterialTheme.typography.labelSmall,

                                color = MaterialTheme.colorScheme.primary,

                                fontWeight = FontWeight.Bold,

                                modifier = Modifier.padding(bottom = 8.dp)

                            )



                            Card(

                                modifier = Modifier.fillMaxWidth(),

                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f)),

                                shape = RoundedCornerShape(12.dp),

                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f))

                            ) {

                                Column(modifier = Modifier.padding(12.dp)) {

                                    Row(

                                        modifier = Modifier.fillMaxWidth(),

                                        horizontalArrangement = Arrangement.SpaceBetween,

                                        verticalAlignment = Alignment.CenterVertically

                                    ) {

                                        Column {

                                            Text(

                                                text = "Offline SQLite Cache",

                                                style = MaterialTheme.typography.bodyMedium,

                                                fontWeight = FontWeight.Bold

                                            )

                                            Text(

                                                text = "Last Synced: $lastSyncTime",

                                                style = MaterialTheme.typography.labelSmall,

                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

                                            )

                                        }

                                        if (isSyncingDatabase) {

                                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)

                                        } else {

                                            Text(

                                                text = "SECURE LOCAL DB",

                                                style = MaterialTheme.typography.labelSmall,

                                                color = Color(0xFF34C759),

                                                fontWeight = FontWeight.Bold

                                            )

                                        }

                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Button(

                                        onClick = {

                                            isSyncingDatabase = true

                                            coroutineScope.launch {

                                                kotlinx.coroutines.delay(1500)

                                                isSyncingDatabase = false

                                                lastSyncTime = "Just now"

                                                Toast.makeText(context, "All city systems & reports successfully synchronized locally!", Toast.LENGTH_SHORT).show()

                                            }

                                        },

                                        enabled = !isSyncingDatabase,

                                        modifier = Modifier.fillMaxWidth().height(36.dp),

                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),

                                        shape = RoundedCornerShape(8.dp)

                                    ) {

                                        Text("Sync Database Offline", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                                    }

                                }

                            }



                            Spacer(modifier = Modifier.height(16.dp))



                            // SECTION 4: Professional Directory (Elected Officials)

                            Text(

                                text = "CIVIC ELECTED DIRECTORY",

                                style = MaterialTheme.typography.labelSmall,

                                color = MaterialTheme.colorScheme.primary,

                                fontWeight = FontWeight.Bold,

                                modifier = Modifier.padding(bottom = 8.dp)

                            )



                            val directory = listOf(

                                Triple("Hon. Sandeep Deshmukh", "Ward 4 Councillor", "+91 94220 12345"),

                                Triple("Dr. Anjali Patil, IAS", "Municipal Commissioner", "+91 22 2262 0123"),

                                Triple("Ward 4 Control Desk", "Command & Emergency", "+91 22 2262 9999")

                            )



                            directory.forEach { (name, desig, phone) ->

                                Row(

                                    modifier = Modifier

                                        .fillMaxWidth()

                                        .padding(vertical = 4.dp),

                                    horizontalArrangement = Arrangement.SpaceBetween,

                                    verticalAlignment = Alignment.CenterVertically

                                ) {

                                    Column {

                                        Text(name, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)

                                        Text(desig, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))

                                    }

                                    IconButton(

                                        onClick = {

                                            val intent = android.content.Intent(android.content.Intent.ACTION_DIAL).apply {

                                                data = android.net.Uri.parse("tel:$phone")

                                            }

                                            context.startActivity(intent)

                                        },

                                        modifier = Modifier.size(36.dp)

                                    ) {

                                        Icon(

                                            imageVector = Icons.Rounded.Call,

                                            contentDescription = "Call",

                                            tint = Color(0xFF34C759),

                                            modifier = Modifier.size(18.dp)

                                        )

                                    }

                                }

                            }



                            Spacer(modifier = Modifier.height(20.dp))



                            // Done Button

                            Button(

                                onClick = { showSettingsDialog = false },

                                modifier = Modifier.fillMaxWidth().height(44.dp),

                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),

                                shape = RoundedCornerShape(10.dp)

                            ) {

                                Text("Apply Preferences", fontWeight = FontWeight.SemiBold)

                            }

                        }

                    }

                }

            if (showSmartCityHub) {

                SmartCityFeatureHubDialog(

                    viewModel = viewModel,

                    currentRole = currentRole,

                    sharedSuggestions = sharedSuggestions,

                    onUpdateSuggestions = onUpdateSuggestions,

                    onDismiss = { showSmartCityHub = false }

                )

            }

        }

    }

} // end MainWorkspace (inner block)

} // end MainWorkspace



// =========================================================================

// 3. SCREEN: CITIZEN DASHBOARD (With Carbon Tracking!)

// =========================================================================

@Composable

fun DashboardScreen(

    currentRole: String,

    onTriggerSos: () -> Unit,

    onNavigateToAi: () -> Unit,

    viewModel: CivicViewModel,

    selectedLanguage: Language = Language.ENGLISH,

    isElderMode: Boolean = false,

    sharedSuggestions: List<SuggestionItem> = emptyList(),

    onUpdateSuggestions: (List<SuggestionItem>) -> Unit = {},

    sharedEmergencyAlerts: List<EmergencyAlertItem> = emptyList(),

    onUpdateEmergencyAlerts: (List<EmergencyAlertItem>) -> Unit = {},

    sharedCertificates: List<CertificateApplication> = emptyList(),

    onUpdateCertificates: (List<CertificateApplication>) -> Unit = {},

    onLaunchSmartCityHub: () -> Unit = {}

) {

    val complaints by viewModel.complaints.collectAsState()

    val carbonMetrics by viewModel.carbonMetrics.collectAsState()

    val projects by viewModel.projects.collectAsState()

    val notices by viewModel.notices.collectAsState()



    val citizenPoints by viewModel.citizenPoints.collectAsState()

    val earnedAchievements by viewModel.earnedAchievements.collectAsState()



    // Auto unlock SDG champion achievement if other four are unlocked

    val hasOtherAchievements = earnedAchievements.contains("eco_sentinel") &&

            earnedAchievements.contains("active_elector") &&

            earnedAchievements.contains("green_dialogue") &&

            earnedAchievements.contains("elder_helper")



    if (hasOtherAchievements && !earnedAchievements.contains("sdg_champion")) {

        LaunchedEffect(Unit) {

            viewModel.unlockAchievement("sdg_champion")

        }

    }



    val pendingCount = complaints.filter { it.status != "Resolved" && it.status != "Closed" }.size

    val context = LocalContext.current



    // State for Ward Officer

    var noticeTitle by remember { mutableStateOf("") }

    var noticeContent by remember { mutableStateOf("") }

    var noticeType by remember { mutableStateOf("Alert") }



    // State for Mayor

    var mayorBroadcastTitle by remember { mutableStateOf("") }

    var mayorBroadcastContent by remember { mutableStateOf("") }



    // State for Elder Mode Dialogs

    var showElderNoticesDialog by remember { mutableStateOf(false) }

    var showElderCertGuideDialog by remember { mutableStateOf(false) }

    var simPhoneCallNumber by remember { mutableStateOf<String?>(null) }



    val helloUser = when (currentRole) {

        "Citizen" -> when (selectedLanguage) {

            Language.HINDI -> "नमस्ते, नागरिक!"

            Language.MARATHI -> "नमस्कार, नागरिक!"

            Language.KANNADA -> "ನಮಸ್ಕಾರ, ನಾಗರಿಕರೆ!"

            Language.TAMIL -> "வணக்கம், குடிமகன்!"

            else -> "Hello, Citizen!"

        }

        "Worker" -> "Hello, Civic Worker!"

        "Admin" -> "Hello, Municipal Admin!"

        else -> "Hello, $currentRole!"

    }



    // Font size scaling factors for Elder Mode

    val scale = if (isElderMode) 1.25f else 1.0f

    val h1Size = (if (isElderMode) 26 else 20).sp

    val h2Size = (if (isElderMode) 22 else 16).sp

    val pSize = (if (isElderMode) 18 else 13).sp

    val smallSize = (if (isElderMode) 15 else 10).sp

    val buttonMinHeight = if (isElderMode) 64.dp else 44.dp



    LazyColumn(

        modifier = Modifier

            .fillMaxSize()

            .background(MaterialTheme.colorScheme.background)

            .padding(if (isElderMode) 20.dp else 16.dp),

        verticalArrangement = Arrangement.spacedBy(if (isElderMode) 20.dp else 16.dp)

    ) {

        // ==========================================

        // WELCOME BANNER (Dynamic styling)

        // ==========================================

        item {

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(

                    containerColor = if (isElderMode) Color(0xFF1E3A8A) else Color(0xFF0F172A)

                )

            ) {

                Row(

                    modifier = Modifier

                        .background(

                            Brush.linearGradient(

                                colors = if (isElderMode) {

                                    listOf(Color(0xFF1E3A8A), Color(0xFF0F172A))

                                } else {

                                    listOf(Color(0xFF2563EB), Color(0xFF1D4ED8))

                                }

                            )

                        )

                        .padding(if (isElderMode) 24.dp else 20.dp)

                        .fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically,

                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Column(modifier = Modifier.weight(1f)) {

                        Text(

                            text = helloUser,

                            fontSize = h1Size,

                            fontWeight = FontWeight.Black,

                            color = Color.White

                        )

                        Text(

                            text = "Ward 4 • Role: $currentRole",

                            fontSize = pSize,

                            color = Color.White.copy(alpha = 0.9f)

                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(

                            modifier = Modifier

                                .clip(RoundedCornerShape(6.dp))

                                .background(Color.White.copy(alpha = 0.25f))

                                .padding(horizontal = 10.dp, vertical = 4.dp)

                        ) {

                            Text(

                                "Profile status: 100% Verified Senior/Citizen ID",

                                color = Color.White,

                                fontSize = smallSize,

                                fontWeight = FontWeight.Bold

                            )

                        }

                    }



                    // Emergency Quick Tap SOS

                    Button(

                        onClick = onTriggerSos,

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),

                        shape = RoundedCornerShape(16.dp),

                        modifier = Modifier

                            .size(if (isElderMode) 90.dp else 72.dp)

                            .testTag("sos_dashboard_button")

                    ) {

                        Column(

                            horizontalAlignment = Alignment.CenterHorizontally,

                            verticalArrangement = Arrangement.Center,

                            modifier = Modifier.fillMaxSize()

                        ) {

                            Icon(

                                Icons.Rounded.Warning,

                                contentDescription = "SOS",

                                tint = Color.White,

                                modifier = Modifier.size(if (isElderMode) 32.dp else 24.dp)

                            )

                            Text(

                                text = "SOS",

                                fontSize = if (isElderMode) 14.sp else 10.sp,

                                fontWeight = FontWeight.Black,

                                color = Color.White

                            )

                        }

                    }

                }

            }

        }



        item {

            Card(

                modifier = Modifier

                    .fillMaxWidth()

                    .clickable { onLaunchSmartCityHub() }

                    .testTag("smart_city_hub_banner"),

                shape = RoundedCornerShape(18.dp),

                colors = CardDefaults.cardColors(

                    containerColor = Color(0xFF0F172A)

                ),

                border = BorderStroke(1.dp, Color(0xFF3B82F6).copy(alpha = 0.4f)),

                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

            ) {

                Row(

                    modifier = Modifier.padding(16.dp),

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Box(

                        modifier = Modifier

                            .size(46.dp)

                            .background(Color(0xFF3B82F6).copy(alpha = 0.15f), CircleShape),

                        contentAlignment = Alignment.Center

                    ) {

                        Icon(Icons.Rounded.Widgets, null, tint = Color(0xFF3B82F6), modifier = Modifier.size(22.dp))

                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {

                        Text(

                            text = "Smart City 2.0 Feature Hub",

                            fontWeight = FontWeight.ExtraBold,

                            color = Color.White,

                            fontSize = h2Size

                        )

                        Text(

                            text = "Sandbox preview of all 50 interactive features",

                            color = Color.White.copy(alpha = 0.7f),

                            fontSize = pSize

                        )

                    }

                    Icon(

                        imageVector = Icons.Rounded.ArrowForwardIos,

                        contentDescription = "Open Hub",

                        tint = Color(0xFF3B82F6),

                        modifier = Modifier.size(16.dp)

                    )

                }

            }

        }



        // ==========================================

        // ELDER-FRIENDLY QUICK ACTIONS PANEL (Active only if isElderMode)

        // ==========================================

        if (isElderMode) {

            item {

                Text(

                    text = "👵 Simple Direct Services (Elder Mode Active)",

                    fontSize = h2Size,

                    fontWeight = FontWeight.ExtraBold,

                    color = Color(0xFF1E293B)

                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(

                    verticalArrangement = Arrangement.spacedBy(12.dp)

                ) {

                    // Big Action 1: Call Emergency Ambulance

                    Card(

                        modifier = Modifier

                            .fillMaxWidth()

                            .clickable { simPhoneCallNumber = "911 / Ambulance Emergency" },

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2)),

                        shape = RoundedCornerShape(16.dp)

                    ) {

                        Row(

                            modifier = Modifier.padding(18.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Box(

                                modifier = Modifier

                                    .size(54.dp)

                                    .background(Color(0xFFEF4444), CircleShape),

                                contentAlignment = Alignment.Center

                            ) {

                                Icon(Icons.Rounded.Phone, contentDescription = "Call", tint = Color.White, modifier = Modifier.size(30.dp))

                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {

                                Text("📞 Call Ambulance Hotline", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF991B1B))

                                Text("Click to call emergency assistance immediately", fontSize = pSize, color = Color(0xFF7F1D1D))

                            }

                        }

                    }



                    // Big Action 2: Read Notices in LARGE FONT

                    Card(

                        modifier = Modifier

                            .fillMaxWidth()

                            .clickable { showElderNoticesDialog = true },

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE)),

                        shape = RoundedCornerShape(16.dp)

                    ) {

                        Row(

                            modifier = Modifier.padding(18.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Box(

                                modifier = Modifier

                                    .size(54.dp)

                                    .background(Color(0xFF0284C7), CircleShape),

                                contentAlignment = Alignment.Center

                            ) {

                                Icon(Icons.Rounded.NotificationsActive, contentDescription = "Notices", tint = Color.White, modifier = Modifier.size(30.dp))

                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {

                                Text("📰 Read Notice Board", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF0369A1))

                                Text("See municipality notices in large, clear writing", fontSize = pSize, color = Color(0xFF0C4A6E))

                            }

                        }

                    }



                    // Big Action 3: Simple Certificate Guide

                    Card(

                        modifier = Modifier

                            .fillMaxWidth()

                            .clickable { showElderCertGuideDialog = true },

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFDCFCE7)),

                        shape = RoundedCornerShape(16.dp)

                    ) {

                        Row(

                            modifier = Modifier.padding(18.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Box(

                                modifier = Modifier

                                    .size(54.dp)

                                    .background(Color(0xFF22C55E), CircleShape),

                                contentAlignment = Alignment.Center

                            ) {

                                Icon(Icons.Rounded.Description, contentDescription = "Certificates", tint = Color.White, modifier = Modifier.size(30.dp))

                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {

                                Text("📜 Pension & Document Help", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF166534))

                                Text("Step-by-step guides for birth, pension & resident certificates", fontSize = pSize, color = Color(0xFF14532D))

                            }

                        }

                    }



                    // Big Action 4: Voice Help Guide

                    Card(

                        modifier = Modifier

                            .fillMaxWidth()

                            .clickable {

                                Toast.makeText(context, "🗣️ Simulated Voice Assistant activated. Try speaking your command!", Toast.LENGTH_LONG).show()

                            },

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF)),

                        shape = RoundedCornerShape(16.dp)

                    ) {

                        Row(

                            modifier = Modifier.padding(18.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Box(

                                modifier = Modifier

                                    .size(54.dp)

                                    .background(Color(0xFF8B5CF6), CircleShape),

                                contentAlignment = Alignment.Center

                            ) {

                                Icon(Icons.Rounded.Mic, contentDescription = "Voice", tint = Color.White, modifier = Modifier.size(30.dp))

                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {

                                Text("🗣️ Voice Assistant Helper", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF6D28D9))

                                Text("Tap & speak to check water bills or ask about scheme info", fontSize = pSize, color = Color(0xFF4C1D95))

                            }

                        }

                    }

                }

            }

        }



        // ==========================================

        // CITIZEN DASHBOARD SCREEN DETAILS

        // ==========================================

        if (currentRole == "Citizen") {

            // Gamified Citizen Score Card

            item {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

                ) {

                    Row(

                        modifier = Modifier.padding(if (isElderMode) 20.dp else 16.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Box(

                            modifier = Modifier

                                .size(if (isElderMode) 58.dp else 48.dp)

                                .background(Color(0xFF10B981).copy(alpha = 0.15f), CircleShape),

                            contentAlignment = Alignment.Center

                        ) {

                            Icon(

                                Icons.Rounded.EmojiEvents,

                                contentDescription = "Prize",

                                tint = Color(0xFF10B981),

                                modifier = Modifier.size(if (isElderMode) 28.dp else 24.dp)

                            )

                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {

                            Text(

                                text = "Your Green Citizen Score: $citizenPoints XP",

                                fontSize = h2Size,

                                fontWeight = FontWeight.Bold,

                                color = Color(0xFF0F172A)

                            )

                            val badgeName = when {

                                earnedAchievements.contains("sdg_champion") -> "SDG GLOBAL CHAMPION"

                                earnedAchievements.contains("eco_sentinel") -> "ECO CRUSADER"

                                earnedAchievements.isNotEmpty() -> "ACTIVE CIVIC LEADER"

                                else -> "CIVIC INITIATE"

                            }

                            Text(

                                text = "Badge Level: $badgeName • ${earnedAchievements.size}/${viewModel.achievementsList.size} Unlocked",

                                fontSize = pSize,

                                color = Color(0xFF10B981),

                                fontWeight = FontWeight.Bold

                            )

                        }

                    }

                }

            }



            // Achievements Header

            item {

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(

                        imageVector = Icons.Rounded.EmojiEvents,

                        contentDescription = "Achievements",

                        tint = Color(0xFFEAB308),

                        modifier = Modifier.size(24.dp)

                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(

                        text = "Your Badges & Civic Achievements",

                        fontSize = h2Size,

                        fontWeight = FontWeight.ExtraBold,

                        color = Color(0xFF0F172A)

                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

            }



            // Achievements List

            items(viewModel.achievementsList) { achievement ->

                val isEarned = earnedAchievements.contains(achievement.id)

                Card(

                    modifier = Modifier

                        .fillMaxWidth()

                        .padding(vertical = 4.dp),

                    shape = RoundedCornerShape(12.dp),

                    colors = CardDefaults.cardColors(

                        containerColor = if (isEarned) Color(0xFFF0FDF4) else Color(0xFFF8FAFC)

                    ),

                    border = androidx.compose.foundation.BorderStroke(

                        width = 1.dp,

                        color = if (isEarned) Color(0xFF86EFAC) else Color(0xFFE2E8F0)

                    )

                ) {

                    Row(

                        modifier = Modifier.padding(12.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Box(

                            modifier = Modifier

                                .size(36.dp)

                                .background(

                                    color = if (isEarned) Color(0xFFDCFCE7) else Color(0xFFF1F5F9),

                                    shape = CircleShape

                                ),

                            contentAlignment = Alignment.Center

                        ) {

                            Icon(

                                imageVector = when (achievement.iconName) {

                                    "EmojiEvents" -> Icons.Rounded.EmojiEvents

                                    "HowToVote" -> Icons.Rounded.HowToVote

                                    "ChatBubble" -> Icons.Rounded.AutoAwesome

                                    "Elderly" -> Icons.Rounded.Accessibility

                                    else -> Icons.Rounded.Verified

                                },

                                contentDescription = achievement.title,

                                tint = if (isEarned) Color(0xFF166534) else Color(0xFF64748B),

                                modifier = Modifier.size(18.dp)

                            )

                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {

                            Text(

                                text = achievement.title,

                                fontSize = pSize,

                                fontWeight = FontWeight.Bold,

                                color = if (isEarned) Color(0xFF14532D) else Color(0xFF1E293B)

                            )

                            Text(

                                text = achievement.description,

                                fontSize = smallSize,

                                color = Color(0xFF64748B)

                            )

                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(

                            modifier = Modifier

                                .clip(RoundedCornerShape(6.dp))

                                .background(if (isEarned) Color(0xFFDCFCE7) else Color(0xFFE2E8F0))

                                .padding(horizontal = 8.dp, vertical = 4.dp)

                        ) {

                            Text(

                                text = if (isEarned) "UNLOCKED • +${achievement.points} XP" else "+${achievement.points} XP",

                                fontSize = 9.sp,

                                fontWeight = FontWeight.Bold,

                                color = if (isEarned) Color(0xFF15803D) else Color(0xFF475569)

                            )

                        }

                    }

                }

            }







            // Active Grievances Status card

            item {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

                ) {

                    Row(

                        modifier = Modifier.padding(if (isElderMode) 20.dp else 16.dp),

                        verticalAlignment = Alignment.CenterVertically,

                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Box(

                                modifier = Modifier

                                    .size(40.dp)

                                    .background(Color(0xFF2563EB).copy(alpha = 0.1f), CircleShape),

                                contentAlignment = Alignment.Center

                            ) {

                                Icon(Icons.Rounded.Assignment, contentDescription = "Active Concerns", tint = Color(0xFF2563EB))

                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {

                                Text("Your Active Concerns", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                Text("$pendingCount unresolved reports lodged", fontSize = pSize, color = Color(0xFF64748B))

                            }

                        }

                        Badge(containerColor = if (pendingCount > 0) Color(0xFFF59E0B) else Color(0xFF10B981)) {

                            Text("$pendingCount", color = Color.White, fontWeight = FontWeight.Bold, fontSize = smallSize)

                        }

                    }

                }

            }



            // Purple AI Assistant widget

            item {

                Card(

                    onClick = onNavigateToAi,

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF)),

                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

                ) {

                    Row(

                        modifier = Modifier.padding(if (isElderMode) 20.dp else 16.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Box(

                            modifier = Modifier

                                .size(if (isElderMode) 54.dp else 44.dp)

                                .background(Color(0xFF7C3AED), CircleShape),

                            contentAlignment = Alignment.Center

                        ) {

                            Icon(Icons.Rounded.AutoAwesome, contentDescription = "AI", tint = Color.White, modifier = Modifier.size(if (isElderMode) 28.dp else 22.dp))

                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {

                            Text(

                                "Consult Gemini AI Governance Officer",

                                fontSize = h2Size,

                                fontWeight = FontWeight.Bold,

                                color = Color(0xFF5B21B6)

                            )

                            Text(

                                "Get instant certificate guides, local language translations, or policy info.",

                                fontSize = pSize,

                                color = Color(0xFF6B21A8)

                            )

                        }

                        Icon(Icons.Rounded.ArrowForwardIos, contentDescription = "Forward", tint = Color(0xFF7C3AED), modifier = Modifier.size(16.dp))

                    }

                }

            }



            // Smart City IoT Live Status Widget

            item {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

                ) {

                    Column(modifier = Modifier.padding(if (isElderMode) 20.dp else 16.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.Sensors, contentDescription = "IoT", tint = Color(0xFF0EA5E9), modifier = Modifier.size(22.dp))

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Smart City Live Sensors", fontSize = h2Size, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0F172A))

                            Spacer(modifier = Modifier.weight(1f))

                            Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(Color(0xFFDCFCE7)).padding(horizontal = 6.dp, vertical = 2.dp)) {

                                Text("LIVE", fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF166534))

                            }

                        }

                        Spacer(modifier = Modifier.height(12.dp))



                        // IoT metrics grid

                        val iotMetrics = listOf(

                            Triple("🌬️", "Air Quality (AQI)", "117 — Moderate"),

                            Triple("🗑️", "Smart Bins Sector 4", "3/8 Full — Pickup Scheduled"),

                            Triple("📶", "Municipal WiFi Hotspots", "12 Active — Fast Speeds"),

                            Triple("🚗", "Parking Availability", "47 Free Slots near Main Rd"),

                            Triple("💧", "Water Quality Index", "98/100 — Excellent (Clean)"),

                            Triple("🔊", "Noise Level Zone A", "58 dB — Normal Residential")

                        )

                        iotMetrics.forEach { (emoji, label, value) ->

                            Row(

                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                                horizontalArrangement = Arrangement.SpaceBetween,

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Text(emoji, fontSize = 14.sp)

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Text(label, fontSize = pSize, color = Color(0xFF475569))

                                }

                                Text(value, fontSize = pSize, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))

                            }

                        }



                        Spacer(modifier = Modifier.height(8.dp))

                        Box(

                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(Color(0xFFEFF6FF)).padding(8.dp)

                        ) {

                            Text("📡 All sensors operating normally. Next sync in 5 min.", fontSize = smallSize, color = Color(0xFF2563EB))

                        }

                    }

                }

            }

        }



        // ==========================================

        // WARD OFFICER DASHBOARD SCREEN

        // ==========================================

        if (currentRole == "Ward Officer") {

            // Metrics Stats

            item {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(2.dp)

                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text("Ward 4 Officer Metrics", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(

                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement = Arrangement.spacedBy(8.dp)

                        ) {

                            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))) {

                                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                                    Text("${complaints.size}", fontSize = h1Size, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB))

                                    Text("Grievances", fontSize = smallSize, color = Color(0xFF64748B))

                                }

                            }

                            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))) {

                                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                                    Text("${notices.size}", fontSize = h1Size, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))

                                    Text("Notices", fontSize = smallSize, color = Color(0xFF64748B))

                                }

                            }

                            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))) {

                                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                                    Text("94.2%", fontSize = h1Size, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))

                                    Text("Resolved", fontSize = smallSize, color = Color(0xFF64748B))

                                }

                            }

                        }

                    }

                }

            }



            // Publish Announcement Form

            item {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(2.dp)

                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.NotificationsActive, contentDescription = "Notice", tint = Color(0xFFF59E0B))

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Publish Ward Notice / Alert", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                        }

                        Spacer(modifier = Modifier.height(12.dp))



                        OutlinedTextField(

                            value = noticeTitle,

                            onValueChange = { noticeTitle = it },

                            label = { Text("Notice Title") },

                            modifier = Modifier.fillMaxWidth(),

                            textStyle = TextStyle(fontSize = pSize)

                        )

                        Spacer(modifier = Modifier.height(8.dp))



                        OutlinedTextField(

                            value = noticeContent,

                            onValueChange = { noticeContent = it },

                            label = { Text("Details & Description") },

                            modifier = Modifier.fillMaxWidth(),

                            textStyle = TextStyle(fontSize = pSize)

                        )

                        Spacer(modifier = Modifier.height(8.dp))



                        Row(

                            horizontalArrangement = Arrangement.spacedBy(8.dp),

                            modifier = Modifier.fillMaxWidth()

                        ) {

                            listOf("Alert", "Tender", "Event").forEach { type ->

                                val selected = noticeType == type

                                Box(

                                    modifier = Modifier

                                        .clip(RoundedCornerShape(8.dp))

                                        .background(if (selected) Color(0xFF2563EB) else Color(0xFFF1F5F9))

                                        .clickable { noticeType = type }

                                        .padding(horizontal = 12.dp, vertical = 6.dp)

                                ) {

                                    Text(type, color = if (selected) Color.White else Color(0xFF475569), fontSize = pSize, fontWeight = FontWeight.Bold)

                                }

                            }

                        }

                        Spacer(modifier = Modifier.height(12.dp))



                        Button(

                            onClick = {

                                if (noticeTitle.isNotBlank() && noticeContent.isNotBlank()) {

                                    viewModel.publishNotice(noticeTitle, noticeContent, noticeType)

                                    Toast.makeText(context, "Ward notice published successfully!", Toast.LENGTH_SHORT).show()

                                    noticeTitle = ""

                                    noticeContent = ""

                                } else {

                                    Toast.makeText(context, "Please fill in title and description", Toast.LENGTH_SHORT).show()

                                }

                            },

                            modifier = Modifier.fillMaxWidth().height(buttonMinHeight),

                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))

                        ) {

                            Text("Publish Notice to Citizens", fontSize = pSize, fontWeight = FontWeight.Bold)

                        }

                    }

                }

            }



            // Quick Ward Grievance Dispatch Queue

            item {

                Text("📋 Ward Grievance Queue", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

                Spacer(modifier = Modifier.height(8.dp))

                if (complaints.isEmpty()) {

                    Text("No active complaints reported in Ward 4.", fontSize = pSize, color = Color(0xFF64748B))

                }

            }



            items(complaints) { complaint ->

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(12.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(1.dp)

                ) {

                    Column(modifier = Modifier.padding(14.dp)) {

                        Row(

                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement = Arrangement.SpaceBetween,

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Text(complaint.title, fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

                            Box(

                                modifier = Modifier

                                    .clip(RoundedCornerShape(6.dp))

                                    .background(Color(0xFFE2E8F0))

                                    .padding(horizontal = 6.dp, vertical = 2.dp)

                            ) {

                                Text(complaint.status, fontSize = smallSize, fontWeight = FontWeight.Bold, color = Color(0xFF475569))

                            }

                        }

                        Text(complaint.description, fontSize = pSize, color = Color(0xFF64748B), modifier = Modifier.padding(vertical = 4.dp))



                        Row(

                            horizontalArrangement = Arrangement.spacedBy(8.dp),

                            modifier = Modifier.padding(top = 8.dp)

                        ) {

                            Button(

                                onClick = {

                                    viewModel.updateComplaint(complaint.copy(status = "In Progress"))

                                    Toast.makeText(context, "Grievance marked: In Progress", Toast.LENGTH_SHORT).show()

                                },

                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),

                                modifier = Modifier.height(34.dp),

                                contentPadding = PaddingValues(horizontal = 10.dp)

                            ) {

                                Text("In Progress", fontSize = smallSize, color = Color.White)

                            }



                            Button(

                                onClick = {

                                    viewModel.updateComplaint(complaint.copy(status = "Resolved"))

                                    Toast.makeText(context, "Grievance marked: Resolved", Toast.LENGTH_SHORT).show()

                                },

                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),

                                modifier = Modifier.height(34.dp),

                                contentPadding = PaddingValues(horizontal = 10.dp)

                            ) {

                                Text("Resolve", fontSize = smallSize, color = Color.White)

                            }

                        }

                    }

                }

            }

        }



        // ==========================================

        // DEPARTMENT OFFICER DASHBOARD SCREEN

        // ==========================================

        if (currentRole == "Department Officer") {

            // Development Project status updater list

            item {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(Icons.Rounded.Build, contentDescription = "Projects", tint = Color(0xFF10B981))

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Department Projects Coordinator", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                }

                Spacer(modifier = Modifier.height(4.dp))

                Text("Manage project progress live telemetries.", fontSize = pSize, color = Color(0xFF64748B))

            }



            items(projects) { project ->

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(2.dp)

                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(

                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement = Arrangement.SpaceBetween,

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Column {

                                Text(project.name, fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                Text("Contractor: ${project.contractor} • Budget: ${project.budget}", fontSize = pSize, color = Color(0xFF64748B))

                            }

                            Box(

                                modifier = Modifier

                                    .clip(RoundedCornerShape(8.dp))

                                    .background(Color(0xFFE2E8F0))

                                    .padding(horizontal = 8.dp, vertical = 4.dp)

                            ) {

                                Text("${project.progress}% Done", fontSize = pSize, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

                            }

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(

                            progress = project.progress / 100f,

                            color = Color(0xFF2563EB),

                            trackColor = Color(0xFFF1F5F9),

                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape)

                        )

                        Spacer(modifier = Modifier.height(12.dp))



                        Row(

                            horizontalArrangement = Arrangement.spacedBy(8.dp)

                        ) {

                            Button(

                                onClick = {

                                    val nextProg = (project.progress + 10).coerceAtMost(100)

                                    viewModel.updateProject(project.copy(progress = nextProg))

                                    Toast.makeText(context, "Updated: progress increased by 10%", Toast.LENGTH_SHORT).show()

                                },

                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),

                                modifier = Modifier.height(38.dp)

                            ) {

                                Text("+10% Progress", fontSize = pSize, color = Color.White)

                            }

                            Button(

                                onClick = {

                                    viewModel.updateProject(project.copy(progress = 100))

                                    Toast.makeText(context, "Updated: marked as Completed", Toast.LENGTH_SHORT).show()

                                },

                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),

                                modifier = Modifier.height(38.dp)

                            ) {

                                Text("Mark Completed", fontSize = pSize, color = Color.White)

                            }

                        }

                    }

                }

            }



            // Certificate applications approvals list

            item {

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(Icons.Rounded.HistoryEdu, contentDescription = "Certificates", tint = Color(0xFF2563EB))

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Digital Certificate Approvals", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                }

                Spacer(modifier = Modifier.height(12.dp))

            }



            items(sharedCertificates) { cert ->

                Card(

                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(2.dp)

                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(

                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement = Arrangement.SpaceBetween,

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Column {

                                Text("${cert.type} #${cert.id}", fontSize = h2Size, fontWeight = FontWeight.Bold)

                                Text("Applicant: ${cert.applicantName}", fontSize = pSize, color = Color(0xFF64748B))

                                Text("Details: ${cert.details}", fontSize = pSize, color = Color(0xFF475569))

                            }

                            Box(

                                modifier = Modifier

                                    .clip(RoundedCornerShape(6.dp))

                                    .background(if (cert.status == "Approved") Color(0xFFDCFCE7) else Color(0xFFFEF3C7))

                                    .padding(horizontal = 8.dp, vertical = 3.dp)

                                ) {

                                    Text(cert.status, fontSize = pSize, color = if (cert.status == "Approved") Color(0xFF166534) else Color(0xFF92400E))

                                }

                            }

                            if (cert.status != "Approved") {

                                Row(modifier = Modifier.padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                                    Button(

                                        onClick = {

                                            val updated = sharedCertificates.map {

                                                if (it.id == cert.id) it.copy(status = "Approved") else it

                                            }

                                            onUpdateCertificates(updated)

                                            Toast.makeText(context, "${cert.type} approved & signed successfully!", Toast.LENGTH_SHORT).show()

                                        },

                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),

                                        modifier = Modifier.height(38.dp)

                                    ) {

                                        Text("Approve & Sign QR", fontSize = pSize, color = Color.White)

                                    }

                                }

                            }

                        }

                    }

                }

            }



        // ==========================================

        // MUNICIPALITY ADMIN DASHBOARD

        // ==========================================

        if (currentRole == "Municipality Admin") {

            item {

                Text("📊 Global Municipality Admin Portal", fontSize = h1Size, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(8.dp))



                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(2.dp)

                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text("Active Systems Telemetry KPIs", fontSize = h2Size, fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(12.dp))



                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                Text("Total Registered Users", fontSize = pSize, color = Color(0xFF475569))

                                Text("8,421 citizens", fontSize = pSize, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                            }

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                Text("Annual Revenue Received", fontSize = pSize, color = Color(0xFF475569))

                                Text("$1.2M", fontSize = pSize, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))

                            }

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                Text("Emergency Services Hub Status", fontSize = pSize, color = Color(0xFF475569))

                                Text("ALL UNITS ONLINE", fontSize = pSize, fontWeight = FontWeight.Bold, color = Color(0xFF22C55E))

                            }

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                Text("Average Grievance Resolve Time", fontSize = pSize, color = Color(0xFF475569))

                                Text("3.2 Hours (Fast)", fontSize = pSize, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB))

                            }

                        }

                    }

                }

            }



            // Emergency SOS dispatch control center

            item {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),

                    elevation = CardDefaults.cardElevation(2.dp)

                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.HealthAndSafety, contentDescription = "SOS Rescue", tint = Color(0xFFEF4444))

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Emergency SOS Dispatch Command", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF991B1B))

                        }

                        Spacer(modifier = Modifier.height(12.dp))



                        sharedEmergencyAlerts.forEach { alert ->

                            Column(modifier = Modifier.padding(vertical = 6.dp)) {

                                Row(

                                    modifier = Modifier.fillMaxWidth(),

                                    horizontalArrangement = Arrangement.SpaceBetween,

                                    verticalAlignment = Alignment.CenterVertically

                                ) {

                                    Column {

                                        Text(alert.title, fontSize = pSize, fontWeight = FontWeight.Bold, color = Color(0xFF7F1D1D))

                                        Text("Type: ${alert.type} • Urgency: ${alert.urgency}", fontSize = smallSize, color = Color(0xFF991B1B))

                                    }

                                    Box(

                                        modifier = Modifier

                                            .clip(RoundedCornerShape(6.dp))

                                            .background(if (alert.status == "Units Dispatched!") Color(0xFFDCFCE7) else Color(0xFFFEE2E2))

                                            .padding(horizontal = 8.dp, vertical = 3.dp)

                                    ) {

                                        Text(alert.status, fontSize = pSize, color = if (alert.status == "Units Dispatched!") Color(0xFF166534) else Color(0xFFB91C1C))

                                    }

                                }

                                if (alert.status != "Units Dispatched!") {

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Button(

                                        onClick = {

                                            val updated = sharedEmergencyAlerts.map {

                                                if (it.id == alert.id) it.copy(status = "Units Dispatched!") else it

                                            }

                                            onUpdateEmergencyAlerts(updated)

                                            Toast.makeText(context, "Dispatch signal broadcasted for: ${alert.title}", Toast.LENGTH_LONG).show()

                                        },

                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),

                                        modifier = Modifier.height(36.dp)

                                    ) {

                                        Text("Dispatch First Responders (GPS Tracking)", fontSize = smallSize, color = Color.White)

                                    }

                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Divider(color = Color(0xFFFECDD3))

                            }

                        }

                    }

                }

            }

        }



        // ==========================================

        // MAYOR / REPRESENTATIVES DASHBOARD

        // ==========================================

        if (currentRole == "Mayor / Representative") {

            // Mayor Broadcast Direct message form

            item {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(20.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(3.dp)

                ) {

                    Column(modifier = Modifier.padding(18.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.AccountBox, contentDescription = "Mayor", tint = Color(0xFF7C3AED), modifier = Modifier.size(28.dp))

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Mayor's Direct Direct Broadcasts", fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                        }

                        Text("Broadcast priority messages immediately to the citizen homepages.", fontSize = pSize, color = Color(0xFF64748B))

                        Spacer(modifier = Modifier.height(14.dp))



                        OutlinedTextField(

                            value = mayorBroadcastTitle,

                            onValueChange = { mayorBroadcastTitle = it },

                            label = { Text("Broadcast Title Heading") },

                            modifier = Modifier.fillMaxWidth(),

                            textStyle = TextStyle(fontSize = pSize)

                        )

                        Spacer(modifier = Modifier.height(8.dp))



                        OutlinedTextField(

                            value = mayorBroadcastContent,

                            onValueChange = { mayorBroadcastContent = it },

                            label = { Text("Mayor's Address Message content...") },

                            modifier = Modifier.fillMaxWidth(),

                            textStyle = TextStyle(fontSize = pSize)

                        )

                        Spacer(modifier = Modifier.height(12.dp))



                        Button(

                            onClick = {

                                if (mayorBroadcastTitle.isNotBlank() && mayorBroadcastContent.isNotBlank()) {

                                    viewModel.publishNotice(mayorBroadcastTitle, "Mayor: $mayorBroadcastContent", "Mayor Alert")

                                    Toast.makeText(context, "Mayor's Direct message broadcasted successfully!", Toast.LENGTH_SHORT).show()

                                    mayorBroadcastTitle = ""

                                    mayorBroadcastContent = ""

                                } else {

                                    Toast.makeText(context, "Fill in the broadcast content first", Toast.LENGTH_SHORT).show()

                                }

                            },

                            modifier = Modifier.fillMaxWidth().height(buttonMinHeight),

                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))

                        ) {

                            Text("Broadcast Direct Message", fontSize = pSize, color = Color.White, fontWeight = FontWeight.Bold)

                        }

                    }

                }

            }



            // Suggestions Reviewer & Vote Tracker

            item {

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(Icons.Rounded.TipsAndUpdates, contentDescription = "Suggestions", tint = Color(0xFFEAB308))

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Review Citizens Development Suggestions", fontSize = h2Size, fontWeight = FontWeight.Bold)

                }

                Spacer(modifier = Modifier.height(12.dp))



                sharedSuggestions.forEach { item ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        shape = RoundedCornerShape(16.dp),

                        colors = CardDefaults.cardColors(containerColor = Color.White),

                        elevation = CardDefaults.cardElevation(1.dp)

                    ) {

                        Row(

                            modifier = Modifier.padding(16.dp),

                            verticalAlignment = Alignment.CenterVertically,

                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {

                            Column(modifier = Modifier.weight(1f)) {

                                Text(item.title, fontSize = h2Size, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                Text(item.description, fontSize = pSize, color = Color(0xFF64748B))

                                Text("Popularity support: ${item.votes} upvotes • Status: ${if (item.funded) "FUNDED BY MAYOR" else "Reviewing"}", fontSize = pSize, fontWeight = FontWeight.Bold, color = if (item.funded) Color(0xFF10B981) else Color(0xFFF59E0B))

                            }

                            if (!item.funded) {

                                Button(

                                    onClick = {

                                        val updated = sharedSuggestions.map {

                                            if (it.id == item.id) it.copy(funded = true, votes = item.votes + 1) else it

                                        }

                                        onUpdateSuggestions(updated)

                                        Toast.makeText(context, "Approved Budget Allocation for: ${item.title}!", Toast.LENGTH_SHORT).show()

                                    },

                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),

                                    modifier = Modifier.height(38.dp)

                                ) {

                                    Text("Fund Project", fontSize = pSize, color = Color.White)

                                }

                            }

                        }

                    }

                }

            }

        }

    }



    // ==========================================

    // DIALOG: READ NOTICES IN LARGE FONT (Elder Mode)

    // ==========================================

    if (showElderNoticesDialog) {

        AlertDialog(

            onDismissRequest = { showElderNoticesDialog = false },

            confirmButton = {

                Button(

                    onClick = { showElderNoticesDialog = false },

                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),

                    modifier = Modifier.fillMaxWidth().height(60.dp)

                ) {

                    Text("Close Board", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                }

            },

            title = {

                Text("📰 Notice Board (Large Text)", fontSize = 24.sp, fontWeight = FontWeight.Black)

            },

            text = {

                Column(

                    modifier = Modifier.verticalScroll(rememberScrollState()),

                    verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {

                    if (notices.isEmpty()) {

                        Text("No notifications at this time.", fontSize = 20.sp)

                    } else {

                        notices.forEach { notice ->

                            Card(

                                modifier = Modifier.fillMaxWidth(),

                                shape = RoundedCornerShape(12.dp),

                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))

                            ) {

                                Column(modifier = Modifier.padding(16.dp)) {

                                    Text("📢 ${notice.title}", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0F172A))

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(notice.content, fontSize = 18.sp, color = Color(0xFF334155), lineHeight = 24.sp)

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text("Published Date: ${notice.date} • Type: ${notice.type}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))

                                }

                            }

                        }

                    }

                }

            },

            shape = RoundedCornerShape(24.dp),

            containerColor = Color.White

        )

    }



    // ==========================================

    // DIALOG: PENSION & CERTIFICATES HELP (Elder Mode)

    // ==========================================

    if (showElderCertGuideDialog) {

        AlertDialog(

            onDismissRequest = { showElderCertGuideDialog = false },

            confirmButton = {

                Button(

                    onClick = { showElderCertGuideDialog = false },

                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),

                    modifier = Modifier.fillMaxWidth().height(60.dp)

                ) {

                    Text("Got It, Thank You!", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                }

            },

            title = {

                Text("📜 Senior Pension & Documents Guide", fontSize = 24.sp, fontWeight = FontWeight.Black)

            },

            text = {

                Column(

                    modifier = Modifier.verticalScroll(rememberScrollState()),

                    verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5))

                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            Text("👵 Senior Citizens Pension Guide", fontSize = 21.sp, fontWeight = FontWeight.Bold, color = Color(0xFF065F46))

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("1. Eligibility: Citizens aged 60 and above.\n" +

                                    "2. Documents Needed: Identity Proof (ID), Resident Verification, Income Slip.\n" +

                                    "3. Cost: Completely Free to apply.\n" +

                                    "4. Time: Normally processed in 10 working days.",

                                fontSize = 18.sp, color = Color(0xFF047857), lineHeight = 24.sp)

                        }

                    }



                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F9FF))

                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            Text("👶 Birth Certificate Guideline", fontSize = 21.sp, fontWeight = FontWeight.Bold, color = Color(0xFF075985))

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("1. Documents: Hospital Birth Slip, Parents IDs.\n" +

                                    "2. Process: Register on portal -> Approval in 3 days -> Download certified PDF with verifiable QR code.",

                                fontSize = 18.sp, color = Color(0xFF0369A1), lineHeight = 24.sp)

                        }

                    }

                }

            },

            shape = RoundedCornerShape(24.dp),

            containerColor = Color.White

        )

    }



    // ==========================================

    // SIMULATED PHONE CALL HUD OVERLAY

    // ==========================================

    simPhoneCallNumber?.let { number ->

        AlertDialog(

            onDismissRequest = { simPhoneCallNumber = null },

            confirmButton = {

                Button(

                    onClick = { simPhoneCallNumber = null },

                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),

                    modifier = Modifier.fillMaxWidth().height(60.dp)

                ) {

                    Text("Hang Up", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)

                }

            },

            title = {

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {

                    Icon(Icons.Rounded.PhoneInTalk, contentDescription = "Calling", tint = Color(0xFF10B981), modifier = Modifier.size(54.dp))

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Connecting Senior Support...", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                }

            },

            text = {

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {

                    Text("Dialing: $number", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF475569))

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Simulating Call Dispatch Connection...", fontSize = 16.sp, color = Color(0xFF64748B))

                }

            },

            shape = RoundedCornerShape(24.dp),

            containerColor = Color.White

        )

    }

}



// =========================================================================

// 4. SCREEN: AI ASSISTANT CHAT (Purple-themed)

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun AiAssistantScreen(

    viewModel: CivicViewModel,

    selectedLanguage: Language = Language.ENGLISH,

    isElderMode: Boolean = false

) {

    val messages by viewModel.aiMessages.collectAsState()

    val loading by viewModel.aiLoading.collectAsState()

    val apiKey by viewModel.apiKey.collectAsState()



    var input by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()



    val suggestions = when (selectedLanguage) {

        Language.HINDI -> listOf(

            "मैं निवास प्रमाणपत्र के लिए कैसे आवेदन करूँ?",

            "सेक्टर 4 संपत्ति कर छूट स्पष्ट करें",

            "हरित सौर रूफटॉप योजनाएं क्या हैं?"

        )

        Language.MARATHI -> listOf(

            "मी रहिवासी प्रमाणपत्रासाठी कसा अर्ज करू?",

            "सेक्टर ४ मालमत्ता कर सवलत स्पष्ट करा",

            "हरित सौर रूफटॉप योजना काय आहेत?"

        )

        Language.KANNADA -> listOf(

            "ವಾಸಸ್ಥಳ ಪ್ರಮಾಣಪತ್ರಕ್ಕೆ ಅರ್ಜಿ ಸಲ್ಲಿಸುವುದು ಹೇಗೆ?",

            "ಸೆಕ್ಟರ್ 4 ಆಸ್ತಿ ತೆರಿಗೆ ರಿಯಾಯಿತಿಯನ್ನು ವಿವರಿಸಿ",

            "ಹಸಿರು ಸೌರ ಮೇಲ್ಛಾವಣಿ ಯೋಜನೆಗಳು ಯಾವುವು?"

        )

        Language.TAMIL -> listOf(

            "இருப்பிடச் சான்றிதழுக்கு எவ்வாறு விண்ணப்பிப்பது?",

            "துறை 4 சொத்து வரி தள்ளுபடி பற்றி விளக்கவும்",

            "பசுமை சூரிய கூரை திட்டங்கள் என்னென்ன?"

        )

        else -> listOf(

            "How do I apply for a residence certificate?",

            "Explain Sector 4 property tax discount",

            "What are green solar rooftop schemes?"

        )

    }



    Column(

        modifier = Modifier

            .fillMaxSize()

            .background(Color(0xFFFDFEFE))

    ) {

        // AI Header Bar

        Box(

            modifier = Modifier

                .fillMaxWidth()

                .background(

                    Brush.horizontalGradient(

                        colors = listOf(Color(0xFF7C3AED), Color(0xFFA855F7))

                    )

                )

                .padding(16.dp)

        ) {

            Row(

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.SpaceBetween,

                modifier = Modifier.fillMaxWidth()

            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(

                        modifier = Modifier

                            .size(36.dp)

                            .background(Color.White.copy(alpha = 0.2f), CircleShape),

                        contentAlignment = Alignment.Center

                    ) {

                        Icon(Icons.Rounded.AutoAwesome, contentDescription = "AI icon", tint = Color.White)

                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {

                        Text(

                            "Gemini Smart Gov Officer",

                            fontWeight = FontWeight.Bold,

                            color = Color.White,

                            fontSize = 15.sp

                        )

                        Text(

                            text = "Online • Official Gov Support",

                            color = Color.White.copy(alpha = 0.85f),

                            fontSize = 11.sp,

                            fontWeight = FontWeight.Medium

                        )

                    }

                }



                TextButton(

                    onClick = { viewModel.clearChat() },

                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White)

                ) {

                    Text("Clear", fontSize = 12.sp)

                }

            }

        }



        // Messages list

        LazyColumn(

            modifier = Modifier

                .weight(1f)

                .padding(horizontal = 16.dp, vertical = 8.dp),

            verticalArrangement = Arrangement.spacedBy(10.dp)

        ) {

            items(messages) { message ->

                Box(

                    modifier = Modifier.fillMaxWidth(),

                    contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart

                ) {

                    Card(

                        shape = RoundedCornerShape(

                            topStart = 16.dp,

                            topEnd = 16.dp,

                            bottomStart = if (message.isUser) 16.dp else 0.dp,

                            bottomEnd = if (message.isUser) 0.dp else 16.dp

                        ),

                        colors = CardDefaults.cardColors(

                            containerColor = if (message.isUser) Color(0xFFF3E8FF) else Color(0xFFF1F5F9)

                        ),

                        modifier = Modifier.widthIn(max = 280.dp)

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Text(

                                text = message.content,

                                fontSize = 13.sp,

                                color = if (message.isUser) Color(0xFF6B21A8) else Color(0xFF0F172A)

                            )

                        }

                    }

                }

            }



            if (loading) {

                item {

                    Row(

                        verticalAlignment = Alignment.CenterVertically,

                        modifier = Modifier.padding(8.dp)

                    ) {

                        CircularProgressIndicator(

                            modifier = Modifier.size(16.dp),

                            color = Color(0xFF7C3AED),

                            strokeWidth = 2.dp

                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Gemini is composing official guidance...", fontSize = 11.sp, color = Color(0xFF64748B))

                    }

                }

            }

        }



        // Suggestion prompt chips

        if (messages.size == 1) {

            LazyRow(

                modifier = Modifier

                    .fillMaxWidth()

                    .padding(horizontal = 16.dp, vertical = 4.dp),

                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {

                items(suggestions) { query ->

                    Card(

                        onClick = { viewModel.askAi(query) },

                        shape = RoundedCornerShape(20.dp),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF5FF)),

                        modifier = Modifier.border(1.dp, Color(0xFFE9D5FF), RoundedCornerShape(20.dp))

                    ) {

                        Text(

                            text = query,

                            fontSize = 11.sp,

                            color = Color(0xFF7C3AED),

                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),

                            maxLines = 1,

                            overflow = TextOverflow.Ellipsis

                        )

                    }

                }

            }

        }





        // Input bar

        Row(

            modifier = Modifier

                .fillMaxWidth()

                .padding(12.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            OutlinedTextField(

                value = input,

                onValueChange = { input = it },

                placeholder = { Text("Ask about permits, solar subsidies, or taxes...") },

                modifier = Modifier

                    .weight(1f)

                    .testTag("ai_chat_input"),

                shape = RoundedCornerShape(24.dp),

                colors = OutlinedTextFieldDefaults.colors(

                    focusedContainerColor = Color(0xFFF8FAFC),

                    unfocusedContainerColor = Color(0xFFF8FAFC)

                ),

                maxLines = 2,

                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),

                keyboardActions = KeyboardActions(onSend = {

                    if (input.isNotBlank()) {

                        viewModel.askAi(input)

                        input = ""

                    }

                })

            )



            Spacer(modifier = Modifier.width(8.dp))



            FloatingActionButton(

                onClick = {

                    if (input.isNotBlank()) {

                        viewModel.askAi(input)

                        input = ""

                    }

                },

                containerColor = Color(0xFF7C3AED),

                contentColor = Color.White,

                shape = CircleShape,

                modifier = Modifier.size(48.dp).testTag("ai_send_button")

            ) {

                Icon(Icons.Rounded.Send, contentDescription = "Send", modifier = Modifier.size(18.dp))

            }

        }

    }

}



// =========================================================================

// 5. SCREEN: SMART COMPLAINT SYSTEM WITH AI CATEGORIZATION

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun ComplaintsScreen(

    viewModel: CivicViewModel,

    currentRole: String = "Citizen",

    selectedLanguage: Language = Language.ENGLISH,

    isElderMode: Boolean = false

) {

    val complaints by viewModel.complaints.collectAsState()

    val submitState by viewModel.submitState.collectAsState()

    val context = LocalContext.current



    var showReportSheet by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }

    var selectedCategory by remember { mutableStateOf<String?>(null) }

    var complaintPhotoAttached by remember { mutableStateOf(false) }

    var complaintLocation by remember { mutableStateOf("Tap pin to tag location") }



    // AI Autofill state

    var aiDraftText by remember { mutableStateOf("") }

    var aiDraftLoading by remember { mutableStateOf(false) }



    var resolvingId by remember { mutableStateOf<Int?>(null) }

    var resolutionNote by remember { mutableStateOf("") }

    var resolutionPhotoAttached by remember { mutableStateOf(false) }



    var ratingId by remember { mutableStateOf<Int?>(null) }

    var ratingStars by remember { mutableStateOf(0) }

    var ratingComment by remember { mutableStateOf("") }

    var tipAmount by remember { mutableStateOf("") }

    var searchQuery by remember { mutableStateOf("") }

    var selectedFilterCategory by remember { mutableStateOf<String?>("All") }

    val categories = listOf("Roads", "Water Supply", "Garbage", "Drainage", "Electricity", "Public Safety", "Parks")



    LaunchedEffect(submitState) {

        when (submitState) {

            is SubmitState.Success -> {

                Toast.makeText(context, "Complaint lodged!", Toast.LENGTH_LONG).show()

                showReportSheet = false; title = ""; description = ""; selectedCategory = null

                complaintPhotoAttached = false; complaintLocation = "Tap pin to tag location"

                viewModel.resetSubmitState()

            }

            is SubmitState.Error -> {

                Toast.makeText(context, (submitState as SubmitState.Error).message, Toast.LENGTH_LONG).show()

                viewModel.resetSubmitState()

            }

            else -> {}

        }

    }



    val screenTitle = when (selectedLanguage) {

        Language.HINDI -> "स्मार्ट शिकायत केंद्र"

        Language.MARATHI -> "स्मार्ट तक्रार निवारण"

        Language.KANNADA -> "ಸ್ಮಾರ್ಟ್ ದೂರು ಕೇಂದ್ರ"

        Language.TAMIL -> "ஸ்மார்ட் குறை தீர்க்கும் மையம்"

        else -> "Smart Grievance Hub"

    }



    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF2F2F7))) {

        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp)) {



            if (currentRole != "Citizen") {

                Card(

                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),

                    shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(0.dp)

                ) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.Info, null, tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(

                            if (currentRole == "Worker") "Worker View — only assigned complaints shown." else "Admin View — all complaints visible.",

                            fontSize = 11.sp, color = Color(0xFF92400E), fontWeight = FontWeight.Medium

                        )

                    }

                }

            }



            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                Text(screenTitle, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0F172A), letterSpacing = (-0.5).sp)

                Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color(0xFF007AFF).copy(alpha = 0.1f)).padding(horizontal = 10.dp, vertical = 4.dp)) {

                    Text("${complaints.size} Cases", color = Color(0xFF007AFF), fontSize = 11.sp, fontWeight = FontWeight.Bold)

                }

            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search grievances...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = "Search") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF007AFF),
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Category Filter Chips
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    val isSelected = selectedFilterCategory == "All"
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) Color(0xFF007AFF) else Color.White)
                            .clickable { selectedFilterCategory = "All" }
                            .border(BorderStroke(1.dp, if (isSelected) Color.Transparent else Color(0xFFE2E8F0)), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("All", color = if (isSelected) Color.White else Color(0xFF475569), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
                items(categories) { cat ->
                    val isSelected = selectedFilterCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) Color(0xFF007AFF) else Color.White)
                            .clickable { selectedFilterCategory = cat }
                            .border(BorderStroke(1.dp, if (isSelected) Color.Transparent else Color(0xFFE2E8F0)), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(cat, color = if (isSelected) Color.White else Color(0xFF475569), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            val filteredComplaints = complaints.filter { complaint ->
                val matchesSearch = complaint.title.contains(searchQuery, ignoreCase = true) || 
                                    complaint.description.contains(searchQuery, ignoreCase = true)
                val matchesCategory = selectedFilterCategory == "All" || complaint.category == selectedFilterCategory
                matchesSearch && matchesCategory
            }

            if (filteredComplaints.isEmpty()) {

                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text("📋", fontSize = 52.sp)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("No grievances filed yet.", color = Color(0xFF94A3B8), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

                        if (currentRole == "Citizen") Text("Tap + below to report an issue.", color = Color(0xFFCBD5E1), fontSize = 13.sp)

                    }

                }

            } else {

                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(bottom = 80.dp)) {

                    items(filteredComplaints) { complaint ->

                        val visible = currentRole != "Worker" || complaint.workerName == "Rajan Patel" || complaint.workerName == null

                        if (!visible) return@items



                        Card(

                            modifier = Modifier.fillMaxWidth().testTag("complaint_item"),

                            colors = CardDefaults.cardColors(containerColor = Color.White),

                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),

                            shape = RoundedCornerShape(18.dp)

                        ) {

                            Column(modifier = Modifier.padding(16.dp)) {

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                                    Column(modifier = Modifier.weight(1f)) {

                                        Text(complaint.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF0F172A))

                                        Row(modifier = Modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                                            Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(Color(0xFFE2E8F0)).padding(horizontal = 6.dp, vertical = 2.dp)) {

                                                Text(complaint.category, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF475569))

                                            }

                                            val (urgBg, urgColor) = when (complaint.urgency) {

                                                "Low" -> Color(0xFFDCFCE7) to Color(0xFF166534)

                                                "Medium" -> Color(0xFFFEF3C7) to Color(0xFF92400E)

                                                else -> Color(0xFFFEE2E2) to Color(0xFF991B1B)

                                            }

                                            Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(urgBg).padding(horizontal = 6.dp, vertical = 2.dp)) {

                                                Text(complaint.urgency, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = urgColor)

                                            }

                                        }

                                    }

                                    val (stBg, stColor) = when (complaint.status) {

                                        "Submitted" -> Color(0xFFEFF6FF) to Color(0xFF2563EB)

                                        "In Progress" -> Color(0xFFFFF7ED) to Color(0xFFEA580C)

                                        "Resolved" -> Color(0xFFECFDF5) to Color(0xFF10B981)

                                        else -> Color(0xFFF8FAFC) to Color(0xFF64748B)

                                    }

                                    Box(modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(stBg).padding(horizontal = 10.dp, vertical = 4.dp)) {

                                        Text(complaint.status, color = stColor, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)

                                    }

                                }



                                Spacer(modifier = Modifier.height(8.dp))

                                Text(complaint.description, fontSize = 12.sp, color = Color(0xFF475569), maxLines = 3, overflow = TextOverflow.Ellipsis)

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                    Text("📍 ${complaint.location}", fontSize = 10.sp, color = Color(0xFF64748B))

                                    Text("Dept: ${complaint.department}", fontSize = 10.sp, color = Color(0xFF007AFF), fontWeight = FontWeight.SemiBold)

                                }



                                Spacer(modifier = Modifier.height(8.dp))

                                StatusTimeline(status = complaint.status, rating = complaint.rating)



                                // Admin/Municipality Admin: View uploaded photos

                                if (currentRole == "Admin" || currentRole == "Municipality Admin") {

                                    if (!complaint.imageBase64.isNullOrBlank()) {

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text("📸 Issue Photo (Admin View):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF475569))

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Card(

                                            modifier = Modifier.fillMaxWidth().height(110.dp),

                                            shape = RoundedCornerShape(10.dp),

                                            elevation = CardDefaults.cardElevation(0.dp)

                                        ) {

                                            val issueBitmap = remember(complaint.imageBase64) {

                                                try {

                                                    val bytes = Base64.decode(complaint.imageBase64, Base64.DEFAULT)

                                                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()

                                                } catch (e: Exception) { null }

                                            }

                                            if (issueBitmap != null) {

                                                androidx.compose.foundation.Image(

                                                    bitmap = issueBitmap,

                                                    contentDescription = "Issue Photo",

                                                    modifier = Modifier.fillMaxSize(),

                                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop

                                                )

                                            } else {

val (placeholderGradient, placeholderIcon, placeholderColor) = when (complaint.category) {

                                                    "Water Supply" -> Triple(Brush.linearGradient(listOf(Color(0xFFE0F2FE), Color(0xFF38BDF8))), Icons.Rounded.WaterDrop, Color(0xFF0369A1))

                                                    "Roads" -> Triple(Brush.linearGradient(listOf(Color(0xFFF1F5F9), Color(0xFF94A3B8))), Icons.Rounded.Build, Color(0xFF334155))

                                                    "Garbage" -> Triple(Brush.linearGradient(listOf(Color(0xFFF0FDF4), Color(0xFF4ADE80))), Icons.Rounded.Delete, Color(0xFF166534))

                                                    "Drainage" -> Triple(Brush.linearGradient(listOf(Color(0xFFE0F2FE), Color(0xFF0EA5E9))), Icons.Rounded.Water, Color(0xFF0369A1))

                                                    "Electricity" -> Triple(Brush.linearGradient(listOf(Color(0xFFFFFBEB), Color(0xFFFBBF24))), Icons.Rounded.ElectricBolt, Color(0xFF92400E))

                                                    "Public Safety" -> Triple(Brush.linearGradient(listOf(Color(0xFFFEE2E2), Color(0xFFF87171))), Icons.Rounded.Security, Color(0xFF991B1B))

                                                    "Parks" -> Triple(Brush.linearGradient(listOf(Color(0xFFECFDF5), Color(0xFF34D399))), Icons.Rounded.Eco, Color(0xFF065F46))

                                                    else -> Triple(Brush.linearGradient(listOf(Color(0xFFF5F3FF), Color(0xFFA78BFA))), Icons.Rounded.Image, Color(0xFF5B21B6))

                                                }

                                                Box(

                                                    modifier = Modifier.fillMaxSize().background(placeholderGradient),

                                                    contentAlignment = Alignment.Center

                                                ) {

                                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                                        Icon(placeholderIcon, null, tint = placeholderColor, modifier = Modifier.size(24.dp))

                                                        Spacer(modifier = Modifier.height(4.dp))

                                                        Text("Premium Photo Viewer — ${complaint.category}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = placeholderColor)

                                                        Text("Attachment Verified ✓", fontSize = 9.sp, color = placeholderColor.copy(alpha = 0.8f))

                                                    }

                                                }

                                            }

                                        }

                                    }

                                    if (!complaint.resolvedImageBase64.isNullOrBlank()) {

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text("📸 Resolution Photo (Admin View):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF166534))

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Card(

                                            modifier = Modifier.fillMaxWidth().height(110.dp),

                                            shape = RoundedCornerShape(10.dp),

                                            elevation = CardDefaults.cardElevation(0.dp)

                                        ) {

                                            val resolvedBitmap = remember(complaint.resolvedImageBase64) {

                                                try {

                                                    val bytes = Base64.decode(complaint.resolvedImageBase64, Base64.DEFAULT)

                                                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()

                                                } catch (e: Exception) { null }

                                            }

                                            if (resolvedBitmap != null) {

                                                androidx.compose.foundation.Image(

                                                    bitmap = resolvedBitmap,

                                                    contentDescription = "Resolution Photo",

                                                    modifier = Modifier.fillMaxSize(),

                                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop

                                                )

                                            } else {

Box(

                                                    modifier = Modifier.fillMaxSize().background(Brush.linearGradient(listOf(Color(0xFFECFDF5), Color(0xFF10B981)))),

                                                    contentAlignment = Alignment.Center

                                                ) {

                                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                                        Icon(Icons.Rounded.TaskAlt, null, tint = Color.White, modifier = Modifier.size(26.dp))

                                                        Spacer(modifier = Modifier.height(4.dp))

                                                        Text("Worker Resolution Photo Verified ✓", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)

                                                        Text("Notes: ${complaint.resolutionNote ?: "Resolved successfully"}", fontSize = 9.sp, color = Color.White.copy(alpha = 0.85f))

                                                    }

                                                }

                                            }

                                        }

                                    }

                                }



                                if (complaint.workerName != null && complaint.status == "In Progress") {

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(0.dp)) {

                                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                                            Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFFEA580C).copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {

                                                Icon(Icons.Rounded.Build, null, tint = Color(0xFFEA580C), modifier = Modifier.size(18.dp))

                                            }

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Column {

                                                Text("Worker Assigned", fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFEA580C))

                                                Text(complaint.workerName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                                if (complaint.workerInfo != null) Text(complaint.workerInfo, fontSize = 10.sp, color = Color(0xFF64748B))

                                            }

                                        }

                                    }

                                }



                                if (complaint.status == "Resolved") {

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(0.dp)) {

                                        Column(modifier = Modifier.padding(10.dp)) {

                                            Row(verticalAlignment = Alignment.CenterVertically) {

                                                Icon(Icons.Rounded.CheckCircle, null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))

                                                Spacer(modifier = Modifier.width(6.dp))

                                                Text("Resolved by ${complaint.workerName ?: "Municipal Worker"}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF065F46))

                                            }

                                            if (complaint.resolutionNote != null) {

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Text(complaint.resolutionNote, fontSize = 11.sp, color = Color(0xFF047857))

                                            }

                                            if (complaint.rating > 0) {

                                                Spacer(modifier = Modifier.height(6.dp))

                                                Row(verticalAlignment = Alignment.CenterVertically) {

                                                    repeat(5) { i -> Icon(if (i < complaint.rating) Icons.Rounded.Star else Icons.Rounded.StarOutline, null, tint = Color(0xFFF59E0B), modifier = Modifier.size(14.dp)) }

                                                    if (!complaint.ratingComment.isNullOrBlank()) {

                                                        Spacer(modifier = Modifier.width(4.dp))

                                                        Text("\"${complaint.ratingComment}\"", fontSize = 9.sp, color = Color(0xFF64748B))

                                                    }

                                                }

                                            }

                                            if (complaint.payoutStatus == "Paid") {

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Row(verticalAlignment = Alignment.CenterVertically) {

                                                    Icon(Icons.Rounded.Payments, null, tint = Color(0xFF10B981), modifier = Modifier.size(13.dp))

                                                    Spacer(modifier = Modifier.width(4.dp))

                                                    Text("Govt payout: Rs.${complaint.governmentPayout.toInt()} processed", fontSize = 10.sp, color = Color(0xFF065F46), fontWeight = FontWeight.SemiBold)

                                                }

                                            }

                                        }

                                    }

                                    if (currentRole == "Citizen" && complaint.rating == 0) {

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Button(

                                            onClick = { ratingId = complaint.id; ratingStars = 0; ratingComment = ""; tipAmount = "" },

                                            shape = RoundedCornerShape(12.dp),

                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),

                                            modifier = Modifier.fillMaxWidth()

                                        ) {

                                            Icon(Icons.Rounded.Star, null, modifier = Modifier.size(15.dp))

                                            Spacer(modifier = Modifier.width(6.dp))

                                            Text("Rate Resolution & Tip Worker", fontSize = 13.sp)

                                        }

                                    }

                                }



                                if (currentRole == "Worker" && complaint.status == "In Progress" && complaint.workerName != null) {

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Button(

                                        onClick = { resolvingId = complaint.id; resolutionNote = ""; resolutionPhotoAttached = false },

                                        shape = RoundedCornerShape(12.dp),

                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),

                                        modifier = Modifier.fillMaxWidth()

                                    ) {

                                        Icon(Icons.Rounded.CheckCircle, null, modifier = Modifier.size(16.dp))

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text("Mark as Resolved")

                                    }

                                }

                                if (currentRole == "Worker" && complaint.workerName == null) {

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Button(

                                        onClick = {

                                            viewModel.assignWorker(complaint.id, "Rajan Patel", "Senior field technician, Ward 4")

                                        },

                                        shape = RoundedCornerShape(12.dp),

                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),

                                        modifier = Modifier.fillMaxWidth()

                                    ) {

                                        Icon(Icons.Rounded.Build, null, modifier = Modifier.size(16.dp))

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text("Accept & Assign to Self")

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }



        if (currentRole == "Citizen") {

            FloatingActionButton(

                onClick = { showReportSheet = true },

                modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp).testTag("add_complaint_fab"),

                containerColor = Color(0xFF007AFF), contentColor = Color.White, shape = CircleShape

            ) { Icon(Icons.Rounded.Add, "Add") }

        }



        if (showReportSheet) {

            AlertDialog(

                onDismissRequest = { showReportSheet = false },

                shape = RoundedCornerShape(24.dp), containerColor = Color.White,

                title = {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFF007AFF).copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {

                            Icon(Icons.Rounded.Report, null, tint = Color(0xFF007AFF), modifier = Modifier.size(20.dp))

                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text("Log New Grievance", fontWeight = FontWeight.Bold, fontSize = 17.sp)

                    }

                },

                text = {

                    Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        // ✨ AI Assistant Autofill Section

                        Card(

                            modifier = Modifier.fillMaxWidth(),

                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F0FF)),

                            shape = RoundedCornerShape(14.dp),

                            border = BorderStroke(1.dp, Color(0xFF7C3AED).copy(alpha = 0.25f)),

                            elevation = CardDefaults.cardElevation(0.dp)

                        ) {

                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Box(

                                        modifier = Modifier.size(28.dp).clip(CircleShape).background(Color(0xFF7C3AED).copy(alpha = 0.12f)),

                                        contentAlignment = Alignment.Center

                                    ) {

                                        Text("✨", fontSize = 14.sp)

                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Column {

                                        Text("AI Assistant Autofill", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6D28D9))

                                        Text("Describe the issue casually — AI will structure it.", fontSize = 10.sp, color = Color(0xFF8B5CF6))

                                    }

                                }

                                OutlinedTextField(

                                    value = aiDraftText,

                                    onValueChange = { aiDraftText = it },

                                    label = { Text("Describe the issue (conversational)") },

                                    placeholder = { Text("e.g. leak near sector b road since last week") },

                                    modifier = Modifier.fillMaxWidth(),

                                    shape = RoundedCornerShape(10.dp),

                                    minLines = 2,

                                    colors = OutlinedTextFieldDefaults.colors(

                                        focusedBorderColor = Color(0xFF7C3AED),

                                        unfocusedBorderColor = Color(0xFF7C3AED).copy(alpha = 0.3f)

                                    )

                                )

                                Button(

                                    onClick = {

                                        if (aiDraftText.isNotBlank()) {

                                            aiDraftLoading = true

                                            viewModel.draftComplaintWithAi(aiDraftText) { draftedTitle, draftedDesc ->

                                                title = draftedTitle

                                                description = draftedDesc

                                                aiDraftLoading = false

                                                aiDraftText = ""

                                            }

                                        }

                                    },

                                    modifier = Modifier.fillMaxWidth(),

                                    shape = RoundedCornerShape(10.dp),

                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)),

                                    enabled = aiDraftText.isNotBlank() && !aiDraftLoading

                                ) {

                                    if (aiDraftLoading) {

                                        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = Color.White)

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text("Autofilling...", fontSize = 12.sp, color = Color.White)

                                    } else {

                                        Text("✨ Autofill Form", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)

                                    }

                                }

                            }

                        }



                        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Issue Title") }, placeholder = { Text("e.g. Pothole on Ring Road") }, modifier = Modifier.fillMaxWidth().testTag("complaint_title_input"), shape = RoundedCornerShape(12.dp))

                        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, placeholder = { Text("Describe in detail. AI will auto-categorize.") }, modifier = Modifier.fillMaxWidth().testTag("complaint_desc_input"), minLines = 3, shape = RoundedCornerShape(12.dp))

                        Card(

                            modifier = Modifier.fillMaxWidth().clickable {

                                complaintPhotoAttached = true

                                Toast.makeText(context, "Photo attached (demo)", Toast.LENGTH_SHORT).show()

                            },

                            colors = CardDefaults.cardColors(containerColor = if (complaintPhotoAttached) Color(0xFFECFDF5) else Color(0xFFF8FAFC)),

                            shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(0.dp)

                        ) {

                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                                Icon(if (complaintPhotoAttached) Icons.Rounded.CheckCircle else Icons.Rounded.PhotoCamera, null, tint = if (complaintPhotoAttached) Color(0xFF10B981) else Color(0xFF64748B), modifier = Modifier.size(20.dp))

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(if (complaintPhotoAttached) "Photo attached" else "Tap to attach photo", fontSize = 13.sp, color = if (complaintPhotoAttached) Color(0xFF065F46) else Color(0xFF475569))

                            }

                        }

                        Card(

                            modifier = Modifier.fillMaxWidth().clickable {

                                complaintLocation = "Ward 4, Sector A - 12.9716N 77.5946E"

                                Toast.makeText(context, "Location tagged (OSM demo)", Toast.LENGTH_SHORT).show()

                            },

                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F9FF)),

                            shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(0.dp)

                        ) {

                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                                Icon(Icons.Rounded.MyLocation, null, tint = Color(0xFF007AFF), modifier = Modifier.size(20.dp))

                                Spacer(modifier = Modifier.width(8.dp))

                                Column {

                                    Text("Tap to Tag Location", fontSize = 10.sp, color = Color(0xFF007AFF), fontWeight = FontWeight.Bold)

                                    Text(complaintLocation, fontSize = 11.sp, color = Color(0xFF475569))

                                }

                            }

                        }

                        Text("Override AI Category (optional):", fontSize = 10.sp, color = Color(0xFF94A3B8))

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                            items(categories) { cat ->

                                FilterChip(selected = selectedCategory == cat, onClick = { selectedCategory = if (selectedCategory == cat) null else cat }, label = { Text(cat, fontSize = 10.sp) })

                            }

                        }

                        if (submitState is SubmitState.Submitting) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = Color(0xFF007AFF))

                                Spacer(modifier = Modifier.width(8.dp))

                                Text("Analyzing via Gemini AI...", fontSize = 11.sp, color = Color(0xFF475569))

                            }

                        }

                    }

                },

                confirmButton = {

                    Button(onClick = { viewModel.submitComplaint(title, description, if (complaintPhotoAttached) "DEMO_COMPLAINT_IMAGE" else null, selectedCategory, if (complaintLocation != "Tap pin to tag location") complaintLocation else null) }, shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))) { Text("Log & AI Route") }

                },

                dismissButton = { TextButton(onClick = { showReportSheet = false }) { Text("Cancel", color = Color(0xFF64748B)) } }

            )

        }



        if (resolvingId != null) {

            AlertDialog(

                onDismissRequest = { resolvingId = null },

                shape = RoundedCornerShape(24.dp), containerColor = Color.White,

                title = {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFF10B981).copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {

                            Icon(Icons.Rounded.CheckCircle, null, tint = Color(0xFF10B981), modifier = Modifier.size(20.dp))

                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text("Mark as Resolved", fontWeight = FontWeight.Bold, fontSize = 17.sp)

                    }

                },

                text = {

                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        OutlinedTextField(value = resolutionNote, onValueChange = { resolutionNote = it }, label = { Text("Resolution Note") }, placeholder = { Text("What was done to fix this issue?") }, modifier = Modifier.fillMaxWidth(), minLines = 3, shape = RoundedCornerShape(12.dp))

                        Card(

                            modifier = Modifier.fillMaxWidth().clickable {

                                resolutionPhotoAttached = true

                                Toast.makeText(context, "After-work photo attached (demo)", Toast.LENGTH_SHORT).show()

                            },

                            colors = CardDefaults.cardColors(containerColor = if (resolutionPhotoAttached) Color(0xFFECFDF5) else Color(0xFFF8FAFC)),

                            shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(0.dp)

                        ) {

                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                                Icon(if (resolutionPhotoAttached) Icons.Rounded.CheckCircle else Icons.Rounded.PhotoCamera, null, tint = if (resolutionPhotoAttached) Color(0xFF10B981) else Color(0xFF64748B), modifier = Modifier.size(20.dp))

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(if (resolutionPhotoAttached) "After-work photo attached" else "Attach after-work photo", fontSize = 13.sp)

                            }

                        }

                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(0.dp)) {

                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                                Icon(Icons.Rounded.Payments, null, tint = Color(0xFF10B981), modifier = Modifier.size(18.dp))

                                Spacer(modifier = Modifier.width(8.dp))

                                Text("Rs.1,500 govt payout auto-credited on resolution.", fontSize = 11.sp, color = Color(0xFF065F46))

                            }

                        }

                    }

                },

                confirmButton = {

                    Button(

                        onClick = {

                            if (resolutionNote.isBlank()) Toast.makeText(context, "Please add a resolution note", Toast.LENGTH_SHORT).show()

                            else {

                                viewModel.resolveComplaint(resolvingId!!, resolutionNote, if (resolutionPhotoAttached) "DEMO_RESOLUTION_IMAGE" else null)

                                Toast.makeText(context, "Complaint resolved! Rs.1500 payout initiated.", Toast.LENGTH_LONG).show()

                                resolvingId = null

                            }

                        },

                        shape = RoundedCornerShape(12.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))

                    ) { Text("Confirm Resolved") }

                },

                dismissButton = { TextButton(onClick = { resolvingId = null }) { Text("Cancel", color = Color(0xFF64748B)) } }

            )

        }



        if (ratingId != null) {

            AlertDialog(

                onDismissRequest = { ratingId = null },

                shape = RoundedCornerShape(24.dp), containerColor = Color.White,

                title = {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFFF59E0B).copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {

                            Icon(Icons.Rounded.Star, null, tint = Color(0xFFF59E0B), modifier = Modifier.size(20.dp))

                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text("Rate Resolution", fontWeight = FontWeight.Bold, fontSize = 17.sp)

                    }

                },

                text = {

                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(14.dp)) {

                        Text("How satisfied are you with the resolution?", fontSize = 13.sp, color = Color(0xFF475569), textAlign = TextAlign.Center)

                        Row(horizontalArrangement = Arrangement.Center) {

                            repeat(5) { i ->

                                IconButton(onClick = { ratingStars = i + 1 }, modifier = Modifier.size(44.dp)) {

                                    Icon(if (i < ratingStars) Icons.Rounded.Star else Icons.Rounded.StarOutline, null, tint = Color(0xFFF59E0B), modifier = Modifier.size(32.dp))

                                }

                            }

                        }

                        val labels = listOf("", "Poor", "Fair", "Good", "Very Good", "Excellent!")

                        if (ratingStars > 0) Text(labels[ratingStars], fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B), fontSize = 14.sp)

                        OutlinedTextField(value = ratingComment, onValueChange = { ratingComment = it }, label = { Text("Optional comment") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))

                        HorizontalDivider(color = Color(0xFFE2E8F0))

                        Text("Tip for Worker (optional)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                            listOf("0" to "Skip", "10" to "Rs.10", "20" to "Rs.20", "50" to "Rs.50", "100" to "Rs.100").forEach { (amt, label) ->

                                FilterChip(selected = tipAmount == amt, onClick = { tipAmount = amt }, label = { Text(label, fontSize = 11.sp) })

                            }

                        }

                        Text("Tip goes directly to the worker via UPI (demo).", fontSize = 10.sp, color = Color(0xFF94A3B8), textAlign = TextAlign.Center)

                    }

                },

                confirmButton = {

                    Button(

                        onClick = {

                            if (ratingStars == 0) Toast.makeText(context, "Please select a star rating", Toast.LENGTH_SHORT).show()

                            else {

                                val tip = tipAmount.toDoubleOrNull() ?: 0.0

                                viewModel.rateAndTipWorker(ratingId!!, ratingStars, ratingComment.ifBlank { null }, tip)

                                Toast.makeText(context, "Rating submitted! ${if (tip > 0) "Rs.${tip.toInt()} tip sent." else ""}", Toast.LENGTH_LONG).show()

                                ratingId = null

                            }

                        },

                        shape = RoundedCornerShape(12.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))

                    ) { Text("Submit Rating") }

                },

                dismissButton = { TextButton(onClick = { ratingId = null }) { Text("Skip", color = Color(0xFF64748B)) } }

            )

        }

    }

}





// =========================================================================

// 6. TRANSPARENCY: TENDERS & SDG PROJECTS

// =========================================================================

@Composable

fun TransparencyScreen(

    viewModel: CivicViewModel,

    currentRole: String = "Citizen",

    selectedLanguage: Language = Language.ENGLISH,

    isElderMode: Boolean = false

) {

    val projects by viewModel.projects.collectAsState()

    val notices by viewModel.notices.collectAsState()

    val noticeSummaries by viewModel.noticeSummaries.collectAsState()

    val summarizingId by viewModel.summarizingNoticeId.collectAsState()

    val context = LocalContext.current



    var showTendersTab by remember { mutableStateOf(false) } // toggle between Projects and Notices



    val projectsLabel = when (selectedLanguage) {

        Language.ENGLISH -> "SDG Projects"

        Language.HINDI -> "एसडीजी परियोजनाएं"

        Language.MARATHI -> "एसडीजी प्रकल्प"

        Language.KANNADA -> "ಎಸ್‌ಡಿಜಿ ಯೋಜನೆಗಳು"

        Language.TAMIL -> "எஸ்டிஜி திட்டங்கள்"

    }

    val noticesLabel = when (selectedLanguage) {

        Language.ENGLISH -> "Public Notice Center"

        Language.HINDI -> "सार्वजनिक सूचना केंद्र"

        Language.MARATHI -> "सार्वजनिक सूचना केंद्र"

        Language.KANNADA -> "ಸಾರ್ವಜನಿಕ ಸೂಚನೆ ಕೇಂದ್ರ"

        Language.TAMIL -> "பொது அறிவிப்பு மையம்"

    }



    Column(

        modifier = Modifier

            .fillMaxSize()

            .background(MaterialTheme.colorScheme.background)

            .padding(16.dp)

    ) {

        // Toggle tabs

        Row(

            modifier = Modifier

                .fillMaxWidth()

                .clip(RoundedCornerShape(12.dp))

                .background(Color(0xFFE2E8F0))

                .padding(4.dp)

        ) {

            Box(

                modifier = Modifier

                    .weight(1f)

                    .clip(RoundedCornerShape(10.dp))

                    .background(if (!showTendersTab) Color.White else Color.Transparent)

                    .clickable { showTendersTab = false }

                    .padding(vertical = 10.dp),

                contentAlignment = Alignment.Center

            ) {

                Text(

                    text = projectsLabel,

                    fontWeight = FontWeight.Bold,

                    color = if (!showTendersTab) Color(0xFF1E293B) else Color(0xFF64748B),

                    fontSize = 13.sp

                )

            }

            Box(

                modifier = Modifier

                    .weight(1f)

                    .clip(RoundedCornerShape(10.dp))

                    .background(if (showTendersTab) Color.White else Color.Transparent)

                    .clickable { showTendersTab = true }

                    .padding(vertical = 10.dp),

                contentAlignment = Alignment.Center

            ) {

                Text(

                    text = noticesLabel,

                    fontWeight = FontWeight.Bold,

                    color = if (showTendersTab) Color(0xFF1E293B) else Color(0xFF64748B),

                    fontSize = 13.sp

                )

            }

        }



        Spacer(modifier = Modifier.height(16.dp))



        if (!showTendersTab) {

            // PROJECTS

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                items(projects) { project ->

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(containerColor = Color.White),

                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),

                        shape = RoundedCornerShape(16.dp)

                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween,

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text(

                                    text = project.name,

                                    style = MaterialTheme.typography.titleMedium,

                                    fontWeight = FontWeight.ExtraBold,

                                    color = Color(0xFF0F172A),

                                    modifier = Modifier.weight(1f)

                                )

                                Box(

                                    modifier = Modifier

                                        .clip(RoundedCornerShape(6.dp))

                                        .background(Color(0xFFDCFCE7))

                                        .padding(horizontal = 8.dp, vertical = 3.dp)

                                ) {

                                    Text("Saved: ${project.co2Saved}", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF166534))

                                }

                            }



                            Spacer(modifier = Modifier.height(8.dp))



                            Text(

                                text = project.description,

                                fontSize = 12.sp,

                                color = Color(0xFF64748B)

                            )



                            Spacer(modifier = Modifier.height(14.dp))



                            // Stats Grid

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {

                                Column {

                                    Text("Budget Approved", fontSize = 10.sp, color = Color(0xFF94A3B8))

                                    Text(project.budget, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                }

                                Column {

                                    Text("Prime Contractor", fontSize = 10.sp, color = Color(0xFF94A3B8))

                                    Text(project.contractor, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                }

                                Column {

                                    Text("Timeline", fontSize = 10.sp, color = Color(0xFF94A3B8))

                                    Text(project.endDate.substringAfterLast(" "), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                }

                            }



                            Spacer(modifier = Modifier.height(14.dp))



                            // Progress Row

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text("Progress", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                Spacer(modifier = Modifier.width(12.dp))

                                LinearProgressIndicator(

                                    progress = project.progress / 100f,

                                    color = Color(0xFF10B981),

                                    trackColor = Color(0xFFE2E8F0),

                                    modifier = Modifier

                                        .weight(1f)

                                        .height(6.dp)

                                        .clip(CircleShape)

                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text("${project.progress}%", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0F172A))

                            }

                        }

                    }

                }

            }

        } else {

            // NOTICES WITH GEMINI SUMMARIZER
            val isAdminRole = currentRole == "Admin" || currentRole == "Municipality Admin" || currentRole == "Ward Officer"
            if (isAdminRole) {
                var showNoticePanel by remember { mutableStateOf(false) }
                var noticeTitle by remember { mutableStateOf("") }
                var noticeContent by remember { mutableStateOf("") }
                var noticeType by remember { mutableStateOf("Announcement") }
                var noticeLocation by remember { mutableStateOf("All") }

                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable { showNoticePanel = !showNoticePanel },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Rounded.Campaign, contentDescription = null, tint = Color(0xFF7C3AED))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Publish Public Notice", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                            Icon(
                                imageVector = if (showNoticePanel) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
                                contentDescription = null,
                                tint = Color(0xFF64748B)
                            )
                        }
                        if (showNoticePanel) {
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = noticeTitle,
                                onValueChange = { noticeTitle = it },
                                label = { Text("Notice Title", fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF7C3AED))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = noticeContent,
                                onValueChange = { noticeContent = it },
                                label = { Text("Content / Details", fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth().height(80.dp),
                                maxLines = 4,
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF7C3AED))
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Select Type
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("Announcement", "Tender", "Emergency", "Alert").forEach { type ->
                                    val selected = noticeType == type
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(if (selected) Color(0xFF7C3AED) else Color(0xFFE2E8F0))
                                            .clickable { noticeType = type }
                                            .padding(vertical = 6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(type.take(5) + ".", color = if (selected) Color.White else Color(0xFF475569), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    if (noticeTitle.isNotBlank() && noticeContent.isNotBlank()) {
                                        viewModel.publishNotice(noticeTitle, noticeContent, noticeType, noticeLocation)
                                        noticeTitle = ""
                                        noticeContent = ""
                                        showNoticePanel = false
                                        Toast.makeText(context, "Notice published successfully!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
                            ) {
                                Text("Publish Broadcast")
                            }
                        }
                    }
                }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                items(notices) { notice ->

                    val summary = noticeSummaries[notice.id]

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(containerColor = Color.White),

                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),

                        shape = RoundedCornerShape(16.dp)

                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween,

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Box(

                                    modifier = Modifier

                                        .clip(RoundedCornerShape(6.dp))

                                        .background(

                                            when (notice.type) {

                                                "Emergency" -> Color(0xFFFEE2E2)

                                                "Tender" -> Color(0xFFEFF6FF)

                                                else -> Color(0xFFFEF3C7)

                                            }

                                        )

                                        .padding(horizontal = 8.dp, vertical = 3.dp)

                                ) {

                                    Text(

                                        notice.type,

                                        fontSize = 9.sp,

                                        fontWeight = FontWeight.Bold,

                                        color = when (notice.type) {

                                            "Emergency" -> Color(0xFF991B1B)

                                            "Tender" -> Color(0xFF1E40AF)

                                            else -> Color(0xFF92400E)

                                        }

                                    )

                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(notice.date, fontSize = 10.sp, color = Color(0xFF94A3B8))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    IconButton(
                                        onClick = { viewModel.toggleNoticeBookmark(notice) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (notice.bookmarked) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                                            contentDescription = "Bookmark",
                                            tint = if (notice.bookmarked) Color(0xFF7C3AED) else Color(0xFF94A3B8),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }



                            Spacer(modifier = Modifier.height(8.dp))



                            Text(

                                text = notice.title,

                                style = MaterialTheme.typography.titleMedium,

                                fontWeight = FontWeight.Bold,

                                color = Color(0xFF0F172A)

                            )



                            Spacer(modifier = Modifier.height(6.dp))



                            Text(

                                text = notice.content,

                                fontSize = 12.sp,

                                color = Color(0xFF475569)

                            )



                            Spacer(modifier = Modifier.height(10.dp))



                            // AI Summary Box

                            if (summary != null) {

                                Box(

                                    modifier = Modifier

                                        .fillMaxWidth()

                                        .clip(RoundedCornerShape(10.dp))

                                        .background(Color(0xFFF3E8FF))

                                        .padding(12.dp)

                                ) {

                                    Row {

                                        Icon(Icons.Rounded.AutoAwesome, contentDescription = "AI summary", tint = Color(0xFF7C3AED), modifier = Modifier.size(16.dp))

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Column {

                                            Text("Gemini 1-Sentence Summary:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF7C3AED))

                                            Text(summary, fontSize = 12.sp, color = Color(0xFF5B21B6))

                                        }

                                    }

                                }

                            } else {

                                Button(

                                    onClick = { viewModel.summarizeNotice(notice.id, notice.content) },

                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)),

                                    modifier = Modifier.height(36.dp),

                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),

                                    shape = RoundedCornerShape(18.dp)

                                ) {

                                    if (summarizingId == notice.id) {

                                        CircularProgressIndicator(modifier = Modifier.size(14.dp), color = Color.White, strokeWidth = 2.dp)

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text("Reading...", fontSize = 11.sp)

                                    } else {

                                        Icon(Icons.Rounded.AutoAwesome, contentDescription = "AI", modifier = Modifier.size(14.dp))

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text("Generate AI Summary", fontSize = 11.sp)

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }

    }

}



// =========================================================================

// 7. COMMUNITY SCREEN: POLLS & SURVEYS

// =========================================================================

@Composable

fun CommunityPollsScreen(
    viewModel: CivicViewModel,
    currentRole: String,
    selectedLanguage: Language = Language.ENGLISH,
    isElderMode: Boolean = false
) {

    val polls by viewModel.polls.collectAsState()
    val context = LocalContext.current
    var showCreatePanel by remember { mutableStateOf(false) }
    var questionText by remember { mutableStateOf("") }
    var optionAText by remember { mutableStateOf("") }
    var optionBText by remember { mutableStateOf("") }



    val subText = when (selectedLanguage) {

        Language.HINDI -> "प्रत्यक्ष लोकतंत्र सक्रिय है। स्थानीय खर्च प्राथमिकताओं को निर्देशित करने के लिए मतदान करें।"

        Language.MARATHI -> "प्रत्यक्ष लोकशाही कार्यरत आहे. स्थानिक खर्च प्राधान्यक्रम ठरवण्यासाठी मतदान करा."

        Language.KANNADA -> "ನೇರ ಪ್ರಜಾಪ್ರಭುತ್ವ ಕಾರ್ಯದಲ್ಲಿದೆ. ಸ್ಥಳೀಯ ವೆಚ್ಚದ ಆದ್ಯತೆಗಳಿಗೆ ಮಾರ್ಗದರ್ಶನ ನೀಡಲು ಮತ ಚಲಾಯಿಸಿ."

        Language.TAMIL -> "நேரடி ஜனநாயகம் செயல்பாட்டில் உள்ளது. உள்ளூர் செலவின முன்னுரிமைகளை வழிநடத்த வாக்களிக்கவும்."

        else -> "Direct democracy in action. Vote to guide local spending priorities."

    }



    Column(

        modifier = Modifier

            .fillMaxSize()

            .background(MaterialTheme.colorScheme.background)

            .padding(16.dp)

    ) {

        Text(

            text = Localization.translate("democratic_polls", selectedLanguage),

            style = MaterialTheme.typography.titleLarge,

            fontWeight = FontWeight.ExtraBold,

            color = Color(0xFF0F172A)

        )

        Text(

            text = subText,

            style = MaterialTheme.typography.bodySmall,

            color = Color(0xFF64748B)

        )



        Spacer(modifier = Modifier.height(16.dp))

        val isAdminRole = currentRole == "Admin" || currentRole == "Municipality Admin" || currentRole == "Ward Officer"
        if (isAdminRole) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.HowToVote,
                                contentDescription = null,
                                tint = Color(0xFF2563EB),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Admin: Create Public Poll",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = questionText,
                        onValueChange = { questionText = it },
                        label = { Text("Poll Question") },
                        placeholder = { Text("e.g. Upgrade Block B park with solar benches?") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = optionAText,
                        onValueChange = { optionAText = it },
                        label = { Text("Option A") },
                        placeholder = { Text("e.g. Yes, allocate funds") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = optionBText,
                        onValueChange = { optionBText = it },
                        label = { Text("Option B") },
                        placeholder = { Text("e.g. No, focus on drainage") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            if (questionText.isBlank() || optionAText.isBlank() || optionBText.isBlank()) {
                                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.publishPoll(
                                    questionText.trim(),
                                    optionAText.trim(),
                                    optionBText.trim()
                                )
                                questionText = ""
                                optionAText = ""
                                optionBText = ""
                                Toast.makeText(context, "🗳️ Municipal poll published successfully!", Toast.LENGTH_LONG).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                    ) {
                        Text("Publish Poll", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

            items(polls) { poll ->

                val hasVoted = poll.userVote != null

                val totalVotes = poll.votesA + poll.votesB

                val percentA = if (totalVotes > 0) (poll.votesA * 100) / totalVotes else 0

                val percentB = if (totalVotes > 0) (poll.votesB * 100) / totalVotes else 0



                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),

                    shape = RoundedCornerShape(20.dp)

                ) {

                    Column(modifier = Modifier.padding(18.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.HowToVote, contentDescription = "Vote", tint = Color(0xFF2563EB))

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Active Municipal Poll", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB))

                        }



                        Spacer(modifier = Modifier.height(8.dp))



                        Text(

                            text = poll.question,

                            style = MaterialTheme.typography.titleMedium,

                            fontWeight = FontWeight.ExtraBold,

                            color = Color(0xFF0F172A)

                        )



                        Spacer(modifier = Modifier.height(14.dp))



                        // Option A Box

                        Box(

                            modifier = Modifier

                                .fillMaxWidth()

                                .clip(RoundedCornerShape(12.dp))

                                .background(

                                    if (poll.userVote == "A") Color(0xFFEFF6FF) else Color(0xFFF1F5F9)

                                )

                                .border(

                                    1.dp,

                                    if (poll.userVote == "A") Color(0xFF2563EB) else Color.Transparent,

                                    RoundedCornerShape(12.dp)

                                )

                                .clickable(enabled = !hasVoted) {

                                    viewModel.voteInPoll(poll.id, "A")

                                }

                                .padding(14.dp)

                        ) {

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween,

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(poll.optionA, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                    if (hasVoted) {

                                        Text("${poll.votesA} votes", fontSize = 11.sp, color = Color(0xFF64748B))

                                    }

                                }

                                if (hasVoted) {

                                    Text("$percentA%", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF2563EB))

                                }

                            }

                        }



                        Spacer(modifier = Modifier.height(10.dp))



                        // Option B Box

                        Box(

                            modifier = Modifier

                                .fillMaxWidth()

                                .clip(RoundedCornerShape(12.dp))

                                .background(

                                    if (poll.userVote == "B") Color(0xFFEFF6FF) else Color(0xFFF1F5F9)

                                )

                                .border(

                                    1.dp,

                                    if (poll.userVote == "B") Color(0xFF2563EB) else Color.Transparent,

                                    RoundedCornerShape(12.dp)

                                )

                                .clickable(enabled = !hasVoted) {

                                    viewModel.voteInPoll(poll.id, "B")

                                }

                                .padding(14.dp)

                        ) {

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween,

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(poll.optionB, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                    if (hasVoted) {

                                        Text("${poll.votesB} votes", fontSize = 11.sp, color = Color(0xFF64748B))

                                    }

                                }

                                if (hasVoted) {

                                    Text("$percentB%", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF2563EB))

                                }

                            }

                        }



                        if (hasVoted) {

                            Spacer(modifier = Modifier.height(12.dp))

                            Box(

                                modifier = Modifier

                                    .fillMaxWidth()

                                    .clip(RoundedCornerShape(8.dp))

                                    .background(Color(0xFFECFDF5))

                                    .padding(8.dp),

                                contentAlignment = Alignment.Center

                            ) {

                                Text("✓ Your vote has been recorded and encrypted securely.", fontSize = 11.sp, color = Color(0xFF065F46), fontWeight = FontWeight.Bold)

                            }

                        }

                    }

                }

            }

        }

    }

}



// =========================================================================

// 8. SERVICES SCREEN — Role-Gated Smart City Services Hub

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun ServicesScreen(

    viewModel: CivicViewModel,

    currentRole: String = "Citizen",

    selectedLanguage: Language = Language.ENGLISH,

    isElderMode: Boolean = false,

    sharedCertificates: List<CertificateApplication>,

    onUpdateCertificates: (List<CertificateApplication>) -> Unit,

    sharedEmergencyAlerts: List<EmergencyAlertItem>,

    onUpdateEmergencyAlerts: (List<EmergencyAlertItem>) -> Unit,

    sharedSuggestions: List<SuggestionItem>,

    onUpdateSuggestions: (List<SuggestionItem>) -> Unit,

    onLaunchSmartCityHub: () -> Unit = {}

) {

    val context = LocalContext.current



    // Dialog state controllers

    var showCertificatesDialog by remember { mutableStateOf(false) }

    var showEmergencyDirectoryDialog by remember { mutableStateOf(false) }

    var showPaymentsDialog by remember { mutableStateOf(false) }

    var showGisDialog by remember { mutableStateOf(false) }

    var showForumsDialog by remember { mutableStateOf(false) }

    var showWelfareDialog by remember { mutableStateOf(false) }

    var showBookingsDialog by remember { mutableStateOf(false) }

    var showTransitDialog by remember { mutableStateOf(false) }

    var showEnvironmentDialog by remember { mutableStateOf(false) }

    var showBudgetDialog by remember { mutableStateOf(false) }

    var showTendersDialog by remember { mutableStateOf(false) }

    var showWardNoticesDialog by remember { mutableStateOf(false) }

    var showSuggestionsDialog by remember { mutableStateOf(false) }

    var showApprovalsDialog by remember { mutableStateOf(false) }

    var showFeedbackDialog by remember { mutableStateOf(false) }

    var showProfileDialog by remember { mutableStateOf(false) }

    var showHealthDialog by remember { mutableStateOf(false) }

    var showLeaderboardDialog by remember { mutableStateOf(false) }

    var showJobsDialog by remember { mutableStateOf(false) }

    var showNewsDialog by remember { mutableStateOf(false) }

    var showAppointmentDialog by remember { mutableStateOf(false) }

    var showCrowdfundingDialog by remember { mutableStateOf(false) }

    var showElderlyAssistDialog by remember { mutableStateOf(false) }

    var showLostFoundDialog by remember { mutableStateOf(false) }

    var showSmartWaterDialog by remember { mutableStateOf(false) }

    var showWorkerRouteDialog by remember { mutableStateOf(false) }

    var showWasteBinDialog by remember { mutableStateOf(false) }

    var showPowerAlertDialog by remember { mutableStateOf(false) }



    val isCitizen = currentRole == "Citizen"

    val isWorker = currentRole == "Worker"

    val isAdmin = currentRole == "Admin" || currentRole == "Municipality Admin"



    // Service tiles organized by role

    data class ServiceTile(

        val icon: androidx.compose.ui.graphics.vector.ImageVector,

        val title: String,

        val subtitle: String,

        val color: Color,

        val bgColor: Color,

        val onClick: () -> Unit,

        val isAvailable: Boolean = true,

        val badge: String? = null

    )



    val citizenServices = if (isCitizen) listOf(

        ServiceTile(Icons.Rounded.Description, "Certificates", "Apply for birth, income & residence certificates", Color(0xFF0EA5E9), Color(0xFFE0F2FE), { showCertificatesDialog = true }),

        ServiceTile(Icons.Rounded.Phone, "Emergency Directory", "Quick call ambulance, police & fire", Color(0xFFEF4444), Color(0xFFFEE2E2), { showEmergencyDirectoryDialog = true }),

        ServiceTile(Icons.Rounded.Payment, "Pay Municipal Bills", "Water, property tax, electricity dues", Color(0xFF10B981), Color(0xFFDCFCE7), { showPaymentsDialog = true }),

        ServiceTile(Icons.Rounded.Map, "City GIS Map", "Projects & infrastructure near you", Color(0xFF8B5CF6), Color(0xFFF3E8FF), { showGisDialog = true }),

        ServiceTile(Icons.Rounded.Forum, "Community Forums", "Connect with ward neighbours", Color(0xFFF59E0B), Color(0xFFFEF3C7), { showForumsDialog = true }),

        ServiceTile(Icons.Rounded.VolunteerActivism, "Welfare Hubs", "Ration, pension & welfare schemes", Color(0xFFEC4899), Color(0xFFFCE7F3), { showWelfareDialog = true }),

        ServiceTile(Icons.Rounded.EventAvailable, "Facility Bookings", "Book halls, parks & public spaces", Color(0xFF0F172A), Color(0xFFF1F5F9), { showBookingsDialog = true }),

        ServiceTile(Icons.Rounded.DirectionsBus, "Public Transit", "Bus schedules, routes & passes", Color(0xFF0284C7), Color(0xFFE0F2FE), { showTransitDialog = true }),

        ServiceTile(Icons.Rounded.EnergySavingsLeaf, "Environment Report", "File pollution or waste complaints", Color(0xFF22C55E), Color(0xFFDCFCE7), { showEnvironmentDialog = true }),

        ServiceTile(Icons.Rounded.Lightbulb, "Suggestions Box", "Submit ideas for city improvement", Color(0xFF7C3AED), Color(0xFFEDE9FE), { showSuggestionsDialog = true }),

        ServiceTile(Icons.Rounded.LocalHospital, "Health Services", "Hospitals, vaccination & ambulance", Color(0xFFEF4444), Color(0xFFFFF1F2), { showHealthDialog = true }),

        ServiceTile(Icons.Rounded.Leaderboard, "Civic Leaderboard", "Top contributors in your ward", Color(0xFF8B5CF6), Color(0xFFF3E8FF), { showLeaderboardDialog = true }),

        ServiceTile(Icons.Rounded.Work, "Job Listings", "Government & vocational training jobs", Color(0xFF0369A1), Color(0xFFE0F2FE), { showJobsDialog = true }),

        ServiceTile(Icons.Rounded.Newspaper, "Local News", "Ward notices, events & city updates", Color(0xFFB45309), Color(0xFFFEF3C7), { showNewsDialog = true }),

        ServiceTile(Icons.Rounded.CalendarToday, "Book Appointment", "Schedule govt office visits", Color(0xFF059669), Color(0xFFECFDF5), { showAppointmentDialog = true }),

        ServiceTile(Icons.Rounded.VolunteerActivism, "Crowdfunding", "Co-fund local community projects", Color(0xFF10B981), Color(0xFFDCFCE7), { showCrowdfundingDialog = true }),

        ServiceTile(Icons.Rounded.Accessibility, "Elder Assist", "Book volunteers for senior citizens", Color(0xFF0EA5E9), Color(0xFFE0F2FE), { showElderlyAssistDialog = true }),

        ServiceTile(Icons.Rounded.FindInPage, "Lost & Found", "Community lost item board — ward 4", Color(0xFF7C3AED), Color(0xFFF3E8FF), { showLostFoundDialog = true }),

        ServiceTile(Icons.Rounded.WaterDrop, "Smart Water Meter", "Daily usage, trends & conservation tips", Color(0xFF0284C7), Color(0xFFE0F2FE), { showSmartWaterDialog = true }),

        ServiceTile(Icons.Rounded.ElectricBolt, "Grid Load Alert", "Power peak alerts & energy saving tips", Color(0xFFF59E0B), Color(0xFFFEF3C7), { showPowerAlertDialog = true })

    ) else emptyList()



    val officialServices = when {

        isAdmin -> listOf(

            ServiceTile(Icons.Rounded.SupervisedUserCircle, "User Management", "Activate, suspend & manage accounts", Color(0xFF0EA5E9), Color(0xFFE0F2FE), { Toast.makeText(context, "User Management Panel: 4,521 Active Accounts, 18 Pending Verification", Toast.LENGTH_LONG).show() }),

            ServiceTile(Icons.Rounded.AccountBalance, "Budget Allocation", "Approve & allocate departmental funds", Color(0xFF10B981), Color(0xFFDCFCE7), { showBudgetDialog = true }),

            ServiceTile(Icons.Rounded.Article, "Manage Notices", "Create & publish official notices", Color(0xFFF59E0B), Color(0xFFFEF3C7), { showWardNoticesDialog = true }),

            ServiceTile(Icons.Rounded.Work, "Departments", "Manage department rosters & assignments", Color(0xFF8B5CF6), Color(0xFFF3E8FF), { Toast.makeText(context, "Departments: Roads (12 staff), Water (8), Sanitation (15), Health (6), Gardens (4)", Toast.LENGTH_LONG).show() }),

            ServiceTile(Icons.Rounded.CheckCircle, "Approve Applications", "Final approval authority for certificates", Color(0xFFEC4899), Color(0xFFFCE7F3), { showApprovalsDialog = true }, badge = "${sharedCertificates.count { it.status == "Pending Approval" }}"),

            ServiceTile(Icons.Rounded.ShowChart, "Analytics Dashboard", "City-wide KPIs & compliance metrics", Color(0xFF0F172A), Color(0xFFF1F5F9), { showGisDialog = true }),

            ServiceTile(Icons.Rounded.Newspaper, "Local News", "Publish ward updates & announcements", Color(0xFFB45309), Color(0xFFFEF3C7), { showNewsDialog = true }),

            ServiceTile(Icons.Rounded.CalendarToday, "Appointments", "Manage citizen office appointments", Color(0xFF059669), Color(0xFFECFDF5), { showAppointmentDialog = true })

        )

        isWorker -> listOf(

            ServiceTile(Icons.Rounded.FactCheck, "Safety Checklist", "Pre-task safety audit before hazardous work", Color(0xFFDC2626), Color(0xFFFFF1F2), { Toast.makeText(context, "Safety Audit: Pre-task checklist loaded. Remember: PPE, Lock-out/Tag-out, Buddy system.", Toast.LENGTH_LONG).show() }),

            ServiceTile(Icons.Rounded.SwapHoriz, "Shift Exchange", "Request or approve shift swaps", Color(0xFF7C3AED), Color(0xFFEDE9FE), { Toast.makeText(context, "Shift Exchange Hub: You have 2 swap requests pending approval from team members.", Toast.LENGTH_LONG).show() }),

            ServiceTile(Icons.Rounded.Badge, "Work Auth ID", "Show QR verification to citizens", Color(0xFF0369A1), Color(0xFFE0F2FE), { Toast.makeText(context, "Your Work Authorization QR is active. Present to citizens on site for identity verification.", Toast.LENGTH_LONG).show() }),

            ServiceTile(Icons.Rounded.LocalHospital, "First Aid Library", "Safety videos & first aid training", Color(0xFF059669), Color(0xFFECFDF5), { Toast.makeText(context, "First Aid Library: CPR, Fire Safety, Electrical Hazard, Drainage Safety videos available.", Toast.LENGTH_LONG).show() }),

            ServiceTile(Icons.Rounded.Payments, "Payout Ledger", "View salary, tips & resolution rewards", Color(0xFF10B981), Color(0xFFDCFCE7), { Toast.makeText(context, "Monthly Payout: Base ₹18,500 | Resolution Bonus: ₹2,400 | Tips Received: ₹350", Toast.LENGTH_LONG).show() }),

            ServiceTile(Icons.Rounded.Leaderboard, "Worker Leaderboard", "Top performers in your department", Color(0xFF8B5CF6), Color(0xFFF3E8FF), { showLeaderboardDialog = true }),

            ServiceTile(Icons.Rounded.Route, "AI Route Optimizer", "Best path for today's complaint orders", Color(0xFF7C3AED), Color(0xFFF5F0FF), { showWorkerRouteDialog = true }),

            ServiceTile(Icons.Rounded.DeleteSweep, "Smart Waste Bins", "Live fill-level alerts & pickup requests", Color(0xFF10B981), Color(0xFFF0FDF4), { showWasteBinDialog = true })

        )

        else -> emptyList()

    }



    val allTiles = citizenServices + officialServices



    Column(

        modifier = Modifier

            .fillMaxSize()

            .background(MaterialTheme.colorScheme.background)

    ) {

        // Header Banner

        Box(

            modifier = Modifier

                .fillMaxWidth()

                .background(

                    Brush.linearGradient(

                        listOf(Color(0xFF1E40AF), Color(0xFF4F46E5))

                    )

                )

                .padding(horizontal = 20.dp, vertical = 20.dp)

        ) {

            Column {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(Icons.Rounded.Widgets, contentDescription = "Services", tint = Color.White, modifier = Modifier.size(28.dp))

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {

                        Text(

                            text = Localization.translate("services", selectedLanguage),

                            style = MaterialTheme.typography.titleLarge,

                            fontWeight = FontWeight.ExtraBold,

                            color = Color.White,

                            fontSize = if (isElderMode) 26.sp else 22.sp

                        )

                        Text(

                            text = "Role: $currentRole • ${allTiles.size} Services Available",

                            style = MaterialTheme.typography.bodySmall,

                            color = Color.White.copy(alpha = 0.8f)

                        )

                    }

                }

            }

        }



        LazyColumn(

            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(16.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            // Role access info card

            item {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(

                        containerColor = when (currentRole) {

                            "Citizen" -> Color(0xFFEFF6FF)

                            "Ward Officer" -> Color(0xFFF0FDF4)

                            "Department Officer" -> Color(0xFFFFF7ED)

                            "Municipality Admin" -> Color(0xFFFDF4FF)

                            "Mayor / Representative" -> Color(0xFFFFFBEB)

                            else -> Color(0xFFF1F5F9)

                        }

                    ),

                    shape = RoundedCornerShape(12.dp),

                    border = BorderStroke(1.dp, Color(0xFF007AFF).copy(alpha = 0.15f))

                ) {

                    Row(

                        modifier = Modifier.padding(12.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Icon(

                            imageVector = when (currentRole) {

                                "Citizen" -> Icons.Rounded.Person

                                "Ward Officer" -> Icons.Rounded.Badge

                                "Department Officer" -> Icons.Rounded.Engineering

                                "Municipality Admin" -> Icons.Rounded.AdminPanelSettings

                                "Mayor / Representative" -> Icons.Rounded.AccountBalance

                                else -> Icons.Rounded.Person

                            },

                            contentDescription = "Role Icon",

                            tint = Color(0xFF007AFF),

                            modifier = Modifier.size(24.dp)

                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {

                            Text(

                                text = "Logged in as: $currentRole",

                                fontWeight = FontWeight.Bold,

                                fontSize = 13.sp,

                                color = Color(0xFF0F172A)

                            )

                            Text(

                                text = when (currentRole) {

                                    "Citizen" -> "Access: Personal services, SOS, bill payments & community tools"

                                    "Ward Officer" -> "Access: Ward management, notices, citizen query resolution"

                                    "Department Officer" -> "Access: Dispatch management, approvals, project updates"

                                    "Municipality Admin" -> "Access: Full system administration & governance tools"

                                    "Mayor / Representative" -> "Access: Policy review, citizen feedback & city KPIs"

                                    else -> ""

                                },

                                fontSize = 11.sp,

                                color = Color(0xFF64748B)

                            )

                        }

                    }

                }

            }



            item {

                Card(

                    modifier = Modifier

                        .fillMaxWidth()

                        .clickable { onLaunchSmartCityHub() }

                        .testTag("services_smart_city_hub_banner"),

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(

                        containerColor = Color(0xFF0F172A)

                    ),

                    border = BorderStroke(1.dp, Color(0xFF3B82F6).copy(alpha = 0.4f)),

                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

                ) {

                    Row(

                        modifier = Modifier.padding(16.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Box(

                            modifier = Modifier

                                .size(46.dp)

                                .background(Color(0xFF3B82F6).copy(alpha = 0.15f), CircleShape),

                            contentAlignment = Alignment.Center

                        ) {

                            Icon(Icons.Rounded.Widgets, null, tint = Color(0xFF3B82F6), modifier = Modifier.size(22.dp))

                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {

                            Text(

                                text = "Smart City 2.0 Feature Hub",

                                fontWeight = FontWeight.ExtraBold,

                                color = Color.White,

                                fontSize = if (isElderMode) 18.sp else 14.sp

                            )

                            Text(

                                text = "Sandbox preview of all 50 interactive features",

                                color = Color.White.copy(alpha = 0.7f),

                                fontSize = if (isElderMode) 14.sp else 11.sp

                            )

                        }

                        Icon(

                            imageVector = Icons.Rounded.ArrowForwardIos,

                            contentDescription = "Open Hub",

                            tint = Color(0xFF3B82F6),

                            modifier = Modifier.size(16.dp)

                        )

                    }

                }

            }



            // Service tiles grid (2-column)

            items(allTiles.chunked(2)) { rowTiles ->

                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.spacedBy(12.dp)

                ) {

                    rowTiles.forEach { tile ->

                        Card(

                            modifier = Modifier

                                .weight(1f)

                                .clickable(enabled = tile.isAvailable) { tile.onClick() },

                            shape = RoundedCornerShape(16.dp),

                            colors = CardDefaults.cardColors(containerColor = tile.bgColor),

                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

                        ) {

                            Column(modifier = Modifier.padding(16.dp)) {

                                Row(

                                    modifier = Modifier.fillMaxWidth(),

                                    horizontalArrangement = Arrangement.SpaceBetween,

                                    verticalAlignment = Alignment.Top

                                ) {

                                    Box(

                                        modifier = Modifier

                                            .size(42.dp)

                                            .background(tile.color.copy(alpha = 0.15f), CircleShape),

                                        contentAlignment = Alignment.Center

                                    ) {

                                        Icon(

                                            imageVector = tile.icon,

                                            contentDescription = tile.title,

                                            tint = tile.color,

                                            modifier = Modifier.size(22.dp)

                                        )

                                    }

                                    tile.badge?.let { badge ->

                                        Box(

                                            modifier = Modifier

                                                .clip(RoundedCornerShape(8.dp))

                                                .background(tile.color)

                                                .padding(horizontal = 6.dp, vertical = 2.dp)

                                        ) {

                                            Text(

                                                text = badge,

                                                color = Color.White,

                                                fontSize = 9.sp,

                                                fontWeight = FontWeight.ExtraBold

                                            )

                                        }

                                    }

                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(

                                    text = tile.title,

                                    fontWeight = FontWeight.ExtraBold,

                                    fontSize = if (isElderMode) 15.sp else 13.sp,

                                    color = Color(0xFF0F172A),

                                    maxLines = 1,

                                    overflow = TextOverflow.Ellipsis

                                )

                                Text(

                                    text = tile.subtitle,

                                    fontSize = if (isElderMode) 12.sp else 10.sp,

                                    color = Color(0xFF64748B),

                                    maxLines = 2,

                                    overflow = TextOverflow.Ellipsis

                                )

                            }

                        }

                    }

                    // Fill empty space if odd tile count

                    if (rowTiles.size == 1) {

                        Spacer(modifier = Modifier.weight(1f))

                    }

                }

            }



            item { Spacer(modifier = Modifier.height(16.dp)) }

        }

    }



    // ---- Dialogs ----

    if (showCertificatesDialog) {

        CertificatesDialog(

            currentRole = currentRole,

            sharedCertificates = sharedCertificates,

            onUpdateCertificates = onUpdateCertificates,

            onDismiss = { showCertificatesDialog = false }

        )

    }

    if (showEmergencyDirectoryDialog) {

        EmergencyDirectoryDialog(

            onDismiss = { showEmergencyDirectoryDialog = false }

        )

    }

    if (showPaymentsDialog) {

        PaymentsDialog(onDismiss = { showPaymentsDialog = false })

    }

    if (showGisDialog) {

        GisMapDialog(currentRole = currentRole, onDismiss = { showGisDialog = false })

    }

    if (showForumsDialog) {

        ForumsDialog(currentRole = currentRole, onDismiss = { showForumsDialog = false })

    }

    if (showWelfareDialog) {

        WelfareDialog(onDismiss = { showWelfareDialog = false })

    }

    if (showBookingsDialog) {

        BookingsDialog(onDismiss = { showBookingsDialog = false })

    }

    if (showTransitDialog) {

        TransitDialog(onDismiss = { showTransitDialog = false })

    }

    if (showEnvironmentDialog) {

        EnvironmentReportDialog(onDismiss = { showEnvironmentDialog = false })

    }

    if (showSuggestionsDialog) {

        SuggestionsDialog(

            currentRole = currentRole,

            sharedSuggestions = sharedSuggestions,

            onUpdateSuggestions = onUpdateSuggestions,

            onDismiss = { showSuggestionsDialog = false }

        )

    }

    if (showBudgetDialog) {

        BudgetDialog(currentRole = currentRole, onDismiss = { showBudgetDialog = false })

    }

    if (showTendersDialog) {

        TendersDialog(currentRole = currentRole, onDismiss = { showTendersDialog = false })

    }

    if (showWardNoticesDialog) {

        WardNoticesDialog(viewModel = viewModel, currentRole = currentRole, onDismiss = { showWardNoticesDialog = false })

    }

    if (showApprovalsDialog) {

        ApprovalsDialog(

            currentRole = currentRole,

            sharedCertificates = sharedCertificates,

            onUpdateCertificates = onUpdateCertificates,

            onDismiss = { showApprovalsDialog = false }

        )

    }

    if (showFeedbackDialog) {

        CitizenFeedbackDialog(

            sharedSuggestions = sharedSuggestions,

            onDismiss = { showFeedbackDialog = false }

        )

    }

    if (showProfileDialog) {

        ProfileDialog(

            currentRole = currentRole,

            viewModel = viewModel,

            onDismiss = { showProfileDialog = false }

        )

    }

    if (showHealthDialog) {

        HealthServicesDialog(onDismiss = { showHealthDialog = false })

    }

    if (showLeaderboardDialog) {

        LeaderboardDialog(viewModel = viewModel, currentRole = currentRole, onDismiss = { showLeaderboardDialog = false })

    }

    if (showJobsDialog) {

        JobsDialog(onDismiss = { showJobsDialog = false })

    }

    if (showNewsDialog) {

        NewsDialog(onDismiss = { showNewsDialog = false })

    }

    if (showAppointmentDialog) {

        AppointmentDialog(onDismiss = { showAppointmentDialog = false })

    }

    if (showCrowdfundingDialog) {

        CrowdfundingDialog(onDismiss = { showCrowdfundingDialog = false })

    }

    if (showElderlyAssistDialog) {

        ElderlyAssistDialog(onDismiss = { showElderlyAssistDialog = false })

    }

    if (showLostFoundDialog) {

        LostFoundDialog(onDismiss = { showLostFoundDialog = false })

    }

    if (showSmartWaterDialog) {

        SmartWaterMeterDialog(onDismiss = { showSmartWaterDialog = false })

    }

    if (showWorkerRouteDialog) {

        WorkerRouteDialog(onDismiss = { showWorkerRouteDialog = false })

    }

    if (showWasteBinDialog) {

        WasteBinDialog(onDismiss = { showWasteBinDialog = false })

    }

    if (showPowerAlertDialog) {

        PowerAlertDialog(onDismiss = { showPowerAlertDialog = false })

    }

}



// =========================================================================

// SERVICE DIALOGS

// =========================================================================



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun CertificatesDialog(

    currentRole: String,

    sharedCertificates: List<CertificateApplication>,

    onUpdateCertificates: (List<CertificateApplication>) -> Unit,

    onDismiss: () -> Unit

) {

    val context = LocalContext.current

    var selectedType by remember { mutableStateOf("Birth Certificate") }

    var applicantName by remember { mutableStateOf("") }

    var showForm by remember { mutableStateOf(false) }

    val certTypes = listOf("Birth Certificate", "Death Certificate", "Residence Certificate", "Income Certificate", "Character Certificate")



    // Type-specific extra fields

    var dobField by remember { mutableStateOf("") }

    var placeField by remember { mutableStateOf("") }

    var fatherNameField by remember { mutableStateOf("") }

    var motherNameField by remember { mutableStateOf("") }

    var causeOfDeathField by remember { mutableStateOf("") }

    var addressField by remember { mutableStateOf("") }

    var yearsResidingField by remember { mutableStateOf("") }

    var ownershipTypeField by remember { mutableStateOf("") }

    var annualIncomeField by remember { mutableStateOf("") }

    var occupationField by remember { mutableStateOf("") }

    var employerField by remember { mutableStateOf("") }

    var purposeField by remember { mutableStateOf("") }

    var refPersonField by remember { mutableStateOf("") }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Description, contentDescription = null, tint = Color(0xFF007AFF))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Certificate Services", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                // Existing Applications

                Text("My Applications", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(8.dp))

                if (sharedCertificates.isEmpty()) {

                    Text("No applications yet. Apply below!", fontSize = 12.sp, color = Color(0xFF64748B))

                } else {

                    sharedCertificates.forEach { cert ->

                        val bgColor = when (cert.status) {

                            "Approved" -> Color(0xFFDCFCE7)

                            "Rejected" -> Color(0xFFFEE2E2)

                            else -> Color(0xFFFFF7ED)

                        }

                        val statusColor = when (cert.status) {

                            "Approved" -> Color(0xFF22C55E)

                            "Rejected" -> Color(0xFFEF4444)

                            else -> Color(0xFFF59E0B)

                        }

                        Card(

                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                            colors = CardDefaults.cardColors(containerColor = bgColor),

                            shape = RoundedCornerShape(10.dp)

                        ) {

                            Column(modifier = Modifier.padding(12.dp)) {

                                Row(

                                    modifier = Modifier.fillMaxWidth(),

                                    horizontalArrangement = Arrangement.SpaceBetween

                                ) {

                                    Text(cert.type, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                    Box(

                                        modifier = Modifier

                                            .clip(RoundedCornerShape(6.dp))

                                            .background(statusColor)

                                            .padding(horizontal = 6.dp, vertical = 2.dp)

                                    ) {

                                        Text(cert.status, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)

                                    }

                                }

                                Text("Applicant: ${cert.applicantName}", fontSize = 11.sp, color = Color(0xFF475569))

                                Text("Details: ${cert.details}", fontSize = 10.sp, color = Color(0xFF64748B), maxLines = 2, overflow = TextOverflow.Ellipsis)

                                Text("Tracking: ${cert.trackingNo}", fontSize = 10.sp, color = Color(0xFF94A3B8))



                                if (cert.status == "Approved") {

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Row(

                                        modifier = Modifier.fillMaxWidth(),

                                        horizontalArrangement = Arrangement.SpaceBetween,

                                        verticalAlignment = Alignment.CenterVertically

                                    ) {

                                        MockQrCode(modifier = Modifier.size(54.dp))

                                        DigitalSignatureStamp()

                                    }

                                }

                            }

                        }

                    }

                }



                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(color = Color(0xFFE2E8F0))

                Spacer(modifier = Modifier.height(12.dp))



                if (!showForm) {

                    Button(

                        onClick = { showForm = true },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))

                    ) {

                        Icon(Icons.Rounded.Add, contentDescription = null, modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(6.dp))

                        Text("Apply for New Certificate")

                    }

                } else {

                    Text("New Application", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                    Spacer(modifier = Modifier.height(8.dp))



                    Text("Certificate Type", fontSize = 11.sp, color = Color(0xFF64748B))

                    Spacer(modifier = Modifier.height(4.dp))

                    certTypes.forEach { type ->

                        Row(

                            modifier = Modifier

                                .fillMaxWidth()

                                .clip(RoundedCornerShape(8.dp))

                                .background(if (selectedType == type) Color(0xFFE0F0FF) else Color.Transparent)

                                .clickable { selectedType = type }

                                .padding(8.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            RadioButton(

                                selected = selectedType == type,

                                onClick = { selectedType = type },

                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF007AFF))

                            )

                            Text(type, fontSize = 13.sp, color = Color(0xFF0F172A))

                        }

                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(

                        value = applicantName,

                        onValueChange = { applicantName = it },

                        label = { Text("Applicant Full Name") },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(10.dp)

                    )

                    Spacer(modifier = Modifier.height(8.dp))



                    // Type-specific required fields

                    when (selectedType) {

                        "Birth Certificate" -> {

                            Text("📝 Required: Birth Certificate Info", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF007AFF))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = dobField, onValueChange = { dobField = it }, label = { Text("Date of Birth (DD/MM/YYYY)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = placeField, onValueChange = { placeField = it }, label = { Text("Place of Birth / Hospital") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = fatherNameField, onValueChange = { fatherNameField = it }, label = { Text("Father's Full Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = motherNameField, onValueChange = { motherNameField = it }, label = { Text("Mother's Full Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                        }

                        "Death Certificate" -> {

                            Text("📝 Required: Death Certificate Info", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF007AFF))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = dobField, onValueChange = { dobField = it }, label = { Text("Date of Death (DD/MM/YYYY)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = placeField, onValueChange = { placeField = it }, label = { Text("Place of Death") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = causeOfDeathField, onValueChange = { causeOfDeathField = it }, label = { Text("Cause of Death") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                        }

                        "Residence Certificate" -> {

                            Text("📝 Required: Residence Info", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF007AFF))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = addressField, onValueChange = { addressField = it }, label = { Text("Current Full Address") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = yearsResidingField, onValueChange = { yearsResidingField = it }, label = { Text("Years Residing at Address") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = ownershipTypeField, onValueChange = { ownershipTypeField = it }, label = { Text("Ownership Type (Own/Rented/Govt Quarter)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                        }

                        "Income Certificate" -> {

                            Text("📝 Required: Income Info", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF007AFF))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = annualIncomeField, onValueChange = { annualIncomeField = it }, label = { Text("Annual Income (₹)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = occupationField, onValueChange = { occupationField = it }, label = { Text("Occupation") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = employerField, onValueChange = { employerField = it }, label = { Text("Employer / Business Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                        }

                        "Character Certificate" -> {

                            Text("📝 Required: Character Info", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF007AFF))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = purposeField, onValueChange = { purposeField = it }, label = { Text("Purpose (Job / Education / Passport)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(value = refPersonField, onValueChange = { refPersonField = it }, label = { Text("Reference Person & Contact") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                        }

                    }



                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                        OutlinedButton(

                            onClick = { showForm = false },

                            modifier = Modifier.weight(1f),

                            shape = RoundedCornerShape(10.dp)

                        ) {

                            Text("Cancel")

                        }

                        Button(

                            onClick = {

                                if (applicantName.isBlank()) {

                                    Toast.makeText(context, "Please enter applicant name", Toast.LENGTH_SHORT).show()

                                } else {

                                    val details = when (selectedType) {

                                        "Birth Certificate" -> "DOB: $dobField | Place: $placeField | Father: $fatherNameField | Mother: $motherNameField"

                                        "Death Certificate" -> "Date: $dobField | Place: $placeField | Cause: $causeOfDeathField"

                                        "Residence Certificate" -> "Address: $addressField | Years: $yearsResidingField | Type: $ownershipTypeField"

                                        "Income Certificate" -> "Income: ₹$annualIncomeField | Occupation: $occupationField | Employer: $employerField"

                                        "Character Certificate" -> "Purpose: $purposeField | Reference: $refPersonField"

                                        else -> "No additional details"

                                    }

                                    val newId = "${selectedType.first()}C-${(1000..9999).random()}"

                                    val newTxn = "TXN${(100000..999999).random()}"

                                    val newCert = CertificateApplication(

                                        id = newId,

                                        type = selectedType,

                                        applicantName = applicantName.trim(),

                                        details = details,

                                        status = "Pending Approval",

                                        trackingNo = newTxn

                                    )

                                    onUpdateCertificates(sharedCertificates + newCert)

                                    applicantName = ""

                                    showForm = false

                                    Toast.makeText(context, "Application $newId submitted! Track with $newTxn", Toast.LENGTH_LONG).show()

                                }

                            },

                            modifier = Modifier.weight(1f),

                            shape = RoundedCornerShape(10.dp),

                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))

                        ) {

                            Text("Submit")

                        }

                    }

                }



                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}







@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun EmergencyDirectoryDialog(

    onDismiss: () -> Unit

) {

    val context = LocalContext.current

    val emergencyNumbers = listOf(

        Triple("Ambulance / Medical", "102 / 108", Icons.Rounded.MedicalServices),

        Triple("Fire Department", "101", Icons.Rounded.LocalFireDepartment),

        Triple("Police / Security", "100 / 112", Icons.Rounded.LocalPolice),

        Triple("Municipal Helpline", "1916", Icons.Rounded.Call),

        Triple("Disaster Management", "1077", Icons.Rounded.Warning)

    )



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Phone, contentDescription = null, tint = Color(0xFFEF4444))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Emergency Quick-Call Directory", fontWeight = FontWeight.Bold, color = Color(0xFFEF4444))

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                Text(

                    "Select a service below to place a quick call. This bypasses the mock SOS broadcast network for immediate direct contact.",

                    fontSize = 12.sp,

                    color = Color(0xFF64748B),

                    modifier = Modifier.padding(bottom = 12.dp)

                )

                

                emergencyNumbers.forEach { (name, number, icon) ->

                    Card(

                        modifier = Modifier

                            .fillMaxWidth()

                            .padding(vertical = 4.dp)

                            .clickable {

                                Toast.makeText(context, "Calling $name ($number) now...", Toast.LENGTH_LONG).show()

                            },

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2).copy(alpha = 0.5f)),

                        shape = RoundedCornerShape(12.dp),

                        border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.2f))

                    ) {

                        Row(

                            modifier = Modifier.padding(12.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Box(

                                modifier = Modifier

                                    .size(36.dp)

                                    .clip(CircleShape)

                                    .background(Color(0xFFFEE2E2)),

                                contentAlignment = Alignment.Center

                            ) {

                                Icon(icon, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(20.dp))

                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF7F1D1D))

                                Text("Emergency Number: $number", fontSize = 11.sp, color = Color(0xFFEF4444))

                            }

                            Icon(Icons.Rounded.Call, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(18.dp))

                        }

                    }

                }

                

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@Composable

fun MockQrCode(modifier: Modifier = Modifier) {

    androidx.compose.foundation.Canvas(modifier = modifier) {

        val size = this.size.width

        val squareSize = size / 21f

        val paintColor = Color.Black



        fun drawFinderPattern(offsetX: Float, offsetY: Float) {

            drawRect(

                color = paintColor,

                topLeft = androidx.compose.ui.geometry.Offset(offsetX, offsetY),

                size = androidx.compose.ui.geometry.Size(squareSize * 7, squareSize * 7)

            )

            drawRect(

                color = Color.White,

                topLeft = androidx.compose.ui.geometry.Offset(offsetX + squareSize, offsetY + squareSize),

                size = androidx.compose.ui.geometry.Size(squareSize * 5, squareSize * 5)

            )

            drawRect(

                color = paintColor,

                topLeft = androidx.compose.ui.geometry.Offset(offsetX + squareSize * 2, offsetY + squareSize * 2),

                size = androidx.compose.ui.geometry.Size(squareSize * 3, squareSize * 3)

            )

        }



        drawFinderPattern(0f, 0f)

        drawFinderPattern(squareSize * 14, 0f)

        drawFinderPattern(0f, squareSize * 14)



        val alignOffset = squareSize * 14

        drawRect(

            color = paintColor,

            topLeft = androidx.compose.ui.geometry.Offset(alignOffset, alignOffset),

            size = androidx.compose.ui.geometry.Size(squareSize * 5, squareSize * 5)

        )

        drawRect(

            color = Color.White,

            topLeft = androidx.compose.ui.geometry.Offset(alignOffset + squareSize, alignOffset + squareSize),

            size = androidx.compose.ui.geometry.Size(squareSize * 3, squareSize * 3)

        )

        drawRect(

            color = paintColor,

            topLeft = androidx.compose.ui.geometry.Offset(alignOffset + squareSize * 2, alignOffset + squareSize * 2),

            size = androidx.compose.ui.geometry.Size(squareSize, squareSize)

        )



        val randomGrid = listOf(

            0x2A3B5C, 0x1F2E3D, 0x5D6C7B, 0x7E8F9A,

            0x3B4C5D, 0x6E7F8A, 0x1A2B3C, 0x4D5E6F,

            0x7A8B9C, 0x2D3E4F, 0x5B6C7D, 0x1E2F3A,

            0x4B5C6D, 0x6A7B8C, 0x3D4E5F, 0x2E3F4A,

            0x5E6F7A, 0x1C2D3E, 0x4A5B6C, 0x7D8E9F,

            0x3A4B5C

        )

        

        for (r in 0 until 21) {

            for (c in 0 until 21) {

                if ((r < 8 && c < 8) || (r < 8 && c > 12) || (r > 12 && c < 8)) {

                    continue

                }

                if (r > 13 && c > 13) {

                    continue

                }

                val fill = ((randomGrid.getOrElse(r % randomGrid.size) { 0 }) shr (c % 24)) and 1

                if (fill == 1) {

                    drawRect(

                        color = paintColor,

                        topLeft = androidx.compose.ui.geometry.Offset(c * squareSize, r * squareSize),

                        size = androidx.compose.ui.geometry.Size(squareSize, squareSize)

                    )

                }

            }

        }

    }

}



@Composable

fun DigitalSignatureStamp(modifier: Modifier = Modifier) {

    Box(

        modifier = modifier

            .rotate(-8f)

            .border(

                border = BorderStroke(2.dp, Color(0xFFDC2626)),

                shape = RoundedCornerShape(8.dp)

            )

            .background(Color(0xFFFEE2E2).copy(alpha = 0.85f))

            .padding(horizontal = 12.dp, vertical = 6.dp)

    ) {

        Column(

            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier.width(IntrinsicSize.Max)

        ) {

            Text(

                text = "APPROVED",

                fontSize = 11.sp,

                fontWeight = FontWeight.ExtraBold,

                color = Color(0xFFDC2626),

                letterSpacing = 1.5.sp

            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(

                text = "DIGITALLY SIGNED",

                fontSize = 8.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFFDC2626),

                letterSpacing = 0.5.sp

            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(

                text = "Ward 4 Commissioner",

                fontSize = 7.sp,

                fontWeight = FontWeight.Normal,

                color = Color(0xFFDC2626)

            )

        }

    }

}



@Composable

fun StatusTimeline(

    status: String,

    rating: Int,

    modifier: Modifier = Modifier

) {

    val steps = listOf("Submitted", "Assigned", "In Progress", "Resolved", "Feedback")

    

    val activeIndex = when {

        rating > 0 -> 4

        status == "Resolved" || status == "Closed" -> 3

        status == "In Progress" || status == "Under Review" -> 2

        status == "Assigned" -> 1

        status == "Submitted" -> 0

        else -> 0

    }



    Column(

        modifier = modifier

            .fillMaxWidth()

            .padding(vertical = 4.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Box(

            modifier = Modifier.fillMaxWidth(),

            contentAlignment = Alignment.CenterStart

        ) {

            Row(

                modifier = Modifier

                    .fillMaxWidth()

                    .padding(horizontal = 24.dp),

                verticalAlignment = Alignment.CenterVertically

            ) {

                for (i in 0 until steps.size - 1) {

                    val lineColor = if (i < activeIndex) Color(0xFF34C759) else Color(0xFFE5E5EA)

                    Box(

                        modifier = Modifier

                            .weight(1f)

                            .height(2.dp)

                            .background(lineColor)

                    )

                }

            }



            Row(

                modifier = Modifier.fillMaxWidth()

            ) {

                steps.forEachIndexed { index, _ ->

                    val isCompleted = index < activeIndex

                    val isActive = index == activeIndex



                    val circleColor = when {

                        isCompleted -> Color(0xFF34C759)

                        isActive -> Color(0xFF007AFF)

                        else -> Color(0xFFE5E5EA)

                    }



                    Column(

                        modifier = Modifier.weight(1f),

                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Box(

                            modifier = Modifier

                                .size(20.dp)

                                .clip(CircleShape)

                                .background(circleColor),

                            contentAlignment = Alignment.Center

                        ) {

                            if (isCompleted) {

                                Icon(

                                    imageVector = Icons.Rounded.Check,

                                    contentDescription = null,

                                    tint = Color.White,

                                    modifier = Modifier.size(12.dp)

                                )

                            } else {

                                Text(

                                    text = (index + 1).toString(),

                                    fontSize = 10.sp,

                                    fontWeight = FontWeight.Bold,

                                    color = if (isActive) Color.White else Color(0xFF8E8E93)

                                )

                            }

                        }

                    }

                }

            }

        }



        Spacer(modifier = Modifier.height(4.dp))



        Row(

            modifier = Modifier.fillMaxWidth()

        ) {

            steps.forEachIndexed { index, step ->

                val isActive = index == activeIndex

                val isCompleted = index < activeIndex

                val textColor = when {

                    isActive -> Color(0xFF007AFF)

                    isCompleted -> Color(0xFF34C759)

                    else -> Color(0xFF8E8E93)

                }

                Box(

                    modifier = Modifier.weight(1f),

                    contentAlignment = Alignment.Center

                ) {

                    Text(

                        text = step,

                        fontSize = 8.sp,

                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,

                        color = textColor,

                        textAlign = TextAlign.Center,

                        maxLines = 1

                    )

                }

            }

        }

    }

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun PaymentsDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    val bills = remember {

        listOf(

            Triple("Water Tax Q2 2026", "₹ 420", false),

            Triple("Property Tax FY 2026-27", "₹ 3,200", false),

            Triple("Garbage Collection Fee", "₹ 150", true),

            Triple("Street Light Cess", "₹ 80", true),

        )

    }

    var paidBills by remember { mutableStateOf(setOf<String>()) }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Payment, contentDescription = null, tint = Color(0xFF10B981))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Pay Municipal Bills", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                Text("Outstanding Dues — Ward 4, Sector Block B", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(12.dp))

                bills.forEach { (name, amount, isPaid) ->

                    val isNowPaid = isPaid || paidBills.contains(name)

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = if (isNowPaid) Color(0xFFDCFCE7) else Color(0xFFF8FAFC)),

                        shape = RoundedCornerShape(10.dp),

                        border = BorderStroke(1.dp, if (isNowPaid) Color(0xFF22C55E).copy(alpha = 0.4f) else Color(0xFFE2E8F0))

                    ) {

                        Row(

                            modifier = Modifier.padding(12.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Icon(

                                imageVector = if (isNowPaid) Icons.Rounded.CheckCircle else Icons.Rounded.HourglassBottom,

                                contentDescription = null,

                                tint = if (isNowPaid) Color(0xFF22C55E) else Color(0xFFF59E0B),

                                modifier = Modifier.size(20.dp)

                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                Text(if (isNowPaid) "Paid ✓" else "Due Now", fontSize = 11.sp, color = if (isNowPaid) Color(0xFF22C55E) else Color(0xFFEF4444))

                            }

                            Text(amount, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = if (isNowPaid) Color(0xFF22C55E) else Color(0xFF0F172A))

                            if (!isNowPaid) {

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(

                                    onClick = {

                                        paidBills = paidBills + name

                                        Toast.makeText(context, "✅ $name paid! Receipt: RCT${(10000..99999).random()}", Toast.LENGTH_LONG).show()

                                    },

                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),

                                    shape = RoundedCornerShape(8.dp),

                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),

                                    modifier = Modifier.height(34.dp)

                                ) {

                                    Text("Pay", fontSize = 11.sp, fontWeight = FontWeight.Bold)

                                }

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),

                    shape = RoundedCornerShape(10.dp)

                ) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.VerifiedUser, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(20.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Payments are secured via UPI / Net Banking gateway.", fontSize = 11.sp, color = Color(0xFF1E40AF))

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun GisMapDialog(currentRole: String, onDismiss: () -> Unit) {

    val projectMarkers = remember {

        listOf(

            Triple("🏗️", "Road Widening - MG Road", "45% complete"),

            Triple("💧", "Water Pipeline Extension", "78% complete"),

            Triple("🌳", "Green Belt Phase 2", "12% complete"),

            Triple("💡", "Solar Streetlight Grid", "90% complete"),

            Triple("🚰", "Sewage Upgrade Block D", "33% complete")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Map, contentDescription = null, tint = Color(0xFF8B5CF6))

                Spacer(modifier = Modifier.width(8.dp))

                Text("City GIS Infrastructure Map", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                // Simulated map placeholder

                Box(

                    modifier = Modifier

                        .fillMaxWidth()

                        .height(160.dp)

                        .clip(RoundedCornerShape(12.dp))

                        .background(

                            Brush.linearGradient(listOf(Color(0xFF1E3A5F), Color(0xFF2D6A4F)))

                        ),

                    contentAlignment = Alignment.Center

                ) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(Icons.Rounded.Map, contentDescription = null, tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(48.dp))

                        Text("Ward 4 — Live Infrastructure Map", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)

                        Text("Lat: 28.6139 | Long: 77.2090", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)

                        // Simulated pins

                        Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                            listOf("🔴", "🟡", "🟢", "🔵", "🟣").forEach { pin ->

                                Text(pin, fontSize = 18.sp)

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.SpaceEvenly

                ) {

                    listOf(Pair("🔴", "Critical"), Pair("🟡", "In Progress"), Pair("🟢", "Complete"), Pair("🔵", "Planned")).forEach { (dot, label) ->

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(dot, fontSize = 12.sp)

                            Spacer(modifier = Modifier.width(3.dp))

                            Text(label, fontSize = 10.sp, color = Color(0xFF64748B))

                        }

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("Active Projects Near You", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(8.dp))

                projectMarkers.forEach { (emoji, name, progress) ->

                    Row(

                        modifier = Modifier

                            .fillMaxWidth()

                            .padding(vertical = 4.dp)

                            .clip(RoundedCornerShape(8.dp))

                            .background(Color(0xFFF8FAFC))

                            .padding(10.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Text(emoji, fontSize = 20.sp)

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {

                            Text(name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))

                            Text(progress, fontSize = 11.sp, color = Color(0xFF64748B))

                        }

                    }

                }



                // Smart City Overlay Panels

                Spacer(modifier = Modifier.height(14.dp))

                Text("🌐 Smart City Overlays", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(8.dp))



                // Parking Finder

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),

                    shape = RoundedCornerShape(10.dp),

                    border = BorderStroke(1.dp, Color(0xFF86EFAC))

                ) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Text("🚗", fontSize = 20.sp)

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {

                            Text("Smart Parking — Sector 4 Zones", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF065F46))

                            Text("Zone A: 12 free • Zone B: 3 free • Zone C: Full", fontSize = 11.sp, color = Color(0xFF059669))

                        }

                    }

                }



                Spacer(modifier = Modifier.height(6.dp))



                // Flood Alert

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)),

                    shape = RoundedCornerShape(10.dp),

                    border = BorderStroke(1.dp, Color(0xFFFDBA74))

                ) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Text("🌊", fontSize = 20.sp)

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {

                            Text("Flood & Waterlog Monitor", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF92400E))

                            Text("Drain Level: 42% • Risk: Low • No active flood warnings", fontSize = 11.sp, color = Color(0xFFD97706))

                        }

                    }

                }



                Spacer(modifier = Modifier.height(6.dp))



                // Traffic Signals

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),

                    shape = RoundedCornerShape(10.dp),

                    border = BorderStroke(1.dp, Color(0xFF93C5FD))

                ) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Text("🚦", fontSize = 20.sp)

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {

                            Text("AI Traffic Signal Optimization", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF1E40AF))

                            Text("Main Rd: 45s cycle • Cross Rd: 30s • High traffic — signals adapting", fontSize = 11.sp, color = Color(0xFF2563EB))

                        }

                    }

                }



                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun ForumsDialog(currentRole: String, onDismiss: () -> Unit) {

    val context = LocalContext.current

    val threads = remember {

        listOf(

            Triple("Pothole on Lane 7 — Any Update?", "Ravi Kumar", 23),

            Triple("New Park Proposal — Need Votes!", "Sunita Devi", 41),

            Triple("Water supply disruption this week", "Mohan Lal", 17),

            Triple("Street light broken near Gate B", "Priya Sharma", 8)

        )

    }

    var newPost by remember { mutableStateOf("") }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Forum, contentDescription = null, tint = Color(0xFFF59E0B))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Community Forums — Ward 4", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                threads.forEach { (title, author, replies) ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),

                        shape = RoundedCornerShape(10.dp),

                        border = BorderStroke(1.dp, Color(0xFFFDE68A))

                    ) {

                        Row(

                            modifier = Modifier.padding(12.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Column(modifier = Modifier.weight(1f)) {

                                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A), maxLines = 1, overflow = TextOverflow.Ellipsis)

                                Text("by $author", fontSize = 10.sp, color = Color(0xFF92400E))

                            }

                            Box(

                                modifier = Modifier

                                    .clip(RoundedCornerShape(8.dp))

                                    .background(Color(0xFFF59E0B).copy(alpha = 0.15f))

                                    .padding(horizontal = 8.dp, vertical = 4.dp)

                            ) {

                                Text("$replies replies", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E))

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(color = Color(0xFFE2E8F0))

                Spacer(modifier = Modifier.height(10.dp))

                Text("Post a New Thread", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(

                    value = newPost,

                    onValueChange = { newPost = it },

                    placeholder = { Text("Share a concern, idea or question with your ward...") },

                    minLines = 2,

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(10.dp)

                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(

                    onClick = {

                        if (newPost.isBlank()) {

                            Toast.makeText(context, "Please type your post first", Toast.LENGTH_SHORT).show()

                        } else {

                            Toast.makeText(context, "✅ Post published to Ward 4 Community Board!", Toast.LENGTH_LONG).show()

                            newPost = ""

                            onDismiss()

                        }

                    },

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(10.dp),

                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))

                ) {

                    Text("Publish Post", fontWeight = FontWeight.Bold)

                }

                Spacer(modifier = Modifier.height(4.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun WelfareDialog(onDismiss: () -> Unit) {

    val schemes = remember {

        listOf(

            Triple("PM-KISAN Scheme", "₹6,000/yr direct benefit for farmers", "Agriculture"),

            Triple("Senior Citizen Pension", "Monthly pension ₹1,000 for citizens 60+", "Social Welfare"),

            Triple("Antyodaya Anna Yojana", "35 kg ration per family per month at subsidized rate", "Food Security"),

            Triple("Pradhan Mantri Awas Yojana", "Housing subsidy up to ₹2.5 lakh for EWS", "Housing"),

            Triple("Ayushman Bharat PMJAY", "Health coverage up to ₹5 lakh/year per family", "Healthcare"),

            Triple("Jan Dhan Yojana", "Zero-balance bank account with RuPay debit card", "Financial Inclusion")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.VolunteerActivism, contentDescription = null, tint = Color(0xFFEC4899))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Welfare Hubs & Schemes", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                Text("Government welfare schemes available in your ward:", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(10.dp))

                schemes.forEach { (name, description, category) ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE7F3)),

                        shape = RoundedCornerShape(10.dp)

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween,

                                verticalAlignment = Alignment.Top

                            ) {

                                Text(name, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Color(0xFF0F172A), modifier = Modifier.weight(1f))

                                Box(

                                    modifier = Modifier

                                        .clip(RoundedCornerShape(6.dp))

                                        .background(Color(0xFFEC4899).copy(alpha = 0.15f))

                                        .padding(horizontal = 6.dp, vertical = 2.dp)

                                ) {

                                    Text(category, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9D174D))

                                }

                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(description, fontSize = 11.sp, color = Color(0xFF475569))

                        }

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),

                    shape = RoundedCornerShape(10.dp)

                ) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.Info, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Visit Ward 4 Welfare Hub, Block C, Room 12 for in-person assistance.", fontSize = 11.sp, color = Color(0xFF1E40AF))

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun BookingsDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    val facilities = remember {

        listOf(

            Triple("Community Hall - Block A", "Capacity: 200 | AC | Projector", true),

            Triple("Public Garden Amphitheatre", "Capacity: 500 | Open Air | Stage", false),

            Triple("Sports Complex Ground", "Cricket / Football | Floodlights", true),

            Triple("Multipurpose Meeting Room", "Capacity: 30 | Whiteboard | AC", false)

        )

    }

    var bookedFacilities by remember { mutableStateOf(setOf<String>()) }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.EventAvailable, contentDescription = null, tint = Color(0xFF0F172A))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Facility Bookings", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                Text("Book public facilities in Ward 4 for events & activities:", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(10.dp))

                facilities.forEach { (name, details, isAvailable) ->

                    val isBooked = bookedFacilities.contains(name)

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(

                            containerColor = when {

                                isBooked -> Color(0xFFDCFCE7)

                                !isAvailable -> Color(0xFFF1F5F9)

                                else -> Color(0xFFF8FAFC)

                            }

                        ),

                        shape = RoundedCornerShape(10.dp),

                        border = BorderStroke(1.dp, if (isBooked) Color(0xFF22C55E).copy(alpha = 0.4f) else Color(0xFFE2E8F0))

                    ) {

                        Row(

                            modifier = Modifier.padding(12.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Column(modifier = Modifier.weight(1f)) {

                                Text(name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))

                                Text(details, fontSize = 10.sp, color = Color(0xFF64748B))

                                Text(

                                    text = when { isBooked -> "Booked by you ✓"; isAvailable -> "Available"; else -> "Occupied Today" },

                                    fontSize = 10.sp,

                                    fontWeight = FontWeight.Bold,

                                    color = when { isBooked -> Color(0xFF22C55E); isAvailable -> Color(0xFF10B981); else -> Color(0xFFEF4444) }

                                )

                            }

                            if (!isBooked && isAvailable) {

                                Button(

                                    onClick = {

                                        bookedFacilities = bookedFacilities + name

                                        Toast.makeText(context, "📅 $name booked! Confirmation ID: BK${(1000..9999).random()}", Toast.LENGTH_LONG).show()

                                    },

                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),

                                    shape = RoundedCornerShape(8.dp),

                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),

                                    modifier = Modifier.height(34.dp)

                                ) {

                                    Text("Book", fontSize = 11.sp)

                                }

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun TransitDialog(onDismiss: () -> Unit) {

    val routes = remember {

        listOf(

            listOf("Bus 42A", "Sector 4 Main Gate", "City Centre", "Every 15 min", "6:00–22:00"),

            listOf("Bus 7B", "Block D Market", "Railway Station", "Every 20 min", "5:30–23:00"),

            listOf("Metro Feeder", "Ward 4 Hub", "Metro Station D", "Every 8 min", "6:00–23:30"),

            listOf("Bus 15C", "Old Town", "Airport Road", "Every 30 min", "7:00–20:00")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.DirectionsBus, contentDescription = null, tint = Color(0xFF0284C7))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Public Transit Info", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                Text("Live bus & metro routes serving Ward 4:", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(10.dp))

                routes.forEach { route ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE)),

                        shape = RoundedCornerShape(10.dp)

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                Text(route[0], fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0284C7))

                                Text(route[3], fontSize = 11.sp, color = Color(0xFF0369A1), fontWeight = FontWeight.Bold)

                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Icon(Icons.Rounded.LocationOn, contentDescription = null, tint = Color(0xFF0284C7), modifier = Modifier.size(14.dp))

                                Text(" ${route[1]}", fontSize = 11.sp, color = Color(0xFF0F172A))

                                Text("  →  ", fontSize = 11.sp, color = Color(0xFF64748B))

                                Icon(Icons.Rounded.LocationOn, contentDescription = null, tint = Color(0xFF0284C7), modifier = Modifier.size(14.dp))

                                Text(" ${route[2]}", fontSize = 11.sp, color = Color(0xFF0F172A))

                            }

                            Text("Hours: ${route[4]}", fontSize = 10.sp, color = Color(0xFF64748B))

                        }

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),

                    shape = RoundedCornerShape(10.dp)

                ) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.CreditScore, contentDescription = null, tint = Color(0xFF22C55E), modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Monthly pass available: ₹350 (Regular) | ₹175 (Senior/Student)", fontSize = 11.sp, color = Color(0xFF166534))

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun EnvironmentReportDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    var selectedIssue by remember { mutableStateOf("Air Pollution") }

    var location by remember { mutableStateOf("") }

    var details by remember { mutableStateOf("") }

    val issueTypes = listOf("Air Pollution", "Noise Pollution", "Illegal Dumping", "Water Contamination", "Industrial Effluent", "Tree Felling")

    var submitted by remember { mutableStateOf(false) }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.EnergySavingsLeaf, contentDescription = null, tint = Color(0xFF22C55E))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Environment Report", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                if (submitted) {

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFDCFCE7)),

                        shape = RoundedCornerShape(12.dp)

                    ) {

                        Column(

                            modifier = Modifier.padding(20.dp),

                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {

                            Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = Color(0xFF22C55E), modifier = Modifier.size(48.dp))

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("Report Submitted!", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color(0xFF166534))

                            Text("The Environment Dept. has been notified. Inspection within 48 hours.", fontSize = 12.sp, color = Color(0xFF14532D), textAlign = TextAlign.Center)

                        }

                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                        Text("Close")

                    }

                } else {

                    Text("Report environmental violations in your area:", fontSize = 12.sp, color = Color(0xFF64748B))

                    Spacer(modifier = Modifier.height(10.dp))



                    // Carbon Footprint Index Card

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),

                        shape = RoundedCornerShape(10.dp),

                        border = BorderStroke(1.dp, Color(0xFF86EFAC))

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Icon(Icons.Rounded.EnergySavingsLeaf, null, tint = Color(0xFF16A34A), modifier = Modifier.size(18.dp))

                                Spacer(modifier = Modifier.width(6.dp))

                                Text("Neighborhood Carbon Footprint Index", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF166534))

                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            val carbonItems = listOf("Grid Energy" to "12.4 kg CO₂/day", "Transport" to "8.7 kg CO₂/day", "Waste" to "3.2 kg CO₂/day")

                            carbonItems.forEach { (source, value) ->

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                    Text(source, fontSize = 11.sp, color = Color(0xFF475569))

                                    Text(value, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF166534))

                                }

                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("🌿 Ward 4 is 18% below the monthly carbon threshold target", fontSize = 10.sp, color = Color(0xFF22C55E), fontWeight = FontWeight.Bold)

                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))



                    // Noise Pollution Level Card

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)),

                        shape = RoundedCornerShape(10.dp),

                        border = BorderStroke(1.dp, Color(0xFFFDBA74))

                    ) {

                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.VolumeUp, null, tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))

                            Spacer(modifier = Modifier.width(8.dp))

                            Column {

                                Text("Live Noise Pollution Monitor", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF92400E))

                                Text("Zone A: 58 dB (Normal) • Zone B: 74 dB (Alert!)", fontSize = 11.sp, color = Color(0xFFD97706))

                                Text("Limit: 55 dB residential / 65 dB commercial", fontSize = 10.sp, color = Color(0xFF78350F))

                            }

                        }

                    }

                    Spacer(modifier = Modifier.height(10.dp))



                    Text("Issue Type", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                        items(issueTypes) { type ->

                            FilterChip(

                                selected = selectedIssue == type,

                                onClick = { selectedIssue = type },

                                label = { Text(type, fontSize = 11.sp) },

                                colors = FilterChipDefaults.filterChipColors(

                                    selectedContainerColor = Color(0xFF22C55E),

                                    selectedLabelColor = Color.White

                                )

                            )

                        }

                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(

                        value = location,

                        onValueChange = { location = it },

                        label = { Text("Location / Landmark") },

                        placeholder = { Text("e.g. Near Block C Park entrance") },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(10.dp)

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(

                        value = details,

                        onValueChange = { details = it },

                        label = { Text("Details") },

                        placeholder = { Text("Describe the issue...") },

                        minLines = 2,

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(10.dp)

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(

                        onClick = {

                            if (location.isBlank()) {

                                Toast.makeText(context, "Please enter location", Toast.LENGTH_SHORT).show()

                            } else {

                                submitted = true

                            }

                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E))

                    ) {

                        Text("Submit Environment Report", fontWeight = FontWeight.Bold)

                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                        Text("Cancel", color = Color(0xFF64748B))

                    }

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun SuggestionsDialog(

    currentRole: String,

    sharedSuggestions: List<SuggestionItem>,

    onUpdateSuggestions: (List<SuggestionItem>) -> Unit,

    onDismiss: () -> Unit

) {

    val context = LocalContext.current

    var suggestionTitle by remember { mutableStateOf("") }

    var suggestionDesc by remember { mutableStateOf("") }

    var showForm by remember { mutableStateOf(false) }

    var votedIds by remember { mutableStateOf(setOf<Int>()) }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Lightbulb, contentDescription = null, tint = Color(0xFF7C3AED))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Citizen Suggestions", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                Text("Community Ideas & Proposals", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(8.dp))

                sharedSuggestions.forEach { suggestion ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(

                            containerColor = if (suggestion.funded) Color(0xFFDCFCE7) else Color(0xFFEDE9FE)

                        ),

                        shape = RoundedCornerShape(10.dp)

                    ) {

                        Row(

                            modifier = Modifier.padding(12.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Column(modifier = Modifier.weight(1f)) {

                                Text(suggestion.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                Text(suggestion.description, fontSize = 11.sp, color = Color(0xFF64748B), maxLines = 2, overflow = TextOverflow.Ellipsis)

                                Text(

                                    text = if (suggestion.funded) "✅ Funded & Approved" else "⏳ Under Review",

                                    fontSize = 10.sp,

                                    fontWeight = FontWeight.Bold,

                                    color = if (suggestion.funded) Color(0xFF22C55E) else Color(0xFF7C3AED)

                                )

                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                val hasVoted = votedIds.contains(suggestion.id)

                                val displayVotes = if (hasVoted) suggestion.votes + 1 else suggestion.votes

                                Text("$displayVotes", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color(0xFF7C3AED))

                                Text("votes", fontSize = 9.sp, color = Color(0xFF94A3B8))

                                if (currentRole == "Citizen" && !hasVoted) {

                                    IconButton(

                                        onClick = { votedIds = votedIds + suggestion.id },

                                        modifier = Modifier.size(32.dp)

                                    ) {

                                        Icon(Icons.Rounded.ThumbUp, contentDescription = "Vote", tint = Color(0xFF7C3AED), modifier = Modifier.size(18.dp))

                                    }

                                }

                            }

                        }

                    }

                }



                if (currentRole == "Citizen") {

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(color = Color(0xFFE2E8F0))

                    Spacer(modifier = Modifier.height(10.dp))

                    if (!showForm) {

                        Button(

                            onClick = { showForm = true },

                            modifier = Modifier.fillMaxWidth(),

                            shape = RoundedCornerShape(10.dp),

                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))

                        ) {

                            Icon(Icons.Rounded.Add, contentDescription = null, modifier = Modifier.size(18.dp))

                            Spacer(modifier = Modifier.width(6.dp))

                            Text("Submit New Suggestion")

                        }

                    } else {

                        OutlinedTextField(

                            value = suggestionTitle,

                            onValueChange = { suggestionTitle = it },

                            label = { Text("Suggestion Title") },

                            modifier = Modifier.fillMaxWidth(),

                            shape = RoundedCornerShape(10.dp)

                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(

                            value = suggestionDesc,

                            onValueChange = { suggestionDesc = it },

                            label = { Text("Describe your idea") },

                            minLines = 2,

                            modifier = Modifier.fillMaxWidth(),

                            shape = RoundedCornerShape(10.dp)

                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                            OutlinedButton(

                                onClick = { showForm = false },

                                modifier = Modifier.weight(1f),

                                shape = RoundedCornerShape(10.dp)

                            ) { Text("Cancel") }

                            Button(

                                onClick = {

                                    if (suggestionTitle.isBlank()) {

                                        Toast.makeText(context, "Please enter a title", Toast.LENGTH_SHORT).show()

                                    } else {

                                        val newSuggestion = SuggestionItem(

                                            id = (1000..9999).random(),

                                            title = suggestionTitle.trim(),

                                            description = suggestionDesc.trim().ifBlank { "No description provided" },

                                            votes = 1

                                        )

                                        onUpdateSuggestions(sharedSuggestions + newSuggestion)

                                        suggestionTitle = ""

                                        suggestionDesc = ""

                                        showForm = false

                                        Toast.makeText(context, "✅ Suggestion submitted for Mayor review!", Toast.LENGTH_LONG).show()

                                    }

                                },

                                modifier = Modifier.weight(1f),

                                shape = RoundedCornerShape(10.dp),

                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))

                            ) { Text("Submit") }

                        }

                    }

                }



                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun BudgetDialog(currentRole: String, onDismiss: () -> Unit) {

    val departments = remember {

        listOf(

            Triple("Roads & Infrastructure", 42_00_000, 31_50_000),

            Triple("Water Supply & Sanitation", 28_50_000, 19_80_000),

            Triple("Public Health & Hospitals", 18_75_000, 14_20_000),

            Triple("Parks & Environment", 8_40_000, 5_10_000),

            Triple("Street Lighting", 6_20_000, 4_80_000),

            Triple("Education & Libraries", 11_30_000, 9_60_000)

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.ShowChart, contentDescription = null, tint = Color(0xFF10B981))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Budget Tracker — FY 2026-27", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                Text("Ward 4 Municipal Budget Allocation & Expenditure:", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(10.dp))

                departments.forEach { (name, allocated, spent) ->

                    val pct = (spent.toFloat() / allocated.toFloat())

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),

                        shape = RoundedCornerShape(10.dp),

                        border = BorderStroke(1.dp, Color(0xFF22C55E).copy(alpha = 0.2f))

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {

                                Text(name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A), modifier = Modifier.weight(1f))

                                Text("${(pct * 100).toInt()}%", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = if (pct > 0.85f) Color(0xFFEF4444) else Color(0xFF10B981))

                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            LinearProgressIndicator(

                                progress = pct,

                                color = if (pct > 0.85f) Color(0xFFEF4444) else Color(0xFF10B981),

                                trackColor = Color(0xFFE2E8F0),

                                modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape)

                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {

                                Text("Spent: ₹${"%,d".format(spent)}", fontSize = 10.sp, color = Color(0xFF64748B))

                                Text("Budget: ₹${"%,d".format(allocated)}", fontSize = 10.sp, color = Color(0xFF64748B))

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                if (currentRole == "Municipality Admin" || currentRole == "Mayor / Representative") {

                    Card(

                        modifier = Modifier.fillMaxWidth(),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)),

                        shape = RoundedCornerShape(10.dp)

                    ) {

                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.AdminPanelSettings, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(18.dp))

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Admin View: Q3 2026 reallocation approval pending for Roads dept (₹6.5L excess request).", fontSize = 11.sp, color = Color(0xFF92400E))

                        }

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun TendersDialog(currentRole: String, onDismiss: () -> Unit) {

    val context = LocalContext.current

    val tenders = remember {

        listOf(

            listOf("MCD/2026/T-441", "Road Resurfacing — Phase 3", "₹42,00,000", "Open", "30 Jun 2026"),

            listOf("MCD/2026/T-442", "Smart Dustbin Installation x50", "₹8,75,000", "Open", "15 Jul 2026"),

            listOf("MCD/2026/T-438", "Public Toilet Construction x5", "₹12,20,000", "Awarded", "Closed"),

            listOf("MCD/2026/T-435", "CCTV Surveillance Network", "₹31,40,000", "Awarded", "Closed")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.ReceiptLong, contentDescription = null, tint = Color(0xFF7C3AED))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Tenders & Procurement", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                Text("Active & recent municipal tenders:", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(10.dp))

                tenders.forEach { tender ->

                    val isOpen = tender[3] == "Open"

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(

                            containerColor = if (isOpen) Color(0xFFEDE9FE) else Color(0xFFF1F5F9)

                        ),

                        shape = RoundedCornerShape(10.dp)

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween,

                                verticalAlignment = Alignment.Top

                            ) {

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(tender[1], fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))

                                    Text(tender[0], fontSize = 10.sp, color = Color(0xFF94A3B8))

                                }

                                Box(

                                    modifier = Modifier

                                        .clip(RoundedCornerShape(6.dp))

                                        .background(if (isOpen) Color(0xFF7C3AED) else Color(0xFF64748B))

                                        .padding(horizontal = 6.dp, vertical = 2.dp)

                                ) {

                                    Text(tender[3], fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)

                                }

                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {

                                Text("Value: ${tender[2]}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF7C3AED))

                                Text("Deadline: ${tender[4]}", fontSize = 10.sp, color = Color(0xFF64748B))

                            }

                            if (isOpen && (currentRole == "Department Officer" || currentRole == "Municipality Admin")) {

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(

                                    onClick = { Toast.makeText(context, "Tender ${tender[0]} marked for award. Awaiting Admin approval.", Toast.LENGTH_LONG).show() },

                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)),

                                    shape = RoundedCornerShape(8.dp),

                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),

                                    modifier = Modifier.height(34.dp)

                                ) {

                                    Text("Award Tender", fontSize = 11.sp)

                                }

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun WardNoticesDialog(viewModel: CivicViewModel, currentRole: String, onDismiss: () -> Unit) {

    val context = LocalContext.current

    val notices by viewModel.notices.collectAsState()

    var noticeTitle by remember { mutableStateOf("") }

    var noticeContent by remember { mutableStateOf("") }

    var selectedNoticeType by remember { mutableStateOf("General") }

    var selectedTargetSector by remember { mutableStateOf("All") }

    val noticeTypes = listOf("General", "Emergency", "Tender", "Event")

    val sectors = listOf("All", "Sector A", "Sector B", "Sector C", "Sector D")



    // Citizen filter state

    var citizenFilter by remember { mutableStateOf("All") }



    val isAdminRole = currentRole == "Admin" || currentRole == "Municipality Admin" || currentRole == "Ward Officer"



    val displayedNotices = if (currentRole == "Citizen") {

        notices.filter { it.targetLocation == "All" || it.targetLocation == citizenFilter || citizenFilter == "All" }

    } else {

        notices

    }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Campaign, contentDescription = null, tint = Color(0xFF0EA5E9))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Ward Notices Board", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                // Citizen: filter chips

                if (currentRole == "Citizen") {

                    Text("Filter by Sector", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))

                    Spacer(modifier = Modifier.height(4.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                        items(sectors) { sector ->

                            FilterChip(

                                selected = citizenFilter == sector,

                                onClick = { citizenFilter = sector },

                                label = { Text(sector, fontSize = 10.sp) },

                                colors = FilterChipDefaults.filterChipColors(

                                    selectedContainerColor = Color(0xFF0EA5E9),

                                    selectedLabelColor = Color.White

                                )

                            )

                        }

                    }

                    Spacer(modifier = Modifier.height(10.dp))

                }



                Text("Published Notices", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(8.dp))



                if (displayedNotices.isEmpty()) {

                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {

                        Text("No notices for this sector.", fontSize = 13.sp, color = Color(0xFF94A3B8))

                    }

                } else {

                    displayedNotices.forEach { notice ->

                        val type = notice.type

                        val sectorTag = if (notice.targetLocation != null && notice.targetLocation != "All") " — ${notice.targetLocation}" else ""

                        Card(

                            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),

                            colors = CardDefaults.cardColors(

                                containerColor = when (type) {

                                    "Emergency" -> Color(0xFFFEE2E2)

                                    "Tender" -> Color(0xFFEFF6FF)

                                    "Event" -> Color(0xFFF0FDF4)

                                    else -> Color(0xFFFFF7ED)

                                }

                            ),

                            shape = RoundedCornerShape(8.dp)

                        ) {

                            Row(

                                modifier = Modifier.padding(10.dp),

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Box(

                                    modifier = Modifier

                                        .clip(RoundedCornerShape(4.dp))

                                        .background(

                                            when (type) {

                                                "Emergency" -> Color(0xFFEF4444)

                                                "Tender" -> Color(0xFF2563EB)

                                                "Event" -> Color(0xFF22C55E)

                                                else -> Color(0xFFF59E0B)

                                            }

                                        )

                                        .padding(horizontal = 5.dp, vertical = 2.dp)

                                ) {

                                    Text(type, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.White)

                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(notice.title, fontSize = 12.sp, color = Color(0xFF0F172A), fontWeight = FontWeight.SemiBold)

                                    if (sectorTag.isNotBlank()) {

                                        Text(sectorTag, fontSize = 9.sp, color = Color(0xFF0EA5E9), fontWeight = FontWeight.Bold)

                                    }

                                }

                            }

                        }

                    }

                }



                if (isAdminRole) {

                    Spacer(modifier = Modifier.height(14.dp))

                    HorizontalDivider(color = Color(0xFFE2E8F0))

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Publish New Notice", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                    Spacer(modifier = Modifier.height(6.dp))



                    Text("Notice Type", fontSize = 10.sp, color = Color(0xFF64748B))

                    Spacer(modifier = Modifier.height(4.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                        items(noticeTypes) { type ->

                            FilterChip(

                                selected = selectedNoticeType == type,

                                onClick = { selectedNoticeType = type },

                                label = { Text(type, fontSize = 10.sp) }

                            )

                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))



                    Text("Target Sector", fontSize = 10.sp, color = Color(0xFF64748B))

                    Spacer(modifier = Modifier.height(4.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                        items(sectors) { sector ->

                            FilterChip(

                                selected = selectedTargetSector == sector,

                                onClick = { selectedTargetSector = sector },

                                label = { Text(sector, fontSize = 10.sp) },

                                colors = FilterChipDefaults.filterChipColors(

                                    selectedContainerColor = Color(0xFF0EA5E9),

                                    selectedLabelColor = Color.White

                                )

                            )

                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))



                    OutlinedTextField(

                        value = noticeTitle,

                        onValueChange = { noticeTitle = it },

                        label = { Text("Notice Title / Summary") },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(10.dp)

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(

                        value = noticeContent,

                        onValueChange = { noticeContent = it },

                        label = { Text("Full Notice Content") },

                        minLines = 2,

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(10.dp)

                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(

                        onClick = {

                            if (noticeTitle.isBlank()) {

                                Toast.makeText(context, "Enter notice title", Toast.LENGTH_SHORT).show()

                            } else {

                                viewModel.publishNotice(

                                    title = noticeTitle.trim(),

                                    content = noticeContent.trim().ifBlank { noticeTitle.trim() },

                                    type = selectedNoticeType,

                                    targetLocation = selectedTargetSector

                                )

                                noticeTitle = ""

                                noticeContent = ""

                                Toast.makeText(context, "📢 Notice published to $selectedTargetSector!", Toast.LENGTH_LONG).show()

                            }

                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0EA5E9))

                    ) {

                        Text("Publish Notice", fontWeight = FontWeight.Bold)

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}







@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun ApprovalsDialog(

    currentRole: String,

    sharedCertificates: List<CertificateApplication>,

    onUpdateCertificates: (List<CertificateApplication>) -> Unit,

    onDismiss: () -> Unit

) {

    val context = LocalContext.current

    val canApprove = currentRole == "Department Officer" || currentRole == "Municipality Admin"



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = Color(0xFF10B981))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Application Approvals", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                val pending = sharedCertificates.filter { it.status == "Pending Approval" }

                val approved = sharedCertificates.filter { it.status == "Approved" }



                if (pending.isNotEmpty()) {

                    Text("📋 Pending Review (${pending.size})", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFFF59E0B))

                    Spacer(modifier = Modifier.height(6.dp))

                    pending.forEach { cert ->

                        Card(

                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)),

                            shape = RoundedCornerShape(12.dp),

                            border = BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.4f))

                        ) {

                            Column(modifier = Modifier.padding(12.dp)) {

                                Text(cert.type, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                Text("Applicant: ${cert.applicantName}", fontSize = 12.sp, color = Color(0xFF475569))

                                Text("Details: ${cert.details}", fontSize = 11.sp, color = Color(0xFF64748B), maxLines = 2, overflow = TextOverflow.Ellipsis)

                                Text("ID: ${cert.id} | Tracking: ${cert.trackingNo}", fontSize = 10.sp, color = Color(0xFF94A3B8))

                                if (canApprove) {

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                                        OutlinedButton(

                                            onClick = {

                                                val updated = sharedCertificates.filter { it.id != cert.id }

                                                onUpdateCertificates(updated)

                                                Toast.makeText(context, "Application ${cert.id} rejected & removed.", Toast.LENGTH_SHORT).show()

                                            },

                                            modifier = Modifier.weight(1f),

                                            shape = RoundedCornerShape(8.dp)

                                        ) { Text("Reject", color = Color(0xFFEF4444), fontSize = 12.sp) }

                                        Button(

                                            onClick = {

                                                val updated = sharedCertificates.map {

                                                    if (it.id == cert.id) it.copy(status = "Approved") else it

                                                }

                                                onUpdateCertificates(updated)

                                                Toast.makeText(context, "✅ ${cert.type} approved for ${cert.applicantName}!", Toast.LENGTH_LONG).show()

                                            },

                                            modifier = Modifier.weight(1f),

                                            shape = RoundedCornerShape(8.dp),

                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))

                                        ) { Text("Approve", fontSize = 12.sp) }

                                    }

                                }

                            }

                        }

                    }

                }



                if (approved.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("✅ Approved (${approved.size})", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF22C55E))

                    Spacer(modifier = Modifier.height(6.dp))

                    approved.forEach { cert ->

                        Card(

                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                            colors = CardDefaults.cardColors(containerColor = Color(0xFFDCFCE7)),

                            shape = RoundedCornerShape(10.dp),

                            border = BorderStroke(1.dp, Color(0xFF22C55E).copy(alpha = 0.3f))

                        ) {

                            Column(modifier = Modifier.padding(12.dp)) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Icon(Icons.Rounded.Verified, contentDescription = null, tint = Color(0xFF22C55E), modifier = Modifier.size(20.dp))

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Column {

                                        Text(cert.type, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))

                                        Text("${cert.applicantName} — Approved ✓", fontSize = 11.sp, color = Color(0xFF22C55E))

                                        Text("Tracking: ${cert.trackingNo}", fontSize = 9.sp, color = Color(0xFF94A3B8))

                                    }

                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(

                                    modifier = Modifier.fillMaxWidth(),

                                    horizontalArrangement = Arrangement.SpaceBetween,

                                    verticalAlignment = Alignment.CenterVertically

                                ) {

                                    MockQrCode(modifier = Modifier.size(52.dp))

                                    DigitalSignatureStamp()

                                }

                            }

                        }

                    }

                }



                if (sharedCertificates.isEmpty()) {

                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {

                        Text("No applications to review. All clear! ✅", fontSize = 14.sp, color = Color(0xFF64748B), textAlign = TextAlign.Center)

                    }

                }



                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun CitizenFeedbackDialog(

    sharedSuggestions: List<SuggestionItem>,

    onDismiss: () -> Unit

) {

    val context = LocalContext.current

    val sentimentData = remember {

        listOf(

            Triple("Infrastructure", 72, Color(0xFF22C55E)),

            Triple("Sanitation", 55, Color(0xFFF59E0B)),

            Triple("Healthcare", 81, Color(0xFF22C55E)),

            Triple("Water Supply", 44, Color(0xFFEF4444)),

            Triple("Public Transport", 67, Color(0xFF0284C7)),

            Triple("Education", 79, Color(0xFF22C55E))

        )

    }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.RateReview, contentDescription = null, tint = Color(0xFF10B981))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Citizen Feedback & Sentiment", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                Text("Ward 4 Citizen Satisfaction Scores (Q2 2026):", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(10.dp))

                sentimentData.forEach { (dept, score, color) ->

                    Row(

                        modifier = Modifier

                            .fillMaxWidth()

                            .padding(vertical = 4.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Text(dept, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF0F172A), modifier = Modifier.weight(0.4f))

                        LinearProgressIndicator(

                            progress = score / 100f,

                            color = color,

                            trackColor = Color(0xFFE2E8F0),

                            modifier = Modifier

                                .weight(0.45f)

                                .height(8.dp)

                                .clip(CircleShape)

                        )

                        Text("$score%", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = color, modifier = Modifier.weight(0.15f).padding(start = 6.dp))

                    }

                }



                Spacer(modifier = Modifier.height(14.dp))

                HorizontalDivider(color = Color(0xFFE2E8F0))

                Spacer(modifier = Modifier.height(10.dp))



                Text("Top Citizen Suggestions (${sharedSuggestions.size} total):", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(6.dp))

                sharedSuggestions.sortedByDescending { it.votes }.take(5).forEach { suggestion ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE9FE)),

                        shape = RoundedCornerShape(8.dp)

                    ) {

                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                            Column(modifier = Modifier.weight(1f)) {

                                Text(suggestion.title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))

                                Text(suggestion.description, fontSize = 10.sp, color = Color(0xFF64748B), maxLines = 1, overflow = TextOverflow.Ellipsis)

                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Icon(Icons.Rounded.ThumbUp, contentDescription = null, tint = Color(0xFF7C3AED), modifier = Modifier.size(14.dp))

                                Spacer(modifier = Modifier.width(3.dp))

                                Text("${suggestion.votes}", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Color(0xFF7C3AED))

                            }

                        }

                    }

                }



                Spacer(modifier = Modifier.height(12.dp))

                Button(

                    onClick = {

                        Toast.makeText(context, "📊 Generating citizen sentiment report for Mayor's office...", Toast.LENGTH_LONG).show()

                        onDismiss()

                    },

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(10.dp),

                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))

                ) {

                    Icon(Icons.Rounded.CloudDownload, contentDescription = null, modifier = Modifier.size(18.dp))

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("Export Sentiment Report", fontWeight = FontWeight.Bold)

                }

                Spacer(modifier = Modifier.height(4.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// PROFILE DIALOG

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun ProfileDialog(

    currentRole: String,

    viewModel: CivicViewModel,

    onDismiss: () -> Unit

) {

    val citizenPoints by viewModel.citizenPoints.collectAsState()

    val workerPoints by viewModel.workerPoints.collectAsState()

    val earnedAchievements by viewModel.earnedAchievements.collectAsState()

    val earnedWorkerAchievements by viewModel.earnedWorkerAchievements.collectAsState()

    val complaints by viewModel.complaints.collectAsState()



    val isWorker = currentRole == "Worker"

    val totalPoints = if (isWorker) workerPoints else citizenPoints

    val earnedAchs = if (isWorker) earnedWorkerAchievements else earnedAchievements

    val achList = if (isWorker) viewModel.workerAchievementsList else viewModel.achievementsList



    // Stats

    val myComplaints = if (isWorker) complaints.filter { it.workerName != null } else complaints

    val resolvedCount = myComplaints.count { it.status == "Resolved" }

    val inProgressCount = myComplaints.count { it.status == "In Progress" }

    val totalEarned = if (isWorker) myComplaints.sumOf { it.governmentPayout } else 0.0

    val totalTips = if (isWorker) myComplaints.sumOf { it.tipAmount } else 0.0



    val headerGradient = if (isWorker)

        Brush.linearGradient(listOf(Color(0xFF065F46), Color(0xFF10B981)))

    else

        Brush.linearGradient(listOf(Color(0xFF1E40AF), Color(0xFF7C3AED)))



    val profileNames by viewModel.profileNames.collectAsState()
    val defaultName = when (currentRole) {
        "Citizen" -> "Priya Sharma"; "Worker" -> "Rajan Patel"; "Admin" -> "Dr. Meena Kulkarni"; else -> "User"
    }
    val profileName = profileNames[currentRole.lowercase()] ?: defaultName

    var nameInput by remember(profileName) { mutableStateOf(profileName) }
    var isEditing by remember { mutableStateOf(false) }

    val profileId = when (currentRole) {
        "Citizen" -> "CT-2026-W4-7842"; "Worker" -> "WK-2026-W4-0312"; "Admin" -> "ADM-2026-001"; else -> "N/A"
    }



    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {

        Card(

            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),

            shape = RoundedCornerShape(24.dp),

            colors = CardDefaults.cardColors(containerColor = Color.White),

            border = BorderStroke(1.dp, Color(0xFFE2E8F0))

        ) {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {



                // ── Gradient header ─────────────────────────────────

                Box(modifier = Modifier.fillMaxWidth().background(headerGradient).padding(24.dp), contentAlignment = Alignment.Center) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Box(modifier = Modifier.size(72.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {

                            Text(if (isWorker) "🔧" else "👤", fontSize = 36.sp)

                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        if (isEditing) {
                            OutlinedTextField(
                                value = nameInput,
                                onValueChange = { nameInput = it },
                                singleLine = true,
                                modifier = Modifier.width(200.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color.White.copy(alpha = 0.1f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f)
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TextButton(onClick = {
                                    if (nameInput.isNotBlank()) {
                                        viewModel.saveProfileName(currentRole, nameInput)
                                        isEditing = false
                                    }
                                }) {
                                    Text("Save", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                                TextButton(onClick = { isEditing = false; nameInput = profileName }) {
                                    Text("Cancel", color = Color.White.copy(alpha = 0.7f))
                                }
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(profileName, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                IconButton(onClick = { isEditing = true }, modifier = Modifier.size(24.dp)) {
                                    Icon(Icons.Rounded.Edit, contentDescription = "Edit Name", tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                                }
                            }
                        }

                        Text("Ward 4 · $currentRole", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(Color.White.copy(alpha = 0.2f)).padding(horizontal = 16.dp, vertical = 6.dp)) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Icon(Icons.Rounded.Star, null, tint = Color(0xFFFBBF24), modifier = Modifier.size(18.dp))

                                Spacer(modifier = Modifier.width(6.dp))

                                Text("$totalPoints ${if (isWorker) "Worker" else "Citizen"} XP", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)

                            }

                        }

                    }

                }



                Column(modifier = Modifier.padding(20.dp)) {



                    // ── Stats cards ─────────────────────────────────

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                        val stats = if (isWorker) listOf(

                            Triple("${myComplaints.size}", "Assigned", Color(0xFF3B82F6)),

                            Triple("$resolvedCount", "Resolved", Color(0xFF10B981)),

                            Triple("Rs.${(totalEarned + totalTips).toInt()}", "Earned", Color(0xFFF59E0B))

                        ) else listOf(

                            Triple("${myComplaints.size}", "Filed", Color(0xFF3B82F6)),

                            Triple("$resolvedCount", "Resolved", Color(0xFF10B981)),

                            Triple("${earnedAchs.size}", "Badges", Color(0xFFF59E0B))

                        )

                        stats.forEach { (value, label, color) ->

                            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f)), shape = RoundedCornerShape(12.dp)) {

                                Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                                    Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = color)

                                    Text(label, fontSize = 10.sp, color = Color(0xFF64748B), textAlign = TextAlign.Center)

                                }

                            }

                        }

                    }



                    // ── Worker earnings ─────────────────────────────

                    if (isWorker && (totalEarned > 0 || totalTips > 0)) {

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("💰 Earnings", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = Color(0xFF0F172A))

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(0.dp)) {

                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                    Text("Govt Payouts", fontSize = 13.sp, color = Color(0xFF475569))

                                    Text("Rs.${totalEarned.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))

                                }

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                    Text("Citizen Tips Received", fontSize = 13.sp, color = Color(0xFF475569))

                                    Text("Rs.${totalTips.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF59E0B))

                                }

                                HorizontalDivider(color = Color(0xFFBBF7D0))

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                    Text("Total", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                                    Text("Rs.${(totalEarned + totalTips).toInt()}", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF065F46))

                                }

                            }

                        }

                    }



                    // ── Achievements ─────────────────────────────────

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("🏆 Achievements", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = Color(0xFF0F172A))

                    Spacer(modifier = Modifier.height(8.dp))

                    if (achList.isEmpty() || earnedAchs.isEmpty()) {

                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)), shape = RoundedCornerShape(12.dp)) {

                            Text("No badges yet. Complete tasks to earn badges!", modifier = Modifier.padding(14.dp), fontSize = 12.sp, color = Color(0xFF94A3B8))

                        }

                    }

                    // Earned badges

                    achList.filter { it.id in earnedAchs }.forEach { ach ->

                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp).clip(RoundedCornerShape(10.dp)).background(Color(0xFFFEF3C7)).padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.EmojiEvents, null, tint = Color(0xFFF59E0B), modifier = Modifier.size(22.dp))

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(ach.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                Text(ach.description, fontSize = 10.sp, color = Color(0xFF64748B))

                            }

                            Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color(0xFFF59E0B).copy(alpha = 0.15f)).padding(horizontal = 6.dp, vertical = 2.dp)) {

                                Text("+${ach.points}XP", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFD97706))

                            }

                        }

                    }

                    // Locked badges (greyed out)

                    achList.filter { it.id !in earnedAchs }.forEach { ach ->

                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp).clip(RoundedCornerShape(10.dp)).background(Color(0xFFF1F5F9)).padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.Lock, null, tint = Color(0xFFCBD5E1), modifier = Modifier.size(22.dp))

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(ach.title, fontWeight = FontWeight.Medium, fontSize = 13.sp, color = Color(0xFF94A3B8))

                                Text(ach.description, fontSize = 10.sp, color = Color(0xFFCBD5E1))

                            }

                            Text("+${ach.points}XP", fontSize = 11.sp, color = Color(0xFFCBD5E1))

                        }

                    }



                    // ── Points leaderboard table ──────────────────────

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("📊 Points Breakdown", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = Color(0xFF0F172A))

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(0.dp)) {

                        Column(modifier = Modifier.padding(14.dp)) {

                            // Header

                            Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(6.dp)).background(Color(0xFFE2E8F0)).padding(horizontal = 10.dp, vertical = 6.dp)) {

                                Text("Activity", modifier = Modifier.weight(1f), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF475569))

                                Text("XP", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF475569))

                            }

                            val tableRows = if (isWorker) listOf(

                                "First Complaint Resolved" to 100, "Project Progress Update" to 100,

                                "Gold 5★ Rating" to 150, "250+ XP Veteran Bonus" to 200,

                                "Per Resolution" to 120, "Citizen Rating Bonus (per ★)" to 15

                            ) else listOf(

                                "First Grievance Filed" to 100, "Voted in Poll" to 50,

                                "AI Chat Interaction" to 30, "5★ Rating Given" to 50,

                                "Elder Mode Used" to 25, "Rating Submitted" to 20

                            )

                            tableRows.forEachIndexed { idx, (activity, xp) ->

                                Row(

                                    modifier = Modifier.fillMaxWidth().background(if (idx % 2 == 0) Color.Transparent else Color(0xFFF1F5F9)).padding(horizontal = 10.dp, vertical = 7.dp),

                                    verticalAlignment = Alignment.CenterVertically

                                ) {

                                    Text(activity, modifier = Modifier.weight(1f), fontSize = 11.sp, color = Color(0xFF475569))

                                    Text("+$xp", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (isWorker) Color(0xFF10B981) else Color(0xFF007AFF))

                                }

                            }

                            HorizontalDivider(color = Color(0xFFE2E8F0))

                            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                                Text("Your Total XP", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))

                                Text("$totalPoints XP", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = if (isWorker) Color(0xFF10B981) else Color(0xFF007AFF))

                            }

                        }

                    }



                    // ── Complaint / Work History ──────────────────────

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(if (isWorker) "🗂️ Work History" else "🗂️ Complaint History", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = Color(0xFF0F172A))

                    Spacer(modifier = Modifier.height(8.dp))

                    if (myComplaints.isEmpty()) {

                        Text("No history yet.", fontSize = 12.sp, color = Color(0xFF94A3B8))

                    } else {

                        myComplaints.take(5).forEach { c ->

                            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)), shape = RoundedCornerShape(10.dp), elevation = CardDefaults.cardElevation(0.dp)) {

                                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                                    val (stColor, stBg) = when (c.status) {

                                        "Resolved" -> Color(0xFF10B981) to Color(0xFFECFDF5)

                                        "In Progress" -> Color(0xFFEA580C) to Color(0xFFFFF7ED)

                                        else -> Color(0xFF2563EB) to Color(0xFFEFF6FF)

                                    }

                                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(stColor))

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Column(modifier = Modifier.weight(1f)) {

                                        Text(c.title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A), maxLines = 1, overflow = TextOverflow.Ellipsis)

                                        Text("${c.category} · ${c.status}", fontSize = 10.sp, color = Color(0xFF64748B))

                                    }

                                    Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(stBg).padding(horizontal = 6.dp, vertical = 2.dp)) {

                                        Text(c.status, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = stColor)

                                    }

                                }

                            }

                        }

                        if (myComplaints.size > 5) {

                            Text("+ ${myComplaints.size - 5} more", fontSize = 11.sp, color = Color(0xFF007AFF), modifier = Modifier.padding(top = 4.dp))

                        }

                    }



                    // ── Profile info ─────────────────────────────────

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("👤 Profile Info", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = Color(0xFF0F172A))

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(0.dp)) {

                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

                            listOf(

                                "Email" to "${currentRole.lowercase()}@civictrack.org",

                                "Mobile" to "+91 98765 43210",

                                "Ward" to "Ward 4, Smart Sector A",

                                "ID" to profileId,

                                "Member Since" to "January 2025"

                            ).forEach { (label, value) ->

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                    Text(label, fontSize = 12.sp, color = Color(0xFF64748B))

                                    Text(value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A))

                                }

                            }

                        }

                    }



                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))) {

                        Text("Close Profile", fontWeight = FontWeight.Bold)

                    }

                }

            }

        }

    }

}



// =========================================================================

// HEALTH SERVICES DIALOG

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun HealthServicesDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    val hospitals = remember {

        listOf(

            Triple("Ward 4 Government Hospital", "NH-4, Sector A · 24/7 Emergency", "022-2345-6789"),

            Triple("Rajiv Gandhi Health Centre", "Civic Lane 12, Sector B · OPD: 8AM–6PM", "022-2345-6790"),

            Triple("Mata Kasturba Clinic", "Market Road, Block C · Mon–Sat", "022-2345-6791"),

            Triple("District Blood Bank", "Civil Hospital Campus · 24/7", "022-2345-6792")

        )

    }

    val vaccineSchedule = remember {

        listOf(

            Pair("Polio Drive", "15 July 2026 · All wards"),

            Pair("MMR Vaccination", "22 July 2026 · PHC Sector B"),

            Pair("COVID Booster", "1 Aug 2026 · Ward Office"),

            Pair("Flu Shot Camp", "10 Aug 2026 · Community Hall")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.LocalHospital, contentDescription = null, tint = Color(0xFFEF4444))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Health Services", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                // Emergency contacts

                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.spacedBy(6.dp)

                ) {

                    listOf("🚑 Ambulance" to "108", "🏥 Hospital" to "102", "🩸 Blood" to "104").forEach { (label, num) ->

                        Card(

                            modifier = Modifier.weight(1f),

                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2)),

                            shape = RoundedCornerShape(10.dp),

                            onClick = { Toast.makeText(context, "Calling $label: $num", Toast.LENGTH_SHORT).show() }

                        ) {

                            Column(

                                modifier = Modifier.padding(8.dp),

                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {

                                Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF7F1D1D), textAlign = TextAlign.Center)

                                Text(num, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFEF4444))

                            }

                        }

                    }

                }



                Spacer(modifier = Modifier.height(14.dp))

                Text("🏥 Nearby Hospitals & Clinics", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(8.dp))

                hospitals.forEach { (name, address, phone) ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),

                        shape = RoundedCornerShape(10.dp),

                        border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.3f))

                    ) {

                        Row(

                            modifier = Modifier.padding(12.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Icon(Icons.Rounded.LocalHospital, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(28.dp))

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                Text(address, fontSize = 10.sp, color = Color(0xFF64748B))

                                Text(phone, fontSize = 11.sp, color = Color(0xFF0284C7), fontWeight = FontWeight.SemiBold)

                            }

                            IconButton(onClick = { Toast.makeText(context, "Calling $phone", Toast.LENGTH_SHORT).show() }) {

                                Icon(Icons.Rounded.Phone, contentDescription = "Call", tint = Color(0xFF10B981), modifier = Modifier.size(20.dp))

                            }

                        }

                    }

                }



                Spacer(modifier = Modifier.height(14.dp))

                Text("💉 Upcoming Vaccination Drives", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(8.dp))

                vaccineSchedule.forEach { (name, detail) ->

                    Row(

                        modifier = Modifier

                            .fillMaxWidth()

                            .padding(vertical = 3.dp)

                            .clip(RoundedCornerShape(8.dp))

                            .background(Color(0xFFEFF6FF))

                            .padding(10.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Icon(Icons.Rounded.Vaccines, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {

                            Text(name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                            Text(detail, fontSize = 10.sp, color = Color(0xFF64748B))

                        }

                    }

                }



                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// JOBS & EMPLOYMENT DIALOG

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun JobsDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    val jobs = remember {

        listOf(

            Triple("Junior Civil Engineer", "PWD – Roads & Highways · Full Time", "₹35,000/mo"),

            Triple("Sanitation Supervisor", "Municipal Sanitation Board · Contract", "₹22,000/mo"),

            Triple("Data Entry Operator", "Ward Office, Sector 4 · Part Time", "₹15,000/mo"),

            Triple("Electrician Technician", "Municipal Power Utility · Full Time", "₹28,000/mo"),

            Triple("Community Health Worker", "PHC Sector B · Contractual", "₹18,500/mo")

        )

    }

    val skillPrograms = remember {

        listOf(

            Pair("Digital Literacy Certificate", "Free · 4 weeks · Online"),

            Pair("Plumbing & Sanitation Skills", "Free · 6 weeks · ITI Sector A"),

            Pair("Mobile App Development", "Free · 8 weeks · Online"),

            Pair("Agri-Tech Training", "Free · 3 weeks · Krishi Centre")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Work, contentDescription = null, tint = Color(0xFF0284C7))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Jobs & Employment", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                Text("📋 Municipal Job Openings", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(8.dp))

                jobs.forEach { (title, dept, salary) ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),

                        shape = RoundedCornerShape(10.dp),

                        border = BorderStroke(1.dp, Color(0xFF0284C7).copy(alpha = 0.3f))

                    ) {

                        Row(

                            modifier = Modifier.padding(12.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Box(

                                modifier = Modifier

                                    .size(36.dp)

                                    .background(Color(0xFF0284C7).copy(alpha = 0.1f), CircleShape),

                                contentAlignment = Alignment.Center

                            ) {

                                Icon(Icons.Rounded.Work, contentDescription = null, tint = Color(0xFF0284C7), modifier = Modifier.size(18.dp))

                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                Text(dept, fontSize = 10.sp, color = Color(0xFF64748B))

                                Text(salary, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF10B981))

                            }

                            TextButton(

                                onClick = { Toast.makeText(context, "Opening application for: $title", Toast.LENGTH_SHORT).show() }

                            ) { Text("Apply", fontSize = 11.sp, color = Color(0xFF0284C7), fontWeight = FontWeight.Bold) }

                        }

                    }

                }



                Spacer(modifier = Modifier.height(14.dp))

                Text("📚 Free Skill Development Programs", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF0F172A))

                Spacer(modifier = Modifier.height(8.dp))

                skillPrograms.forEach { (name, detail) ->

                    Row(

                        modifier = Modifier

                            .fillMaxWidth()

                            .padding(vertical = 3.dp)

                            .clip(RoundedCornerShape(8.dp))

                            .background(Color(0xFFF0FDF4))

                            .padding(10.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Icon(Icons.Rounded.School, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {

                            Text(name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                            Text(detail, fontSize = 10.sp, color = Color(0xFF64748B))

                        }

                    }

                }



                Spacer(modifier = Modifier.height(8.dp))

                Button(

                    onClick = { Toast.makeText(context, "Opening Municipal Job Portal...", Toast.LENGTH_SHORT).show(); onDismiss() },

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(10.dp),

                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))

                ) {

                    Icon(Icons.Rounded.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("Visit Full Job Portal", fontWeight = FontWeight.Bold)

                }

                Spacer(modifier = Modifier.height(4.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// NEWS & UPDATES DIALOG

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun NewsDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    val categories = listOf("All", "Infrastructure", "Health", "Environment", "Events")

    var selectedCategory by remember { mutableStateOf("All") }



    data class NewsItem(

        val headline: String,

        val summary: String,

        val date: String,

        val category: String,

        val color: Color,

        val bookmarked: Boolean = false

    )



    val newsItems = remember {

        mutableStateOf(listOf(

            NewsItem("New Flyover Construction Begins on NH-4", "The long-awaited flyover at Junction 7 will reduce traffic congestion by 40%. Work begins July 1.", "25 Jun 2026", "Infrastructure", Color(0xFF3B82F6)),

            NewsItem("Ward 4 Launches Free Health Camp", "Free checkups including sugar, BP and eye tests conducted at Community Hall every Saturday this month.", "24 Jun 2026", "Health", Color(0xFF10B981)),

            NewsItem("Air Quality Index Improves by 18%", "Ward 4 AQI dropped from 142 to 117 following the ban on open waste burning and EV transit introduction.", "23 Jun 2026", "Environment", Color(0xFF22C55E)),

            NewsItem("Monsoon Sports Day at Sector Park", "Annual Municipal Sports Day on July 15 at Sector A Public Park. Registration open for all age groups.", "22 Jun 2026", "Events", Color(0xFF8B5CF6)),

            NewsItem("Water Supply Restored in Block D", "Following emergency pipeline repair, water supply has been fully restored to all 450 households in Block D.", "21 Jun 2026", "Infrastructure", Color(0xFF0EA5E9)),

            NewsItem("Dengue Prevention Drive Launched", "Health workers will visit all households July 5–15 to check stagnant water and distribute mosquito nets.", "20 Jun 2026", "Health", Color(0xFFEF4444)),

            NewsItem("Tree Plantation Drive: 500 Trees Planted", "As part of Green Ward 2027, 500 saplings were planted along Central Boulevard.", "19 Jun 2026", "Environment", Color(0xFF16A34A))

        ))

    }



    val filtered = if (selectedCategory == "All") newsItems.value else newsItems.value.filter { it.category == selectedCategory }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Newspaper, contentDescription = null, tint = Color(0xFFF59E0B))

                Spacer(modifier = Modifier.width(8.dp))

                Text("News & Updates", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                LazyRow(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.spacedBy(6.dp)

                ) {

                    items(categories) { cat ->

                        val isSelected = cat == selectedCategory

                        Box(

                            modifier = Modifier

                                .clip(RoundedCornerShape(20.dp))

                                .background(if (isSelected) Color(0xFFF59E0B) else Color(0xFFF1F5F9))

                                .clickable { selectedCategory = cat }

                                .padding(horizontal = 12.dp, vertical = 6.dp)

                        ) {

                            Text(

                                text = cat,

                                fontSize = 11.sp,

                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,

                                color = if (isSelected) Color.White else Color(0xFF64748B)

                            )

                        }

                    }

                }



                Spacer(modifier = Modifier.height(10.dp))



                filtered.forEach { news ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = news.color.copy(alpha = 0.06f)),

                        shape = RoundedCornerShape(12.dp),

                        border = BorderStroke(1.dp, news.color.copy(alpha = 0.25f))

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween,

                                verticalAlignment = Alignment.Top

                            ) {

                                Column(modifier = Modifier.weight(1f)) {

                                    Box(

                                        modifier = Modifier

                                            .clip(RoundedCornerShape(6.dp))

                                            .background(news.color.copy(alpha = 0.15f))

                                            .padding(horizontal = 6.dp, vertical = 2.dp)

                                    ) {

                                        Text(news.category, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = news.color)

                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(news.headline, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Color(0xFF0F172A), lineHeight = 17.sp)

                                }

                                Icon(

                                    imageVector = Icons.Rounded.Bookmark,

                                    contentDescription = "Bookmark",

                                    tint = news.color.copy(alpha = 0.4f),

                                    modifier = Modifier.size(18.dp)

                                )

                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(news.summary, fontSize = 11.sp, color = Color(0xFF475569), lineHeight = 15.sp)

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(

                                modifier = Modifier.fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween,

                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Text(news.date, fontSize = 10.sp, color = Color(0xFF94A3B8))

                                TextButton(

                                    onClick = { Toast.makeText(context, "Opening: ${news.headline}", Toast.LENGTH_SHORT).show() }

                                ) { Text("Read More →", fontSize = 11.sp, color = news.color, fontWeight = FontWeight.Bold) }

                            }

                        }

                    }

                }



                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// APPOINTMENT BOOKING DIALOG

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

@Composable

fun AppointmentDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    val officers = remember {

        listOf(

            Triple("Shri. R.K. Mehta", "Ward Officer · Sector A", Icons.Rounded.Badge),

            Triple("Ms. Anita Joshi", "Health Inspector · Sector B", Icons.Rounded.MedicalServices),

            Triple("Mr. Vivek Nair", "Tax Collector · Revenue Dept", Icons.Rounded.AccountBalance),

            Triple("Ms. Sonal Desai", "Building Permit Officer", Icons.Rounded.Construction)

        )

    }

    val dates = remember { listOf("Mon 30 Jun", "Tue 1 Jul", "Wed 2 Jul", "Thu 3 Jul", "Fri 4 Jul", "Mon 7 Jul") }

    val timeSlots = remember { listOf("9:00 AM", "10:00 AM", "11:00 AM", "2:00 PM", "3:00 PM", "4:00 PM") }



    var selectedOfficer by remember { mutableStateOf(0) }

    var selectedDate by remember { mutableStateOf(0) }

    var selectedTime by remember { mutableStateOf(-1) }

    var reason by remember { mutableStateOf("") }

    var confirmed by remember { mutableStateOf(false) }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.CalendarMonth, contentDescription = null, tint = Color(0xFF10B981))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Book Appointment", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                if (confirmed) {

                    Column(

                        modifier = Modifier.fillMaxWidth(),

                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(

                            modifier = Modifier

                                .size(64.dp)

                                .background(Color(0xFF10B981).copy(alpha = 0.1f), CircleShape),

                            contentAlignment = Alignment.Center

                        ) {

                            Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(40.dp))

                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Appointment Confirmed!", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color(0xFF0F172A))

                        Text("Your token: APT-28741", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = Color(0xFF10B981))

                        Spacer(modifier = Modifier.height(12.dp))

                        Card(

                            modifier = Modifier.fillMaxWidth(),

                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),

                            shape = RoundedCornerShape(12.dp)

                        ) {

                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {

                                Text("👤 ${officers[selectedOfficer].first}", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                                Text("📅 ${dates[selectedDate]}", fontSize = 12.sp, color = Color(0xFF64748B))

                                Text("⏰ ${if (selectedTime >= 0) timeSlots[selectedTime] else "N/A"}", fontSize = 12.sp, color = Color(0xFF64748B))

                                if (reason.isNotBlank()) Text("📝 $reason", fontSize = 12.sp, color = Color(0xFF64748B))

                            }

                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(

                            onClick = onDismiss,

                            modifier = Modifier.fillMaxWidth(),

                            shape = RoundedCornerShape(10.dp),

                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))

                        ) { Text("Done", fontWeight = FontWeight.Bold) }

                    }

                } else {

                    Text("Select Officer", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Color(0xFF0F172A))

                    Spacer(modifier = Modifier.height(6.dp))

                    officers.forEachIndexed { idx, (name, role, icon) ->

                        val isSelected = idx == selectedOfficer

                        Row(

                            modifier = Modifier

                                .fillMaxWidth()

                                .padding(vertical = 3.dp)

                                .clip(RoundedCornerShape(10.dp))

                                .background(if (isSelected) Color(0xFF10B981).copy(alpha = 0.1f) else Color(0xFFF8FAFC))

                                .border(BorderStroke(1.dp, if (isSelected) Color(0xFF10B981) else Color(0xFFE2E8F0)), RoundedCornerShape(10.dp))

                                .clickable { selectedOfficer = idx }

                                .padding(10.dp),

                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Icon(icon, contentDescription = null, tint = if (isSelected) Color(0xFF10B981) else Color(0xFF64748B), modifier = Modifier.size(20.dp))

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(name, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                Text(role, fontSize = 10.sp, color = Color(0xFF64748B))

                            }

                            if (isSelected) Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(18.dp))

                        }

                    }



                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Select Date", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Color(0xFF0F172A))

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                        items(dates.size) { idx ->

                            val isSelected = idx == selectedDate

                            Box(

                                modifier = Modifier

                                    .clip(RoundedCornerShape(10.dp))

                                    .background(if (isSelected) Color(0xFF10B981) else Color(0xFFF1F5F9))

                                    .clickable { selectedDate = idx }

                                    .padding(horizontal = 12.dp, vertical = 8.dp)

                            ) {

                                Text(dates[idx], fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium, color = if (isSelected) Color.White else Color(0xFF64748B))

                            }

                        }

                    }



                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Select Time Slot", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Color(0xFF0F172A))

                    Spacer(modifier = Modifier.height(6.dp))

                    androidx.compose.foundation.layout.FlowRow(

                        horizontalArrangement = Arrangement.spacedBy(6.dp),

                        verticalArrangement = Arrangement.spacedBy(6.dp)

                    ) {

                        timeSlots.forEachIndexed { idx, slot ->

                            val isSelected = idx == selectedTime

                            Box(

                                modifier = Modifier

                                    .clip(RoundedCornerShape(8.dp))

                                    .background(if (isSelected) Color(0xFF10B981) else Color(0xFFDCFCE7))

                                    .clickable { selectedTime = idx }

                                    .padding(horizontal = 12.dp, vertical = 6.dp)

                            ) {

                                Text(slot, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium, color = if (isSelected) Color.White else Color(0xFF0F172A))

                            }

                        }

                    }



                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(

                        value = reason,

                        onValueChange = { reason = it },

                        label = { Text("Reason for Visit (optional)") },

                        minLines = 2,

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(10.dp),

                        colors = OutlinedTextFieldDefaults.colors(

                            focusedBorderColor = Color(0xFF10B981),

                            unfocusedBorderColor = Color(0xFFE2E8F0)

                        )

                    )



                    Spacer(modifier = Modifier.height(12.dp))

                    Button(

                        onClick = {

                            if (selectedTime < 0) {

                                Toast.makeText(context, "Please select a time slot", Toast.LENGTH_SHORT).show()

                            } else {

                                confirmed = true

                            }

                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))

                    ) {

                        Icon(Icons.Rounded.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(6.dp))

                        Text("Confirm Appointment", fontWeight = FontWeight.Bold)

                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                        Text("Cancel", color = Color(0xFF64748B))

                    }

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// CITIZEN REWARDS LEADERBOARD DIALOG

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun LeaderboardDialog(

    viewModel: CivicViewModel,

    currentRole: String,

    onDismiss: () -> Unit

) {

    val citizenPoints by viewModel.citizenPoints.collectAsState()



    data class LeaderboardEntry(

        val rank: Int,

        val name: String,

        val ward: String,

        val points: Int,

        val isCurrentUser: Boolean = false

    )



    val leaderEntries = remember(citizenPoints) {

        listOf(

            LeaderboardEntry(1, "Meera Nair", "Ward 4, Sector A", 2850),

            LeaderboardEntry(2, "Arjun Sharma", "Ward 4, Sector B", 2400),

            LeaderboardEntry(3, "Priya Kulkarni", "Ward 4, Sector C", 2190),

            LeaderboardEntry(4, "Ravi Pillai", "Ward 4, Sector A", 1780),

            LeaderboardEntry(5, "Sunita Joshi", "Ward 4, Sector D", 1650),

            LeaderboardEntry(6, "You", "Ward 4", citizenPoints, isCurrentUser = true),

            LeaderboardEntry(7, "Kiran Deshpande", "Ward 4, Sector B", 1100),

            LeaderboardEntry(8, "Asha Singh", "Ward 4, Sector C", 890),

            LeaderboardEntry(9, "Suresh Patil", "Ward 4, Sector A", 750),

            LeaderboardEntry(10, "Deepa Mehta", "Ward 4, Sector D", 620)

        ).sortedByDescending { it.points }.mapIndexed { idx, e -> e.copy(rank = idx + 1) }

    }



    val medalColors = listOf(Color(0xFFFFD700), Color(0xFFC0C0C0), Color(0xFFCD7F32))



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Leaderboard, contentDescription = null, tint = Color(0xFF8B5CF6))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Civic Leaderboard", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(

                modifier = Modifier

                    .fillMaxWidth()

                    .verticalScroll(rememberScrollState())

            ) {

                // Podium — top 3

                Row(

                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.spacedBy(6.dp),

                    verticalAlignment = Alignment.Bottom

                ) {

                    val top3 = leaderEntries.take(3)

                    top3.forEachIndexed { idx, entry ->

                        val podiumHeight = when (idx) { 0 -> 90; 1 -> 70; else -> 55 }.dp

                        Card(

                            modifier = Modifier.weight(1f),

                            colors = CardDefaults.cardColors(containerColor = medalColors[idx].copy(alpha = 0.12f)),

                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 0.dp, bottomEnd = 0.dp)

                        ) {

                            Column(

                                modifier = Modifier

                                    .fillMaxWidth()

                                    .height(podiumHeight)

                                    .padding(6.dp),

                                horizontalAlignment = Alignment.CenterHorizontally,

                                verticalArrangement = Arrangement.Center

                            ) {

                                Text(when (idx) { 0 -> "🥇"; 1 -> "🥈"; else -> "🥉" }, fontSize = 20.sp)

                                Text(entry.name.split(" ").first(), fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF0F172A), textAlign = TextAlign.Center)

                                Text("${entry.points} XP", fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, color = medalColors[idx])

                            }

                        }

                    }

                }



                Spacer(modifier = Modifier.height(10.dp))



                leaderEntries.forEach { entry ->

                    val bgColor = when {

                        entry.isCurrentUser -> Color(0xFF8B5CF6).copy(alpha = 0.08f)

                        entry.rank <= 3 -> medalColors[entry.rank - 1].copy(alpha = 0.06f)

                        else -> Color(0xFFF8FAFC)

                    }

                    val borderColor = if (entry.isCurrentUser) Color(0xFF8B5CF6) else Color(0xFFE2E8F0)



                    Row(

                        modifier = Modifier

                            .fillMaxWidth()

                            .padding(vertical = 3.dp)

                            .clip(RoundedCornerShape(10.dp))

                            .background(bgColor)

                            .border(BorderStroke(if (entry.isCurrentUser) 2.dp else 1.dp, borderColor), RoundedCornerShape(10.dp))

                            .padding(10.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Text(

                            text = when (entry.rank) { 1 -> "🥇"; 2 -> "🥈"; 3 -> "🥉"; else -> "#${entry.rank}" },

                            fontWeight = FontWeight.ExtraBold,

                            fontSize = 14.sp,

                            color = if (entry.rank <= 3) medalColors[entry.rank - 1] else Color(0xFF64748B),

                            modifier = Modifier.width(36.dp)

                        )

                        Column(modifier = Modifier.weight(1f)) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(entry.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                if (entry.isCurrentUser) {

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Box(

                                        modifier = Modifier

                                            .clip(RoundedCornerShape(4.dp))

                                            .background(Color(0xFF8B5CF6))

                                            .padding(horizontal = 4.dp, vertical = 1.dp)

                                    ) { Text("YOU", fontSize = 8.sp, fontWeight = FontWeight.ExtraBold, color = Color.White) }

                                }

                            }

                            Text(entry.ward, fontSize = 10.sp, color = Color(0xFF94A3B8))

                        }

                        Text("${entry.points} XP", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Color(0xFF8B5CF6))

                    }

                }



                Spacer(modifier = Modifier.height(10.dp))

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF)),

                    shape = RoundedCornerShape(10.dp)

                ) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.Info, contentDescription = null, tint = Color(0xFF8B5CF6), modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Earn XP by filing complaints, voting in polls, and giving 5-star feedback!", fontSize = 11.sp, color = Color(0xFF6D28D9), lineHeight = 15.sp)

                    }

                }



                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #2 — COMMUNITY CROWDFUNDING FOR LOCAL PROJECTS

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun CrowdfundingDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    data class FundProject(val title: String, val description: String, val raised: Int, val goal: Int, val category: String)

    val projects = remember {

        listOf(

            FundProject("Sector 4 Children's Library", "Build a public reading space with 2000 books for kids aged 5-14.", 84500, 150000, "Education"),

            FundProject("Park Benches & Shade Canopy", "Install 20 benches and 5 shade sails in Block C Park.", 31200, 50000, "Environment"),

            FundProject("Community Kitchen Setup", "Support a free meal kitchen for daily wage workers & seniors.", 112000, 200000, "Welfare"),

            FundProject("Solar LED Street Lights", "Replace 15 broken sodium lamps with solar-powered LEDs.", 67800, 120000, "Infrastructure")

        )

    }

    var fundedIds by remember { mutableStateOf(setOf<String>()) }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.VolunteerActivism, contentDescription = null, tint = Color(0xFF10B981))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Community Crowdfunding", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                Text("Co-fund local projects with matching municipal grants:", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(10.dp))

                projects.forEach { proj ->

                    val progress = proj.raised.toFloat() / proj.goal.toFloat()

                    val funded = proj.title in fundedIds

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = if (funded) Color(0xFFF0FDF4) else Color(0xFFF8FAFC)),

                        shape = RoundedCornerShape(12.dp),

                        border = BorderStroke(1.dp, if (funded) Color(0xFF86EFAC) else Color(0xFFE2E8F0))

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                Text(proj.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A), modifier = Modifier.weight(1f))

                                Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(Color(0xFF10B981).copy(0.12f)).padding(horizontal = 6.dp, vertical = 2.dp)) {

                                    Text(proj.category, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF065F46))

                                }

                            }

                            Text(proj.description, fontSize = 11.sp, color = Color(0xFF64748B), modifier = Modifier.padding(top = 4.dp))

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)), color = Color(0xFF10B981), trackColor = Color(0xFFDCFCE7))

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                Text("₹${proj.raised.toInt() / 1000}K raised of ₹${proj.goal / 1000}K", fontSize = 11.sp, color = Color(0xFF64748B))

                                Text("${(progress * 100).toInt()}%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))

                            }

                            if (!funded) {

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(

                                    onClick = {

                                        fundedIds = fundedIds + proj.title

                                        Toast.makeText(context, "✅ You contributed ₹100 to ${proj.title}!", Toast.LENGTH_LONG).show()

                                    },

                                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp),

                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))

                                ) { Text("Contribute ₹100", fontSize = 12.sp, fontWeight = FontWeight.Bold) }

                            } else {

                                Spacer(modifier = Modifier.height(4.dp))

                                Text("✅ Your contribution recorded! Municipal match: +₹100", fontSize = 11.sp, color = Color(0xFF059669), fontWeight = FontWeight.Bold)

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {

                    Text("Close", color = Color(0xFF64748B))

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #7 — ELDERLY ASSISTANCE COMPANIONSHIP BOOKING

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun ElderlyAssistDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    var seniorName by remember { mutableStateOf("") }

    var serviceType by remember { mutableStateOf("Office Visit Help") }

    var preferredDate by remember { mutableStateOf("") }

    var booked by remember { mutableStateOf(false) }

    val serviceTypes = listOf("Office Visit Help", "Hospital Appointment", "Form Filling", "Bank Visit", "Ration Card Office", "Pension Office")

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Accessibility, contentDescription = null, tint = Color(0xFF0EA5E9))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Elderly Assistance Booking", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                if (booked) {

                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)), shape = RoundedCornerShape(12.dp)) {

                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                            Icon(Icons.Rounded.CheckCircle, null, tint = Color(0xFF2563EB), modifier = Modifier.size(48.dp))

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("Volunteer Booked!", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color(0xFF1E40AF))

                            Text("A trained municipal volunteer will assist $seniorName with their $serviceType on $preferredDate.\nContact: +91-98765-43210", fontSize = 12.sp, color = Color(0xFF2563EB), textAlign = TextAlign.Center)

                        }

                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close") }

                } else {

                    Text("Book a trained volunteer for senior citizen public office visits:", fontSize = 12.sp, color = Color(0xFF64748B))

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(value = seniorName, onValueChange = { seniorName = it }, label = { Text("Senior Citizen's Full Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Service Required", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                        items(serviceTypes) { type ->

                            FilterChip(selected = serviceType == type, onClick = { serviceType = type }, label = { Text(type, fontSize = 10.sp) }, colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFF0EA5E9), selectedLabelColor = Color.White))

                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(value = preferredDate, onValueChange = { preferredDate = it }, label = { Text("Preferred Date (DD/MM/YYYY)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(

                        onClick = {

                            if (seniorName.isBlank() || preferredDate.isBlank()) {

                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()

                            } else { booked = true }

                        },

                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0EA5E9))

                    ) { Text("Book Volunteer", fontWeight = FontWeight.Bold) }

                    TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Cancel", color = Color(0xFF64748B)) }

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #10 — GEO-LOCATED LOST & FOUND HUB

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun LostFoundDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    data class LostItem(val id: Int, val type: String, val description: String, val location: String, val date: String, val status: String)

    val lostItems = remember {

        listOf(

            LostItem(1, "🐕", "Golden Retriever, male, answers to 'Bruno'", "Near Sector 4 Park Gate A", "24 Jun 2026", "Active"),

            LostItem(2, "👜", "Brown leather wallet, contains Aadhaar & PAN", "Bus Stop 12 — MG Road", "25 Jun 2026", "Active"),

            LostItem(3, "🔑", "Key bunch with Honda motorcycle key fob", "Municipal Office Parking", "23 Jun 2026", "Found ✅"),

            LostItem(4, "📱", "Samsung Galaxy S22, cracked screen, blue case", "Sector 4 Market Area", "26 Jun 2026", "Active")

        )

    }

    var reportType by remember { mutableStateOf("") }

    var reportDesc by remember { mutableStateOf("") }

    var showForm by remember { mutableStateOf(false) }

    var submitted by remember { mutableStateOf(false) }



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.FindInPage, contentDescription = null, tint = Color(0xFF7C3AED))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Lost & Found Hub", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                Text("Ward 4 Community Lost & Found Board", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(10.dp))

                lostItems.forEach { item ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = if (item.status == "Found ✅") Color(0xFFF0FDF4) else Color(0xFFF5F0FF)),

                        shape = RoundedCornerShape(10.dp),

                        border = BorderStroke(1.dp, if (item.status == "Found ✅") Color(0xFF86EFAC) else Color(0xFFDDD6FE))

                    ) {

                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                            Text(item.type, fontSize = 22.sp)

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(item.description, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A), maxLines = 2, overflow = TextOverflow.Ellipsis)

                                Text("📍 ${item.location}", fontSize = 10.sp, color = Color(0xFF64748B))

                                Text("Date: ${item.date}", fontSize = 10.sp, color = Color(0xFF94A3B8))

                            }

                            Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (item.status == "Found ✅") Color(0xFFDCFCE7) else Color(0xFFEDE9FE)).padding(horizontal = 6.dp, vertical = 3.dp)) {

                                Text(item.status, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = if (item.status == "Found ✅") Color(0xFF166534) else Color(0xFF5B21B6))

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                if (!showForm) {

                    Button(onClick = { showForm = true }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))) {

                        Icon(Icons.Rounded.Add, null, modifier = Modifier.size(16.dp))

                        Spacer(modifier = Modifier.width(6.dp))

                        Text("Report Lost / Found Item", fontWeight = FontWeight.Bold)

                    }

                } else if (!submitted) {

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(value = reportType, onValueChange = { reportType = it }, label = { Text("Item Type (e.g. Dog, Phone, Bag)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(value = reportDesc, onValueChange = { reportDesc = it }, label = { Text("Description & Location Found") }, minLines = 2, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(

                        onClick = {

                            if (reportType.isBlank() || reportDesc.isBlank()) {

                                Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()

                            } else { submitted = true }

                        },

                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))

                    ) { Text("Submit Report", fontWeight = FontWeight.Bold) }

                } else {

                    Text("✅ Report submitted! Nearby citizens within 2km radius have been alerted.", fontSize = 12.sp, color = Color(0xFF059669), fontWeight = FontWeight.Bold)

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close", color = Color(0xFF64748B)) }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #15 — SMART WATER METER INSIGHTS

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun SmartWaterMeterDialog(onDismiss: () -> Unit) {

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.WaterDrop, contentDescription = null, tint = Color(0xFF0EA5E9))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Smart Water Meter", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                // Usage Summary Card

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),

                    shape = RoundedCornerShape(12.dp)

                ) {

                    Column(modifier = Modifier.padding(14.dp)) {

                        Text("Meter ID: WM-4-2024-00723", fontSize = 11.sp, color = Color(0xFF64748B))

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                Text("284 L", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color(0xFF0EA5E9))

                                Text("Today", fontSize = 11.sp, color = Color(0xFF64748B))

                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                Text("1,832 L", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color(0xFF0369A1))

                                Text("This Week", fontSize = 11.sp, color = Color(0xFF64748B))

                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                Text("7,284 L", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color(0xFF1E40AF))

                                Text("This Month", fontSize = 11.sp, color = Color(0xFF64748B))

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                Text("Daily Usage Trend (Past Week)", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                Spacer(modifier = Modifier.height(8.dp))

                val days = listOf("Mon" to 260, "Tue" to 310, "Wed" to 245, "Thu" to 290, "Fri" to 320, "Sat" to 380, "Sun" to 284)

                val maxVal = days.maxOf { it.second }

                days.forEach { (day, usage) ->

                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {

                        Text(day, fontSize = 11.sp, color = Color(0xFF475569), modifier = Modifier.width(32.dp))

                        LinearProgressIndicator(

                            progress = { usage.toFloat() / maxVal },

                            modifier = Modifier.weight(1f).height(10.dp).clip(RoundedCornerShape(5.dp)),

                            color = if (usage > 300) Color(0xFFEF4444) else Color(0xFF0EA5E9),

                            trackColor = Color(0xFFDCF0FF)

                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("$usage L", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.width(50.dp), color = if (usage > 300) Color(0xFFDC2626) else Color(0xFF0F172A))

                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)), shape = RoundedCornerShape(10.dp)) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.Warning, null, tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Weekend usage 34% above target. Conservation tips: Fix leaks, use bucket for washing.", fontSize = 11.sp, color = Color(0xFF92400E))

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)), shape = RoundedCornerShape(10.dp)) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.EmojiEvents, null, tint = Color(0xFF16A34A), modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("You saved 1,200L this month vs. Ward 4 average! Earn 25 Green XP! 🌿", fontSize = 11.sp, color = Color(0xFF166534), fontWeight = FontWeight.Bold)

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close", color = Color(0xFF64748B)) }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #16 — AI ROUTE OPTIMIZATION (WORKER)

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun WorkerRouteDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    data class RouteStop(val order: Int, val address: String, val category: String, val urgency: String, val estimatedTime: String)

    val route = remember {

        listOf(

            RouteStop(1, "Block A, Sector 4 Lane 2", "Drainage", "High", "~8 min away"),

            RouteStop(2, "Main Road Junction Gate", "Roads", "Medium", "~15 min"),

            RouteStop(3, "Near Community Garden B", "Garbage", "Low", "~22 min"),

            RouteStop(4, "Substation Road, Block D", "Electricity", "High", "~31 min"),

            RouteStop(5, "Block F Water Tank Area", "Water Supply", "Medium", "~40 min")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.Route, contentDescription = null, tint = Color(0xFF7C3AED))

                Spacer(modifier = Modifier.width(8.dp))

                Text("AI Route Optimizer", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F0FF)), shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, Color(0xFFDDD6FE))) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.AutoAwesome, null, tint = Color(0xFF7C3AED), modifier = Modifier.size(18.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("AI calculated the fastest route for your 5 assigned complaints today. Estimated total: 40 min, 12.3 km.", fontSize = 12.sp, color = Color(0xFF5B21B6), lineHeight = 16.sp)

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                route.forEach { stop ->

                    val (urgBg, urgColor) = when (stop.urgency) {

                        "High" -> Color(0xFFFEE2E2) to Color(0xFF991B1B)

                        "Medium" -> Color(0xFFFEF3C7) to Color(0xFF92400E)

                        else -> Color(0xFFDCFCE7) to Color(0xFF166534)

                    }

                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.Top) {

                        Box(modifier = Modifier.size(28.dp).background(Color(0xFF7C3AED), CircleShape), contentAlignment = Alignment.Center) {

                            Text("${stop.order}", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)

                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {

                            Text(stop.address, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))

                            Text("${stop.category} • ${stop.estimatedTime}", fontSize = 11.sp, color = Color(0xFF64748B))

                        }

                        Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(urgBg).padding(horizontal = 6.dp, vertical = 2.dp)) {

                            Text(stop.urgency, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = urgColor)

                        }

                    }

                    if (stop.order < route.size) {

                        Row(modifier = Modifier.padding(start = 14.dp)) {

                            Box(modifier = Modifier.width(1.dp).height(12.dp).background(Color(0xFFDDD6FE)))

                        }

                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(

                    onClick = { Toast.makeText(context, "🗺️ Navigation started! Follow the optimized route.", Toast.LENGTH_LONG).show() },

                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),

                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))

                ) {

                    Icon(Icons.Rounded.Navigation, null, modifier = Modifier.size(16.dp))

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("Start Optimized Navigation", fontWeight = FontWeight.Bold)

                }

                Spacer(modifier = Modifier.height(4.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close", color = Color(0xFF64748B)) }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #42 — WASTE BIN FILL-LEVEL ALERTS

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun WasteBinDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    data class WasteBin(val id: String, val location: String, val fillLevel: Int, val lastPickup: String)

    val bins = remember {

        listOf(

            WasteBin("WB-001", "Sector 4 Main Market Entrance", 92, "2 days ago"),

            WasteBin("WB-002", "Block C Park South Gate", 45, "Yesterday"),

            WasteBin("WB-003", "Community Hall Parking", 78, "3 days ago"),

            WasteBin("WB-004", "Lane 7 Bus Stop", 31, "Today"),

            WasteBin("WB-005", "Sector 4 School Entrance", 88, "2 days ago")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.DeleteSweep, contentDescription = null, tint = Color(0xFF10B981))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Smart Waste Bin Monitor", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                Text("Real-time bin fill levels via IoT sensors — Sector 4:", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(10.dp))

                bins.forEach { bin ->

                    val binColor = when {

                        bin.fillLevel >= 85 -> Color(0xFFEF4444)

                        bin.fillLevel >= 60 -> Color(0xFFF59E0B)

                        else -> Color(0xFF22C55E)

                    }

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = binColor.copy(alpha = 0.06f)),

                        shape = RoundedCornerShape(10.dp),

                        border = BorderStroke(1.dp, binColor.copy(alpha = 0.25f))

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(bin.id, fontSize = 10.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.Bold)

                                    Text(bin.location, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))

                                    Text("Last pickup: ${bin.lastPickup}", fontSize = 10.sp, color = Color(0xFF64748B))

                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                    Text("${bin.fillLevel}%", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = binColor)

                                    Text("Full", fontSize = 10.sp, color = Color(0xFF94A3B8))

                                }

                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            LinearProgressIndicator(

                                progress = { bin.fillLevel / 100f },

                                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),

                                color = binColor, trackColor = binColor.copy(0.15f)

                            )

                            if (bin.fillLevel >= 85) {

                                Spacer(modifier = Modifier.height(6.dp))

                                Button(

                                    onClick = { Toast.makeText(context, "🚛 Pickup request sent for ${bin.id}!", Toast.LENGTH_SHORT).show() },

                                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp),

                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),

                                    contentPadding = PaddingValues(8.dp)

                                ) { Text("⚠️ Request Urgent Pickup", fontSize = 11.sp, fontWeight = FontWeight.Bold) }

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)), shape = RoundedCornerShape(10.dp)) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.Info, null, tint = Color(0xFFD97706), modifier = Modifier.size(16.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("2 bins require immediate pickup. Sanitation dept. auto-notified.", fontSize = 11.sp, color = Color(0xFF92400E))

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close", color = Color(0xFF64748B)) }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #47 — GRID LOAD / POWER PEAK ALERT

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun PowerAlertDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.ElectricBolt, contentDescription = null, tint = Color(0xFFF59E0B))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Grid Load Monitor", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)), shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, Color(0xFFFDE68A))) {

                    Column(modifier = Modifier.padding(14.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.Warning, null, tint = Color(0xFFD97706), modifier = Modifier.size(22.dp))

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Peak Load Alert — Ward 4 Feeder", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color(0xFF92400E))

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Current grid load: 87% capacity\nPeak hours: 6:00 PM – 10:00 PM today\nRisk: Moderate overload possible", fontSize = 12.sp, color = Color(0xFF78350F), lineHeight = 18.sp)

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("Live Zone Status", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                Spacer(modifier = Modifier.height(8.dp))

                val zones = listOf(

                    Triple("Zone A — Block A & B", 92, Color(0xFFEF4444)),

                    Triple("Zone B — Block C & D", 74, Color(0xFFF59E0B)),

                    Triple("Zone C — Block E & F", 58, Color(0xFF22C55E)),

                    Triple("Industrial Zone", 45, Color(0xFF22C55E))

                )

                zones.forEach { (zone, load, color) ->

                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {

                        Text(zone, fontSize = 11.sp, color = Color(0xFF475569), modifier = Modifier.weight(1f))

                        LinearProgressIndicator(

                            progress = { load / 100f },

                            modifier = Modifier.width(80.dp).height(8.dp).clip(RoundedCornerShape(4.dp)),

                            color = color, trackColor = color.copy(0.15f)

                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text("$load%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color, modifier = Modifier.width(36.dp))

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("💡 Energy Conservation Tips for Tonight:", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(6.dp))

                listOf("Avoid using washing machines & geysers during 6–10 PM", "Set AC to 26°C or above during peak hours", "Switch off standby appliances & unnecessary lights", "Delay charging EVs to post 11 PM for reduced grid stress").forEach { tip ->

                    Row(modifier = Modifier.padding(vertical = 2.dp)) {

                        Text("•  $tip", fontSize = 11.sp, color = Color(0xFF475569), lineHeight = 16.sp)

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(

                    onClick = { Toast.makeText(context, "✅ You will receive a push alert 30 min before grid peak!", Toast.LENGTH_LONG).show() },

                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),

                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))

                ) {

                    Icon(Icons.Rounded.NotificationsActive, null, modifier = Modifier.size(16.dp))

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("Enable Peak Alert Notifications", fontWeight = FontWeight.Bold, color = Color(0xFF1C1917))

                }

                Spacer(modifier = Modifier.height(4.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close", color = Color(0xFF64748B)) }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #4 — GARBAGE TRUCK TRACKER

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun GarbageTruckDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    data class Truck(val id: String, val sector: String, val status: String, val eta: String, val lastStop: String)

    val trucks = remember {

        listOf(

            Truck("GVH-001", "Sector 4A — Blocks A-C", "En Route", "~12 min away", "Block B Market"),

            Truck("GVH-002", "Sector 4B — Blocks D-F", "Collecting", "Collecting now", "Block D Lane 3"),

            Truck("GVH-003", "Sector 4C — Commercial", "Returning to Depot", "Completed for today", "MG Road Market")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.LocalShipping, contentDescription = null, tint = Color(0xFF10B981))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Garbage Truck Tracker", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)), shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, Color(0xFF86EFAC))) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.Info, null, tint = Color(0xFF059669), modifier = Modifier.size(16.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Live GPS tracking of Sector 4 waste collection fleet. Place garbage bags outside before ETA.", fontSize = 11.sp, color = Color(0xFF166534), lineHeight = 15.sp)

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                trucks.forEach { truck ->

                    val (bg, border, statusColor) = when (truck.status) {

                        "En Route" -> Triple(Color(0xFFEFF6FF), Color(0xFF93C5FD), Color(0xFF2563EB))

                        "Collecting" -> Triple(Color(0xFFF0FDF4), Color(0xFF86EFAC), Color(0xFF059669))

                        else -> Triple(Color(0xFFF8FAFC), Color(0xFFE2E8F0), Color(0xFF64748B))

                    }

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = bg),

                        shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, border)

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                                Text(truck.id, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(statusColor.copy(0.12f)).padding(horizontal = 6.dp, vertical = 2.dp)) {

                                    Text(truck.status, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = statusColor)

                                }

                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("Route: ${truck.sector}", fontSize = 12.sp, color = Color(0xFF475569))

                            Text("ETA: ${truck.eta}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = statusColor)

                            Text("Last stop: ${truck.lastStop}", fontSize = 11.sp, color = Color(0xFF94A3B8))

                        }

                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(

                    onClick = { Toast.makeText(context, "🔔 You'll be notified 15 minutes before the truck arrives!", Toast.LENGTH_LONG).show() },

                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),

                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))

                ) {

                    Icon(Icons.Rounded.NotificationsActive, null, modifier = Modifier.size(16.dp))

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("Get Arrival Notification", fontWeight = FontWeight.Bold)

                }

                Spacer(modifier = Modifier.height(4.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close", color = Color(0xFF64748B)) }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #13 — HERITAGE & CULTURAL TOURISM GUIDE

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun HeritageGuideDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    data class HeritageSite(val emoji: String, val name: String, val type: String, val distance: String, val description: String)

    val sites = remember {

        listOf(

            HeritageSite("🕌", "Sector 4 Stepwell (Baoli)", "Historical Monument", "0.8 km", "18th-century stepwell restored by the municipal heritage board. Free guided tours on weekends."),

            HeritageSite("🎭", "Cultural Heritage Museum", "Museum", "1.4 km", "Collections spanning 400 years of local craft, textiles, and historical artefacts."),

            HeritageSite("🌳", "Martyrs' Memorial Park", "Garden & Memorial", "2.1 km", "Serene garden with sculptures commemorating local freedom fighters. Open 6AM–8PM."),

            HeritageSite("🎶", "Lal Bagh Open Theatre", "Cultural Venue", "2.8 km", "Open-air amphitheatre hosting folk performances every Saturday night. Entry: Free.")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.AccountBalance, contentDescription = null, tint = Color(0xFFB45309))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Heritage & Tourism Guide", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                Text("Discover heritage sites & cultural venues near Ward 4:", fontSize = 12.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(10.dp))

                sites.forEach { site ->

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),

                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),

                        shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, Color(0xFFFDE68A))

                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(site.emoji, fontSize = 22.sp)

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(site.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF0F172A))

                                    Text("${site.type} • ${site.distance}", fontSize = 10.sp, color = Color(0xFF92400E))

                                }

                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(site.description, fontSize = 11.sp, color = Color(0xFF64748B), lineHeight = 15.sp)

                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedButton(

                                onClick = { Toast.makeText(context, "📍 Opening directions to ${site.name}...", Toast.LENGTH_SHORT).show() },

                                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp),

                                border = BorderStroke(1.dp, Color(0xFFD97706)),

                                contentPadding = PaddingValues(8.dp)

                            ) {

                                Icon(Icons.Rounded.Navigation, null, tint = Color(0xFFD97706), modifier = Modifier.size(14.dp))

                                Spacer(modifier = Modifier.width(4.dp))

                                Text("Get Directions", fontSize = 11.sp, color = Color(0xFFD97706))

                            }

                        }

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close", color = Color(0xFF64748B)) }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #23 — INCIDENT REPORTING LOG (WORKER)

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun IncidentLogDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    var incidentType by remember { mutableStateOf("Aggressive Animal") }

    var incidentLocation by remember { mutableStateOf("") }

    var incidentNotes by remember { mutableStateOf("") }

    var submitted by remember { mutableStateOf(false) }

    val incidentTypes = listOf("Aggressive Animal", "Blocked Site Access", "Unsafe Structure", "Chemical Hazard", "Electrical Risk", "Physical Threat", "Equipment Failure")



    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.ReportProblem, contentDescription = null, tint = Color(0xFFEF4444))

                Spacer(modifier = Modifier.width(8.dp))

                Text("Incident Report Log", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                if (submitted) {

                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)), shape = RoundedCornerShape(12.dp)) {

                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                            Icon(Icons.Rounded.CheckCircle, null, tint = Color(0xFFEF4444), modifier = Modifier.size(40.dp))

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("Incident Logged!", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color(0xFF991B1B))

                            Text("Your supervisor and safety team have been notified. Incident reference: INC-${(10000..99999).random()}", fontSize = 12.sp, color = Color(0xFF7F1D1D), textAlign = TextAlign.Center)

                        }

                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close") }

                } else {

                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)), shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, Color(0xFFFCA5A5))) {

                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.Warning, null, tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Document unexpected site hazards to warn future workers assigned to this area.", fontSize = 11.sp, color = Color(0xFF7F1D1D), lineHeight = 15.sp)

                        }

                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Incident Type", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                        items(incidentTypes) { type ->

                            FilterChip(selected = incidentType == type, onClick = { incidentType = type }, label = { Text(type, fontSize = 10.sp) }, colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFFEF4444), selectedLabelColor = Color.White))

                        }

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(value = incidentLocation, onValueChange = { incidentLocation = it }, label = { Text("Location / Site Address") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp))

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(value = incidentNotes, onValueChange = { incidentNotes = it }, label = { Text("Description & Safety Notes") }, minLines = 3, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), placeholder = { Text("Describe the hazard so future workers are prepared...") })

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(

                        onClick = {

                            if (incidentLocation.isBlank() || incidentNotes.isBlank()) {

                                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()

                            } else { submitted = true }

                        },

                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))

                    ) { Text("Log Incident", fontWeight = FontWeight.Bold) }

                    TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Cancel", color = Color(0xFF64748B)) }

                }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #45 — DIGITAL IDENTITY (AADHAAR / DIGILOCKER)

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun DigiLockerDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    var aadhaarInput by remember { mutableStateOf("") }

    var verified by remember { mutableStateOf(false) }

    val linkedDocs = remember {

        listOf("Aadhaar Card", "PAN Card", "Driving Licence", "Voter ID", "Birth Certificate", "Property Tax Records")

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.VerifiedUser, contentDescription = null, tint = Color(0xFF0EA5E9))

                Spacer(modifier = Modifier.width(8.dp))

                Text("DigiLocker — Digital Identity", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                if (!verified) {

                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)), shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, Color(0xFF93C5FD))) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Icon(Icons.Rounded.Lock, null, tint = Color(0xFF2563EB), modifier = Modifier.size(18.dp))

                                Spacer(modifier = Modifier.width(8.dp))

                                Text("Secure Identity Verification", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1E40AF))

                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text("Link your Aadhaar for instant certificate applications & payment KYC.", fontSize = 11.sp, color = Color(0xFF2563EB), lineHeight = 15.sp)

                        }

                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(

                        value = aadhaarInput,

                        onValueChange = { if (it.length <= 12 && it.all { c -> c.isDigit() }) aadhaarInput = it },

                        label = { Text("Enter 12-digit Aadhaar Number") },

                        placeholder = { Text("XXXX XXXX XXXX") },

                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),

                        singleLine = true

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(

                        onClick = {

                            if (aadhaarInput.length != 12) {

                                Toast.makeText(context, "Enter valid 12-digit Aadhaar number", Toast.LENGTH_SHORT).show()

                            } else { verified = true }

                        },

                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0EA5E9))

                    ) { Text("Verify & Link Identity", fontWeight = FontWeight.Bold) }

                } else {

                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)), shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, Color(0xFF86EFAC))) {

                        Column(modifier = Modifier.padding(14.dp)) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Icon(Icons.Rounded.VerifiedUser, null, tint = Color(0xFF059669), modifier = Modifier.size(28.dp))

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {

                                    Text("Identity Verified ✓", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = Color(0xFF166534))

                                    Text("Aadhaar: XXXX XXXX ${aadhaarInput.takeLast(4)}", fontSize = 12.sp, color = Color(0xFF059669))

                                }

                            }

                        }

                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Documents Linked via DigiLocker:", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                    Spacer(modifier = Modifier.height(8.dp))

                    linkedDocs.forEach { doc ->

                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.CheckCircle, null, tint = Color(0xFF22C55E), modifier = Modifier.size(16.dp))

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(doc, fontSize = 12.sp, color = Color(0xFF0F172A), modifier = Modifier.weight(1f))

                            Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(Color(0xFFDCFCE7)).padding(horizontal = 6.dp, vertical = 2.dp)) {

                                Text("Linked", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF166534))

                            }

                        }

                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED)), shape = RoundedCornerShape(8.dp)) {

                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Rounded.Info, null, tint = Color(0xFFD97706), modifier = Modifier.size(14.dp))

                            Spacer(modifier = Modifier.width(6.dp))

                            Text("Certificate applications are now pre-filled using verified identity data.", fontSize = 10.sp, color = Color(0xFF92400E))

                        }

                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close", color = Color(0xFF64748B)) }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}



// =========================================================================

// FEATURE #41 — IOT STREETLIGHT AUTO-REPORTING

// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun StreetlightDialog(onDismiss: () -> Unit) {

    val context = LocalContext.current

    data class Streetlight(val id: String, val location: String, val status: String, val lastReport: String)

    val lights = remember {

        listOf(

            Streetlight("SL-004A-012", "MG Road near Gate 3", "OFFLINE — Auto-complaint filed", "Just now"),

            Streetlight("SL-004B-007", "Block B Crossroads", "ONLINE", "3 hrs ago"),

            Streetlight("SL-004C-019", "Sector 4 School Entrance", "OFFLINE — Auto-complaint filed", "2 hrs ago"),

            Streetlight("SL-004A-031", "Park Lane Walkway", "ONLINE", "1 hr ago"),

            Streetlight("SL-004D-003", "Near Community Centre", "FLICKERING — Flagged", "45 min ago")

        )

    }

    AlertDialog(

        onDismissRequest = onDismiss,

        confirmButton = {


            TextButton(


                onClick = onDismiss,


                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))


            ) {


                Text("Close", fontWeight = FontWeight.Bold)


            }


        },

        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(Icons.Rounded.LightMode, contentDescription = null, tint = Color(0xFFF59E0B))

                Spacer(modifier = Modifier.width(8.dp))

                Text("IoT Streetlight Monitor", fontWeight = FontWeight.Bold)

            }

        },

        text = {

            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)), shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, Color(0xFFFDE68A))) {

                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Icon(Icons.Rounded.AutoAwesome, null, tint = Color(0xFFD97706), modifier = Modifier.size(16.dp))

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("IoT sensors auto-file complaints when zero electricity draw is detected after 6PM. No manual reporting needed.", fontSize = 11.sp, color = Color(0xFF92400E), lineHeight = 15.sp)

                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("Sector 4 Streetlight Grid Status (${lights.count { it.status.startsWith("ONLINE") }}/${lights.size} Online)", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                Spacer(modifier = Modifier.height(8.dp))

                lights.forEach { light ->

                    val (bg, statusColor) = when {

                        light.status.startsWith("ONLINE") -> Color(0xFFF0FDF4) to Color(0xFF059669)

                        light.status.contains("FLICKERING") -> Color(0xFFFFF7ED) to Color(0xFFD97706)

                        else -> Color(0xFFFFF1F2) to Color(0xFFEF4444)

                    }

                    Card(

                        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),

                        colors = CardDefaults.cardColors(containerColor = bg),

                        shape = RoundedCornerShape(8.dp)

                    ) {

                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                            Icon(if (light.status.startsWith("ONLINE")) Icons.Rounded.Lightbulb else Icons.Rounded.LightbulbCircle, null, tint = statusColor, modifier = Modifier.size(18.dp))

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(light.id, fontSize = 10.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.Bold)

                                Text(light.location, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))

                                Text(light.status, fontSize = 11.sp, color = statusColor, fontWeight = FontWeight.SemiBold)

                            }

                            Text("${light.lastReport}", fontSize = 9.sp, color = Color(0xFF94A3B8))

                        }

                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(

                    onClick = { Toast.makeText(context, "📋 Manual complaint filed for all offline lights!", Toast.LENGTH_LONG).show() },

                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),

                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))

                ) { Text("Report All Offline Lights", fontWeight = FontWeight.Bold, color = Color(0xFF1C1917)) }

                Spacer(modifier = Modifier.height(4.dp))

                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Close", color = Color(0xFF64748B)) }

            }

        },

        shape = RoundedCornerShape(20.dp)

    )

}

// =========================================================================
// SMART CITY 2.0 FEATURE HUB & INTERACTIVE PREVIEW SANDBOX (50 FEATURES)
// =========================================================================
data class HubFeature(
    val id: Int,
    val title: String,
    val desc: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val hasCustomDialog: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartCityFeatureHubDialog(
    viewModel: CivicViewModel,
    currentRole: String,
    sharedSuggestions: List<SuggestionItem>,
    onUpdateSuggestions: (List<SuggestionItem>) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var selectedCategoryTab by remember { mutableStateOf("Citizen") }
    var activeSubDialogId by remember { mutableStateOf<Int?>(null) }
    var activeSubDialogTitle by remember { mutableStateOf("") }
    var activeSubDialogDesc by remember { mutableStateOf("") }

    val categories = listOf("Citizen", "Worker", "Admin", "Smart IoT")

    val features = remember {
        listOf(
            // --- CITIZEN PORTAL (1-15) ---
            HubFeature(1, "GIS Neighborhood Map", "Zoomable map overlays showing construction, water cuts, or sanitation schedules.", Icons.Rounded.Map, true),
            HubFeature(2, "Community Crowdfunding", "Suggest sector upgrades and co-fund them with matching municipal grants.", Icons.Rounded.VolunteerActivism, true),
            HubFeature(3, "One-Tap Emergency SOS", "Trigger location-tagged distress signals to nearby response vehicles.", Icons.Rounded.Warning, false),
            HubFeature(4, "Garbage Truck Tracker", "Real-time map and ETA tracker of municipal waste collection fleet.", Icons.Rounded.LocalShipping, true),
            HubFeature(5, "Smart Ward-Level Polls", "Direct feedback channel for municipal budget allocations & local playground upgrades.", Icons.Rounded.FactCheck, false),
            HubFeature(6, "Tax & Bill Payment Center", "Ledger and instant payments for water, property tax, and utilities.", Icons.Rounded.Payment, true),
            HubFeature(7, "Elderly Assist Volunteers", "Book companion volunteers to assist seniors with forms or office visits.", Icons.Rounded.Accessibility, true),
            HubFeature(8, "Digital Parking Finder", "Sensors mapping and pre-booking available street parking slots.", Icons.Rounded.Map, false),
            HubFeature(9, "Municipal Asset Booking", "Rent community halls and parks with real-time slot selection.", Icons.Rounded.EventAvailable, true),
            HubFeature(10, "Lost & Found Board", "Report lost pets or objects with geofenced radius alerts to neighbors.", Icons.Rounded.FindInPage, true),
            HubFeature(11, "Local Job Postings", "Platform for vocational workshops, micro-jobs, and local employment.", Icons.Rounded.Work, true),
            HubFeature(12, "Health Camps Updates", "Vaccination drives, health camps, and epidemic notices by ward pincode.", Icons.Rounded.LocalHospital, true),
            HubFeature(13, "Heritage & Cultural Guides", "Guides to restored historical monuments, parks, and weekly cultural events.", Icons.Rounded.AccountBalance, true),
            HubFeature(14, "Air & Noise reports", "Live ward widget tracking AQI levels and sound pollution statistics.", Icons.Rounded.EnergySavingsLeaf, true),
            HubFeature(15, "Smart Water Consumption", "Insights dashboard displaying water meter telemetry and conservation tips.", Icons.Rounded.WaterDrop, true),

            // --- WORKER PORTAL (16-30) ---
            HubFeature(16, "AI Route Optimization", "Auto-calculates the fastest route for daily audits and audits.", Icons.Rounded.Route, true),
            HubFeature(17, "Offline Resolution Reporting", "Allows workers to log photos and progress coordinates without internet connection.", Icons.Rounded.Build, false),
            HubFeature(18, "Asset Request Depot", "Request tools, replacement streetlights, or PPE directly from inventory.", Icons.Rounded.Build, false),
            HubFeature(19, "Salary & Payout milestones", "Breakdown of wages, resolution rewards, and citizen gratuity tips.", Icons.Rounded.Payments, false),
            HubFeature(20, "Peer Help Forum", "Request assistance from nearby colleagues for high-complexity jobs.", Icons.Rounded.Forum, false),
            HubFeature(21, "Leaderboard Badges Store", "Redeem leaderboard points for vouchers, insurance, or gear upgrades.", Icons.Rounded.Leaderboard, false),
            HubFeature(22, "Safety Audit checklist", "Required pre-task checklist before performing hazardous work.", Icons.Rounded.FactCheck, false),
            HubFeature(23, "Incident Reporting Log", "Record unexpected site hazards to warn future assigned workers.", Icons.Rounded.ReportProblem, true),
            HubFeature(24, "Voice Progress Notes", "Log status updates on the job using built-in speech-to-text dictation.", Icons.Rounded.Description, false),
            HubFeature(25, "Work Auth QR ID", "Digital authorization badge verifiable by citizens to verify identity.", Icons.Rounded.Badge, false),
            HubFeature(26, "Shift Exchange Hub", "Request shift swap operations with team members online.", Icons.Rounded.SwapHoriz, false),
            HubFeature(27, "Supervisor Audio Hotline", "Express audio channel for workers needing administrative clarification.", Icons.Rounded.Phone, false),
            HubFeature(28, "Hazard Heatmaps", "Visual alerts showing zones marked with high-risk warning flags.", Icons.Rounded.Warning, false),
            HubFeature(29, "First Aid Video Library", "Quick-access interactive video course for emergency training guidelines.", Icons.Rounded.Article, false),
            HubFeature(30, "Vehicle Fuel Log", "Log odometer readings and fuel receipts matching vehicle barcodes.", Icons.Rounded.Build, false),

            // --- ADMIN PORTAL (31-40) ---
            HubFeature(31, "Heatmap Dashboard", "Analytics showing complaint clusters to plan preventative maintenance.", Icons.Rounded.ShowChart, false),
            HubFeature(32, "Smart Allocation Engine", "AI algorithm matching tasks to closest, highest-rated worker.", Icons.Rounded.Lightbulb, false),
            HubFeature(33, "Certificate QR Scanner", "Built-in scanner for verifying agency stamps on official certificates.", Icons.Rounded.Badge, false),
            HubFeature(34, "SLA Escalation Matrix", "Auto-flags unattended complaints to senior officials if SLA is breached.", Icons.Rounded.CalendarToday, false),
            HubFeature(35, "Multi-Channel Notices", "Broadcast announcements via SMS, WhatsApp, and push widgets in bulk.", Icons.Rounded.Article, false),
            HubFeature(36, "Vendor Contract Analytics", "Track SLAs, performance metrics, and contracts of private contractors.", Icons.Rounded.SupervisedUserCircle, false),
            HubFeature(37, "Tender Bid Appraisals", "View, filter, and score vendor bids for infrastructure projects.", Icons.Rounded.Description, false),
            HubFeature(38, "Sentiment Analysis Engine", "Runs NLP on complaint text to isolate municipal friction points.", Icons.Rounded.Forum, false),
            HubFeature(39, "Budget Tracking Ledger", "Real-time ledger matching allocations to expenditures on smart works.", Icons.Rounded.Payments, true),
            HubFeature(40, "Staff Shift Calendars", "Calendar dispatch board to coordinate field team assignments.", Icons.Rounded.CalendarToday, false),

            // --- SMART IOT GRID (41-50) ---
            HubFeature(41, "IoT Streetlight Alerts", "Automated alert filed when zero electricity draw is reported after 6PM.", Icons.Rounded.Lightbulb, true),
            HubFeature(42, "Waste Bin Level Alert", "Smart sensors triggering pickup notifications when bins are full.", Icons.Rounded.DeleteSweep, true),
            HubFeature(43, "Seismic Health Monitor", "Sensors on public bridges sending alarms to prevent structural failures.", Icons.Rounded.Warning, false),
            HubFeature(44, "Flood & Waterlog Warnings", "Water level sensors in drains warning citizens of high logs.", Icons.Rounded.WaterDrop, false),
            HubFeature(45, "DigiLocker Verification", "Instant digital credential checking for quick payouts and permissions.", Icons.Rounded.VerifiedUser, true),
            HubFeature(46, "Sector Carbon Index", "Telemetry board charting aggregates of local green metrics.", Icons.Rounded.EnergySavingsLeaf, false),
            HubFeature(47, "Grid Power Peak Alerts", "Prompts citizens to reduce consumption during high-load peaks.", Icons.Rounded.ElectricBolt, true),
            HubFeature(48, "AI Adaptive Signal Timers", "Smart traffic signals adapting green timings based on camera feeds.", Icons.Rounded.DirectionsBus, false),
            HubFeature(49, "Noise Violation Network", "Decibel meters triggering logs on residential decibel breaches.", Icons.Rounded.Phone, false),
            HubFeature(50, "Public Wi-Fi Locator", "Displays signal coverage maps and connectivity speeds nearby.", Icons.Rounded.Settings, false)
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {

            TextButton(

                onClick = onDismiss,

                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))

            ) {

                Text("Close", fontWeight = FontWeight.Bold)

            }

        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Widgets, contentDescription = null, tint = Color(0xFF007AFF), modifier = Modifier.size(26.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Smart City 2.0 Hub", fontWeight = FontWeight.Black)
                    Text("Interactive Sandbox • 50 Features", fontSize = 11.sp, color = Color(0xFF64748B))
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth().height(480.dp)) {
                // Category Tabs Selector
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    categories.forEach { cat ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selectedCategoryTab == cat) Color(0xFF007AFF) else Color(0xFFF1F5F9))
                                .clickable { selectedCategoryTab = cat }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cat,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedCategoryTab == cat) Color.White else Color(0xFF475569)
                            )
                        }
                    }
                }

                // Features List
                val filtered = features.filter {
                    when (selectedCategoryTab) {
                        "Citizen" -> it.id in 1..15
                        "Worker" -> it.id in 16..30
                        "Admin" -> it.id in 31..40
                        else -> it.id in 41..50
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filtered) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .background(Color(0xFF007AFF).copy(alpha = 0.1f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(item.icon, null, tint = Color(0xFF007AFF), modifier = Modifier.size(16.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${item.id}. ${item.title}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFF0F172A)
                                    )
                                    Text(
                                        text = item.desc,
                                        fontSize = 10.sp,
                                        color = Color(0xFF64748B),
                                        lineHeight = 13.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Button(
                                    onClick = {
                                        activeSubDialogId = item.id
                                        activeSubDialogTitle = item.title
                                        activeSubDialogDesc = item.desc
                                    },
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (item.hasCustomDialog) Color(0xFF10B981) else Color(0xFF007AFF)
                                    ),
                                    modifier = Modifier.height(28.dp)
                                ) {
                                    Text(
                                        text = if (item.hasCustomDialog) "Launch" else "Test",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                    Text("Close Sandbox Hub", color = Color(0xFF64748B))
                }
            }
        },
        shape = RoundedCornerShape(20.dp)
    )

    // Render Sub-Dialogs / Simulations dynamically
    activeSubDialogId?.let { subId ->
        when (subId) {
            1 -> GisMapDialog(currentRole = currentRole, onDismiss = { activeSubDialogId = null })
            2 -> CrowdfundingDialog(onDismiss = { activeSubDialogId = null })
            4 -> GarbageTruckDialog(onDismiss = { activeSubDialogId = null })
            6 -> PaymentsDialog(onDismiss = { activeSubDialogId = null })
            7 -> ElderlyAssistDialog(onDismiss = { activeSubDialogId = null })
            9 -> BookingsDialog(onDismiss = { activeSubDialogId = null })
            10 -> LostFoundDialog(onDismiss = { activeSubDialogId = null })
            11 -> JobsDialog(onDismiss = { activeSubDialogId = null })
            12 -> HealthServicesDialog(onDismiss = { activeSubDialogId = null })
            13 -> HeritageGuideDialog(onDismiss = { activeSubDialogId = null })
            14 -> EnvironmentReportDialog(onDismiss = { activeSubDialogId = null })
            15 -> SmartWaterMeterDialog(onDismiss = { activeSubDialogId = null })
            16 -> WorkerRouteDialog(onDismiss = { activeSubDialogId = null })
            23 -> IncidentLogDialog(onDismiss = { activeSubDialogId = null })
            39 -> BudgetDialog(currentRole = currentRole, onDismiss = { activeSubDialogId = null })
            41 -> StreetlightDialog(onDismiss = { activeSubDialogId = null })
            42 -> WasteBinDialog(onDismiss = { activeSubDialogId = null })
            45 -> DigiLockerDialog(onDismiss = { activeSubDialogId = null })
            47 -> PowerAlertDialog(onDismiss = { activeSubDialogId = null })
            else -> FeatureSimulationDialog(
                id = subId,
                title = activeSubDialogTitle,
                description = activeSubDialogDesc,
                onDismiss = { activeSubDialogId = null }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureSimulationDialog(
    id: Int,
    title: String,
    description: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val rand = remember(id) { (1000..9999).random() }

    // State bindings depending on the feature being simulated
    var sliderValue by remember { mutableStateOf(50f) }
    var checkedState by remember { mutableStateOf(false) }
    var checkedState2 by remember { mutableStateOf(false) }
    var textInputState by remember { mutableStateOf("") }
    var triggerActionDone by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {

            TextButton(

                onClick = onDismiss,

                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF007AFF))

            ) {

                Text("Close", fontWeight = FontWeight.Bold)

            }

        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.AutoAwesome, contentDescription = null, tint = Color(0xFF007AFF))
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)), shape = RoundedCornerShape(10.dp)) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Info, null, tint = Color(0xFF2563EB), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(description, fontSize = 11.sp, color = Color(0xFF1E40AF), lineHeight = 15.sp)
                    }
                }

                // Render customized interactive controllers for each simulated feature ID
                when (id) {
                    3 -> { // Emergency Broadcast SOS
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text("Broadcasting coordinates to dispatch...", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            CircularProgressIndicator(color = Color(0xFFEF4444))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Units dispatched: Ward 4 Ambulance Group 2", fontSize = 11.sp, color = Color(0xFFEF4444))
                        }
                    }
                    5 -> { // Polls
                        Text("Should Ward 4 allocate 15% budget for solar panels?", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Button(
                                onClick = { triggerActionDone = true; Toast.makeText(context, "Vote cast: Yes!", Toast.LENGTH_SHORT).show() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) { Text("Vote Yes") }
                            Button(
                                onClick = { triggerActionDone = true; Toast.makeText(context, "Vote cast: No!", Toast.LENGTH_SHORT).show() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) { Text("Vote No") }
                        }
                        if (triggerActionDone) {
                            Text("Current Results: Yes (82%) | No (18%)", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color(0xFF10B981))
                        }
                    }
                    8 -> { // Parking Finder
                        Text("Sector 4 Commercial - Available Slots (Green)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        val grid = listOf(true, false, true, true, false, true)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            grid.forEachIndexed { idx, available ->
                                val color = if (available) Color(0xFF10B981) else Color(0xFFEF4444)
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(color.copy(0.12f))
                                        .border(1.dp, color, RoundedCornerShape(6.dp))
                                        .clickable {
                                            if (available) Toast.makeText(context, "Slot P-${idx+1} pre-booked!", Toast.LENGTH_SHORT).show()
                                            else Toast.makeText(context, "Slot occupied", Toast.LENGTH_SHORT).show()
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("P${idx+1}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
                                }
                            }
                        }
                    }
                    17 -> { // Offline Reporting
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Simulate Offline Mode", fontSize = 12.sp, modifier = Modifier.weight(1f))
                            Switch(checked = checkedState, onCheckedChange = { checkedState = it })
                        }
                        if (checkedState) {
                            Text("Network Offline. Submitting reports will queue them local database.", fontSize = 11.sp, color = Color(0xFFEA580C))
                            Button(onClick = { Toast.makeText(context, "Report added to Sync Queue!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                                Text("Log Grievance Offline")
                            }
                        }
                    }
                    18 -> { // Asset depot
                        Text("Depot Tool Catalog", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        val tools = listOf("Led bulb 100W", "Heavy gloves", "Barricade cone")
                        tools.forEach { tool ->
                            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(tool, fontSize = 11.sp, modifier = Modifier.weight(1f))
                                Button(onClick = { Toast.makeText(context, "Requested: $tool", Toast.LENGTH_SHORT).show() }, contentPadding = PaddingValues(horizontal = 6.dp), modifier = Modifier.height(24.dp)) {
                                    Text("Request", fontSize = 9.sp)
                                }
                            }
                        }
                    }
                    19 -> { // Payout milestones
                        Text("Monthly Ledger Breakdown", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Base Wages: Rs. 18,500\n• Completed resolutions: Rs. 4,500\n• Gratuity tips: Rs. 320\n• Deductions: None", fontSize = 11.sp, color = Color(0xFF475569))
                        Button(onClick = { Toast.makeText(context, "Bank transfer request initiated!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Request Payout Transfer")
                        }
                    }
                    20 -> { // Peer Network
                        Text("Active Peer Help Requests (2 km)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Block B Sewer: Spare filter needed (Amit)\n• MG Road: Rusted post help (Suresh)", fontSize = 11.sp, color = Color(0xFF475569))
                        Button(onClick = { Toast.makeText(context, "Notified Amit that you are on the way!", Toast.LENGTH_LONG).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Offer Buddy Help")
                        }
                    }
                    21 -> { // Worker Rewards store
                        Text("Leaderboard points: 1,450 pts", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        val rewards = listOf("Rs.200 Gift Card (500 pts)", "Safety boots premium (1000 pts)")
                        rewards.forEach { reward ->
                            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(reward, fontSize = 11.sp, modifier = Modifier.weight(1f))
                                Button(onClick = { Toast.makeText(context, "Redeemed $reward!", Toast.LENGTH_SHORT).show() }, contentPadding = PaddingValues(horizontal = 6.dp), modifier = Modifier.height(24.dp)) {
                                    Text("Redeem", fontSize = 9.sp)
                                }
                            }
                        }
                    }
                    22 -> { // Safety checklist
                        Text("Audit items required for shift:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = checkedState, onCheckedChange = { checkedState = it })
                            Text("PPE Helmet & Vest checked", fontSize = 11.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = checkedState2, onCheckedChange = { checkedState2 = it })
                            Text("Gas sensors calibrated", fontSize = 11.sp)
                        }
                        Button(
                            onClick = { Toast.makeText(context, "Safety certificate logged!", Toast.LENGTH_SHORT).show() },
                            enabled = checkedState && checkedState2,
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Unlock shift routing") }
                    }
                    24 -> { // Voice Progress Notes
                        Text("Speech to text note log", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        OutlinedTextField(value = textInputState, onValueChange = { textInputState = it }, label = { Text("Spoken progress notes") }, modifier = Modifier.fillMaxWidth())
                        Button(onClick = { textInputState = "Pothole filled with gravel binder. Curing complete."; Toast.makeText(context, "Voice memo converted!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("🎤 Simulate Dictation")
                        }
                    }
                    25 -> { // Auth ID
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text("VERIFIED MUNICIPAL WORKER", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF10B981))
                            Text("Name: Ramesh Kumar\nID: EMP-94012\nDept: Sanitation & Waste", fontSize = 11.sp, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.height(6.dp))
                            Icon(Icons.Rounded.Badge, null, modifier = Modifier.size(72.dp), tint = Color(0xFF007AFF))
                            Text("Scan QR on site to verify credentials", fontSize = 9.sp, color = Color(0xFF64748B))
                        }
                    }
                    26 -> { // Shift Swap
                        Text("Shift Trade Requests", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• June 28 Morning -> requestor: Ramesh", fontSize = 11.sp)
                        Button(onClick = { Toast.makeText(context, "Trade proposal submitted!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Accept shift exchange")
                        }
                    }
                    27 -> { // Hotline
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text("Audio bridge with supervisor", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(onClick = { triggerActionDone = true }, colors = ButtonDefaults.buttonColors(containerColor = if (triggerActionDone) Color(0xFFEF4444) else Color(0xFF10B981))) {
                                Text(if (triggerActionDone) "☎ Hang Up" else "📞 Dial supervisor")
                            }
                            if (triggerActionDone) {
                                Text("Call Active: 00:04", color = Color(0xFF10B981), fontSize = 11.sp)
                            }
                        }
                    }
                    28 -> { // Hazard heatmap
                        Text("Sector 4 Danger Areas", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• High voltage transformer line MG road (Restricted)\n• Active excavation block F crossroads", fontSize = 11.sp, color = Color(0xFFEF4444))
                        Button(onClick = { Toast.makeText(context, "Danger zones loaded to navigation!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Load alerts to map")
                        }
                    }
                    29 -> { // Training library
                        Text("Interactive Courses", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Sewage LOTO guidelines (Completed ✓)\n• Electric post grounding rules (Pending)", fontSize = 11.sp)
                        Button(onClick = { Toast.makeText(context, "Opening training player...", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Play training video")
                        }
                    }
                    30 -> { // Fuel logs
                        OutlinedTextField(value = textInputState, onValueChange = { textInputState = it }, label = { Text("Odometer reading & liters") }, placeholder = { Text("e.g. Odo: 120512, Liters: 42") }, modifier = Modifier.fillMaxWidth())
                        Button(onClick = { Toast.makeText(context, "Fuel log saved!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Submit fuel details")
                        }
                    }
                    31 -> { // Heatmap Admin
                        Text("Complaint concentration analysis", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Garbage: 45 reports (Sector A)\n• Water leaks: 12 reports (Sector B)\n• Road potholes: 26 reports (Sector C)", fontSize = 11.sp)
                        Button(onClick = { Toast.makeText(context, "Prevention checklist dispatched to teams!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Deploy preventive teams")
                        }
                    }
                    32 -> { // Auto-allocation
                        Text("AI Task Dispatch Settings", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Enable Auto Dispatch", fontSize = 12.sp, modifier = Modifier.weight(1f))
                            Switch(checked = checkedState, onCheckedChange = { checkedState = it })
                        }
                        Text("Dispatch weight (Distance)", fontSize = 11.sp)
                        Slider(value = sliderValue, onValueChange = { sliderValue = it }, valueRange = 0f..100f)
                        Text("Value: ${sliderValue.toInt()}%", fontSize = 10.sp)
                    }
                    33 -> { // Scanner
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text("QR Code scanner interface", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(onClick = { triggerActionDone = true }) { Text("Simulate certificate scan") }
                            if (triggerActionDone) {
                                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFDCFCE7)), modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                                    Text("✓ VERIFIED: Character Cert #CC-${rand}\nApplicant: Priya Sen", modifier = Modifier.padding(10.dp), fontSize = 11.sp, color = Color(0xFF166534), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    34 -> { // Escalation
                        Text("SLA breaching alerts", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFFEF4444))
                        Text("• Water leak at Block C: outstanding 32 hrs\n• Pothole MG road: outstanding 28 hrs", fontSize = 11.sp)
                        Button(onClick = { Toast.makeText(context, "SLA alarms dispatched to supervisors!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Trigger escalation flags")
                        }
                    }
                    35 -> { // Bulk broadcast
                        OutlinedTextField(value = textInputState, onValueChange = { textInputState = it }, label = { Text("Broadcaster announcement text") }, placeholder = { Text("Water cut tomorrow from 8AM to 1PM") }, modifier = Modifier.fillMaxWidth())
                        Button(onClick = { Toast.makeText(context, "Announcements sent to all devices!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Broadcast via SMS & App")
                        }
                    }
                    36 -> { // Vendor Analytics
                        Text("Vendor Performance ledger", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Larsen & Toubro: SLA 94.2% compliance\n• National Builders: SLA 71.5% (Warning!)", fontSize = 11.sp)
                        Button(onClick = { Toast.makeText(context, "SLA notices dispatched to contractors!", Toast.LENGTH_LONG).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Issue performance SLA warning")
                        }
                    }
                    37 -> { // Tender Appraisals
                        Text("Tender portal active bids", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Apex builders: Rs. 4.2 Cr proposal\n• GMR Infra: Rs. 4.8 Cr proposal", fontSize = 11.sp)
                        Button(onClick = { Toast.makeText(context, "Score: 8.5 submitted for GMR!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Appraise GMR proposal")
                        }
                    }
                    38 -> { // Sentiment NLP
                        OutlinedTextField(value = textInputState, onValueChange = { textInputState = it }, label = { Text("Type citizen query text") }, placeholder = { Text("No garbage collected since last week very upset") }, modifier = Modifier.fillMaxWidth())
                        Button(onClick = { triggerActionDone = true }, modifier = Modifier.fillMaxWidth()) { Text("Analyze Sentiment") }
                        if (triggerActionDone) {
                            Text("Analysis: Dissatisfied (86%) - Category: Sanitation", fontWeight = FontWeight.Bold, color = Color(0xFFEF4444), fontSize = 12.sp)
                        }
                    }
                    40 -> { // Dispatch calendars
                        Text("Calendar schedule dispatch", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Amit: Mon-Wed (Morning sewage crew)\n• Ramesh: Mon-Fri (General Roads repair)", fontSize = 11.sp)
                        Button(onClick = { Toast.makeText(context, "Schedules synchronized!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Re-align calendar roster")
                        }
                    }
                    43 -> { // Seismic Structure
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text("Sector 4 Flyover Seismic sensor", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("Vibration frequency: 12.4 Hz (Stable)", fontSize = 11.sp, color = Color(0xFF10B981))
                            Spacer(modifier = Modifier.height(10.dp))
                            CircularProgressIndicator(progress = { 0.12f }, color = Color(0xFF10B981))
                        }
                    }
                    44 -> { // Flood alerts
                        Text("Water Drainage Levels", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Underpass B sensor: 1.2m (Warning level!)\n• Ring road drain sensor: 0.4m (Stable)", fontSize = 11.sp, color = Color(0xFFEA580C))
                        Button(onClick = { Toast.makeText(context, "Traffic rerouting dispatched!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Evacuation warnings dispatch")
                        }
                    }
                    46 -> { // Carbon index
                        Text("Local Aggregated Carbon footprint", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Ward aggregate saving: 45.2 Tons CO2/yr\n• Net target saving: 50.0 Tons CO2/yr\n• Current status: On track to Net Zero 2027", fontSize = 11.sp, color = Color(0xFF10B981))
                    }
                    48 -> { // AI traffic adapt
                        Text("AI Traffic cameras active", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Lane A count: 42 vehicles/min\n• Lane B count: 12 vehicles/min", fontSize = 11.sp)
                        Button(onClick = { Toast.makeText(context, "Green light timer adapted: Lane A extended +12s!", Toast.LENGTH_LONG).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Adapt timers automatically")
                        }
                    }
                    49 -> { // Noise violation
                        Text("Ward decibel alarms", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Commercial block C: 82 dB (Overnight limit breach!)\n• School zone block D: 45 dB (Normal)", fontSize = 11.sp, color = Color(0xFFEF4444))
                        Button(onClick = { Toast.makeText(context, "Auto warning alert dispatched to commercial zone!", Toast.LENGTH_LONG).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Dispatch noise inspector warning")
                        }
                    }
                    50 -> { // Public Wifi
                        Text("Local Ward Wi-Fi hotspots", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("• Block C market: 120m coverage range - 45 Mbps\n• Main park gate: 80m coverage range - 12 Mbps", fontSize = 11.sp)
                        Button(onClick = { Toast.makeText(context, "Connected to Ward 4 Secure Wi-Fi!", Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                            Text("Connect to hotspot")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                    Text("Close", color = Color(0xFF64748B))
                }
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}
