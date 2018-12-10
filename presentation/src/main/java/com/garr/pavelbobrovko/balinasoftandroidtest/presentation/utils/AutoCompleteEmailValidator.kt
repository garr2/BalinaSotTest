package com.garr.pavelbobrovko.balinasoftandroidtest.presentation.utils

import android.widget.AutoCompleteTextView
import java.util.*

class AutoCompleteEmailValidator(private val arrayList: Array<String>): AutoCompleteTextView.Validator {


    override fun fixText(invalidText: CharSequence?): CharSequence {
        return if (invalidText?.contains("@") == true){

            val fixableTextArr = parseText(invalidText.toString()).toCharArray()

            val validationArr = getMatchArr(fixableTextArr)

            val bestMatchPosition = getBestMatch(validationArr)

            if (bestMatchPosition > -1 ){ // Составляем исправленную строку
                val paths = invalidText.split("@")


                arrayList[bestMatchPosition]

            }else invalidText

        } else invalidText ?: ""
    }

    override fun isValid(text: CharSequence?): Boolean {
        return if (text?.contains("@") == true){
            Arrays.binarySearch(arrayList,text.toString())>0
        }else false
    }

    // Парсим строку возвращем все после знака @
    private fun parseText(invalidText: CharSequence): String{
        val paths = invalidText.split("@")
        val fixableText = paths[1]
        return fixableText.toLowerCase()
    }

    //Создаем массив совпадениий знаков
    private fun getMatchArr(fixableTextArr: CharArray): Array<Int>{
        val validationArr = Array(arrayList.size){0}

        for (possibleOptionsPos in 0 until  arrayList.size){
            val itemArr: CharArray = arrayList[possibleOptionsPos].split("@")[1].toCharArray()
            var matchCounter = 0
            for (fixableArrPos in 0 until fixableTextArr.size - 1){
                if (itemArr.size > fixableArrPos && fixableTextArr[fixableArrPos] == itemArr[fixableArrPos])
                    matchCounter++
            }
            validationArr[possibleOptionsPos] = matchCounter
        }
        return validationArr
    }

    //Выбираем строку с наибольшим совпадением знаков
    private fun getBestMatch(validationArr: Array<Int>): Int{
        var mostPossiblePos = 0
        var bestMatch = 0

        for (position in 0 until validationArr.size){
            if (bestMatch< validationArr[position]){
                bestMatch = validationArr[position]
                mostPossiblePos = position

            }
        }

        return if (bestMatch == 0){
            -1
        }else mostPossiblePos
    }

}