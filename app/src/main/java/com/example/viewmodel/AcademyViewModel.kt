package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.CtfSolutionEntity
import com.example.data.local.UserProgressEntity
import com.example.data.model.Badge
import com.example.data.model.ChatMessage
import com.example.data.model.CtfChallenge
import com.example.data.model.LevelEnum
import com.example.data.model.Module
import com.example.data.model.Quiz
import com.example.data.model.UserRank
import com.example.data.repository.AcademyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AcademyViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = AcademyRepository(db.userProgressDao())

    val userProgress: StateFlow<UserProgressEntity> = repository.userProgress.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserProgressEntity()
    )

    val selectedLevelFilter = MutableStateFlow<LevelEnum?>(null)
    val searchQuery = MutableStateFlow("")

    val filteredModules: StateFlow<List<Module>> = combine(selectedLevelFilter, searchQuery) { level, query ->
        var list = repository.getModules(level)
        if (query.isNotBlank()) {
            list = list.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true) ||
                it.topics.any { topic -> topic.contains(query, ignoreCase = true) }
            }
        }
        list
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = repository.getModules()
    )

    val ctfChallenges: StateFlow<List<CtfChallenge>> = combine(selectedLevelFilter, searchQuery) { level, query ->
        var list = repository.getCtfChallenges(level)
        if (query.isNotBlank()) {
            list = list.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
            }
        }
        list
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = repository.getCtfChallenges()
    )

    val ctfSolutions: StateFlow<List<CtfSolutionEntity>> = repository.ctfSolutions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val chatMessages: StateFlow<List<ChatMessage>> = repository.chatMessages.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val isAiThinking = MutableStateFlow(false)

    fun setLevelFilter(level: LevelEnum?) {
        selectedLevelFilter.value = level
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun completeModule(moduleId: String) {
        viewModelScope.launch {
            repository.completeModule(moduleId)
        }
    }

    fun submitQuizScore(quizId: String, scorePercentage: Int, totalQuestions: Int, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFirstTime = repository.saveQuizResult(quizId, scorePercentage, totalQuestions)
            onComplete(isFirstTime)
        }
    }

    fun submitCtfFlag(ctfId: String, flagInput: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isCorrect = repository.submitCtfFlag(ctfId, flagInput)
            onResult(isCorrect)
        }
    }

    fun sendAiMessage(userText: String) {
        if (userText.isBlank()) return
        viewModelScope.launch {
            isAiThinking.value = true
            try {
                repository.sendChatMessage(userText)
            } finally {
                isAiThinking.value = false
            }
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            repository.clearChat()
        }
    }

    fun getModuleById(id: String): Module? = repository.getModuleById(id)
    fun getQuizByLevel(level: LevelEnum): Quiz? = repository.getQuizByLevel(level)
    fun getCtfById(id: String): CtfChallenge? = repository.getCtfById(id)
    fun getBadges(unlockedIdsString: String): List<Badge> = repository.getBadges(unlockedIdsString)
    fun calculateRank(xp: Int): UserRank = repository.calculateRank(xp)
}
