package com.example.ukk_kasircafe.Meja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.R

class EditMeja : AppCompatActivity() {
    lateinit var edtNo: EditText
    lateinit var save: Button
    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_meja)

        edtNo = findViewById(R.id.nomeja)
        save = findViewById(R.id.btnSave2)

        db = CafeDatabase.getInstance(applicationContext)

        var id = intent.getIntExtra("id", 0)
        edtNo.setText(db.cafeDao().getMeja(id).nomor_meja)


        save.setOnClickListener {
            if(edtNo.text.toString().isNotEmpty()){
                db.cafeDao().updateMeja(edtNo.text.toString(), id, false)
                Toast.makeText(applicationContext,"Meja berhasil diubah", Toast.LENGTH_SHORT).show()
                finish()

            }
        }
    }
}