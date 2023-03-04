package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivitytinhtoan : AppCompatActivity() {
    var tongkl:Double = 0.0
    var sobao:Int=0
    var tapchat:Double=0.0
    var klconlai:Double=0.0
    var thanhtien:Double=0.0
    var dongia:Double=0.0
    var tiendatcoc:Double=0.0
    var tienconlai:Double=0.0
    var tiendatra:Double=0.0
    var add=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitytinhtoan)
        var edtennguoimua=findViewById<EditText>(R.id.edtennguoimua)
        var edtongkhoiluong=findViewById<EditText>(R.id.edtongkhoiluong)
        var edsobao=findViewById<EditText>(R.id.edsobao)
        var edtrutapchat=findViewById<EditText>(R.id.edtrutapchat)
        var edkhoiluongconlai=findViewById<EditText>(R.id.edkhoiluongconlai)
        var edgiathumua=findViewById<EditText>(R.id.edgiathumua)
        var edthanhtien=findViewById<EditText>(R.id.edthanhtien)
        var edtiendatcoc=findViewById<EditText>(R.id.edtiendatcoc)
        var edtiendatra=findViewById<EditText>(R.id.edtiendatra)
        var edtienconlai=findViewById<EditText>(R.id.edtienconlai)
        //
        var editText1=findViewById<EditText>(R.id.editText1)
        var editText2=findViewById<EditText>(R.id.editText2)
        var editText3=findViewById<EditText>(R.id.editText3)
        var editText4=findViewById<EditText>(R.id.editText4)
        var editText5=findViewById<EditText>(R.id.editText5)
        var editText6=findViewById<EditText>(R.id.editText6)
        var editText7=findViewById<EditText>(R.id.editText7)
        var editText8=findViewById<EditText>(R.id.editText8)
        var editText9=findViewById<EditText>(R.id.editText9)
        var editText10=findViewById<EditText>(R.id.editText10)
        var editText11=findViewById<EditText>(R.id.editText11)
        var editText12=findViewById<EditText>(R.id.editText12)
        var editText13=findViewById<EditText>(R.id.editText13)
        var editText14=findViewById<EditText>(R.id.editText14)
        var editText15=findViewById<EditText>(R.id.editText15)
        var editText16=findViewById<EditText>(R.id.editText16)
        var editText17=findViewById<EditText>(R.id.editText17)
        var editText18=findViewById<EditText>(R.id.editText18)
        var editText19=findViewById<EditText>(R.id.editText19)
        var editText20=findViewById<EditText>(R.id.editText20)
        var editText21=findViewById<EditText>(R.id.editText21)
        var editText22=findViewById<EditText>(R.id.editText22)
        var editText23=findViewById<EditText>(R.id.editText23)
        var editText24=findViewById<EditText>(R.id.editText24)
        var editText25=findViewById<EditText>(R.id.editText25)
        var btnnhap=findViewById<Button>(R.id.btnnhap)
        //
        var intent=intent
        var id=intent.getStringExtra("id")
        var idd=intent.getStringExtra("idd")
        add=idd.toString()
        //
        var helper = DB(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("select * from user where _id='$id'",null)
        //
        if (rs.moveToFirst()){
            val editTexts = arrayOf(edtennguoimua, edgiathumua, edsobao, edtongkhoiluong, edthanhtien, edtiendatcoc, edtiendatra, edtienconlai, edtrutapchat, edkhoiluongconlai)
            for (i in editTexts.indices) {
                editTexts[i].setText(rs.getString(i + 1))
            }
            // so bao
            var sobaotemp=rs.getString(3).toString()
            sobao = sobaotemp.toInt()
            // tong kl
            var tongkltemp=rs.getString(4).toString()
            tongkl=tongkltemp.toDouble()
            //tap chat
            var tapchattemp=rs.getString(9).toString()
            tapchat=tapchattemp.toDouble()
            //kl con lai
            var klconlaitemp=rs.getString(10).toString()
            klconlai=klconlaitemp.toDouble()
            //thanh tien
            var thanhtientemp=rs.getString(5).toString()
            thanhtien=thanhtientemp.toDouble()
            //don gia
            var dongiatemp=rs.getString(2).toString()
            dongia=dongiatemp.toDouble()
            // tien dat coc
            var tiendatcoctemp=rs.getString(6).toString()
            tiendatcoc=tiendatcoctemp.toDouble()
            //tien con lai
            var tienconlaitemp=rs.getString(8).toString()
            tienconlai=tienconlaitemp.toDouble()
            // tien da tra
            var tiendatratemp=rs.getString(7).toString()
            tiendatra=tiendatratemp.toDouble()
        }
        //su kien luu sau khi hoan thanh o tru tap chat
        edtrutapchat.setOnEditorActionListener { textView, i, keyEvent ->
            // add tap chat
            if(edtrutapchat.text.isEmpty()){
                Toast.makeText(this,"Không được bỏ trống tạp chất",Toast.LENGTH_LONG).show()
            }else {
                var cv = ContentValues()
                cv.put("tapchat", edtrutapchat.text.toString())
                db.update("user", cv, "_id ='$id'", null)
                rs.requery()
                // kl con lai
                var cc = ContentValues()
                var chat = edtrutapchat.text.toString()
                var lozz = chat.toDouble()
                var vaz = tongkl - lozz
                cc.put("klconlai", vaz)
                db.update("user", cc, "_id ='$id'", null)
                rs.requery()

                //thanh tien
                var cl = ContentValues()
                var zz = dongia * vaz
                cl.put("thanhtien", zz)
                db.update("user", cl, "_id ='$id'", null)
                rs.requery()
            }
            //reload
            if (rs.moveToFirst()){
                edtienconlai.setText(rs.getString(8).toString())
                edtrutapchat.setText(rs.getString(9).toString())
                edkhoiluongconlai.setText(rs.getString(10).toString())
                edthanhtien.setText(rs.getString(5).toString())
            }
            return@setOnEditorActionListener false
        }
        // su kien edittext tien dat coc
        edtiendatcoc.setOnEditorActionListener { textView, i, keyEvent ->
            if (edtiendatcoc.text.isEmpty()) {
                Toast.makeText(this, "Không được bỏ trống tiền đặt cọc", Toast.LENGTH_LONG).show()
            } else {
                // tien coc
                var cv = ContentValues()
                var z=edtiendatcoc.text.toString()
                var zz=z.toDouble()
                cv.put("tiencoc", zz)
                db.update("user", cv, "_id ='$id'", null)
                rs.requery()
                // tien con lai
                var tt=edthanhtien.text.toString()
                var t=tt.toDouble()
                var s=t-(zz+tiendatra)
                Log.d("vt",t.toString())
                var cl = ContentValues()
                cl.put("conlai", s)
                db.update("user", cl, "_id ='$id'", null)
                rs.requery()
            }
            if (rs.moveToFirst()) {
                edtiendatcoc.setText(rs.getString(6).toString())
                edtienconlai.setText(rs.getString(8).toString())
            }
            return@setOnEditorActionListener false
        }
        // su kien eddittext tien da tra
        edtiendatra.setOnEditorActionListener { textView, i, keyEvent ->
            if(edtiendatra.text.isEmpty()){
                Toast.makeText(this, "Không được bỏ trống tiền đã trả", Toast.LENGTH_LONG).show()
            }else{
                // tien da tra
                var cl = ContentValues()
                var z=edtiendatra.text.toString()
                var zz=z.toDouble()
                cl.put("datra", zz)
                db.update("user", cl, "_id ='$id'", null)
                rs.requery()
                // tien con lai
                var coc=edtiendatcoc.text.toString()
                var lz=coc.toDouble()
                var tt=edthanhtien.text.toString()
                var t=tt.toDouble()
                var s=t-(lz+zz)
                var cc = ContentValues()
                cc.put("conlai", s)
                db.update("user", cc, "_id ='$id'", null)
                rs.requery()
            }
            if (rs.moveToFirst()) {
                edtiendatra.setText(rs.getString(7).toString())
                edtienconlai.setText(rs.getString(8).toString())
            }
            return@setOnEditorActionListener false
        }
        //button nhap
        btnnhap.setOnClickListener({
            // tinh so bao
            val editTexts = arrayOf(editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10, editText11, editText12, editText13, editText14, editText15, editText16, editText17, editText18, editText19, editText20, editText21, editText22, editText23, editText24, editText25)
            val values = DoubleArray(25)
            var s=0.0
            var t=0
            for (i in 0..24) {
                val a = editTexts[i].text.toString()
                if (a.isEmpty()) {
                    values[i] = 0.0
                } else {
                    values[i] = a.toDouble()
                    s=s+values[i]
                    sobao++
                    t++
                }
            }
           Toast.makeText(this,"Đã thêm "+t.toString()+" bao",Toast.LENGTH_LONG).show()
            //add tong kl
            var cvtkl = ContentValues()
            tongkl=tongkl+s
            cvtkl.put("khoiluong",tongkl)
            db.update("user",cvtkl,"_id ='$id'",null)
            rs.requery()
            s=0.0
            //add so bao
            var cv = ContentValues()
            cv.put("sobao",sobao)
            db.update("user",cv,"_id ='$id'",null)
            rs.requery()

            //
            if (rs.moveToFirst()){
                val editTexts = arrayOf(edtennguoimua, edgiathumua, edsobao, edtongkhoiluong, edthanhtien, edtiendatcoc, edtiendatra, edtienconlai, edtrutapchat, edkhoiluongconlai)
                for (i in editTexts.indices) {
                    editTexts[i].setText(rs.getString(i + 1))
                }
            }
            // clear edittext
            editTexts.forEach { it.text.clear() }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_back, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cc -> {
                val intent: Intent = Intent(this,MainActivity::class.java)
                intent.putExtra("reload",1)
                intent.putExtra("idz",add)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}