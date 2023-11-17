package com.example.ukk_kasircafe.Meja

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.ukk_kasircafe.Data.Adapter.MejaAdapter
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.Meja
import com.example.ukk_kasircafe.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TampilMeja : AppCompatActivity() {
    private lateinit var recycler : RecyclerView
    private lateinit var fab : FloatingActionButton
    private var list = mutableListOf<Meja>()

    private lateinit var db : CafeDatabase
    private lateinit var adapter : MejaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tampil_meja)

        recycler = findViewById(R.id.recyclerview)
        fab = findViewById(R.id.fab)

        db = CafeDatabase.getInstance(applicationContext)
        adapter = MejaAdapter(list)
        adapter.setDialog(object :MejaAdapter.Dialog{
            override fun onClick(position: Int) {
                val dialog = AlertDialog.Builder(this@TampilMeja)
                dialog.setTitle(list[position].nomor_meja)
                dialog.setItems(R.array.item_option, DialogInterface.OnClickListener{ dialog, which ->
                    if(which == 0){
                        val intent = Intent(this@TampilMeja, EditMeja::class.java)
                        intent.putExtra("id", list[position].id_meja)
                        startActivity(intent)
                    } else if(which == 1){
                        db.cafeDao().deleteMeja(list[position])
                        getMeja()
                        Toast.makeText(applicationContext, "Berhasil Hapus Meja", Toast.LENGTH_SHORT).show()

                    }

                })
                val dialogView = dialog.create()
                dialogView.show()
            }
        })
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false)
        recycler.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))

        fab.setOnClickListener{
            val Intent = Intent(this@TampilMeja, MejaActivity::class.java)
            startActivity(Intent)
        }
    }
    override fun onResume(){
        super.onResume()
        getMeja()
    }
    fun getMeja(){
        list.clear()
        list.addAll(db.cafeDao().getAllMeja())
        adapter.notifyDataSetChanged()
    }
}