package com.example.quizapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.quizapp.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_add_category.*
import java.io.ByteArrayOutputStream

class AddCategory : AppCompatActivity() {

    private var db: DatabaseHelper? = null
    private val selectPhoto = 2222
    private var imgCategory: ImageView? = null
    private var iconCategory: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        title = getString(R.string.AddCategory)
        db = DatabaseHelper(this)

        //  Edit text, saving into newCategoryName on text changed
        val etTitle = findViewById<EditText>(R.id.editText_addCategory)
        etTitle.text = SpannableStringBuilder(newCategoryName)
        etTitle.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newCategoryName = p0.toString()
            }
        })

        //  Selecting between image and icon
        setImageSelect(newCategoryImageSelect)

        //  Setting image and icon Images
        imgCategory = findViewById<ImageView>(R.id.imageView_addCategory_image)
        imgCategory!!.setImageBitmap(getImage(helpImage!!))
        iconCategory = findViewById(R.id.imageView_addCategory_icon)
        iconCategory!!.setImageBitmap(getImage(helpIcon!!))

        //  Frame image selecting
        constraintLayout2.setOnClickListener {
            newCategoryImageSelect = true
            setImageSelect(newCategoryImageSelect)
        }

        //  Frame icon selecting
        constraintLayout3.setOnClickListener {
            newCategoryImageSelect = false
            setImageSelect(newCategoryImageSelect)
        }

        //  Select image from gallery
        val btnSelect = findViewById<Button>(R.id.btnSelect_addCategory)
        btnSelect.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            startActivityForResult(photoPicker,selectPhoto)
        }

        //  Select image from ImagesList
        val btnSelectImage = findViewById<Button>(R.id.btnSelectFromImages_addCategory)
        btnSelectImage.setOnClickListener {
            finish()
            startActivity(Intent(this, ImagesList::class.java))
        }

        //  Adding category to the database if category does not exist
        val btnAdd = findViewById<Button>(R.id.btnAdd_addCategory)
        btnAdd.setOnClickListener {
            val title = etTitle.text.toString()
            if (title.isNotEmpty()) {
                val bitmapImage = (imgCategory!!.drawable as BitmapDrawable).bitmap
                val bitmapIcon = (iconCategory!!.drawable as BitmapDrawable).bitmap
                val img = getBytes(bitmapImage)
                val icon = getBytes(bitmapIcon)
                for (element in db!!.allCategories) {
                    if (element.name == title) {
                        Toast.makeText(
                            this,
                            getString(R.string.ThisCategoryExist),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                }
                Toast.makeText(this, getString(R.string.CategWasAdd), Toast.LENGTH_SHORT).show()
                db!!.addCategory(title, img, icon)
            }else{
                Toast.makeText(this, getString(R.string.FillPole), Toast.LENGTH_LONG).show()
            }
        }
    }

    //  Frame image or icon selecting by status
    private fun setImageSelect(status: Boolean) {
        if (status) {
            constraintLayout2.setBackgroundResource(R.drawable.background_white_black)
            constraintLayout3.setBackgroundResource(R.color.colorInvisible)
        } else {
            constraintLayout3.setBackgroundResource(R.drawable.background_white_black)
            constraintLayout2.setBackgroundResource(R.color.colorInvisible)
        }
    }

    //  Byte array to Bitmap
    private fun getImage(image:ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image,0,image.size)
    }

    //  Bitmap to ByteArray
    private fun getBytes(bitmap: Bitmap):ByteArray
    {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream)
        return stream.toByteArray()
    }

    //  Select image from gallery, setting help Value
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == selectPhoto && resultCode == Activity.RESULT_OK && data != null)
        {
            val pickedImage = data.data
            if (newCategoryImageSelect) {
                imgCategory!!.setImageURI(pickedImage)
                val bitmap = (imgCategory!!.drawable as BitmapDrawable).bitmap
                helpImage = getBytes(bitmap)
            }else{
                iconCategory!!.setImageURI(pickedImage)
                val bitmap = (iconCategory!!.drawable as BitmapDrawable).bitmap
                helpIcon = getBytes(bitmap)
            }
        }
    }
}
