package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AlertDialog

class MainActivitytrangchu : AppCompatActivity() {
    var dem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitytrangchu)
        //
        var intent=intent
        dem=intent.getIntExtra("reload",0)
        //
        var lv=findViewById<ListView>(R.id.lv)
        //
        var helper = DB(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("select * from nhom",null)
        var adapter = SimpleCursorAdapter(applicationContext,R.layout.customtrangchu,rs,
            arrayOf("tennhom","ngaymua","sdt","tongkl","tongbao"),
            intArrayOf(R.id.tvtennhom,R.id.tvngaymua,R.id.tvsdtt,R.id.tvtongkl,R.id.tvtongbao),0)

        lv.adapter=adapter
        if(dem==1){
            rs.requery()
            adapter.notifyDataSetChanged()
        }
        //
        lv.setOnItemClickListener { adapterView, view, i, l ->
            var myArray = arrayOf(rs.getString(0))
            val intent: Intent = Intent(this,MainActivity::class.java)
            intent.putExtra("idz",myArray[0])
            startActivity(intent)
        }
        lv.setOnItemLongClickListener { adapterView, view, i, l ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Bạn có chắc muốn xóa [" +rs.getString(1)+"]")
            builder.setMessage("Sau khi xóa sẽ không thể khôi phục lại được")

            builder.setPositiveButton("Xóa"){dialog, which ->
                db.delete("nhom","_id=?",arrayOf(rs.getString(0)))
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
        //
        //
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_trangchu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_button -> {
                true
            }
            R.id.btnthemnhom -> {
                val intent: Intent = Intent(this,MainActivitythemnhom::class.java)
                startActivity(intent)
                true
            }
            R.id.btnmoyoutube ->{
                val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/ajxBS4z3a2A"))
                startActivity(youtubeIntent)
                true
            }
            R.id.btnweb ->{
                val intent: Intent =Intent(this,MainActivityweb::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}