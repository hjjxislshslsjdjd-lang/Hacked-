package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.UserProgressEntity
import com.example.data.model.CtfChallenge
import com.example.data.model.LevelEnum
import com.example.data.model.Module
import com.example.data.model.UserRank
import com.example.ui.components.LevelChip
import com.example.ui.components.RankHeaderCard
import com.example.ui.components.TerminalCard
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.NeonPink
import com.example.ui.theme.TerminalCardBg
import com.example.ui.theme.TerminalGreenText
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    progress: UserProgressEntity,
    currentRank: UserRank,
    featuredModules: List<Module>,
    featuredCtfs: List<CtfChallenge>,
    onSelectModule: (String) -> Unit,
    onSelectCtf: (String) -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToCtf: () -> Unit,
    onNavigateToAiTutor: () -> Unit,
    onNavigateToBadges: () -> Unit,
    modifier: Modifier = Modifier
) {
    val completedModuleSet = progress.completedModuleIds.split(",").filter { it.isNotBlank() }.toSet()
    val completedCtfSet = progress.completedCtfIds.split(",").filter { it.isNotBlank() }.toSet()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }

        // Top Rank Card
        item {
            RankHeaderCard(
                userRank = currentRank,
                xp = progress.xp,
                streakDays = progress.streakDays
            )
        }

        // Section Shortcuts Grid
        item {
            Text(
                text = "SYSTEM_MODULES // SECTIONS",
                style = MaterialTheme.typography.labelSmall,
                color = NeonCyan,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ShortcutCard(
                    title = "1. Beginner",
                    subtitle = "Foundations",
                    icon = Icons.Default.School,
                    accentColor = NeonGreen,
                    modifier = Modifier.weight(1f),
                    testTag = "shortcut_beginner",
                    onClick = onNavigateToLearn
                )
                ShortcutCard(
                    title = "2. Intermediate",
                    subtitle = "Defense & Web",
                    icon = Icons.Default.Shield,
                    accentColor = NeonCyan,
                    modifier = Modifier.weight(1f),
                    testTag = "shortcut_intermediate",
                    onClick = onNavigateToLearn
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ShortcutCard(
                    title = "3. Advanced",
                    subtitle = "Ethical Hacking",
                    icon = Icons.Default.Code,
                    accentColor = NeonPink,
                    modifier = Modifier.weight(1f),
                    testTag = "shortcut_advanced",
                    onClick = onNavigateToLearn
                )
                ShortcutCard(
                    title = "CTF Labs",
                    subtitle = "Legal Sandbox",
                    icon = Icons.Default.Flag,
                    accentColor = NeonAmber,
                    modifier = Modifier.weight(1f),
                    testTag = "shortcut_ctf",
                    onClick = onNavigateToCtf
                )
            }
        }

        // AI Tutor Feature Banner
        item {
            TerminalCard(
                headerTitle = "AI_ASSISTANT // CYBER SENTINEL",
                borderColor = NeonCyan,
                onClick = onNavigateToAiTutor
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            color = NeonCyan.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Ask Cyber Sentinel AI",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Instant answers, attack defense tips & CTF hints",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Ask AI",
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Beginner to Advanced Learning Path Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CURRICULUM // CORE MODULES",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonCyan,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "VIEW ALL",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonGreen,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier
                        .testTag("btn_view_all_modules")
                        .clickable { onNavigateToLearn() }
                )
            }
        }

        // Featured Modules
        items(featuredModules.take(4)) { module ->
            val isCompleted = completedModuleSet.contains(module.id)
            ModuleItemRow(
                module = module,
                isCompleted = isCompleted,
                onClick = { onSelectModule(module.id) }
            )
        }

        // CTF Challenge Labs Header
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LEGAL_SANDBOX // CTF LABS",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonAmber,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "ALL LABS",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonAmber,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier
                        .testTag("btn_view_all_ctfs")
                        .clickable { onNavigateToCtf() }
                )
            }
        }

        // Featured CTFs
        items(featuredCtfs.take(2)) { ctf ->
            val isSolved = completedCtfSet.contains(ctf.id)
            CtfCardRow(
                ctf = ctf,
                isSolved = isSolved,
                onClick = { onSelectCtf(ctf.id) }
            )
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun ShortcutCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String
) {
    Surface(
        modifier = modifier
            .testTag(testTag)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .clickable { onClick() },
        color = TerminalCardBg
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun ModuleItemRow(
    module: Module,
    isCompleted: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TerminalCard(
        modifier = modifier.testTag("module_item_${module.id}"),
        headerTitle = "MODULE // ${module.level.displayName.uppercase()}",
        borderColor = if (isCompleted) NeonGreen else CyberBorder,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LevelChip(level = module.level)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${module.readTimeMinutes} min read",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = module.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = module.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = NeonGreen,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Surface(
                    color = NeonCyan.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan)
                ) {
                    Text(
                        text = "+${module.xpReward} XP",
                        style = MaterialTheme.typography.labelSmall,
                        color = NeonCyan,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CtfCardRow(
    ctf: CtfChallenge,
    isSolved: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TerminalCard(
        modifier = modifier.testTag("ctf_item_${ctf.id}"),
        headerTitle = "CTF_LAB // ${ctf.category.uppercase()}",
        borderColor = if (isSolved) NeonGreen else NeonAmber,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = NeonAmber.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = ctf.difficulty.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = NeonAmber,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = ctf.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = ctf.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ctf.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            if (isSolved) {
                Text(
                    text = "SOLVED",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonGreen,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Surface(
                    color = NeonAmber.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NeonAmber)
                ) {
                    Text(
                        text = "+${ctf.xpReward} XP",
                        style = MaterialTheme.typography.labelSmall,
                        color = NeonAmber,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
