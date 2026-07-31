package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Quiz
import com.example.ui.components.CyberButton
import com.example.ui.components.CyberProgressBar
import com.example.ui.components.TerminalCard
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberRedAlert
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.TerminalCardBg
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun QuizScreen(
    quiz: Quiz,
    onBackClick: () -> Unit,
    onSubmitQuizScore: (scorePercentage: Int, totalQuestions: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var userAnswers by remember { mutableStateOf(mutableMapOf<Int, Int>()) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    var isQuizCompleted by remember { mutableStateOf(false) }

    val currentQuestion = quiz.questions.getOrNull(currentQuestionIndex)
    val totalQuestions = quiz.questions.size

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
                modifier = Modifier.testTag("btn_back_quiz")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = NeonCyan
                )
            }
            Text(
                text = "QUIZ // ${quiz.level.displayName.uppercase()}",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.weight(1f)
            )
            Surface(
                color = NeonAmber.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonAmber)
            ) {
                Text(
                    text = "+${quiz.xpReward} XP",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonAmber,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        if (!isQuizCompleted && currentQuestion != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "QUESTION ${currentQuestionIndex + 1} OF $totalQuestions",
                        style = MaterialTheme.typography.labelSmall,
                        color = NeonCyan,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "${((currentQuestionIndex + 1).toFloat() / totalQuestions * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                CyberProgressBar(
                    progress = (currentQuestionIndex + 1).toFloat() / totalQuestions.toFloat(),
                    barColor = NeonCyan
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Question Card
                TerminalCard(
                    headerTitle = "QUESTION_CARD // ID #${currentQuestion.id}",
                    borderColor = NeonCyan
                ) {
                    Text(
                        text = currentQuestion.question,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 24.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Options List
                currentQuestion.options.forEachIndexed { index, optionText ->
                    val isSelected = selectedOptionIndex == index
                    val isCorrect = index == currentQuestion.correctOptionIndex

                    val (bgColor, borderColor, icon) = when {
                        !isAnswerSubmitted -> {
                            if (isSelected) Triple(TerminalCardBg, NeonCyan, null)
                            else Triple(TerminalCardBg, CyberBorder, null)
                        }
                        isCorrect -> Triple(NeonGreen.copy(alpha = 0.15f), NeonGreen, Icons.Default.CheckCircle)
                        isSelected && !isCorrect -> Triple(CyberRedAlert.copy(alpha = 0.15f), CyberRedAlert, Icons.Default.Cancel)
                        else -> Triple(TerminalCardBg, CyberBorder, null)
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("option_$index")
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                            .clickable(enabled = !isAnswerSubmitted) {
                                selectedOptionIndex = index
                            },
                        color = bgColor
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${('A' + index)}. ",
                                style = MaterialTheme.typography.labelLarge,
                                color = borderColor,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = optionText,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextPrimary,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                            if (icon != null) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = borderColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Explanation Box when submitted
                AnimatedVisibility(visible = isAnswerSubmitted) {
                    TerminalCard(
                        headerTitle = "EXPLANATION // FEEDBACK",
                        borderColor = if (selectedOptionIndex == currentQuestion.correctOptionIndex) NeonGreen else CyberRedAlert
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = currentQuestion.explanation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bottom Action Button
                if (!isAnswerSubmitted) {
                    CyberButton(
                        text = "CONFIRM ANSWER",
                        onClick = {
                            if (selectedOptionIndex != null) {
                                userAnswers[currentQuestionIndex] = selectedOptionIndex!!
                                isAnswerSubmitted = true
                            }
                        },
                        enabled = selectedOptionIndex != null,
                        accentColor = NeonCyan,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "btn_confirm_answer"
                    )
                } else {
                    CyberButton(
                        text = if (currentQuestionIndex < totalQuestions - 1) "NEXT QUESTION" else "FINISH & SUBMIT QUIZ",
                        onClick = {
                            if (currentQuestionIndex < totalQuestions - 1) {
                                currentQuestionIndex++
                                selectedOptionIndex = null
                                isAnswerSubmitted = false
                            } else {
                                isQuizCompleted = true
                                var correctCount = 0
                                quiz.questions.forEachIndexed { i, q ->
                                    if (userAnswers[i] == q.correctOptionIndex) {
                                        correctCount++
                                    }
                                }
                                val scorePct = (correctCount.toFloat() / totalQuestions.toFloat() * 100).toInt()
                                onSubmitQuizScore(scorePct, totalQuestions)
                            }
                        },
                        accentColor = NeonGreen,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "btn_next_question"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        } else {
            // Quiz Summary & Celebration Screen
            var correctCount = 0
            quiz.questions.forEachIndexed { i, q ->
                if (userAnswers[i] == q.correctOptionIndex) {
                    correctCount++
                }
            }
            val scorePercentage = (correctCount.toFloat() / totalQuestions.toFloat() * 100).toInt()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🏆",
                    fontSize = 60.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "QUIZ COMPLETED!",
                    style = MaterialTheme.typography.displayLarge,
                    color = NeonGreen,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Score: $scorePercentage% ($correctCount / $totalQuestions Correct)",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                TerminalCard(
                    headerTitle = "REWARD_SUMMARY // XP EARNED",
                    borderColor = NeonAmber
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "+${if (scorePercentage >= 100) 150 else if (scorePercentage >= 75) 100 else 50} XP",
                            style = MaterialTheme.typography.displayLarge,
                            color = NeonAmber,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (scorePercentage >= 100) "PERFECT SCORE! Badge Progress Updated!" else "Great effort! Practice more to reach 100%!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                CyberButton(
                    text = "RETURN TO LESSONS",
                    onClick = onBackClick,
                    accentColor = NeonCyan,
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "btn_return_lessons"
                )
            }
        }
    }
}
