package com.example.ui.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LevelEnum
import com.example.ui.screens.AiTutorScreen
import com.example.ui.screens.BadgesScreen
import com.example.ui.screens.CtfSandboxScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LessonDetailScreen
import com.example.ui.screens.LessonsScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDarkBackground
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.TerminalCardBg
import com.example.ui.theme.TerminalHeaderBg
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.AcademyViewModel

enum class NavDestination(val route: String, val label: String, val icon: ImageVector) {
    HOME("home", "Dashboard", Icons.Default.Home),
    LEARN("learn", "Learn", Icons.Default.School),
    CTF("ctf_sandbox", "CTF Labs", Icons.Default.Flag),
    AI_TUTOR("ai_tutor", "AI Tutor", Icons.Default.Psychology),
    ACHIEVEMENTS("achievements", "Badges", Icons.Default.EmojiEvents)
}

@Composable
fun AcademyAppMain(viewModel: AcademyViewModel) {
    var currentTab by remember { mutableStateOf(NavDestination.HOME) }
    var activeModuleId by remember { mutableStateOf<String?>(null) }
    var activeQuizLevel by remember { mutableStateOf<LevelEnum?>(null) }
    var selectedCtfId by remember { mutableStateOf<String?>(null) }

    val userProgress by viewModel.userProgress.collectAsState()
    val filteredModules by viewModel.filteredModules.collectAsState()
    val ctfChallenges by viewModel.ctfChallenges.collectAsState()
    val ctfSolutions by viewModel.ctfSolutions.collectAsState()
    val chatMessages by viewModel.chatMessages.collectAsState()
    val isAiThinking by viewModel.isAiThinking.collectAsState()
    val selectedLevelFilter by viewModel.selectedLevelFilter.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val currentRank = viewModel.calculateRank(userProgress.xp)
    val badges = viewModel.getBadges(userProgress.unlockedBadgeIds)

    val showBottomBar = activeModuleId == null && activeQuizLevel == null

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = TerminalHeaderBg,
                    contentColor = TextPrimary,
                    tonalElevation = 8.dp,
                    modifier = Modifier.border(
                        androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
                    )
                ) {
                    NavDestination.values().forEach { destination ->
                        val isSelected = currentTab == destination
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                currentTab = destination
                                selectedCtfId = null
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.label,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = destination.label,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = NeonCyan,
                                selectedTextColor = NeonCyan,
                                indicatorColor = NeonCyan.copy(alpha = 0.2f),
                                unselectedIconColor = TextSecondary,
                                unselectedTextColor = TextSecondary
                            ),
                            modifier = Modifier.testTag("nav_item_${destination.route}")
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                // Active Quiz Screen Overlay
                activeQuizLevel != null -> {
                    val quiz = viewModel.getQuizByLevel(activeQuizLevel!!)
                    if (quiz != null) {
                        QuizScreen(
                            quiz = quiz,
                            onBackClick = { activeQuizLevel = null },
                            onSubmitQuizScore = { scorePct, total ->
                                viewModel.submitQuizScore(quiz.id, scorePct, total) { _ -> }
                            }
                        )
                    } else {
                        activeQuizLevel = null
                    }
                }

                // Active Lesson Detail Overlay
                activeModuleId != null -> {
                    val module = viewModel.getModuleById(activeModuleId!!)
                    if (module != null) {
                        val isCompleted = userProgress.completedModuleIds
                            .split(",")
                            .contains(module.id)

                        LessonDetailScreen(
                            module = module,
                            isCompleted = isCompleted,
                            onBackClick = { activeModuleId = null },
                            onMarkCompleted = {
                                viewModel.completeModule(module.id)
                            },
                            onStartQuiz = { level ->
                                activeModuleId = null
                                activeQuizLevel = level
                            }
                        )
                    } else {
                        activeModuleId = null
                    }
                }

                // Bottom Tab Screens
                else -> {
                    Crossfade(targetState = currentTab, label = "TabSwitch") { tab ->
                        when (tab) {
                            NavDestination.HOME -> {
                                HomeScreen(
                                    progress = userProgress,
                                    currentRank = currentRank,
                                    featuredModules = filteredModules,
                                    featuredCtfs = ctfChallenges,
                                    onSelectModule = { id -> activeModuleId = id },
                                    onSelectCtf = { id ->
                                        selectedCtfId = id
                                        currentTab = NavDestination.CTF
                                    },
                                    onNavigateToLearn = { currentTab = NavDestination.LEARN },
                                    onNavigateToCtf = { currentTab = NavDestination.CTF },
                                    onNavigateToAiTutor = { currentTab = NavDestination.AI_TUTOR },
                                    onNavigateToBadges = { currentTab = NavDestination.ACHIEVEMENTS }
                                )
                            }
                            NavDestination.LEARN -> {
                                LessonsScreen(
                                    modules = filteredModules,
                                    progress = userProgress,
                                    selectedLevel = selectedLevelFilter,
                                    searchQuery = searchQuery,
                                    onLevelSelected = { lvl -> viewModel.setLevelFilter(lvl) },
                                    onSearchQueryChanged = { q -> viewModel.setSearchQuery(q) },
                                    onSelectModule = { id -> activeModuleId = id },
                                    onLaunchQuiz = { lvl -> activeQuizLevel = lvl }
                                )
                            }
                            NavDestination.CTF -> {
                                CtfSandboxScreen(
                                    challenges = ctfChallenges,
                                    solvedSolutions = ctfSolutions,
                                    selectedCtfId = selectedCtfId,
                                    onSelectCtf = { id -> selectedCtfId = id },
                                    onSubmitFlag = { ctfId, flagInput, onResult ->
                                        viewModel.submitCtfFlag(ctfId, flagInput, onResult)
                                    }
                                )
                            }
                            NavDestination.AI_TUTOR -> {
                                AiTutorScreen(
                                    chatMessages = chatMessages,
                                    isAiThinking = isAiThinking,
                                    onSendMessage = { text -> viewModel.sendAiMessage(text) },
                                    onClearChat = { viewModel.clearChat() }
                                )
                            }
                            NavDestination.ACHIEVEMENTS -> {
                                BadgesScreen(
                                    badges = badges,
                                    progress = userProgress,
                                    currentRank = currentRank
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
