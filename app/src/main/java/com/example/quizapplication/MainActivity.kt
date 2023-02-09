package com.example.quizapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btnStart)
        val nameInput = findViewById<EditText>(R.id.etName)
        btnStart.setOnClickListener {
            // if the user does not enter any name, a toast will be displayed and ask for a name input
            // else proceed to answer the quiz
            if (nameInput.text.isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    "You must fill in your name to start the quiz!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // create intent to start the question activity
                val int = Intent(this@MainActivity, QuestionsActivity::class.java)
                int.putExtra(Constants.CANDIDATE_NAME, nameInput.text.toString()) // take the name input and pass to the question activity
                startActivity(int)
            }
        }
    }
}