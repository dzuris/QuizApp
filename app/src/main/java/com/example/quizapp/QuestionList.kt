package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ExpandableListView
import com.example.quizapp.adapters.ExpendableListAdapter
import com.example.quizapp.database.DatabaseHelper

class QuestionList : AppCompatActivity() {

    private var listView: ExpandableListView? = null
    private var listAdapter: ExpendableListAdapter? = null
    private var listHash: HashMap<CCategory, ArrayList<CQuestion>>? = null
    private var db: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)

        title = getString(R.string.QuestionList)
        db = DatabaseHelper(this)

        listHash = HashMap()
        val listOfCategories = db!!.allCategories

        for (element in listOfCategories){
            val list = db!!.getQuestionsByType(element.id)
            listHash!![element] = list
        }

        listView = findViewById(R.id.lvExp)
        listAdapter = ExpendableListAdapter(this, listOfCategories, listHash!!)
        listView!!.setAdapter(listAdapter)
    }
}
