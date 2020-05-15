package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.Math.round

class End : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        title = getString(R.string.Title)

        val declension = when (questionNumber2) {
            1 -> getString(R.string.QuestionOne)
            else -> getString(R.string.QuestionMore)
        }

        val tEnd = findViewById<TextView>(R.id.txtV_end)
        val tEnd2 = findViewById<TextView>(R.id.textView3)
        when (mode){
            0 -> {
                val percents = round(points.toDouble()*10000 / questionNumber2.toDouble()) / 100
                tEnd.text =
                    """${getString(R.string.CorrectAnswered)} $points ${getString(R.string.From)} $questionNumber2 $declension"""
                tEnd2.text = "$percents%"
            }
            1 -> {
                when (result){
                    0 -> {
                        tEnd.text = getString(R.string.YouLost) + " :("
                        tEnd2.text = getString(R.string.TryAgain)
                    }
                    1 -> {
                        tEnd.text = getString(R.string.YouWin) + "!"
                        tEnd2.text = getString(R.string.Congratulations)
                    }
                    else -> {
                        tEnd.text = "error"
                        tEnd2.text = "error"
                    }
                }
            }
            2 -> {
                tEnd.text = getString(R.string.Score) + questionNumber1
                tEnd2.text = ""
            }
        }

        val btnMenu = findViewById<Button>(R.id.btn_menu)
        btnMenu.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}
