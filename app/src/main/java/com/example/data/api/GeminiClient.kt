package com.example.data.api

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private const val SYSTEM_PROMPT = """You are Cyber Sentinel AI, an expert cybersecurity tutor and ethical hacking mentor for Cyber Hack Academy.
Your role is to teach cybersecurity concepts, ethical hacking principles, network security, web vulnerability mitigation, password security, and CTF problem-solving in a safe, educational, legal simulation environment.
Keep responses educational, concise, and structured with bullet points or code snippets when helpful.
Strictly emphasize defensive security, ethical guidelines, and legal compliance."""

    suspend fun askTutor(prompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig::class.java.getField("GEMINI_API_KEY").get(null) as? String ?: ""
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getOfflineResponse(prompt)
        }

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
            
            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().put("text", SYSTEM_PROMPT))
                    })
                })
            }

            val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseText = response.body?.string() ?: ""

            if (!response.isSuccessful || responseText.isBlank()) {
                return@withContext getOfflineResponse(prompt)
            }

            val responseJson = JSONObject(responseText)
            val candidates = responseJson.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val content = firstCandidate.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    val text = parts.getJSONObject(0).optString("text", "")
                    if (text.isNotBlank()) return@withContext text
                }
            }
            return@withContext getOfflineResponse(prompt)
        } catch (e: Exception) {
            return@withContext getOfflineResponse(prompt)
        }
    }

    private fun getOfflineResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            "white hat" in lower || "black hat" in lower || "types of hacker" in lower -> {
                "🛡️ **Hackers Classification**:\n\n" +
                "• **White Hat (Ethical Hacker)**: Authorized security professionals who test systems with permission to find vulnerabilities and patch them.\n" +
                "• **Black Hat (Malicious Hacker)**: Unauthorized attackers who break into networks for financial gain, data theft, or sabotage.\n" +
                "• **Grey Hat**: Operates without full permission but generally without malicious intent, reporting flaws for publicity or bounties."
            }
            "sqli" in lower || "sql injection" in lower -> {
                "💉 **SQL Injection (SQLi) & Defense**:\n\n" +
                "SQL Injection happens when untrusted user input is directly concatenated into SQL queries.\n\n" +
                "**Vulnerable Query**:\n`SELECT * FROM users WHERE user = '\" + userInput + \"'`\n\n" +
                "**Defensive Best Practice**:\nAlways use **Parameterized Queries** or Prepared Statements:\n" +
                "`PreparedStatement stmt = conn.prepareStatement(\"SELECT * FROM users WHERE user = ?\");`\n" +
                "`stmt.setString(1, userInput);`"
            }
            "phishing" in lower -> {
                "🎣 **Phishing Defense Tips**:\n\n" +
                "1. **Check Sender Domain**: Look closely at subdomains (e.g. `support@paypa1-update.com` vs `paypal.com`).\n" +
                "2. **Verify Urgency**: Attackers create artificial panic (\"Account Suspended in 2 Hours\").\n" +
                "3. **Inspect Links**: Hover over buttons before clicking to view the destination URL.\n" +
                "4. **MFA Protection**: Always enforce Multi-Factor Authentication!"
            }
            "ctf" in lower || "flag" in lower || "hint" in lower -> {
                "🚩 **CTF Hints & Strategy**:\n\n" +
                "• **Base64 Challenge**: Strings ending with `=` or `==` are often Base64 encoded. Try `echo '<string>' | base64 -d` in terminal.\n" +
                "• **HTTP Headers**: Use developer tools or `curl -I <url>` to inspect custom `X-CTF-*` headers.\n" +
                "• **Port Scan**: Common service ports: HTTP (80/8080), HTTPS (443), SSH (22), FTP (21)."
            }
            "zero trust" in lower -> {
                "🔒 **Zero Trust Architecture**:\n\n" +
                "The core principle of Zero Trust is: **'Never Trust, Always Verify'**.\n" +
                "• Continuous authentication and authorization\n" +
                "• Least privilege access control\n" +
                "• Micro-segmentation of internal network boundaries"
            }
            else -> {
                "🤖 **Cyber Sentinel AI Assistant**:\n\n" +
                "I am ready to assist with your ethical hacking and cybersecurity queries!\n\n" +
                "**Topics I can help with**:\n" +
                "• Penetration testing methodologies & OWASP Top 10\n" +
                "• Network security, firewalls, and port scanning\n" +
                "• Strong password hashing & cryptographic basics\n" +
                "• CTF challenge hints and flag verification"
            }
        }
    }
}
