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
import com.example.quizapp.*
import java.io.ByteArrayOutputStream

class ImagesListAdapter(private var context: Activity, private var images1: ArrayList<Int>) : BaseAdapter() {
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //  Setting row view
        val rowView = context.layoutInflater.inflate(R.layout.layout_images_list, null)

        //  Setting widget and value
        val image = rowView.findViewById<ImageView>(R.id.iv_imagesList_layout)
        image.setImageResource(getItem(position))

        //  On click save image to help value, start again add category
        rowView.setOnClickListener {
            if (newCategoryImageSelect){
                helpImage = imageToByteArray(getItem(position))
            }else{
                helpIcon = imageToByteArray(getItem(position))
            }
            context.finish()
            context.startActivity(Intent(context, AddCategory::class.java))
        }

        return rowView
    }

    override fun getItem(position: Int): Int {
        return images1[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return images1.size
    }

    private fun imageToByteArray(image: Int): ByteArray{
        val icon = BitmapFactory.decodeResource(context.resources, image)

        val stream = ByteArrayOutputStream()
        icon.compress(Bitmap.CompressFormat.PNG,0,stream)
        return stream.toByteArray()
    }
}