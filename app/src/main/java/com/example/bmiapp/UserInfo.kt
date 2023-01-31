package com.example.bmiapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class UserInfo : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var tvName: TextView
    lateinit var tvAge: TextView
    lateinit var tvWei: TextView
    lateinit var tvHei: TextView
    lateinit var tvBmi: TextView

    lateinit var str_name: String
    var age: Int = 0
    var wei: Int = 0
    var hei: Int = 0
    var bmi: Int = 0

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        tvName = findViewById(R.id.edtName)
        tvAge = findViewById(R.id.edtAge)
        tvWei = findViewById(R.id.edtWei)
        tvHei = findViewById(R.id.edtHei)
        tvBmi = findViewById(R.id.edtBmi)

        val intent = intent
        str_name = intent.getStringExtra("intent_name").toString()

        dbManager = DBManager(this, "userDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM user WHERE name = '"
                + str_name + "';", null)

        if(cursor.moveToNext()){
            age = cursor.getInt(cursor.getColumnIndex("age"))
            wei = cursor.getInt(cursor.getColumnIndex("wei"))
            hei = cursor.getInt(cursor.getColumnIndex("hei"))
            bmi = cursor.getInt(cursor.getColumnIndex("bmi"))
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        tvName.text = str_name
        tvAge.text = "" + age
        tvWei.text = "" + wei
        tvHei.text = "" + hei
        tvBmi.text = "" + bmi +"\n"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_info, menu)
        return true
    }

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
            R.id.act_del -> {

                dbManager = DBManager(this, "userDB", null, 1)
                sqlitedb = dbManager.readableDatabase

                sqlitedb.execSQL("DELETE FROM user WHERE name = '" + str_name + "';")
                sqlitedb.close()
                dbManager.close()

                val intent = Intent(this, UserList::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}