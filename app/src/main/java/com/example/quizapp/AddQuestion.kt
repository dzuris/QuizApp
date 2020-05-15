package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import com.example.quizapp.database.DatabaseHelper

class AddQuestion : AppCompatActivity() {

    private var textViewCategory: TextView? = null
    private var aIndex = 0
    private var categoryItem: CCategory? = null
    private var db: DatabaseHelper? = null
    private var listOfCategories: ArrayList<CCategory>? = null

    var editTextQuestion: EditText? = null
    var editTextOptionA: EditText? = null
    var editTextOptionB: EditText? = null
    var editTextOptionC: EditText? = null
    var editTextOptionD: EditText? = null
    var counterQuestion: TextView? = null
    var counterOptionA: TextView? = null
    var counterOptionB: TextView? = null
    var counterOptionC: TextView? = null
    var counterOptionCorrect: TextView? = null
    private var imageCategory: ImageView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)

        title = getString(R.string.Title)
        db = DatabaseHelper(this)

        listOfCategories = db!!.allCategories
        counterQuestion = findViewById(R.id.addQuestion_questionCounter)
        counterOptionA = findViewById(R.id.addQuestion_optionACounter)
        counterOptionB = findViewById(R.id.addQuestion_optionBCounter)
        counterOptionC = findViewById(R.id.addQuestion_optionCCounter)
        counterOptionCorrect = findViewById(R.id.addQuestion_optionCorrectCounter)

        counterQuestion!!.text = "0/220"
        counterQuestion!!.textSize = 14F

        counterOptionA!!.text = "0/120"
        counterOptionA!!.textSize = 10F

        counterOptionB!!.text = "0/120"
        counterOptionB!!.textSize = 10F

        counterOptionC!!.text = "0/120"
        counterOptionC!!.textSize = 10F

        counterOptionCorrect!!.text = "0/120"
        counterOptionCorrect!!.textSize = 10F

        editTextQuestion = findViewById(R.id.etQuestion_addQuestion)
        //  text counter question
        editTextQuestion!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val questionText:String = editTextQuestion!!.text.toString()
                val questionLength = questionText.length
                counterQuestion!!.text = "$questionLength/220"
            }
        })

        editTextOptionA = findViewById(R.id.etOption1_addQuestion)
        editTextOptionA!!.hint = getString(R.string.Option) + " A"
        //  text counter option A
        editTextOptionA!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val optionAText:String = editTextOptionA!!.text.toString()
                val optionALength = optionAText.length
                counterOptionA!!.text = "$optionALength/120"
            }
        })

        editTextOptionB = findViewById(R.id.etOption2_addQuestion)
        editTextOptionB!!.hint = getString(R.string.Option) + " B"
        //  text counter option B
        editTextOptionB!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val optionBText:String = editTextOptionB!!.text.toString()
                val optionBLength = optionBText.length
                counterOptionB!!.text = "$optionBLength/120"
            }
        })

        editTextOptionC = findViewById(R.id.etOption3_addQuestion)
        editTextOptionC!!.hint = getString(R.string.Option) + " C"
        //  text counter option C
        editTextOptionC!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val optionCText:String = editTextOptionC!!.text.toString()
                val optionCLength = optionCText.length
                counterOptionC!!.text = "$optionCLength/120"
            }
        })

        editTextOptionD = findViewById(R.id.etOptionCorrect_addQuestion)
        //  text counter option correct
        editTextOptionD!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val optionCorrectText:String = editTextOptionD!!.text.toString()
                val optionCorrectLength = optionCorrectText.length
                counterOptionCorrect!!.text = "$optionCorrectLength/120"
            }
        })

        categoryItem = listOfCategories!!.elementAt(aIndex)

        textViewCategory = findViewById(R.id.tvCategory_addQuestion)
        textViewCategory!!.maxWidth = 225
        textViewCategory!!.textSize = 20F
        textViewCategory!!.text = categoryItem!!.name

        imageCategory = findViewById(R.id.imgViewCategory_addQuestion)
        imageCategory!!.setImageBitmap(getImage(listOfCategories!!.elementAt(aIndex).image))

        val buttonRArrow = findViewById<Button>(R.id.btn_rightArrow_addQuestion)
        buttonRArrow.text = ">"
        buttonRArrow.setOnClickListener {
            aIndex += 1
            categoryPlus()
        }

        val buttonLArrow = findViewById<Button>(R.id.btn_leftArrow_addQuestion)
        buttonLArrow.text = "<"
        buttonLArrow.setOnClickListener {
            aIndex -= 1
            categoryMinus()
        }

        val buttonAddQ = findViewById<Button>(R.id.btn_addQuestion)
        buttonAddQ.setOnClickListener {
            val typK = listOfCategories!!.elementAt(aIndex)
            val editTOtazka = findViewById<EditText>(R.id.etQuestion_addQuestion).text.toString()
            val editTM1 = findViewById<EditText>(R.id.etOption1_addQuestion).text.toString()
            val editTM2 = findViewById<EditText>(R.id.etOption2_addQuestion).text.toString()
            val editTM3 = findViewById<EditText>(R.id.etOption3_addQuestion).text.toString()
            val editTMC = findViewById<EditText>(R.id.etOptionCorrect_addQuestion).text.toString()

            if (editTOtazka.isNotEmpty() && editTM1.isNotEmpty() && editTM2.isNotEmpty() &&
                editTM3.isNotEmpty() && editTMC.isNotEmpty()) {
                var existingQuestion = false
                for (element in db!!.allQuestions){
                    if (element.question == editTOtazka){
                        existingQuestion = true
                    }
                }
                if (!existingQuestion){
                    db!!.addQuestion(editTOtazka, editTM1, editTM2, editTM3, editTMC, typK.id)
                    startActivity(Intent(this,MainActivity::class.java))
                }else{
                    Toast.makeText(this, getString(R.string.ThisQuestionExist), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.FillAllPole), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun categoryPlus() {
        if (aIndex > (listOfCategories!!.size-1)) {
            aIndex = 0
        }
        categoryItem = listOfCategories!!.elementAt(aIndex)
        textViewCategory!!.text = categoryItem!!.name
        imageCategory!!.setImageBitmap(getImage(listOfCategories!!.elementAt(aIndex).image))
    }

    private fun categoryMinus() {
        if (aIndex < 0) {
            aIndex = (listOfCategories!!.size - 1)
        }
        categoryItem = listOfCategories!!.elementAt(aIndex)
        textViewCategory!!.text = categoryItem!!.name
        imageCategory!!.setImageBitmap(getImage(listOfCategories!!.elementAt(aIndex).image))
    }

    private fun getImage(image:ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image,0,image.size)
    }
}
