package com.example.ukk_kasircafe.Manager

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Adapter.DetailAdapter
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.DetailTransaksi
import com.example.ukk_kasircafe.R

class ManagerDetailTransk : AppCompatActivity() {
    lateinit var recycler : RecyclerView
    lateinit var ttl : TextView
    lateinit var adap : DetailAdapter
    private var listDtl = arrayListOf<DetailTransaksi>()
    private var id_transaksi = 0
    private var ttlHrg = 0
    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_detail_transk)

        recycler = findViewById(R.id.recyclerdtl)
        ttl = findViewById(R.id.total)
        id_transaksi = intent.getIntExtra("id_transaksi", 0)

        db = CafeDatabase.getInstance(applicationContext)

        recycler.layoutManager = LinearLayoutManager(this)
        adap = DetailAdapter(listDtl)
        recycler.adapter = adap
    }

    override fun onResume() {
        super.onResume()
        getDtl()
        ttlHrg = 0
        for (i in listDtl){
            ttlHrg += i.harga
        }
        ttl.text = "Total : " +
                "Rp. " + ttlHrg
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getDtl(){
        listDtl.clear()
        listDtl.addAll(db.cafeDao().getDetailTransaksi(id_transaksi))
        adap.notifyDataSetChanged()
    }
}