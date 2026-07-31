package com.example.data.repository

import com.example.data.api.GeminiClient
import com.example.data.local.ChatMessageEntity
import com.example.data.local.CtfSolutionEntity
import com.example.data.local.QuizScoreEntity
import com.example.data.local.UserProgressDao
import com.example.data.local.UserProgressEntity
import com.example.data.model.Badge
import com.example.data.model.ChatMessage
import com.example.data.model.ChatSender
import com.example.data.model.CtfChallenge
import com.example.data.model.LevelEnum
import com.example.data.model.Module
import com.example.data.model.Quiz
import com.example.data.model.UserRank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AcademyRepository(private val dao: UserProgressDao) {

    val userProgress: Flow<UserProgressEntity> = dao.getUserProgress().map { entity ->
        entity ?: UserProgressEntity(id = 1, xp = 0, currentRankTitle = "Cyber Cadet")
    }

    val ctfSolutions: Flow<List<CtfSolutionEntity>> = dao.getAllCtfSolutions()
    val quizScores: Flow<List<QuizScoreEntity>> = dao.getAllQuizScores()

    val chatMessages: Flow<List<ChatMessage>> = dao.getAllChatMessages().map { list ->
        list.map { entity ->
            ChatMessage(
                id = entity.id,
                sender = if (entity.sender == "USER") ChatSender.USER else ChatSender.AI_SENTINEL,
                text = entity.text,
                timestamp = entity.timestamp
            )
        }
    }

    fun getModules(levelFilter: LevelEnum? = null): List<Module> {
        return if (levelFilter == null) {
            AcademyContent.MODULES
        } else {
            AcademyContent.MODULES.filter { it.level == levelFilter }
        }
    }

    fun getModuleById(id: String): Module? {
        return AcademyContent.MODULES.find { it.id == id }
    }

    fun getQuizByLevel(level: LevelEnum): Quiz? {
        return AcademyContent.QUIZZES.find { it.level == level }
    }

    fun getCtfChallenges(levelFilter: LevelEnum? = null): List<CtfChallenge> {
        return if (levelFilter == null) {
            AcademyContent.CTF_CHALLENGES
        } else {
            AcademyContent.CTF_CHALLENGES.filter { it.level == levelFilter }
        }
    }

    fun getCtfById(id: String): CtfChallenge? {
        return AcademyContent.CTF_CHALLENGES.find { it.id == id }
    }

    fun getBadges(unlockedIdsString: String): List<Badge> {
        val unlockedSet = unlockedIdsString.split(",").toSet()
        return AcademyContent.BADGES.map { badge ->
            badge.copy(isUnlocked = unlockedSet.contains(badge.id))
        }
    }

    fun calculateRank(xp: Int): UserRank {
        val ranks = AcademyContent.RANKS
        return ranks.find { xp in it.minXp..it.maxXp } ?: ranks.last()
    }

    suspend fun completeModule(moduleId: String) {
        val current = dao.getUserProgressOnce() ?: UserProgressEntity()
        val completedSet = current.completedModuleIds.split(",").filter { it.isNotBlank() }.toMutableSet()
        
        if (!completedSet.contains(moduleId)) {
            completedSet.add(moduleId)
            val module = getModuleById(moduleId)
            val gainedXp = module?.xpReward ?: 50
            val newXp = current.xp + gainedXp
            val newRank = calculateRank(newXp).title

            val updatedBadges = updateBadgeList(
                current.unlockedBadgeIds,
                completedModuleCount = completedSet.size,
                completedCtfCount = current.completedCtfIds.split(",").filter { it.isNotBlank() }.size,
                perfectQuizCount = current.completedQuizIds.split(",").filter { it.isNotBlank() }.size
            )

            dao.saveUserProgress(
                current.copy(
                    xp = newXp,
                    currentRankTitle = newRank,
                    completedModuleIds = completedSet.joinToString(","),
                    unlockedBadgeIds = updatedBadges
                )
            )
        }
    }

    suspend fun saveQuizResult(quizId: String, scorePercentage: Int, totalQuestions: Int): Boolean {
        dao.saveQuizScore(QuizScoreEntity(quizId = quizId, scorePercentage = scorePercentage))

        val current = dao.getUserProgressOnce() ?: UserProgressEntity()
        val quizSet = current.completedQuizIds.split(",").filter { it.isNotBlank() }.toMutableSet()
        val isFirstTime = !quizSet.contains(quizId)
        
        if (isFirstTime) {
            quizSet.add(quizId)
            val bonusXp = if (scorePercentage >= 100) 150 else if (scorePercentage >= 75) 100 else 50
            val newXp = current.xp + bonusXp
            val newRank = calculateRank(newXp).title

            val perfectQuizCount = if (scorePercentage >= 100) 1 else 0
            val updatedBadges = updateBadgeList(
                current.unlockedBadgeIds,
                completedModuleCount = current.completedModuleIds.split(",").filter { it.isNotBlank() }.size,
                completedCtfCount = current.completedCtfIds.split(",").filter { it.isNotBlank() }.size,
                perfectQuizCount = perfectQuizCount
            )

            dao.saveUserProgress(
                current.copy(
                    xp = newXp,
                    currentRankTitle = newRank,
                    completedQuizIds = quizSet.joinToString(","),
                    unlockedBadgeIds = updatedBadges
                )
            )
        }
        return isFirstTime
    }

    suspend fun submitCtfFlag(ctfId: String, flagInput: String): Boolean {
        val challenge = getCtfById(ctfId) ?: return false
        val isCorrect = flagInput.trim().equals(challenge.flag.trim(), ignoreCase = true)

        if (isCorrect) {
            dao.saveCtfSolution(
                CtfSolutionEntity(ctfId = ctfId, isSolved = true, submittedFlag = flagInput.trim())
            )

            val current = dao.getUserProgressOnce() ?: UserProgressEntity()
            val ctfSet = current.completedCtfIds.split(",").filter { it.isNotBlank() }.toMutableSet()
            if (!ctfSet.contains(ctfId)) {
                ctfSet.add(ctfId)
                val newXp = current.xp + challenge.xpReward
                val newRank = calculateRank(newXp).title

                val updatedBadges = updateBadgeList(
                    current.unlockedBadgeIds,
                    completedModuleCount = current.completedModuleIds.split(",").filter { it.isNotBlank() }.size,
                    completedCtfCount = ctfSet.size,
                    perfectQuizCount = 0
                )

                dao.saveUserProgress(
                    current.copy(
                        xp = newXp,
                        currentRankTitle = newRank,
                        completedCtfIds = ctfSet.joinToString(","),
                        unlockedBadgeIds = updatedBadges
                    )
                )
            }
        }
        return isCorrect
    }

    private fun updateBadgeList(
        existingBadgeIdsString: String,
        completedModuleCount: Int,
        completedCtfCount: Int,
        perfectQuizCount: Int
    ): String {
        val badgeSet = existingBadgeIdsString.split(",").filter { it.isNotBlank() }.toMutableSet()

        if (completedModuleCount >= 1) badgeSet.add("b_first_step")
        if (completedModuleCount >= 5) badgeSet.add("b_beg_master")
        if (completedModuleCount >= 18) badgeSet.add("b_adv_master")
        if (completedCtfCount >= 1) badgeSet.add("b_ctf_solver")
        if (completedCtfCount >= 3) badgeSet.add("b_crypto")
        if (perfectQuizCount >= 1) badgeSet.add("b_quiz_100")

        return badgeSet.joinToString(",")
    }

    suspend fun sendChatMessage(userText: String): String {
        dao.insertChatMessage(ChatMessageEntity(sender = "USER", text = userText))
        val reply = GeminiClient.askTutor(userText)
        dao.insertChatMessage(ChatMessageEntity(sender = "AI_SENTINEL", text = reply))
        return reply
    }

    suspend fun clearChat() {
        dao.clearChatHistory()
    }
}
