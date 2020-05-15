package com.example.quizapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.quizapp.CCategory
import com.example.quizapp.database.DatabaseHelper
import com.example.quizapp.R

class DeleteCategoryAdapter(val context: Activity, private val categories1: ArrayList<CCategory>) : BaseAdapter() {

    private val db = DatabaseHelper(context)

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //  Setting rowView
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.layout_list_play_category, null)
        //  Setting widget
        val image = rowView.findViewById<ImageView>(R.id.ivLayout_playCategory)
        val tvName = rowView.findViewById<TextView>(R.id.tvLayout_playCategory)

        //  Setting widget values
        image.setImageBitmap(getImage(getItem(position).image))
        tvName.text = getItem(position).name

        //  On click start alert dialog, you must confirm deleting category, delete all questions same category
        rowView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.DeleteCheckTitle))
            builder.setMessage(context.getString(R.string.DeleteCheckMessage) + getItem(position).name)
            builder.setPositiveButton(context.getString(R.string.Yes)){ _, _ ->
                val listOfQuestions = db.getQuestionsByType(getItem(position).id)
                for (element in listOfQuestions){
                    db.deleteQuestion(element.id)
                }
                db.deleteCategory(getItem(position).id)
                context.finish()
            }
            builder.setNegativeButton(context.getString(R.string.No)){ dialog, which ->

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
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