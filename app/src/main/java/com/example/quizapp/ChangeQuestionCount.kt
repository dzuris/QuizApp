package com.example.quizapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_change_question_count.*

class ChangeQuestionCount : AppCompatActivity() {

    //  Cislo ktore je vypisane na displeji
    private var number = 0
    //  Pocet vsetkych otazok v databaze
    private var maxNumber = 0
    //  Nastaveny nowNumber otazok
    private var nowNumber: Int? = null
    private var numberText: TextView? = null
    private var db: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_question_count)

        title = getString(R.string.ChangeQuestionsCount)
        db = DatabaseHelper(this)
        nowNumber = getCount()
        maxNumber = db!!.allQuestions.size

        val txtMaxCount = findViewById<TextView>(R.id.textView_maxCount)
        txtMaxCount.text = getString(R.string.MaxQuestCount) + ": " + maxNumber.toString()

        val tvPo = findViewById<TextView>(R.id.textView4)
        tvPo.text = getString(R.string.ChooseQuestCount) + ":"

        number = nowNumber!!

        numberText = findViewById<TextView>(R.id.tvNumber)
        numberText!!.text = number.toString()

        btnN1.setOnClickListener { click(1) }
        btnN2.setOnClickListener { click(2) }
        btnN3.setOnClickListener { click(3) }
        btnN4.setOnClickListener { click(4) }
        btnN5.setOnClickListener { click(5) }
        btnN6.setOnClickListener { click(6) }
        btnN7.setOnClickListener { click(7) }
        btnN8.setOnClickListener { click(8) }
        btnN9.setOnClickListener { click(9) }
        btnN0.setOnClickListener { click(0) }

        btnBackspace.text = "<-"
        btnBackspace.setOnClickListener { number /= 10; numberText!!.text = number.toString() }

        btnClear.setOnClickListener { number = 0; numberText!!.text = number.toString() }

        val btnSave = findViewById<Button>(R.id.btn_saveQCounts)
        btnSave.setOnClickListener {
            if (number != 0) {
                saveCount(number)
                nowNumber = getCount()
                numberText!!.text = nowNumber.toString()
                Toast.makeText(this,getString(R.string.NumberChanged), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,getString(R.string.NumberNotChanged), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCount(): Int{
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(NUMBER_OF_QUESTIONS, 15)
    }

    private fun saveCount(number: Int){
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt(NUMBER_OF_QUESTIONS, number)

        editor.apply()
    }

    private fun click(value: Int){
        if (number*10+value <= maxNumber){
            number = number*10 + value
            numberText!!.text = number.toString()
        }
    }
}
