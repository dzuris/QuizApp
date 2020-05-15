package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.quizapp.adapters.DeleteCategoryAdapter
import com.example.quizapp.database.DatabaseHelper

class DeleteCategory : AppCompatActivity() {

    private var db: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_category)

        title = getString(R.string.DeleteCategory)
        db = DatabaseHelper(this)

        val listView = findViewById<ListView>(R.id.listView_categories)

        val arrayAdapter = DeleteCategoryAdapter(this, db!!.allCategories)
        listView.adapter = arrayAdapter
    }
}
