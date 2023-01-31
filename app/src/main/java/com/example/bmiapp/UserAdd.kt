package com.example.bmiapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText

class UserAdd : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var btnCancel: Button
    lateinit var btnAdd: Button
    lateinit var edtName: EditText
    lateinit var edtAge: EditText
    lateinit var edtWei: EditText
    lateinit var edtHei: EditText
    lateinit var edtBmi: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_add)

        btnCancel = findViewById(R.id.btnCancel)
        btnAdd = findViewById(R.id.btnAdd)
        edtName = findViewById(R.id.edtName)
        edtAge = findViewById(R.id.edtAge)
        edtWei = findViewById(R.id.edtWei)
        edtHei = findViewById(R.id.edtHei)
        edtBmi = findViewById(R.id.edtBmi)

        dbManager = DBManager(this, "userDB", null, 1)

        btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnAdd.setOnClickListener {
            var str_name: String = edtName.text.toString()
            var str_age: String = edtAge.text.toString()
            var str_hei: String = edtHei.text.toString()
            var str_wei: String = edtWei.text.toString()
            var str_bmi: String = edtBmi.text.toString()

            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO user VALUES ('"
                    + str_name + "', " + str_age + ", '"
                    + str_hei + "', '" + str_wei + "', '" + str_bmi + "')")
            sqlitedb.close()

            val intent = Intent(this, UserInfo::class.java)
            intent.putExtra("intent_name", str_name)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.act_list -> {
                val intent = Intent(this, UserList::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}