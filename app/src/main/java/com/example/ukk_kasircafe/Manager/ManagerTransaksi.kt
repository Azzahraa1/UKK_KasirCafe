package com.example.ukk_kasircafe.Manager

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Adapter.TransaksiAdapter
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.Transaksi
import com.example.ukk_kasircafe.R

class ManagerTransaksi : AppCompatActivity() {
    lateinit var recycler :RecyclerView
    lateinit var adap: TransaksiAdapter
    lateinit var db: CafeDatabase
    private var listTransk = arrayListOf<Transaksi>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_transaksi)

        recycler = findViewById(R.id.recyclertransk)
        db = CafeDatabase.getInstance(applicationContext)

        recycler.layoutManager =LinearLayoutManager(this)
        adap = TransaksiAdapter(listTransk)
        recycler.adapter = adap
        adap.onHlder ={
            val inten = Intent(this@ManagerTransaksi, ManagerDetailTransk::class.java)
            inten.putExtra("id_transaksi", it.id_transaksi)
            startActivity(inten)
        }
    }

    override fun onResume() {
        super.onResume()
        getTransk()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun getTransk(){
        listTransk.clear()
        listTransk.addAll(db.cafeDao().getAllTransaksi())
        adap.notifyDataSetChanged()
    }
}