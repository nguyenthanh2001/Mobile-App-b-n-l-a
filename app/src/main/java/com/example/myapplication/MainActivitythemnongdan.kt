package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.math.cos

class MainActivitythemnongdan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitythemnongdan)
        var name=findViewById<EditText>(R.id.edname)
        var cost=findViewById<EditText>(R.id.edcost)
        var sdt=findViewById<EditText>(R.id.edsdt)
        var btnluu=findViewById<Button>(R.id.btnluu)
        var btnquaylai=findViewById<Button>(R.id.btnquaylai)
        //
        var intent=intent
        var id=intent.getStringExtra("idd")
        //
        btnquaylai.setOnClickListener({
            val intent: Intent = Intent(this,MainActivity::class.java)
            intent.putExtra("reload",1)
            intent.putExtra("idz",id)
            startActivity(intent)
        })
        //
        var helper = DB(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("select * from user",null)
        //
        btnluu.setOnClickListener {
            val ten = name.text.toString().trim()
            val gia = cost.text.toString().trim()
            val sodienthoai = sdt.text.toString().trim()

            if (ten.isEmpty() || gia.isEmpty() || sodienthoai.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val cv = ContentValues().apply {
                put("ten", ten)
                put("giamua", gia)
                put("sobao", 0)
                put("khoiluong", 0)
                put("thanhtien", 0)
                put("tiencoc", 0)
                put("datra", 0)
                put("conlai", 0)
                put("tapchat", 0)
                put("klconlai", 0)
                put("sodienthoai", sodienthoai)
                put("user_id", id);
            }

            db.insert("user", null, cv)
            rs.requery()
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show()
        }


    }
}