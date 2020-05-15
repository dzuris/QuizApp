package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.quizapp.adapters.CategoriesAdapter
import com.example.quizapp.database.DatabaseHelper

class PlayCategory : AppCompatActivity() {

    private var db: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_category)

        title = getString(R.string.Categories)
        db = DatabaseHelper(this)

        val listView = findViewById<ListView>(R.id.list_categories)

        val arrayAdapter = CategoriesAdapter(this, db!!.allCategories)
        listView.adapter = arrayAdapter
    }
}
