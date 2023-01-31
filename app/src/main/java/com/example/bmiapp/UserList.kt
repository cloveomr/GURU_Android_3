package com.example.bmiapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView

class UserList : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        dbManager = DBManager(this, "userDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        layout = findViewById(R.id.user)

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM user", null)

        var num: Int = 0
        while (cursor.moveToNext()){

            var str_name = cursor.getString(cursor.getColumnIndex("name")).toString()
            var age = cursor.getInt(cursor.getColumnIndex("age"))
            var wei = cursor.getInt(cursor.getColumnIndex("wei"))
            var hei = cursor.getInt(cursor.getColumnIndex("hei"))
            var bmi = cursor.getInt(cursor.getColumnIndex("bmi"))

            var layout_item: LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var tvName: TextView = TextView(this)
            tvName.text = "\n" + "   " + str_name
            tvName.textSize = 23f
            layout_item.addView(tvName)

            var tvAge: TextView = TextView(this)
            layout_item.addView(tvAge)

            var tvHei: TextView = TextView(this)
            tvHei.text = "     " + age.toString() + " 세(만)  |  " + hei.toString() + " cm  |  " + wei.toString() + " kg  |  " + bmi.toString() + " kg/㎡"
            layout_item.addView(tvHei)

            var tvWei: TextView = TextView(this)
            layout_item.addView(tvWei)

            var tvBmi: TextView = TextView(this)
            tvBmi.text = "     __________________________________________________________"
            layout_item.addView(tvBmi)

            layout_item.setOnClickListener {
                val intent = Intent(this, UserInfo::class.java)
                intent.putExtra("intent_name", str_name)
                startActivity(intent)
            }

            layout.addView(layout_item)
            num++;
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.act_bmi -> {
                val intent = Intent(this, MainActivity::class.java)
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