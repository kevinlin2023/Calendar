package com.example.calendar.ui.components

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calendar.ui.theme.CalendarTheme
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import androidx.compose.ui.input.pointer.pointerInput
import com.example.calendar.ui.base.NextQ
import java.time.LocalDate
import androidx.compose.ui.platform.LocalContext

class CalendarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalendarScreen()
                }
            }
        }
    }
}

@Composable
fun CalendarScreen() {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount > 50) {
                        // 向右滑：上一個月
                        currentMonth = currentMonth.minusMonths(1)
                    } else if (dragAmount < -50) {
                        // 向左滑：下一個月
                        currentMonth = currentMonth.plusMonths(1)
                    }
                }
            }
    ) {
        // 月份標題
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${currentMonth.year} 年 ${currentMonth.monthValue} 月",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 星期標頭
        val weekHeaders = listOf("日", "一", "二", "三", "四", "五", "六")
        val calendarData = remember(currentMonth) {
            weekHeaders + generateCalendarGridData(currentMonth) // 先加星期標頭
        }

        // 今天做標誌
        val today = LocalDate.now()

        // 日期表格
        LazyVerticalGrid(columns = GridCells.Fixed(7), contentPadding = PaddingValues(4.dp)) {
            items(calendarData) { dayText ->
                val isToday = try {
                    val day = dayText.toInt()
                    val date = currentMonth.atDay(day)
                    date == today
                } catch (e: Exception) {
                    false
                }

                val shape = RoundedCornerShape(6.dp)
                val bgColor = if (isToday) Color(0xFFBBDEFB) else Color.Transparent
                val borderColor = if (isToday) Color(0xFF1976D2) else Color.LightGray

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .background(bgColor, shape)
                        .border(1.dp, borderColor, shape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = dayText, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            val context = LocalContext.current
            NextQ {
                val intent = Intent(context, LineActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}

// 傳回整個月份顯示資料（包含前導空格）
fun generateCalendarGridData(yearMonth: YearMonth): List<String> {
    val firstDayOfMonth = yearMonth.atDay(1)
    val daysInMonth = yearMonth.lengthOfMonth()
    val dayOfWeekIndex = firstDayOfMonth.dayOfWeek.value % 7 // 星期日是 0

    val list = mutableListOf<String>()
    repeat(dayOfWeekIndex) { list.add("") }

    for (day in 1..daysInMonth) {
        val date = yearMonth.atDay(day)
        val formatted = date.format(DateTimeFormatter.ofPattern("d"))
        list.add(formatted)
    }
    return list
}
