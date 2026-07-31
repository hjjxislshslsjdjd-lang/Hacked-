package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val id: Int = 1,
    val xp: Int = 0,
    val currentRankTitle: String = "Cyber Cadet",
    val completedModuleIds: String = "", // Comma-separated
    val completedQuizIds: String = "",
    val completedCtfIds: String = "",
    val unlockedBadgeIds: String = "",
    val streakDays: Int = 1,
    val lastActiveTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ctf_solutions")
data class CtfSolutionEntity(
    @PrimaryKey val ctfId: String,
    val isSolved: Boolean = true,
    val submittedFlag: String,
    val solvedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_scores")
data class QuizScoreEntity(
    @PrimaryKey val quizId: String,
    val scorePercentage: Int,
    val completedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sender: String, // "USER" or "AI_SENTINEL"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)
