package com.example.bmiapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var startButton: Button
    lateinit var bmiSeekBar: SeekBar
    lateinit var resultTextView: TextView
    lateinit var avrkcalTextView1: TextView
    lateinit var avrkcalTextView2: TextView
    lateinit var avrkcalTextView3: TextView

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bmiSeekBar = findViewById<SeekBar>(R.id.bmiSeekBar)
        startButton = findViewById<Button>(R.id.startButton)
        resultTextView = findViewById(R.id.resultTextView)
        avrkcalTextView1 = findViewById(R.id.avrkcalTextView1)
        avrkcalTextView2 = findViewById(R.id.avrkcalTextView2)
        avrkcalTextView3 = findViewById(R.id.avrkcalTextView3)

        // seekbar 터치 불가 코드 작성
        bmiSeekBar.setOnTouchListener { v, event -> true }

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    var weight = it.data?.getStringExtra("weight")!!.toInt()
                    var height = it.data?.getStringExtra("height")!!.toInt()
                    var age = it.data?.getStringExtra("age")!!.toInt()

                    //BMI 계산
                    var bmi = weight / Math.pow(height / 100.0, 2.0)

                    resultTextView.setText("%.1f".format(bmi))
                    bmiSeekBar.setProgress((bmi * 10).toInt())

                    var bmr_h = 447.593 + (9.25 * weight) + (3.10 * height) - (4.33 * age)
                    var bmr_m = (10.0 * weight) + (6.25 * height) - (5.0 * age) - 161
                    var bmr_avr = (bmr_h + bmr_m) / 2

                    avrkcalTextView2.setText("%.1f".format(bmr_h) + " 칼로리/일")
                    avrkcalTextView3.setText("%.1f".format(bmr_m) + " 칼로리/일")
                    avrkcalTextView1.setText("%.1f".format(bmr_avr) + " 칼로리/일")

                }
            }

        startButton.setOnClickListener {
            val intent = Intent(this, BMIBMRInput::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //menu 설정 코드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.act_list -> {
                val intent = Intent(this, UserList::class.java)
                startActivity(intent)
                return true
            }
            R.id.act_add -> {
                val intent = Intent(this, UserAdd::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
