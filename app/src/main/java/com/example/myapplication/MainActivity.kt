package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.io.File

class MainActivity : AppCompatActivity() {
    var dem = 0
    var vlz=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        var intent=intent
        dem=intent.getIntExtra("reload",0)
        //
        var lv=findViewById<ListView>(R.id.lv)
        var searchview=findViewById<SearchView>(R.id.searchview)
        //
//        var adapter = SimpleCursorAdapter(applicationContext,android.R.layout.simple_expandable_list_item_2,rs,
//            arrayOf("ten","giamua"),
//            intArrayOf(android.R.id.text1,android.R.id.text2),0)
        var intentt=intent
        var id=intentt.getStringExtra("idz")
        vlz=id.toString()
        //Toast.makeText(this,id.toString(),Toast.LENGTH_LONG).show()
        //
        var helper = DB(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT user.* FROM user\n" +
                "JOIN nhom ON user.user_id = nhom._id\n" +
                "WHERE nhom._id = $id;\n",null)
        var adapter = SimpleCursorAdapter(applicationContext,R.layout.custommain,rs,
            arrayOf("ten","giamua","sodienthoai","klconlai","sobao","thanhtien","tiencoc","datra","conlai"),
            intArrayOf(R.id.tvten,R.id.tvgiamua,R.id.tvsdt,R.id.tvkl,R.id.tvsobao,R.id.tvtienlucdau,R.id.tvtiendatcoc,R.id.tvtiendatra,R.id.tvtiencantra),0)
        //
        var cursor = db.rawQuery("SELECT sum(user.klconlai), sum(user.sobao) FROM user " +
                "JOIN nhom ON user.user_id = nhom._id " +
                "WHERE nhom._id = $id", null)
        if (cursor != null && cursor.moveToFirst()) {
            val nhomSum = cursor.getString(0)
            val sobaoSum = cursor.getString(1)
            if (nhomSum != null && sobaoSum != null) {
                val cv = ContentValues().apply {
                    put("tongkl", nhomSum)
                    put("tongbao", sobaoSum)
                }
                db.update("nhom", cv, "_id = $id", null)
            } else {
                // Handle the case where nhomSum or sobaoSum is null
            }
        }
        cursor?.close()


        //

        lv.adapter=adapter
        if(dem==1){
            rs.requery()
            adapter.notifyDataSetChanged()
        }
        //
        searchview.queryHint="Hiện có ${rs.count} hộ dân"
        //
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                rs=db.rawQuery("select * from user where ten like '%$p0%' OR sodienthoai like '%$p0%'",null)
                adapter.changeCursor(rs)
                return false
            }
        })
        //

        lv.setOnItemClickListener { adapterView, view, i, l ->
            var myArray = arrayOf(rs.getString(0))
            //Log.d("vt",myArray[0])
            val intent: Intent = Intent(this,MainActivitytinhtoan::class.java)
            intent.putExtra("id",myArray[0])
            intent.putExtra("idd",vlz)
            startActivity(intent)
        }
        lv.setOnItemLongClickListener { adapterView, view, i, l ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Bạn có chắc muốn xóa [" +rs.getString(1)+"]")
            builder.setMessage("Sau khi xóa sẽ không thể khôi phục lại được")

            builder.setPositiveButton("Xóa"){dialog, which ->
                db.delete("user","_id=?",arrayOf(rs.getString(0)))
                rs.requery()
                adapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("Quay lại"){dialog, which ->
                // Perform action on click
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
            return@setOnItemLongClickListener(true)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_button -> {
                true
            }
            R.id.btnquaylai -> {
                val intent: Intent =Intent(this,MainActivitytrangchu::class.java)
                startActivity(intent)
                true
            }
            R.id.btnthem -> {
                dem=0
                val intent: Intent =Intent(this,MainActivitythemnongdan::class.java)
                intent.putExtra("idd",vlz)
                startActivity(intent)
                true
            }
            R.id.btnmail ->{
                val recipient = "recipient@example.com"
                val subject = "Subject"
                val helper = DB(applicationContext)
                val db = helper.readableDatabase
                val bodyBuilder = StringBuilder()
                var c = 0
                db.rawQuery("SELECT COUNT(*) FROM user WHERE user.user_id = $vlz;", null).use { rs ->
                    rs.moveToFirst()
                    c = rs.getInt(0)
                }
                bodyBuilder.append("Tổng cộng có $c hộ dân\n")
                bodyBuilder.append("---------------------------\n")
                var rs = db.rawQuery("SELECT user.* FROM user\n" +
                        "JOIN nhom ON user.user_id = nhom._id\n" +
                        "WHERE nhom._id = $vlz;\n",null).use { rs ->
                    while (rs.moveToNext()) {
                        bodyBuilder.append("Tên: ${rs.getString(1)}\n")
                        bodyBuilder.append("Giá mua: ${rs.getString(2)}\n")
                        bodyBuilder.append("Số bao: ${rs.getString(3)}\n")
                        bodyBuilder.append("Khối lượng: ${rs.getString(4)}\n")
                        bodyBuilder.append("Thành tiền: ${rs.getString(5)}\n")
                        bodyBuilder.append("Tiền cọc: ${rs.getString(6)}\n")
                        bodyBuilder.append("Đã trả: ${rs.getString(7)}\n")
                        bodyBuilder.append("Còn lại: ${rs.getString(8)}\n")
                        bodyBuilder.append("Tạp chất: ${rs.getString(9)}\n")
                        bodyBuilder.append("Khối lượng sau khi trừ tạp chất: ${rs.getString(10)}\n")
                        bodyBuilder.append("Số điện thoại: ${rs.getString(11)}\n")
                        bodyBuilder.append("---------------------------\n")
                    }
                }
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "plain/text"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                emailIntent.putExtra(Intent.EXTRA_TEXT, bodyBuilder.toString())
                startActivity(Intent.createChooser(emailIntent, "Send email..."))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}