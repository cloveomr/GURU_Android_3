package com.example.bmiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class BMIBMRInput : AppCompatActivity() {
    lateinit var OKButton : Button
    lateinit var NOButton : Button

    lateinit var heightEditText: EditText
    lateinit var weightEditText: EditText
    lateinit var ageEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmibmrinput)

        heightEditText = findViewById<EditText>(R.id.heightText)
        weightEditText = findViewById<EditText>(R.id.weightText)
        ageEditText = findViewById<EditText>(R.id.ageText)
        OKButton = findViewById<Button>(R.id.OKbutton)
        NOButton = findViewById<Button>(R.id.NObutton)

        //돌아갈 때 화면 전환
        OKButton.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("height", heightEditText.text.toString())
            intent.putExtra("weight", weightEditText.text.toString())
            intent.putExtra("age", ageEditText.text.toString())
            setResult(RESULT_OK, intent)
            if (!isFinishing) finish()
        }

        NOButton.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}