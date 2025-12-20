package com.example.self_healdemoapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.self_healdemoapplication.data.DemoUsers
import com.example.self_healdemoapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: com.example.self_healdemoapplication.viewmodel.SelfHealViewModel,
    modifier: Modifier = Modifier
) {
    val isDemoMode by viewModel.isDemoModeEnabled.collectAsState()
    var selectedUserEmail by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LightBackground),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .widthIn(max = 400.dp)
                .fillMaxHeight(0.95f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Header
                Text(
                    text = "Welcome Back",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Text(
                    text = "Sign in to your account",
                    fontSize = 14.sp,
                    color = Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Self-Heal Mode Active Banner
                if (isDemoMode) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        color = Color(0xFFFFF3E0),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "⚡",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = "Self-Heal mode active - Elements are modified",
                                fontSize = 14.sp,
                                color = Color(0xFFE65100),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Quick Select User Dropdown
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Quick Select User",
                        fontSize = 14.sp,
                        color = Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // Healing Demo notification for dropdown
                    if (isDemoMode) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            color = Color(0xFFFFF8E1),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Healing Demo: Select-User element Id changed from 'user_dropdown' to 'user_dropdown_demo'",
                                fontSize = 12.sp,
                                color = Color(0xFFF57C00),
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.testTag(
                            if (isDemoMode) "user_dropdown_demo" else "user_dropdown"
                        )
                    ) {
                        OutlinedTextField(
                            value = if (selectedUserEmail.isEmpty()) "Choose a test user..." else selectedUserEmail,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = LightGray,
                                focusedBorderColor = DarkNavy
                            ),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DemoUsers.users.forEach { user ->
                                DropdownMenuItem(
                                    text = { Text(user.email) },
                                    onClick = {
                                        selectedUserEmail = user.email
                                        email = user.email
                                        password = user.password
                                        expanded = false
                                    },
                                    modifier = Modifier.testTag("user_option_${user.email}")
                                )
                            }
                        }
                    }
                }

                // Email Input
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Healing Demo notification for email
                    if (isDemoMode) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            color = Color(0xFFFFF8E1),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Healing Demo: Email element Id changed from 'email_input' to 'email_input_demo'",
                                fontSize = 12.sp,
                                color = Color(0xFFF57C00),
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        placeholder = { Text("Enter your email") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = LightGray,
                            focusedBorderColor = DarkNavy
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(if (isDemoMode) "email_input_demo" else "email_input")
                    )
                }

                // Password Input
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Healing Demo notification for password
                    if (isDemoMode) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            color = Color(0xFFFFF8E1),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Healing Demo: Password element Id changed from 'password_input' to 'password_input_demo'",
                                fontSize = 12.sp,
                                color = Color(0xFFF57C00),
                                modifier = Modifier.padding(8.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        placeholder = { Text("Enter your password") },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            TextButton(
                                onClick = { passwordVisible = !passwordVisible },
                                modifier = Modifier.testTag(
                                    if (isDemoMode) "password_toggle_demo" else "password_toggle"
                                )
                            ) {
                                Text(
                                    text = if (passwordVisible) "Hide" else "Show",
                                    fontSize = 14.sp
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = LightGray,
                            focusedBorderColor = DarkNavy
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(if (isDemoMode) "password_input_demo" else "password_input")
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sign In Button
                Button(
                    onClick = {
                        // Simple validation - check if user exists
                        val user = DemoUsers.getUserByEmail(email)
                        if (user != null && user.password == password) {
                            onLoginSuccess()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag(if (isDemoMode) "sign_in_button_demo" else "sign_in_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkNavy
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Sign In",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
