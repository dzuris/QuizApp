package com.example.quizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Pop : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop)

        //  Background button, finish pop
        val finishButton = findViewById<Button>(R.id.buttonClosePopWindow)
        finishButton.setOnClickListener {
            finish()
        }

        //  Get number of questions
        val numberOfQuestions = getCount()

        //  Set everything for normal mode
        val btnNormal =findViewById<Button>(R.id.btnNormal)
        btnNormal.setOnClickListener {
            index = 0
            mode = 0
            questionNumber1 = 1
            questionNumber2 = if (numberOfQuestions > questionsList.size) questionsList.size else numberOfQuestions
            points = 0
            startActivity(Intent(this, Question::class.java))
            finish()
        }

        //  Set everything for death match mode
        val btnDeath = findViewById<Button>(R.id.btnDeathmatch)
        btnDeath.setOnClickListener {
            index = 0
            mode = 1
            questionNumber1 = if (numberOfQuestions > questionsList.size) questionsList.size else numberOfQuestions
            startActivity(Intent(this, Question::class.java))
            finish()
        }

        //  Set everything for run mode
        val btnRun = findViewById<Button>(R.id.btnRun)
        btnRun.setOnClickListener {
            index = 0
            mode = 2
            questionNumber1 = 0
            startActivity(Intent(this, Question::class.java))
        }
    }

    private fun getCount(): Int{
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(NUMBER_OF_QUESTIONS, 15)
    }
}
