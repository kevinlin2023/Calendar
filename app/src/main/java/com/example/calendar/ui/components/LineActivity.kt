package com.example.calendar.ui.components

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.calendar.ui.base.BaseActivity
import com.example.calendar.ui.theme.CalendarTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class LineActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                var selectedScreen by remember { mutableStateOf("Home") }

                // 背景顏色狀態
                var backgroundColor by remember { mutableStateOf(Color.White) }

                // 是否顯示 Dialog
                var showColorDialog by remember { mutableStateOf(false) }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            Text("選單", modifier = Modifier.padding(16.dp))
                            HorizontalDivider()
                            NavigationDrawerItem(
                                label = { Text("首頁") },
                                selected = selectedScreen == "Home",
                                onClick = {
                                    selectedScreen = "Home"
                                    scope.launch { drawerState.close() }
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text("主題") },
                                selected = false,
                                onClick = {
                                    showColorDialog = true
                                    scope.launch { drawerState.close() }
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("我的 App") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch { drawerState.open() }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                }
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(backgroundColor)
                                .padding(innerPadding),
                            contentAlignment = Alignment.TopCenter
                        ) {

                            Text(
                                "目前頁面：$selectedScreen", fontSize = 24.sp,
                                modifier = Modifier.padding(top = 20.dp)
                            )

                        }

                        // 彈出選色 Dialog
                        if (showColorDialog) {
                            AlertDialog(
                                onDismissRequest = { showColorDialog = false },
                                title = { Text("選擇背景顏色") },
                                text = {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        val colors = listOf(
                                            Color.White, Color.LightGray, Color.Yellow,
                                            Color.Cyan, Color(0xFFFFCDD2), Color(0xFFBBDEFB)
                                        )
                                        colors.forEach { color ->
                                            Box(
                                                modifier = Modifier
                                                    .size(35.dp)
                                                    .background(color, shape = CircleShape)
                                                    .border(2.dp, Color.DarkGray, CircleShape)
                                                    .clickable {
                                                        backgroundColor = color
                                                        showColorDialog = false
                                                    }
                                            )
                                        }
                                    }
                                },
                                confirmButton = {
                                    TextButton(onClick = { showColorDialog = false }) {
                                        Text("取消")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}