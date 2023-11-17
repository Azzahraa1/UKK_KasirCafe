package com.example.ukk_kasircafe.Menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.R

class EditMenu : AppCompatActivity() {
    lateinit var edtmenu : EditText
    lateinit var edthrga: EditText
    lateinit var edtspin : Spinner
    lateinit var sv : Button
    lateinit var db: CafeDatabase

    var id: Int = 0
    var namamenu: String = ""
    var jenisMenu : String = ""
    var harga: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)

        id =intent.getIntExtra("id", 0)

        init()
        setDataSpinner()
        db = CafeDatabase.getInstance(applicationContext)

        namamenu = db.cafeDao().getMenu(id).nama_menu!!
        jenisMenu = db.cafeDao().getMenu(id).jenis
        harga = db.cafeDao().getMenu(id).harga

        edtmenu.setText(db.cafeDao().getMenu(id).nama_menu)
        val adapter = ArrayAdapter.createFromResource(applicationContext, R.array.tipe, android.R.layout.simple_spinner_item)
        edtspin.setSelection(adapter.getPosition(jenisMenu))
        edthrga.setText(db.cafeDao().getMenu(id).harga.toString())


        sv.setOnClickListener {
            if(edtmenu.text.toString().isNotEmpty()&& edthrga.text.toString().isNotEmpty()&&edtspin.selectedItem.toString() != "Pilih tipe item"){
                    val namamenu = edtmenu.text.toString()
                val tipemenu = edtspin.selectedItem.toString()
                val hrgamenu = edthrga.text.toString().toInt()
                db.cafeDao().updateMenu(namamenu, tipemenu, hrgamenu, id)
                Toast.makeText(applicationContext, "Menu berhasil diubah", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }


        }
    }
    fun init(){
        edtmenu = findViewById(R.id.namamenu)
        edthrga = findViewById(R.id.harga)
        edtspin = findViewById(R.id.spinmenu)
        sv = findViewById(R.id.btnSave)



    }
    fun setDataSpinner(){
        val adap= ArrayAdapter.createFromResource(applicationContext, R.array.tipe, android.R.layout.simple_spinner_item)
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtspin.adapter = adap
    }
}