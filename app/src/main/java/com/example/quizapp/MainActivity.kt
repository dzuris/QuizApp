package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.quizapp.database.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private var db: DatabaseHelper? = null
    private var testGithub: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = getString(R.string.Title)
        db = DatabaseHelper(this)

        //  New game button, start only when database is not empty
        val btnNG = findViewById<Button>(R.id.btn_newGame)
        btnNG.setOnClickListener {
            if (db!!.allQuestions.size == 0){
                Toast.makeText(this, getString(R.string.WeDontHaveQuestion), Toast.LENGTH_LONG).show()
            }else{
                questionsList = db!!.allQuestions
                questionsList.shuffle()
                startActivity(Intent(this, Pop::class.java))
            }
        }

        //  Playing categories
        val btnC = findViewById<Button>(R.id.btn_category)
        btnC.setOnClickListener {
            startActivity(Intent(this, PlayCategory::class.java))
        }

        //  Options activity
        val btnO = findViewById<Button>(R.id.btn_options)
        btnO.setOnClickListener {
            startActivity(Intent(this, Options::class.java))
        }
    }
}
