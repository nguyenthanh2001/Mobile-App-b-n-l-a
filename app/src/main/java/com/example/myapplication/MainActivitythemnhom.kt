package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivitythemnhom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitythemnhom)
        var name=findViewById<EditText>(R.id.edname)
        var cost=findViewById<EditText>(R.id.edcost)
        var sdt=findViewById<EditText>(R.id.edsdt)
        var btnluu=findViewById<Button>(R.id.btnluu)
        var btnquaylai=findViewById<Button>(R.id.btnquaylai)
        btnquaylai.setOnClickListener({
            val intent: Intent = Intent(this,MainActivitytrangchu::class.java)
            intent.putExtra("reload",1)
            startActivity(intent)
        })
        var helper = DB(applicationContext)
        var db = helper.readableDatabase
//        var rs = db.rawQuery("select * from my_table",null)
        btnluu.setOnClickListener {
            val ten = name.text.toString().trim()
            val gia = cost.text.toString().trim()
            val sodienthoai = sdt.text.toString().trim()

            if (ten.isEmpty() || gia.isEmpty() || sodienthoai.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val cv = ContentValues().apply {
                put("tennhom", ten)
                put("ngaymua", gia)
                put("sdt", sodienthoai)
                put("tongkl", "")
                put("tongbao", "")
            }

            db.insert("nhom", null, cv)
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show()
        }
    }
}