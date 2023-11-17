package com.example.ukk_kasircafe.Transaksi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Adapter.DetailAdapter
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.DetailTransaksi
import com.example.ukk_kasircafe.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailTransaksi : AppCompatActivity() {
    lateinit var fab : FloatingActionButton
    lateinit var recycler: RecyclerView
    lateinit var ttl : TextView
    lateinit var tunai : TextView
    lateinit var kembalian : TextView

    lateinit var adap : DetailAdapter
    private var listDtl = arrayListOf<DetailTransaksi>()
    private var id_transaksi = 0
    private var totalhrg = 0

    lateinit var db: CafeDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)
        fab = findViewById(R.id.fab)
        recycler = findViewById(R.id.recyclerdtl)
        ttl = findViewById(R.id.total)
        tunai = findViewById(R.id.tunai)
        kembalian = findViewById(R.id.kembalian)


        id_transaksi = intent.getIntExtra("id_transaksi", 0)
        db = CafeDatabase.getInstance(applicationContext)
        tunai.text = "Tunai : Rp. " + db.cafeDao().getTransaksi(id_transaksi).tunai.toString()
        recycler.layoutManager = LinearLayoutManager(this)
        adap = DetailAdapter(listDtl)
        recycler.adapter =adap

        if(db.cafeDao().getTransaksi(id_transaksi).status == "Dibayar"){
            fab.isEnabled = false
            fab.visibility = View.INVISIBLE
        }
        fab.setOnClickListener {
            val inten = Intent(this@DetailTransaksi, AddItemDetail::class.java)
            inten.putExtra("id_transaksi", id_transaksi)
            startActivity(inten)
        }
    }

    override fun onResume() {
        super.onResume()
        getDtl()
        totalhrg = 0
        for (i in listDtl){
            totalhrg += i.harga
    }
        ttl.text = "Total : " +
                "Rp. " + totalhrg
        kembalian.text = "Kembalian : Rp. "+ (db.cafeDao().getTransaksi(id_transaksi).tunai - totalhrg).toString()

    }


    @SuppressLint("NotifyDataSetChanged")
    fun getDtl(){
        listDtl.clear()
        listDtl.addAll(db.cafeDao().getDetailTransaksi(id_transaksi))
        adap.notifyDataSetChanged()
    }
}