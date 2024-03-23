package com.example.wordle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WordleKeyboard : AppCompatActivity() {

//    interface OnAlphabetButtonClickListener {
//        fun onAlphabetButtonClicked(letter: Char)
//    }
//
//    private var listener: OnAlphabetButtonClickListener? = null
//
//    fun setOnAlphabetButtonClickListener(listener: OnAlphabetButtonClickListener) {
//        this.listener = listener
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.keyboard)

        val alphabet = ('A'..'Z')

        for (letter in alphabet) {
            val buttonId = resources.getIdentifier("button$letter", "id", packageName)
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener {
                Log.v("KEWB", button.text.toString())
//                listener?.onAlphabetButtonClicked(letter)
            }
        }
    }
}
