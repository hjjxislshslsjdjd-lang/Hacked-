package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserProgressEntity
import com.example.data.model.LevelEnum
import com.example.data.model.Module
import com.example.ui.components.CyberButton
import com.example.ui.components.TerminalCard
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDarkBackground
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.NeonPink
import com.example.ui.theme.TerminalCardBg
import com.example.ui.theme.TerminalHeaderBg
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun LessonsScreen(
    modules: List<Module>,
    progress: UserProgressEntity,
    selectedLevel: LevelEnum?,
    searchQuery: String,
    onLevelSelected: (LevelEnum?) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onSelectModule: (String) -> Unit,
    onLaunchQuiz: (LevelEnum) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedSet = progress.completedModuleIds.split(",").filter { it.isNotBlank() }.toSet()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            placeholder = {
                Text(
                    text = "SEARCH_TOPICS // e.g., Phishing, SQLi, Passwords...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = NeonCyan
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_search_modules"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = TerminalCardBg,
                unfocusedContainerColor = TerminalCardBg,
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = CyberBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Level Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChipItem(
                    title = "ALL LEVELS",
                    isSelected = selectedLevel == null,
                    onClick = { onLevelSelected(null) },
                    accentColor = NeonCyan,
                    testTag = "filter_all"
                )
            }
            item {
                FilterChipItem(
                    title = "BEGINNER",
                    isSelected = selectedLevel == LevelEnum.BEGINNER,
                    onClick = { onLevelSelected(LevelEnum.BEGINNER) },
                    accentColor = NeonGreen,
                    testTag = "filter_beginner"
                )
            }
            item {
                FilterChipItem(
                    title = "INTERMEDIATE",
                    isSelected = selectedLevel == LevelEnum.INTERMEDIATE,
                    onClick = { onLevelSelected(LevelEnum.INTERMEDIATE) },
                    accentColor = NeonCyan,
                    testTag = "filter_intermediate"
                )
            }
            item {
                FilterChipItem(
                    title = "ADVANCED",
                    isSelected = selectedLevel == LevelEnum.ADVANCED,
                    onClick = { onLevelSelected(LevelEnum.ADVANCED) },
                    accentColor = NeonPink,
                    testTag = "filter_advanced"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quiz Prompt Banner when a level is selected
        if (selectedLevel != null) {
            TerminalCard(
                headerTitle = "LEVEL_ASSESSMENT // ${selectedLevel.displayName.uppercase()} QUIZ",
                borderColor = NeonAmber,
                onClick = { onLaunchQuiz(selectedLevel) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = null,
                            tint = NeonAmber,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Take ${selectedLevel.displayName} Quiz",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Test comprehension & earn +100 XP",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }
                    CyberButton(
                        text = "START",
                        onClick = { onLaunchQuiz(selectedLevel) },
                        accentColor = NeonAmber,
                        testTag = "btn_start_quiz_${selectedLevel.name.lowercase()}"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Modules List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(modules) { module ->
                val isCompleted = completedSet.contains(module.id)
                ModuleItemRow(
                    module = module,
                    isCompleted = isCompleted,
                    onClick = { onSelectModule(module.id) }
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun FilterChipItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    accentColor: Color,
    testTag: String
) {
    Surface(
        modifier = Modifier
            .testTag(testTag)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        color = if (isSelected) accentColor.copy(alpha = 0.2f) else TerminalCardBg,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) accentColor else CyberBorder
        )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) accentColor else TextSecondary,
            fontFamily = FontFamily.Monospace,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
