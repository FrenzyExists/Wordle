package com.example.wordle

import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val maxTries = 4
    private var tries = maxTries
    private val keyboardEditTexts = ArrayList<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: MainActivity

        setContentView(R.layout.activity_main)

//        val relativeWordle = findViewById<RelativeLayout>(R.id.relativeWordle)
//        createWordleGrid(this, relativeWordle, 4, 4)

//        val alphabet = ('A'..'Z')


//        for (i in 0 until 4) {
//            for (j in 0 until 4) {
//                val resourceId = resources.getIdentifier("edt_${i}${j}", "id", packageName)
//                val editText = findViewById<EditText>(resourceId)
//                keyboardEditTexts.add(editText)
//            }
//        }

//        for (i in 0 until 4*2-1) {
//            passFocusToNextEdt(keyboardEditTexts[i], keyboardEditTexts[i+1])

//        }

//        for (letter in alphabet) {
//            val buttonId = resources.getIdentifier("button$letter", "id", packageName)
//            val button = findViewById<TextView>(buttonId)
//            button.setOnClickListener {
//                for (i in 0 until 4*2-1) {
//                    Log.v("KEWB", button.text.toString())
//                    wordleCellThing(keyboardEditTexts[i], button.text.toString())
//                }
//            }
//        }











        var whatToGuess = FourLetterWord.getRandomFourLetterWord()
//        val guessText =
//            findViewById<TextView>(R.id.guess) // The stuff I'm guessing, that will appear
//        val checkText = findViewById<TextView>(R.id.check) // The check I do
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

            if (attemptedGuessTxt.isEmpty()) {
                return@setOnClickListener
            }

            val checkPlz = checkAnswer(whatToGuess, attemptedGuessTxt)
//            guessText.text =
//                StringBuilder(guessText.text).append("Guess: $attemptedGuessTxt\n")
//                    .toString()
//
//            checkText.text = colorThing(attemptedGuessTxt, checkPlz, "Check: ", checkText.text)

            val tempAns = StringBuilder()

            val oldAttempt = answerText.text.toString()

            for (i in 0 until 4) {
                tempAns.append(
                    if (checkPlz[i] == 'O') attemptedGuessTxt[i]
                    else if (attemptedGuessTxt[i] == '_') '_'
                    else if (oldAttempt.isNotEmpty()) oldAttempt[i]
                    else '_'
                )
            }
            answerText.text = tempAns.toString()

            val btnMode = guessBtn.text;

            if (btnMode == getString(R.string.guessSubmissionBtnText)) {
                if (tries == 1) {
                    guessAttemptIndicator.text = "💀"
                    Toast.makeText(this, "No more tries. Skill issue bro", Toast.LENGTH_SHORT)
                        .show()
                    guessBtn.text = getString(R.string.RestartSubmissionBtnText)
                    answerText.text = whatToGuess
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
                        guessAttemptIndicator.text = (tries).toString()
                    }
                }
            } else if (btnMode == getString(R.string.RestartSubmissionBtnText)) {
                tries = maxTries
                guessAttemptIndicator.text = tries.toString()
                answerText.text = ""
//                checkText.text = ""
//                whatToGuess = FourLetterWord.getRandomFourLetterWord() // Get a new word to guess
//                guessText.text = "" // Clear the previous guessed text
                guessBtn.text = getString(R.string.guessSubmissionBtnText)
            }
        }
    }

    private fun colorThing(
        guess: String,
        check: String,
        connector: String,
        prev: CharSequence = ""
    ): SpannableStringBuilder {
        val spannableStringBuilder = SpannableStringBuilder()
        spannableStringBuilder.append(prev)
        spannableStringBuilder.append(connector)

        if (guess.isEmpty()) {
            return spannableStringBuilder
        }
        var colorResId: Int = R.color.gui02
        for (i in guess.indices) {
            val g = guess[i]
            when (check[i]) {
                'X' -> {
                    colorResId = R.color.gui08
                }

                'O' -> {
                    colorResId = R.color.gui0C
                }

                '+' -> {
                    colorResId = R.color.gui09
                }
            }
            val color = ContextCompat.getColor(this, colorResId)
            val spannableString = SpannableString(g.toString())
            spannableString.setSpan(
                ForegroundColorSpan(color),
                0,
                1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableStringBuilder.append(spannableString)
        }
        spannableStringBuilder.append("\n")
        return spannableStringBuilder
    }

    private fun passFocusToNextEdt(edt1: EditText, edt2: EditText) {
        // on below line we are passing focus to
        // next edt is previous one is filled.
        edt1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    edt2.requestFocus()
                }
            }

        })
    }

    private  fun wordleCellThing(cell: EditText, letter: String) {
        cell.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Log.v("FOCUS", "HAS FOCUS: $letter")
                cell.text = Editable.Factory.getInstance().newEditable(letter)
            } else {
                // Handle the case when focus is lost
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

    private fun createWordleGrid(context: Context, parentLayout: RelativeLayout, numRows: Int, numCols: Int) {
        val cellSize = 70 // Change this to adjust cell size
        val margin = 4 // Change this to adjust margin

        val parentParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        parentLayout.layoutParams = parentParams

        for (i in 0 until numRows) {
            val linearLayout = LinearLayout(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    cellSize
                )
                orientation = LinearLayout.HORIZONTAL
                weightSum = numCols.toFloat()
            }

            for (j in 0 until numCols) {
                val editText = EditText(context).apply {
                    id = View.generateViewId()
                    background = ContextCompat.getDrawable(context, R.drawable.cells)
                    isFocusable = true
                    isFocusableInTouchMode = true
                    gravity = Gravity.CENTER
                    inputType = InputType.TYPE_CLASS_TEXT
                    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(1))
                    textSize = 24f
                    setTypeface(typeface, Typeface.BOLD)
                }
                linearLayout.addView(editText)
            }

            parentLayout.addView(linearLayout)
        }
    }



}