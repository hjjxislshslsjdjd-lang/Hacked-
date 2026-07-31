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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.local.CtfSolutionEntity
import com.example.data.model.CtfChallenge
import com.example.data.model.LevelEnum
import com.example.ui.components.CyberButton
import com.example.ui.components.TerminalCard
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDarkBackground
import com.example.ui.theme.CyberRedAlert
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.TerminalCardBg
import com.example.ui.theme.TerminalHeaderBg
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CtfSandboxScreen(
    challenges: List<CtfChallenge>,
    solvedSolutions: List<CtfSolutionEntity>,
    selectedCtfId: String?,
    onSelectCtf: (String?) -> Unit,
    onSubmitFlag: (ctfId: String, flagInput: String, onResult: (Boolean) -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val solvedMap = solvedSolutions.associateBy { it.ctfId }
    val activeCtf = challenges.find { it.id == selectedCtfId }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Title Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "CTF_SANDBOX // LEGAL SIMULATOR",
                    style = MaterialTheme.typography.titleMedium,
                    color = NeonAmber,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Hands-on cybersecurity challenges & flag verification",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
            Surface(
                color = NeonAmber.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonAmber)
            ) {
                Text(
                    text = "${solvedSolutions.size}/${challenges.size} SOLVED",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonAmber,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (activeCtf == null) {
            // List of CTF Challenges
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(challenges) { ctf ->
                    val isSolved = solvedMap.containsKey(ctf.id)
                    CtfChallengeItemRow(
                        ctf = ctf,
                        isSolved = isSolved,
                        onClick = { onSelectCtf(ctf.id) }
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        } else {
            // Active CTF Challenge Workspace
            ActiveCtfWorkspace(
                ctf = activeCtf,
                isSolved = solvedMap.containsKey(activeCtf.id),
                onBackToList = { onSelectCtf(null) },
                onSubmitFlag = { flagInput, onResult ->
                    onSubmitFlag(activeCtf.id, flagInput, onResult)
                }
            )
        }
    }
}

@Composable
fun CtfChallengeItemRow(
    ctf: CtfChallenge,
    isSolved: Boolean,
    onClick: () -> Unit
) {
    TerminalCard(
        headerTitle = "CHALLENGE // ${ctf.category.uppercase()}",
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
                        text = ctf.level.displayName,
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
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Solved",
                    tint = NeonGreen,
                    modifier = Modifier.size(24.dp)
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

@Composable
fun ActiveCtfWorkspace(
    ctf: CtfChallenge,
    isSolved: Boolean,
    onBackToList: () -> Unit,
    onSubmitFlag: (flagInput: String, onResult: (Boolean) -> Unit) -> Unit
) {
    var flagInput by remember { mutableStateOf("") }
    var showHints by remember { mutableStateOf(false) }
    var submitFeedback by remember { mutableStateOf<Pair<Boolean, String>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Back Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = TerminalCardBg,
                shape = RoundedCornerShape(6.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
                modifier = Modifier
                    .testTag("btn_back_ctf_list")
                    .clickable { onBackToList() }
            ) {
                Text(
                    text = "< RETURN TO LABS LIST",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonCyan,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Challenge Overview Card
        TerminalCard(
            headerTitle = "CTF_TASK // ${ctf.id.uppercase()}",
            borderColor = if (isSolved) NeonGreen else NeonAmber
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = ctf.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (isSolved) {
                        Surface(
                            color = NeonGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "SOLVED ✓",
                                style = MaterialTheme.typography.labelSmall,
                                color = NeonGreen,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = ctf.scenarioText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimary,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Simulated Logs / Headers Terminal View
        if (ctf.simulatedLogsOrHeaders != null) {
            TerminalCard(
                headerTitle = "INTERCEPTED_TRANSMISSION // CONSOLE LOGS",
                borderColor = NeonCyan
            ) {
                Surface(
                    color = CyberDarkBackground,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = ctf.simulatedLogsOrHeaders,
                        style = MaterialTheme.typography.bodyMedium,
                        color = NeonGreen,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Hints Expander
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, CyberBorder, RoundedCornerShape(8.dp))
                .clickable { showHints = !showHints },
            color = TerminalCardBg
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = NeonAmber,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "DECRYPTOR HINTS (${ctf.hints.size})",
                        style = MaterialTheme.typography.labelSmall,
                        color = NeonAmber,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Text(
                    text = if (showHints) "▲ HIDE" else "▼ SHOW",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        AnimatedVisibility(visible = showHints) {
            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .background(TerminalCardBg, RoundedCornerShape(8.dp))
                    .border(1.dp, CyberBorder, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ctf.hints.forEachIndexed { idx, hint ->
                    Text(
                        text = "• Hint ${idx + 1}: $hint",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Flag Submission Form
        TerminalCard(
            headerTitle = "FLAG_SUBMISSION // ENTER flag{...}",
            borderColor = if (isSolved) NeonGreen else NeonCyan
        ) {
            Column {
                OutlinedTextField(
                    value = flagInput,
                    onValueChange = {
                        flagInput = it
                        submitFeedback = null
                    },
                    placeholder = {
                        Text(
                            text = "user@cyberhack:~$ flag{...}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_ctf_flag"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CyberDarkBackground,
                        unfocusedContainerColor = CyberDarkBackground,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = CyberBorder,
                        focusedTextColor = NeonGreen,
                        unfocusedTextColor = NeonGreen
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (submitFeedback != null) {
                    val (success, message) = submitFeedback!!
                    Text(
                        text = message,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (success) NeonGreen else CyberRedAlert,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                CyberButton(
                    text = if (isSolved) "SUBMIT AGAIN" else "SUBMIT FLAG (+${ctf.xpReward} XP)",
                    onClick = {
                        if (flagInput.isNotBlank()) {
                            onSubmitFlag(flagInput) { isCorrect ->
                                if (isCorrect) {
                                    submitFeedback = true to "✓ FLAG VERIFIED! +${ctf.xpReward} XP AWARDED!"
                                } else {
                                    submitFeedback = false to "✗ ACCESS DENIED: Invalid flag string. Check format or hints."
                                }
                            }
                        }
                    },
                    accentColor = if (isSolved) NeonGreen else NeonCyan,
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "btn_submit_flag"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
