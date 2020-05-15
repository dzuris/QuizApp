package com.example.quizapp

class CQuestion(
    val id: Int,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionCorrect: String,
    val category: CCategory
)

class CCategory(
    val id: Int,
    val name: String,
    val image: ByteArray,
    val icon_image: ByteArray
)