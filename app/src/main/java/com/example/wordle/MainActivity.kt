package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun checkAnswer(answer: String, guess: String): String {
        val result = CharArray(answer.length) { '*' } // Initialize result array with '*'
        val charCountMap = answer.groupingBy { it }.eachCount().toMutableMap()

        // Check for characters that match in both position and value
        for (i in guess.indices) {
            if (answer[i] == guess[i]) {
                result[i] = 'O'
                charCountMap[guess[i]] = charCountMap.getOrDefault(guess[i], 0) - 1
            }
        }

        // Check for characters that are in the answer but in the wrong position
        for (i in guess.indices) {
            if (result[i] != '*') continue
            val currentChar = guess[i]
            if (charCountMap.getOrDefault(currentChar, 0) > 0) {
                result[i] = '+'
                charCountMap[currentChar] = charCountMap.getOrDefault(currentChar, 0) - 1
            } else {
                result[i] = 'X'
            }
        }

        return String(result)
    }

//    private fun checkAnswer(answer: String, guess: String): String {
//        val result = charArrayOf('*', '*', '*', '*') // * = unprocessed
//        val dict = mutableMapOf<Char, Int>()
//        for (ch in answer){
//            dict[ch] = dict.getOrDefault(ch, 0) + 1
//        }
//        // in first pass, check for chars that match and put O in places they match
//        for (i in guess.indices){
//            val g = guess[i]
//            if (answer[i] == g){
//                result[i] = 'O'
//                dict[g] = dict.getOrDefault(g, 0) - 1
//                if (dict.getOrDefault(g, 0) == 0){
//                    dict.remove(g)
//                }
//            }
//        }
//        // in second pass, check for the wrong positions
//        for (i in guess.indices){
//            if (result[i] != '*')
//                continue
//            val g = guess[i]
//            if (dict.containsKey(g)){
//                result[i] = '+'
//                dict[g] = dict.getOrDefault(g, 0) - 1
//                if (dict.getOrDefault(g, 0) == 0){
//                    dict.remove(g)
//                }
//            }else{
//                result[i] = 'X'
//            }
//        }
//        return String(result)
//    }

}