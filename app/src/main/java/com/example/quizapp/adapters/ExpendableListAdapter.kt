package com.example.quizapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.quizapp.*

class ExpendableListAdapter(private val context: Context,
                            private val listDataHeader: List<CCategory>,
                            private val listHashMap: HashMap<CCategory, ArrayList<CQuestion>>) : BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int): CCategory {
        return listDataHeader[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    @SuppressLint("InflateParams")
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        //  Setting group view
        var view = convertView

        if (view == null) {
            val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_question_list_group, null)
        }
        //  Setting widgets
        val categoryItem = getGroup(groupPosition)
        val image = view!!.findViewById<ImageView>(R.id.iv_questionList_group)
        val tvName = view.findViewById<TextView>(R.id.tv_questionList_group)
        val tvCount = view.findViewById<TextView>(R.id.tv_questionListCount_group)
        //  Setting widgets values
        image.setImageBitmap(getImage(categoryItem.image))
        tvName.text = categoryItem.name
        tvCount.text = getChildrenCount(groupPosition).toString()

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listHashMap[listDataHeader[groupPosition]]!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): CQuestion {
        return listHashMap[listDataHeader[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        //  Setting view
        var view = convertView

        if (view == null) {
            val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.layout_question_list_child, null)
        }

        //  Setting widgets
        val questionItem = getChild(groupPosition, childPosition)
        val imageIcon = view!!.findViewById<ImageView>(R.id.iv_questionList_child)
        val tvQuestion = view.findViewById<TextView>(R.id.tv_questionList_child)

        //  Setting widgets values
        imageIcon.setImageBitmap(getImage(questionItem.category.icon_image))
        tvQuestion.text = questionItem.question

        //  On click get question and start Edit question
        view.setOnClickListener {
            helpQuestion = questionItem
            context.startActivity(Intent(context, EditQuestion::class.java))
        }

        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return listDataHeader.size
    }

    private fun getImage(image:ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image,0,image.size)
    }
}