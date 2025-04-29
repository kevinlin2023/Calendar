package com.example.calendar

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendar.ViewModel.CountdownViewModel
import com.example.calendar.ui.theme.CalendarTheme
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.calendar.Activity.CalendarActivity
import com.example.calendar.ui.theme.LightBlue

class MainActivity : ComponentActivity() {
    val viewModel: CountdownViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

            CalendarTheme {
                Scaffold {
                    val countdownTime by viewModel.countdownTime.observeAsState()

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Gray)
                            .padding(it),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "倒數計時器",
                                fontSize = 40.sp,
                                color = Color.Blue,
                            )
                            Text(
                                text = "Countdown",
                                fontSize = 40.sp,
                                color = Color.Red,
                            )
                            countdownTime?.let { time ->
                                Text(
                                    "${time.days}天 ${time.hours}時 ${time.minutes}分 ${time.seconds}秒",
                                    color = Color.White
                                )
                            }

                            NextQ {
                                val intent = Intent(context, CalendarActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }
}

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