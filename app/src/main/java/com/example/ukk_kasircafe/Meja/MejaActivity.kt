package com.example.ukk_kasircafe.Meja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.Meja
import com.example.ukk_kasircafe.R

class MejaActivity : AppCompatActivity() {
    lateinit var nomeja: EditText
    lateinit var btnSave: Button
    lateinit var db: CafeDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meja)

        nomeja = findViewById(R.id.nomeja)
        btnSave = findViewById(R.id.btnSave2)
        db = CafeDatabase.getInstance(applicationContext)

        btnSave.setOnClickListener {
            if(nomeja.text.toString().isNotEmpty()){
                db.cafeDao().insertMeja(
                    Meja(
                        null,
                        nomeja.text.toString(),
                        false
                    )
                )
                Toast.makeText(applicationContext, "Meja berhasil ditambahkan", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
            if (nomeja.text.toString().isEmpty()){
                nomeja.setError("Masukkan Nomor Meja")
            }
        }
//

    }
}