package com.example.self_healdemoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.self_healdemoapplication.ui.screens.LoginScreen
import com.example.self_healdemoapplication.ui.screens.ProductsHomeScreen
import com.example.self_healdemoapplication.ui.theme.*
import com.example.self_healdemoapplication.viewmodel.HealingElement
import com.example.self_healdemoapplication.viewmodel.SelfHealViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SelfHealDemoApplicationTheme {
                SelfHealDemoApp()
            }
        }
    }
}

@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun SelfHealDemoApp(viewModel: SelfHealViewModel = viewModel()) {
    var isLoggedIn by rememberSaveable { mutableStateOf(false) }
    val isDemoMode by viewModel.isDemoModeEnabled.collectAsState()

    // Set healing element to SELECT_USER only
    LaunchedEffect(Unit) {
        viewModel.setHealingElement(HealingElement.SELECT_USER)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .semantics {
                // Enable testTags as resource IDs for Appium UiAutomator2
                testTagsAsResourceId = true
            }
    ) {
        // Self-Healing Demo Mode Toggle Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("demo_mode_bar"),
            color = OrangeAccent,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Self-Heal Mode",
                        color = White,
                        fontSize = 14.sp
                    )
                    Text(
                        text = if (isDemoMode) "Toggle Mode: Active" else "Toggle Mode: Inactive",
                        color = White,
                        fontSize = 12.sp
                    )
                }
                Switch(
                    checked = isDemoMode,
                    onCheckedChange = { viewModel.toggleDemoMode() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = White,
                        checkedTrackColor = DarkNavy,
                        uncheckedThumbColor = LightGray,
                        uncheckedTrackColor = White
                    ),
                    modifier = Modifier.testTag("demo_mode_switch")
                )
            }
        }

        // Main Content
        if (isLoggedIn) {
            ProductsHomeScreen(
                onLogout = { isLoggedIn = false },
                viewModel = viewModel
            )
        } else {
            LoginScreen(
                onLoginSuccess = { isLoggedIn = true },
                viewModel = viewModel
            )
        }
    }
}