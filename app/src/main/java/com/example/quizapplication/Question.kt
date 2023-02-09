package com.example.quizapplication

// question model: used to hold the information needed for each question
data class Question(
    val id: Int,
    val questionTitle: String,
    val image: Int,
    val optOne: String,
    val optTwo: String,
    val optThree: String,
    val optFour: String,
    val correctAns: Int,
)
