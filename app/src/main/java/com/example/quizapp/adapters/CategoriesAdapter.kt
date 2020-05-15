package com.example.quizapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.CCategory
import com.example.quizapp.Pop
import com.example.quizapp.database.DatabaseHelper
import com.example.quizapp.R
import com.example.quizapp.questionsList

class CategoriesAdapter(val context: Activity, val categories1: ArrayList<CCategory>) : BaseAdapter() {

    private val db = DatabaseHelper(context)

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //  Setting rowView
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.layout_list_play_category, null)
        //  Setting widgets
        val tvTitle = rowView.findViewById<TextView>(R.id.tvLayout_playCategory)
        val imCategory = rowView.findViewById<ImageView>(R.id.ivLayout_playCategory)
        //  Setting widget values, get category item
        val categoryItem = getItem(position)
        tvTitle.text = categoryItem.name
        imCategory.setImageBitmap(getImage(categoryItem.image))

        //  On click start Pop if category has some questions
        rowView.setOnClickListener {
            questionsList = db.getQuestionsByType(categoryItem.id)
            if (questionsList.size > 0){
                questionsList.shuffle()
                context.startActivity(Intent(context, Pop::class.java))
            }else{
                Toast.makeText(context, context.getString(R.string.WeDontHaveQuestionCategory), Toast.LENGTH_LONG).show()
            }
        }

        return rowView
    }

    override fun getItem(position: Int): CCategory {
        return categories1[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return categories1.size
    }

    private fun getImage(image:ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image,0,image.size)
    }
}