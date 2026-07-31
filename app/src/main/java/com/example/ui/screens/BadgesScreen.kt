package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.Badge
import com.example.data.model.UserRank
import com.example.data.repository.AcademyContent
import com.example.ui.components.RankHeaderCard
import com.example.ui.components.TerminalCard
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.TerminalCardBg
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun BadgesScreen(
    badges: List<Badge>,
    progress: UserProgressEntity,
    currentRank: UserRank,
    modifier: Modifier = Modifier
) {
    val completedModules = progress.completedModuleIds.split(",").filter { it.isNotBlank() }.size
    val completedCtfs = progress.completedCtfIds.split(",").filter { it.isNotBlank() }.size
    val completedQuizzes = progress.completedQuizIds.split(",").filter { it.isNotBlank() }.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }

        // User Rank Header
        item {
            RankHeaderCard(
                userRank = currentRank,
                xp = progress.xp,
                streakDays = progress.streakDays
            )
        }

        // Stats Overview Grid
        item {
            Text(
                text = "STATS_TELEMETRY // ACCOMPLISHMENTS",
                style = MaterialTheme.typography.labelSmall,
                color = NeonCyan,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatBox(
                    numberText = "$completedModules / 18",
                    labelText = "Lessons Completed",
                    accentColor = NeonGreen,
                    modifier = Modifier.weight(1f)
                )
                StatBox(
                    numberText = "$completedCtfs / 6",
                    labelText = "CTFs Solved",
                    accentColor = NeonAmber,
                    modifier = Modifier.weight(1f)
                )
                StatBox(
                    numberText = "$completedQuizzes / 3",
                    labelText = "Quizzes Mastered",
                    accentColor = NeonCyan,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Badges Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "BADGES // ACHIEVEMENTS",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonAmber,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "${badges.count { it.isUnlocked }}/${badges.size} UNLOCKED",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonGreen,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        // Badges List
        items(badges) { badge ->
            BadgeRowCard(badge = badge)
        }

        // Ranking Hierarchy Guide
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "RANK_HIERARCHY // ADVANCEMENT PATH",
                style = MaterialTheme.typography.labelSmall,
                color = NeonCyan,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(8.dp))
            TerminalCard(
                headerTitle = "RANKING_SYSTEM // XP THRESHOLDS",
                borderColor = NeonCyan
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AcademyContent.RANKS.forEach { rank ->
                        val isCurrent = rank.title == currentRank.title
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isCurrent) NeonCyan.copy(alpha = 0.15f) else Color.Transparent,
                                    RoundedCornerShape(6.dp)
                                )
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(rank.badgeSymbol, fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = rank.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = if (isCurrent) NeonCyan else TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = rank.levelName,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                            Text(
                                text = "${rank.minXp} - ${rank.maxXp} XP",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isCurrent) NeonGreen else TextSecondary,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun StatBox(
    numberText: String,
    labelText: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
        color = TerminalCardBg
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = numberText,
                style = MaterialTheme.typography.titleLarge,
                color = accentColor,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = labelText,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
fun BadgeRowCard(badge: Badge) {
    TerminalCard(
        headerTitle = "BADGE // ID #${badge.id.uppercase()}",
        borderColor = if (badge.isUnlocked) NeonAmber else CyberBorder
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = if (badge.isUnlocked) NeonAmber.copy(alpha = 0.2f) else CyberBorder,
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (badge.isUnlocked) NeonAmber else Color.Transparent
                )
            ) {
                Box(
                    modifier = Modifier.size(44.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (badge.isUnlocked) {
                        Text(text = badge.iconSymbol, fontSize = 22.sp)
                    } else {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = badge.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (badge.isUnlocked) TextPrimary else TextSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = badge.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Requirement: ${badge.unlockedCondition}",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (badge.isUnlocked) NeonGreen else NeonCyan,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp
                )
            }
        }
    }
}
