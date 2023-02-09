package com.example.quizapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DisplayScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_score)

        // assign the components to the variables
        var tvName: TextView = findViewById(R.id.tvName)
        var tvPoints: TextView = findViewById(R.id.tvPoints)
        val btnFinish: Button = findViewById(R.id.btnFinish)

        // get the values from the intent and assign to the variables
        val totalQuestions: Int = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers: Int = intent.getIntExtra(Constants.POINTS, 0)
        tvName.text = intent.getStringExtra(Constants.CANDIDATE_NAME)

        tvPoints.text = "Your have scored $correctAnswers out of $totalQuestions"

        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}