package com.example.ukk_kasircafe.Transaksi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.DetailTransaksi
import com.example.ukk_kasircafe.Data.Entity.Menu
import com.example.ukk_kasircafe.Data.Entity.Transaksi
import com.example.ukk_kasircafe.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CheckOutActivity : AppCompatActivity() {
    lateinit var namaplnggan : TextView
    lateinit var spinMeja : Spinner
    lateinit var bayar : CheckBox
    lateinit var checkout : Button
    lateinit var tunai : EditText

    lateinit var db: CafeDatabase
    var id_user: Int = 0
    var listIdMenu = arrayListOf<Int>()
    var listMenu = arrayListOf<Menu>()
    var addAgain: Boolean = false
    var id_transaksi: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)
        namaplnggan = findViewById(R.id.namaPelanggan)
        spinMeja = findViewById(R.id.spinnerMeja)
        bayar = findViewById(R.id.dibayar)
        checkout = findViewById(R.id.checkOut)
        tunai = findViewById(R.id.nominal)

        db = CafeDatabase.getInstance(applicationContext)
        id_user = intent.getIntExtra("id_user", 0)
        id_transaksi = intent.getIntExtra("id_transaksi", 0)
        listIdMenu = intent.getIntegerArrayListExtra("list")!!
        addAgain = intent.getBooleanExtra("addAgain",false)

        for (i in listIdMenu){
            var menu = db.cafeDao().getMenu(i)
            listMenu.add(menu)
        }
        setDataSpinner()
         var status = "Belum bayar"
        if(addAgain == true){
            spinMeja.visibility = View.INVISIBLE
            spinMeja.isEnabled = false
//            namaplnggan.text = db.cafeDao().getTransaksi(id_transaksi).nama_pelanggan
//            //spinMeja.setSelection(db.cafeDao().getTransaksi(id_transaksi).id_meja -1)
//            if(db.cafeDao().getTransaksi(id_transaksi).status == "Dibayar"){
//                bayar.isChecked = true
//            }
        }
        checkout.setOnClickListener {
            if(addAgain == true){
                for (i in listMenu){
                    db.cafeDao().insertDetailTransaksi(
                        DetailTransaksi(
                            null,
                            id_transaksi,
                            i.id_menu!!,
                            i.harga,
                        )
                    )
                }
                finish()
            }else {
                if (namaplnggan.text.isNotEmpty() && spinMeja.selectedItem != null) {
                    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    val formatterWaktu = DateTimeFormatter.ofPattern("HH:mm")
                    var current = LocalDateTime.now().format(formatter)
                    val currentWaktu = LocalDateTime.now().format(formatterWaktu)
//                    if (bayar.isChecked){
//                        status = "Dibayar"
//                    }
                    var newTransaksi = Transaksi(
                        null,
                        currentWaktu,
                        current,
                        id_user,
                        db.cafeDao().getIdMejaFromNo(spinMeja.selectedItem.toString()),
                        namaplnggan.text.toString(),
                        status,
                        tunai.text.toString().toInt()
                    )
                    db.cafeDao().insertTransaksi(newTransaksi)
//                if(bayar.isChecked){
//                    status = "Dibayar"
//                }
//                var newTransaksi = Transaksi(null,
//                    current,
//                    db.cafeDao().getIdMejaFromNo(spinMeja.selectedItem.toString()),
//                    namaplnggan.text.toString(),
//                status)
//                db.cafeDao().insertTransaksi(newTransaksi)
                    var idtransaksi = db.cafeDao().getIdTransaksiFromOther(
                        newTransaksi.tgl_transaksi,
                        newTransaksi.id_user,
                        newTransaksi.id_meja,
                        newTransaksi.nama_pelanggan,
                        newTransaksi.status,

                    )
                    var meja = db.cafeDao().getMeja(newTransaksi.id_meja)
                    db.cafeDao().updateMeja(meja.nomor_meja, meja.id_meja!!, true)



                    for (i in listMenu) {
                        db.cafeDao().insertDetailTransaksi(
                            DetailTransaksi(
                                null,
                                idtransaksi,
                                i.id_menu!!,
                                i.harga
                            )
                        )
                    }
                    Toast.makeText(applicationContext, "Checkout Berhasil", Toast.LENGTH_SHORT).show()
                    finish()


                } else if (namaplnggan.text.isEmpty()) {
                    namaplnggan.setError("Nama harus di isi")
                } else {
                    namaplnggan.setError("Meja Penuh")
                }

            }
        }
    }
    private fun setDataSpinner(){
        val adap = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, db.cafeDao().getAllNomorMeja())
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinMeja.adapter = adap
    }
}