package com.example.quizapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {
    // create variables to hold the current question position, selected option by the user, and correct points earned by the user
    private var currentQuestion: Int = 1
    private var questionList: ArrayList<Question>? = null
    private var selectedOption: Int = 0
    private var correctPoints: Int = 0

    // create variable to store the component from xml file
    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null
    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var btnSubmit: Button? = null
    private var candidateName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        // get the user's name passed from the main's intent
        candidateName = intent.getStringExtra(Constants.CANDIDATE_NAME)

        // assign the xml components to the respective variables
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tvProgress)
        tvQuestion = findViewById(R.id.tvQuestion)
        ivImage = findViewById(R.id.ivImage)
        tvOptionOne = findViewById(R.id.tvOptionOne)
        tvOptionTwo = findViewById(R.id.tvOptionTwo)
        tvOptionThree = findViewById(R.id.tvOptionThree)
        tvOptionFour = findViewById(R.id.tvOptionFour)
        btnSubmit = findViewById(R.id.btnSubmit)

        // set the onclick listener to change the border and submit function
        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        // fetch the questions into the question list
        questionList = Constants.getQuestions()

        // set the questions into the activity
        setQuestions()
    }

    private fun setQuestions() {
        defaultOptionView()

        // set the question into the view
        val question = questionList!![currentQuestion - 1]
        progressBar?.progress = currentQuestion
        tvProgress?.text = "$currentQuestion/${progressBar?.max}"
        tvQuestion?.text = question.questionTitle
        tvOptionOne?.text = question.optOne
        tvOptionTwo?.text = question.optTwo
        tvOptionThree?.text = question.optThree
        tvOptionFour?.text = question.optFour
        ivImage?.setImageResource(question.image)

        if (currentQuestion == questionList!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "NEXT"
        }
    }

    private fun defaultOptionView() {
        var options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0, it)
        }

        tvOptionTwo?.let {
            options.add(1, it)
        }

        tvOptionThree?.let {
            options.add(2, it)
        }

        tvOptionFour?.let {
            options.add(3, it)
        }

        for (opt in options) {
            opt.setTextColor(Color.parseColor("#7A8089"))
            opt.typeface = Typeface.DEFAULT
            opt.background = ContextCompat.getDrawable(
                this, R.drawable.options_background_border
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()
        selectedOption = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_border
        )
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tvOptionOne -> {
                tvOptionOne?.let { selectedOptionView(it, 1) }
                selectedOption == 0
            }

            R.id.tvOptionTwo -> {
                tvOptionTwo?.let { selectedOptionView(it, 2) }
                selectedOption == 0
            }

            R.id.tvOptionThree -> {
                tvOptionThree?.let { selectedOptionView(it, 3) }
                selectedOption == 0
            }

            R.id.tvOptionFour -> {
                tvOptionFour?.let { selectedOptionView(it, 4) }
                selectedOption == 0
            }

            R.id.btnSubmit -> {
                if (selectedOption == 0) {
                    currentQuestion++
                    when {
                        // if the question is not yet finish, the system will continue to set the next question
                        currentQuestion <= questionList!!.size -> {
                            setQuestions()
                        }
                        else -> {
                            // else it will bring the user to the display score activity to view the score
                            val int = Intent(this, DisplayScoreActivity::class.java)
                            int.putExtra(Constants.CANDIDATE_NAME, candidateName)
                            int.putExtra(Constants.POINTS, correctPoints)
                            int.putExtra(Constants.TOTAL_QUESTIONS, questionList?.size)
                            startActivity(int)
                            finish()
                        }
                    }
                } else {
                    // check the answer is correct or wrong
                    val question = questionList?.get(currentQuestion - 1)

                    if (question!!.correctAns != selectedOption) {
                        answerView(selectedOption, R.drawable.wrong_option_bg) // display wrong option view
                    } else {
                        correctPoints++ // accumulate points by 1 if answer correctly
                    }

                    answerView(question.correctAns, R.drawable.correct_option_bg) // display correct option view

                    if (currentQuestion == questionList!!.size) {
                        btnSubmit?.text = "FINISH" // when the questions are finish, change the button text to FINISH
                    } else {
                        btnSubmit?.text = "NEXT QUESTION" // else change the button text to NEXT QUESTION
                    }

                    selectedOption = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(this, drawableView)
            }

            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(this, drawableView)
            }

            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(this, drawableView)
            }

            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }
}