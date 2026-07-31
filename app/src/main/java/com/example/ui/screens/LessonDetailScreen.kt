package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.LevelEnum
import com.example.data.model.Module
import com.example.ui.components.CyberButton
import com.example.ui.components.LevelChip
import com.example.ui.components.TerminalCard
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.TerminalCardBg
import com.example.ui.theme.TerminalGreenText
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LessonDetailScreen(
    module: Module,
    isCompleted: Boolean,
    onBackClick: () -> Unit,
    onMarkCompleted: () -> Unit,
    onStartQuiz: (LevelEnum) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageRes = when (module.id) {
        "beg_1", "int_1", "adv_6" -> R.drawable.img_cyber_hero_1785521647320
        "beg_3", "int_5", "adv_3" -> R.drawable.img_digital_lock_1785521661440
        "beg_4", "int_4", "adv_5" -> R.drawable.img_network_diagram_1785521676981
        else -> R.drawable.img_code_terminal_1785521692944
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.testTag("btn_back_lesson")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = NeonCyan
                )
            }
            Text(
                text = "LESSON // ${module.level.displayName.uppercase()}",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.weight(1f)
            )
            LevelChip(level = module.level)
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Hero Illustration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, CyberBorder, RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = module.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title & Meta
            Text(
                text = module.title,
                style = MaterialTheme.typography.displayLarge,
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Estimated read time: ${module.readTimeMinutes} minutes • Ethical Hacking Curriculum",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Topics Flow
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                module.topics.forEach { topic ->
                    Surface(
                        color = TerminalCardBg,
                        shape = RoundedCornerShape(4.dp),
                        border = androidx.compose.foundation.BorderStroke(0.8.dp, CyberBorder)
                    ) {
                        Text(
                            text = "# $topic",
                            style = MaterialTheme.typography.labelSmall,
                            color = NeonCyan,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lesson Markdown Content Card
            TerminalCard(
                headerTitle = "LESSON_TEXT // READ_MODE",
                borderColor = NeonCyan
            ) {
                Text(
                    text = module.contentMarkdown,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Key Takeaways
            TerminalCard(
                headerTitle = "KEY_TAKEAWAYS // SUMMARY",
                borderColor = NeonGreen
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    module.keyTakeaways.forEach { takeaway ->
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = NeonGreen,
                                modifier = Modifier
                                    .size(18.dp)
                                    .padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = takeaway,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Actions
            if (isCompleted) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        tint = NeonGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "LESSON COMPLETED (+${module.xpReward} XP EARNED)",
                        style = MaterialTheme.typography.labelLarge,
                        color = NeonGreen,
                        fontFamily = FontFamily.Monospace
                    )
                }
            } else {
                CyberButton(
                    text = "MARK LESSON COMPLETED (+${module.xpReward} XP)",
                    onClick = onMarkCompleted,
                    accentColor = NeonGreen,
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "btn_complete_lesson"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            CyberButton(
                text = "TAKE LEVEL QUIZ (+100 XP)",
                onClick = { onStartQuiz(module.level) },
                accentColor = NeonAmber,
                modifier = Modifier.fillMaxWidth(),
                testTag = "btn_take_quiz_from_lesson"
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
