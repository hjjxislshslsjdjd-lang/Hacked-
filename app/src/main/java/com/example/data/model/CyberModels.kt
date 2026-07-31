package com.example.data.model

enum class LevelEnum(val displayName: String, val levelNumber: Int) {
    BEGINNER("Beginner", 1),
    INTERMEDIATE("Intermediate", 2),
    ADVANCED("Advanced", 3)
}

data class Module(
    val id: String,
    val level: LevelEnum,
    val title: String,
    val description: String,
    val readTimeMinutes: Int,
    val drawableResName: String,
    val topics: List<String>,
    val contentMarkdown: String,
    val keyTakeaways: List<String>,
    val codeSnippet: String? = null,
    val xpReward: Int = 50
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class Quiz(
    val id: String,
    val level: LevelEnum,
    val title: String,
    val description: String,
    val questions: List<QuizQuestion>,
    val xpReward: Int = 100
)

data class CtfChallenge(
    val id: String,
    val level: LevelEnum,
    val title: String,
    val category: String,
    val difficulty: String,
    val description: String,
    val scenarioText: String,
    val simulatedLogsOrHeaders: String? = null,
    val hints: List<String>,
    val flag: String,
    val xpReward: Int = 200
)

data class Badge(
    val id: String,
    val title: String,
    val description: String,
    val iconSymbol: String,
    val unlockedCondition: String,
    val isUnlocked: Boolean = false
)

data class UserRank(
    val title: String,
    val levelName: String,
    val minXp: Int,
    val maxXp: Int,
    val badgeSymbol: String
)

enum class ChatSender {
    USER, AI_SENTINEL
}

data class ChatMessage(
    val id: Long = System.currentTimeMillis(),
    val sender: ChatSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val codeSnippet: String? = null
)
