package com.example.quizapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_question.*
import com.example.quizapp.R.drawable.*

class Question : AppCompatActivity() {

    private val countList = arrayListOf(1,2,3,4)
    private var btnCh1: Button? = null
    private var btnCh2: Button? = null
    private var btnCh3: Button? = null
    private var btnCh4: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        title = getString(R.string.Title)
        val question = questionsList[index]
        countList.shuffle()

        btnCh1 = findViewById(R.id.btn_chooseA)
        btnCh2 = findViewById(R.id.btn_chooseB)
        btnCh3 = findViewById(R.id.btn_chooseC)
        btnCh4 = findViewById(R.id.btn_chooseD)

        //  Set question order, how much questions left or your current score
        val tvPoc = findViewById<TextView>(R.id.textView7)
        tvPoc.text = when (mode){
            //  Example: 1/15
            0 -> "$questionNumber1/$questionNumber2"
            //  Example: 4 questions left
            1 -> {
                val declension = if (questionNumber1 == 1) getString(R.string.OneQuestionLeft) else getString(R.string.MoreQuestionsLeft)
                "$questionNumber1 $declension"
            }
            //  Example: Score: 5
            2 -> getString(R.string.Score) + questionNumber1
            else -> "error"
        }

        //  Setting image on activity by category of current question
        imageView_icon.setImageBitmap(getImage(question.category.icon_image))

        //  Set button next to invisible
        btnNext.visibility = View.INVISIBLE

        //  Text view where is question show
        val tvQuestion = findViewById<TextView>(R.id.txtV_quest)
        tvQuestion.text = question.question

        //  If ban is true, you can't hit any button
        var ban = false
        when(countList[0]){
            1 -> {
                btnCh1!!.text = question.optionA
                btnCh1!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh1!!)
                    }
                }
            }
            2 -> {
                btnCh1!!.text = question.optionB
                btnCh1!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh1!!)
                    }
                }
            }
            3 -> {
                btnCh1!!.text = question.optionC
                btnCh1!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh1!!)
                    }
                }
            }
            4 -> {
                btnCh1!!.text = question.optionCorrect
                btnCh1!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        correctOption(btnCh1!!)
                    }
                }
            }
        }

        when(countList[1]){
            1 -> {
                btnCh2!!.text = question.optionA
                btnCh2!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh2!!)
                    }
                }
            }
            2 -> {
                btnCh2!!.text = question.optionB
                btnCh2!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh2!!)
                    }
                }
            }
            3 -> {
                btnCh2!!.text = question.optionC
                btnCh2!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh2!!)
                    }
                }
            }
            4 -> {
                btnCh2!!.text = question.optionCorrect
                btnCh2!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        correctOption(btnCh2!!)
                    }
                }
            }
        }

        when(countList[2]){
            1 -> {
                btnCh3!!.text = question.optionA
                btnCh3!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh3!!)
                    }
                }
            }
            2 -> {
                btnCh3!!.text = question.optionB
                btnCh3!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh3!!)
                    }
                }
            }
            3 -> {
                btnCh3!!.text = question.optionC
                btnCh3!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh3!!)
                    }
                }
            }
            4 -> {
                btnCh3!!.text = question.optionCorrect
                btnCh3!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        correctOption(btnCh3!!)
                    }
                }
            }
        }

        when(countList[3]){
            1 -> {
                btnCh4!!.text = question.optionA
                btnCh4!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh4!!)
                    }
                }
            }
            2 -> {
                btnCh4!!.text = question.optionB
                btnCh4!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh4!!)
                    }
                }
            }
            3 -> {
                btnCh4!!.text = question.optionC
                btnCh4!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        wrongOption(btnCh4!!)
                    }
                }
            }
            4 -> {
                btnCh4!!.text = question.optionCorrect
                btnCh4!!.setOnClickListener {
                    if (!ban) {
                        ban = true
                        questionsList.remove(question)
                        correctOption(btnCh4!!)
                    }
                }
            }
        }
    }

    //  Result is only for death mode, finish question activity and start end activity
    private fun finishPage(res: Int = 0) {
        result = res
        finish()
        val intent = Intent(this, End::class.java)
        startActivity(intent)
    }

    //  If answer is wrong, program shows you correct answer
    private fun setCorrectBackground(indexOfCorrectAnswer: Int){
        when (indexOfCorrectAnswer){
            1 -> btnCh1!!.setBackgroundResource(background_correct_question)
            2 -> btnCh2!!.setBackgroundResource(background_correct_question)
            3 -> btnCh3!!.setBackgroundResource(background_correct_question)
            4 -> btnCh4!!.setBackgroundResource(background_correct_question)
        }
    }

    //  Function called when you hit wrong answer
    private fun wrongOption(button: Button){
        //  Set button background to red
        button.setBackgroundResource(background_wrong_question)
        //  Find correct option and set button background to green
        when(4){
            countList[0] -> setCorrectBackground(1)
            countList[1] -> setCorrectBackground(2)
            countList[2] -> setCorrectBackground(3)
            countList[3] -> setCorrectBackground(4)
        }
        //  Show next button
        btnNext.visibility = View.VISIBLE
        btnNext.setOnClickListener {
            when (mode){
                0 -> if (questionNumber1 >= questionNumber2){
                    finishPage()
                }else{
                    questionNumber1 += 1
                    recreate()
                }
                1 -> finishPage(0)
                2 -> finishPage()
            }
        }
    }

    //  Function called when you hit correct answer
    private fun correctOption(button: Button){
        //  Set background to green
        button.setBackgroundResource(background_correct_question)
        //  Show next button
        btnNext.visibility = View.VISIBLE
        btnNext.setOnClickListener{
            when (mode){
                0 -> {
                    points += 1
                    if (questionNumber1 == questionNumber2){
                        finishPage()
                    }else{
                        questionNumber1 += 1
                        recreate()
                    }
                }
                1 -> {
                    questionNumber1 -= 1
                    if (questionNumber1 == 0) finishPage(1) else recreate()
                }
                2 -> {
                    questionNumber1 += 1
                    if (questionsList.size > 0) recreate() else finishPage()
                }
            }
        }
    }

    //  Converting image from byte array to bitmap
    private fun getImage(image:ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image,0,image.size)
    }
}
