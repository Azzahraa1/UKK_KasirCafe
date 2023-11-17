package com.example.ukk_kasircafe.Menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.Menu
import com.example.ukk_kasircafe.R

class MenuActivity : AppCompatActivity() {

    private lateinit var namamenu: EditText
    private lateinit var harga: EditText
    private lateinit var btnSave : Button
    private lateinit var jenisMenu: Spinner

    private lateinit var db : CafeDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        init()
        setDataSpinner()
        db = CafeDatabase.getInstance(applicationContext)
        btnSave.setOnClickListener {
            if (namamenu.text.toString().isNotEmpty() && harga.text.toString()
                    .isNotEmpty() && jenisMenu.selectedItem.toString() != "Pilih Item") {
                val namamenu = namamenu.text.toString()
                val harga = harga.text.toString().toInt()
                val jenisMenu = jenisMenu.selectedItem.toString()
                db.cafeDao().insertMenu(Menu(null, namamenu, jenisMenu, harga))
                Toast.makeText(applicationContext, "Menu Berhasil ditambahkan", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
            if (namamenu.text.toString().isEmpty()){
                namamenu.setError("Masukkan Menu")
            }
            if (harga.text.toString().isEmpty()){
                harga.setError("Masukkan Harga")
            }

        }




    }

    fun init(){
        namamenu = findViewById(R.id.namamenu)
        harga = findViewById(R.id.harga)
        jenisMenu = findViewById(R.id.spinmenu)
        btnSave = findViewById(R.id.btnSave)

        db = CafeDatabase.getInstance(applicationContext)
    }
    private fun setDataSpinner(){
        val adapter = ArrayAdapter.createFromResource(applicationContext, R.array.tipe, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jenisMenu.adapter = adapter
    }
}