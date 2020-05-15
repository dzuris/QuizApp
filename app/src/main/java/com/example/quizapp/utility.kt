package com.example.quizapp

var questionsList = ArrayList<CQuestion>()
var helpQuestion: CQuestion? = null
var helpImage: ByteArray? = null
var helpIcon: ByteArray? = null

var newCategoryName = ""
var newCategoryImageSelect = true

val SHARED_PREFS = "sharedPrefs"
val NUMBER_OF_QUESTIONS:String = "countData"

//  Used only with death match mode
var result: Int = 0

var index = 0 //used in question activity

var questionNumber1 = 5
var questionNumber2 = 1
var points = 0

/**
 * 0 - normal
 * 1 - deathmatch
 * 2 - run
 */

var mode = 0

const val maxQuestionLength = 225
const val maxOptionLength = 120