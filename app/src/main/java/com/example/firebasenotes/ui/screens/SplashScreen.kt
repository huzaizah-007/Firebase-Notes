package com.example.firebasenotes.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebasenotes.ui.theme.BackgroundDark
import com.example.firebasenotes.ui.theme.PrimaryDark
import com.example.firebasenotes.ui.theme.SurfaceDark
import com.example.firebasenotes.ui.theme.TextPrimaryDark
import com.example.firebasenotes.ui.theme.TextSecondaryDark
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var visible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        visible = true
        delay(2000.milliseconds)
        onSplashFinished()
    }
    // Same Purple Gradient
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            BackgroundDark, SurfaceDark, Color(0xFF2D0A63)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible, enter = fadeIn(
                animationSpec = tween(700)
            ) + scaleIn(
                animationSpec = tween(700)
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // App Logo
                Box(
                    modifier = Modifier
                        .background(
                            color = PrimaryDark, shape = RoundedCornerShape(22.dp)
                        )
                        .padding(18.dp)
                ) {
                    Text(
                        text = "📝", fontSize = 40.sp, color = TextPrimaryDark
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                // App Name
                Text(
                    text = "Firebase Notes",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimaryDark
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Tagline
                Text(
                    text = "Your thoughts, organized.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryDark
                )
            }
        }
    }
}