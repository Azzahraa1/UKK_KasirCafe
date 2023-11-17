package com.example.ukk_kasircafe.Manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Adapter.MejaAdapter
import com.example.ukk_kasircafe.Data.Adapter.MejasAdapter
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.Meja
import com.example.ukk_kasircafe.R

class ManagerMeja : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var adap : MejasAdapter
    private var listMeja = mutableListOf<Meja>()
    lateinit var db: CafeDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_meja)

        recycler = findViewById(R.id.recyclerview)
        db = CafeDatabase.getInstance(applicationContext)

        recycler.layoutManager = LinearLayoutManager(this)
        adap = MejasAdapter(listMeja)
        recycler.adapter = adap
    }

    override fun onResume() {
        super.onResume()
        getMeja()
    }

    fun getMeja(){
        listMeja.clear()
        listMeja.addAll(db.cafeDao().getAllMeja())
        adap.notifyDataSetChanged()
    }
}