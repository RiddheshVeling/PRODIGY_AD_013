package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isRunning = false
    private var startTimeMillis = 0L
    private var timerMillis = 0L

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val currentTimeMillis = System.currentTimeMillis()
            timerMillis = currentTimeMillis - startTimeMillis

            val minutes = (timerMillis / 1000) / 60
            val seconds = (timerMillis / 1000) % 60
            val milliseconds = timerMillis % 1000

            val time = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
            binding.timerText.text = time

            handler.postDelayed(this, 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            startTimer()
        }
        binding.stopBtn.setOnClickListener {
            stopTimer()
        }
        binding.resetBtn.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (!isRunning) {
            startTimeMillis = System.currentTimeMillis() - timerMillis
            handler.postDelayed(runnable, 1)
            isRunning = true

            binding.startBtn.isEnabled = false
            binding.stopBtn.isEnabled = true
            binding.resetBtn.isEnabled = true
        }
    }

    private fun stopTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable)
            isRunning = false

            binding.startBtn.isEnabled = true
            binding.startBtn.text = "Resume"
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = true
        }
    }

    private fun resetTimer() {
        stopTimer()

        startTimeMillis = 0
        timerMillis = 0
        binding.timerText.text = "00:00:000"

        binding.startBtn.isEnabled = true
        binding.stopBtn.isEnabled = false
        binding.startBtn.text = "Start"
    }
}
