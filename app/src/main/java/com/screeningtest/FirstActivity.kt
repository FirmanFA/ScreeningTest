package com.screeningtest

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val btnNext = findViewById<Button>(R.id.btnUser)
        val btnCheck = findViewById<Button>(R.id.btnCheck)
        val edName = findViewById<EditText>(R.id.edName)
        val edSentence = findViewById<EditText>(R.id.edSentence)
        btnNext.setOnClickListener {

            if (isFormValid(edName)){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name", edName.text.toString())
                startActivity(intent)
            }
        }

        btnCheck.setOnClickListener {

            val message = if (isPalindrome(edSentence.text.toString())){
                            "isPalindrome"
                        }else{
                            "not palindrome"
                        }
            showDialog(message)
        }

    }

    private fun showDialog(message:String){
        AlertDialog.Builder(this)
            .setTitle("Check if palindrome")
            .setMessage(message)
            .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->})
            .show()
    }

    private fun isPalindrome(sentence: String):Boolean{

        val filtered = sentence.replace("\\s".toRegex(), "")
        val reversed = filtered.reversed()
        return filtered == reversed

    }

    private fun isFormValid(name: EditText):Boolean{
        val valid = if (name.text.toString() == ""){
                        name.error = "Required"
                        false
                    }else{
                        true
                    }
        return valid
    }

}