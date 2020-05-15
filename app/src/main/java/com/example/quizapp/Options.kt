package com.example.quizapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import java.io.ByteArrayOutputStream

class Options : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        title = getString(R.string.Options)

        val listView = findViewById<ListView>(R.id.list_options)

        val arrayList = ArrayList<String>()

        arrayList.add(getString(R.string.QuestionList))
        arrayList.add(getString(R.string.AddQuestion))
        arrayList.add(getString(R.string.AddCategory))
        arrayList.add(getString(R.string.DeleteCategory))
        arrayList.add(getString(R.string.ChangeQuestionsCount))

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)

        listView.adapter = arrayAdapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, l->
            when (arrayList[i]) {
                getString(R.string.QuestionList) -> {
                    startActivity(Intent(this, QuestionList::class.java))
                }
                getString(R.string.AddQuestion) -> {
                    startActivity(Intent(this, AddQuestion::class.java))
                }
                getString(R.string.AddCategory) -> {
                    newCategoryName = ""
                    newCategoryImageSelect = true
                    helpImage = defaultImage()
                    helpIcon = defaultImage()
                    startActivity(Intent(this, AddCategory::class.java))
                }
                getString(R.string.DeleteCategory) -> {
                    startActivity(Intent(this, DeleteCategory::class.java))
                }
                getString(R.string.ChangeQuestionsCount) -> {
                    startActivity(Intent(this, ChangeQuestionCount::class.java))
                }
            }
        }
    }

    private fun defaultImage(): ByteArray{
        val icon = BitmapFactory.decodeResource(this.resources, R.drawable.image_none)

        val stream = ByteArrayOutputStream()
        icon.compress(Bitmap.CompressFormat.PNG,0,stream)
        return stream.toByteArray()
    }
}
