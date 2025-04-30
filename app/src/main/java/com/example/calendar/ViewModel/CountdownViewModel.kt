package com.example.calendar.viewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calendar.model.CountdownTime
import java.time.Duration
import java.time.LocalDateTime

class CountdownViewModel : ViewModel() {

    private val _countdownTime = MutableLiveData<CountdownTime>()
    val countdownTime: LiveData<CountdownTime> = _countdownTime

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            _countdownTime.value = calculateRemainingTime()
            handler.postDelayed(this, 1000) // 每秒更新
        }
    }

    init {
        handler.post(updateRunnable)
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(updateRunnable)
    }

    private fun calculateRemainingTime(): CountdownTime {
        val target = LocalDateTime.of(2026, 1, 1, 0, 0)
        val now = LocalDateTime.now()
        val duration = Duration.between(now, target)

        val days = duration.toDays()
        val hours = duration.minusDays(days).toHours()
        val minutes = duration.minusDays(days).minusHours(hours).toMinutes()
        val seconds = duration.minusDays(days).minusHours(hours).minusMinutes(minutes).seconds

        return CountdownTime(days, hours, minutes, seconds)
    }
}
