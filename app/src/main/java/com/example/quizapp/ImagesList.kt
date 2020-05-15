package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import com.example.quizapp.adapters.ImagesListAdapter

class ImagesList : AppCompatActivity() {

    private var gridView: GridView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_list)

        val listOfImages = arrayListOf(
            R.drawable.person_another,
            R.drawable.person_geography,
            R.drawable.person_sport,
            R.drawable.person_history,
            R.drawable.person_logic,
            R.drawable.person_animal,
            R.drawable.person_flag,
            R.drawable.icon_bee,
            R.drawable.icon_boat,
            R.drawable.icon_brain,
            R.drawable.icon_flag_cake,
            R.drawable.icon_map,
            R.drawable.icon_pen,
            R.drawable.icon_weight,
            R.drawable.image_none
        )

        gridView = findViewById(R.id.gridView_imagesList)
        gridView!!.adapter = ImagesListAdapter(this, listOfImages)
    }
}
