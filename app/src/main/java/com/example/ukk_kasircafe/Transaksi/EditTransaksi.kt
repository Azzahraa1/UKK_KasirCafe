package com.example.ukk_kasircafe.Transaksi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.R

class EditTransaksi : AppCompatActivity() {
    lateinit var namaplnggan : EditText
    lateinit var spinMeja : TextView
    lateinit var checkout: Button
    lateinit var bayar : CheckBox
    lateinit var nominal : EditText

    lateinit var db : CafeDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaksi)

        namaplnggan = findViewById(R.id.namaPelanggan)
        spinMeja = findViewById(R.id.nomorMeja)
        checkout = findViewById(R.id.checkOut)
        bayar = findViewById(R.id.dibayar)
        nominal = findViewById(R.id.nominal)

        db = CafeDatabase.getInstance(applicationContext)

        var id_transaksi: Int? = null
        id_transaksi = intent.getIntExtra("ID", 0)

        namaplnggan.setText(db.cafeDao().getTransaksi(id_transaksi).nama_pelanggan)
        spinMeja.text = db.cafeDao().getMeja(db.cafeDao().getTransaksi(id_transaksi).id_meja).nomor_meja

        checkout.setOnClickListener {
            var status = "Belum bayar"
            if (bayar.isChecked){
                status = "Dibayar"
            }
            if (namaplnggan.text.toString().isNotEmpty()&& nominal.text.toString().isNotEmpty()){
                db.cafeDao().updateTransaksi(
                    namaplnggan.text.toString(),
                    status,
                    nominal.text.toString().toInt(),
                    id_transaksi
                )
                if(bayar.isChecked){
                    var meja = db.cafeDao().getMeja(db.cafeDao().getTransaksi(id_transaksi).id_meja)
                    db.cafeDao().updateMeja(meja.nomor_meja, meja.id_meja!!, false)
                }
                finish()
                Toast.makeText(applicationContext, "Transaksi Selesai", Toast.LENGTH_SHORT).show()
            }
        }
    }
}