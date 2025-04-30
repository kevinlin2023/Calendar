package com.example.calendar.ui.base

import androidx.activity.ComponentActivity
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.calendar.ui.theme.LightBlue

open class BaseActivity : ComponentActivity()

@Composable
fun NextQ(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = LightBlue, // 背景色
            contentColor = Color.White         // 文字顏色
        )
    ) {
        Text("下一題")
    }
}