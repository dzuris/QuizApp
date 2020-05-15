package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_edit_question.*

class EditQuestion : AppCompatActivity() {

    private var tvCategory: TextView? = null
    private var imgCategory: ImageView? = null
    private var db: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_question)

        title = getString(R.string.Title)
        db = DatabaseHelper(this)

        //  Edit texts
        val editTQuestion = findViewById<EditText>(R.id.et_editQuestion_question)
        val editTM1 = findViewById<EditText>(R.id.et_editQuestion_optionA)
        val editTM2 = findViewById<EditText>(R.id.et_editQuestion_optionB)
        val editTM3 = findViewById<EditText>(R.id.et_editQuestion_optionC)
        val editTM4 = findViewById<EditText>(R.id.et_editQuestion_optionCorrect)

        //region Category
        tvCategory = findViewById<TextView>(R.id.tvCategory_editQuestion)
        imgCategory = findViewById<ImageView>(R.id.imageViewCategory_editQuestion)

        tvCategory!!.text = helpQuestion!!.category.name
        imgCategory!!.setImageBitmap(getImage(helpQuestion!!.category.image))
        //endregion

        //  Show question in edit text
        editTQuestion.text = SpannableStringBuilder(helpQuestion!!.question)
        //  Set counter for question
        tvCounter_question.text = optionLength(helpQuestion!!.question, maxQuestionLength)
        //  Show option A in edit text
        editTM1.text = SpannableStringBuilder(helpQuestion!!.optionA)
        //  Set counter for option A
        tvCounter_optionA.text = optionLength(helpQuestion!!.optionA, maxOptionLength)
        //  Show option B in edit text
        editTM2.text = SpannableStringBuilder(helpQuestion!!.optionB)
        //  Set counter for option B
        tvCounter_optionB.text = optionLength(helpQuestion!!.optionB, maxOptionLength)
        //  Show option C in edit text
        editTM3.text = SpannableStringBuilder(helpQuestion!!.optionC)
        //  Set counter for option C
        tvCounter_optionC.text = optionLength(helpQuestion!!.optionC, maxOptionLength)
        //  Show correct option in edit text
        editTM4.text = SpannableStringBuilder(helpQuestion!!.optionCorrect)
        //  Set counter for correct option
        tvCounter_optionCorrect.text = optionLength(helpQuestion!!.optionCorrect, maxOptionLength)
        //  Counters on change, change text view with lengths
        counter(editTQuestion, tvCounter_question, maxQuestionLength)
        counter(editTM1, tvCounter_optionA, maxOptionLength)
        counter(editTM2, tvCounter_optionB, maxOptionLength)
        counter(editTM3, tvCounter_optionC, maxOptionLength)
        counter(editTM4, tvCounter_optionCorrect, maxOptionLength)

        //  Update edited question in database
        val btnSave = findViewById<Button>(R.id.btn_save_editQuestion)
        btnSave.setOnClickListener {
            val question = editTQuestion.text.toString()
            val option1 = editTM1.text.toString()
            val option2 = editTM2.text.toString()
            val option3 = editTM3.text.toString()
            val optionCorrect = editTM4.text.toString()

            val itemCategory = helpQuestion!!.category.id
            finish()
            db!!.updateQuestion(helpQuestion!!.id, question, option1, option2, option3, optionCorrect, itemCategory)
            startActivity(Intent(this,QuestionList::class.java))
        }

        //  Delete question from database
        val btnDeleteQ = findViewById<Button>(R.id.btn_deleteQuestion)
        btnDeleteQ.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.DeleteQuestionAsk))
            builder.setMessage(helpQuestion!!.question)
            builder.setPositiveButton(getString(R.string.Yes)){ _, _ ->
                db!!.deleteQuestion(helpQuestion!!.id)
                startActivity(Intent(this,MainActivity::class.java))
                Toast.makeText(this,getString(R.string.QuestionWasDeleted), Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton(getString(R.string.No)){ _, _ ->

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    //  Converting byte array to bitmap
    private fun getImage(image:ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image,0,image.size)
    }

    //  Function to set onChangeEditTexts due counters
    private fun counter(et: EditText, tvForChange: TextView, from: Int){
        et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tvForChange.text = p0.toString().length.toString() + "/" + from.toString()
            }
        })
    }

    //  Return format for show counters
    private fun optionLength(word: String, from: Int): String{
        return word.length.toString() + "/" + from.toString()
    }
}
