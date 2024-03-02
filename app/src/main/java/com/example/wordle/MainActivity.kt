package com.example.wordle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val maxTries = 4
    private var tries = maxTries

    /**
     * Where the guessing stuff happens. Will later change tihs to a menu so i can have two
     * wordle versions if I can speed run this with enough monster energy drinks and
     * ecchi mangas
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var whatToGuess = FourLetterWord.getRandomFourLetterWord()
        val guessText =
            findViewById<TextView>(R.id.guess) // The stuff I'm guessing, that will appear
        val checkText = findViewById<TextView>(R.id.check) // The check I do
        val answerText = findViewById<TextView>(R.id.answer) // The answer, hidden basically
        val guessAttemptIndicator =
            findViewById<TextView>(R.id.remainingTries) // Something that shows how many tries you have left
        val guessBtn =
            findViewById<Button>(R.id.attemptedGuess) // The button that will trigger the guessing thing

        guessAttemptIndicator.text = tries.toString() // set this at the beginning or whatever

        guessBtn.setOnClickListener {
          // TODO: Change this text thing so it onl takes 4 letters and you can edit all 4 letters

            val attemptedGuessTxt =
                findViewById<EditText>(R.id.guessInput).text.toString().uppercase()

            val checkPlz = checkAnswer(whatToGuess, attemptedGuessTxt)
            guessText.text =
                StringBuilder(guessText.text).append("Guess: $attemptedGuessTxt\n")
                    .toString()

            val btnMode = guessBtn.text;

            if (btnMode == getString(R.string.guessSubmissionBtnText)) {
                if (tries == 0) {
                    guessAttemptIndicator.text = "💀"
                    Toast.makeText(this, "No more tries. Skill issue bro", Toast.LENGTH_SHORT)
                        .show()
                    guessBtn.text = getString(R.string.RestartSubmissionBtnText)
                } else {
                    if (checkPlz == "OOOO") {
                        guessAttemptIndicator.text = "🍀"
                        answerText.text = "Correct!"
                        answerText.text = buildString {
                            append("The answer is: ")
                            append(whatToGuess)
                        }
                        guessBtn.text = getString(R.string.RestartSubmissionBtnText)
                    } else {
                        --tries
                        guessAttemptIndicator.text = tries.toString()
                    }
                }
            } else if (btnMode == getString(R.string.RestartSubmissionBtnText)) {
                tries = maxTries
                guessAttemptIndicator.text = tries.toString()
                answerText.text = ""
                checkText.text = ""
                whatToGuess = FourLetterWord.getRandomFourLetterWord() // Get a new word to guess
                guessText.text = "" // Clear the previous guessed text
                guessBtn.text = getString(R.string.guessSubmissionBtnText)
            }
        }
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