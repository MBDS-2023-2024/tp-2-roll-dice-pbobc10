package com.mbds.haiti.diceroller

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var textViewDice1: TextView
    private lateinit var textViewDice2: TextView
    private lateinit var rollButton: Button
    private lateinit var editTextTarget: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewDice1 = findViewById(R.id.textView1)
        textViewDice2 = findViewById(R.id.textView2)
        rollButton = findViewById(R.id.button)
        editTextTarget = findViewById(R.id.editTextTarget)

        // TextWatcher to enable/disable the button based on EditText input
        editTextTarget.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if input is a valid number and within the valid range (2 to 12)
                val input = s.toString()
                val target = input.toIntOrNull()
                // Validate target range and show error if invalid
                if (target == null || target < 2 || target > 12) {
                    rollButton.isEnabled = false
                    editTextTarget.error = "Target must be between 2 and 12"
                } else {
                    rollButton.isEnabled = true
                    editTextTarget.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed
            }
        })

        rollButton.setOnClickListener { rollDice() }
    }

    private fun rollDice() {
        val dice1 = Dice(6)
        val diceRoll1 = dice1.roll()

        val dice2 = Dice(6)
        val diceRoll2 = dice2.roll()

        val target: Int

        // Get target value from EditText
        try {
            target = editTextTarget.text.toString().toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid target number", Toast.LENGTH_SHORT).show()
            return
        }

        val sum = diceRoll1 + diceRoll2

        textViewDice1.text = diceRoll1.toString()
        textViewDice2.text = diceRoll2.toString()

        if (sum == target) {
            Toast.makeText(this, "Congratulations! You win!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "You lose! Try again.", Toast.LENGTH_SHORT).show()
        }
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }

}