package com.example.ukk_kasircafe.Transaksi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Adapter.TransaksiAdapter
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.Transaksi
import com.example.ukk_kasircafe.R
import com.example.ukk_kasircafe.SwipeGesture

class TransaksiActivity : AppCompatActivity() {
    lateinit var recyler: RecyclerView
    lateinit var adap: TransaksiAdapter
    lateinit var db: CafeDatabase
    private var listTransk = arrayListOf<Transaksi>()
//    var role: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi)

        recyler = findViewById(R.id.recyclertransk)
        db = CafeDatabase.getInstance(applicationContext)
//        role = intent.getStringExtra("role")!!


        recyler.layoutManager = LinearLayoutManager(this)
        adap = TransaksiAdapter(listTransk)
        recyler.adapter = adap
        adap.onHlder = {
            val ingten = Intent(this@TransaksiActivity, DetailTransaksi::class.java)
            ingten.putExtra("id_transaksi", it.id_transaksi)
            startActivity(ingten)
        }
        swipe(recyler)

    }

    override fun onResume() {
        super.onResume()
        getTransaksi()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getTransaksi() {
        listTransk.clear()
        listTransk.addAll(db.cafeDao().getAllTransaksi())
        adap.notifyDataSetChanged()
    }

    private fun swipe(itemRv: RecyclerView) {
        val swipeGesture = object : SwipeGesture(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val actionBtnTapped = false

                try {
                    when (direction) {
                        ItemTouchHelper.LEFT -> {
                            var adap: TransaksiAdapter = itemRv.adapter as TransaksiAdapter
                            db.cafeDao().deleteTransaksi(adap.getItem(position))
                            var listDtl = db.cafeDao()
                                .getDetailTransaksifromTransaksi(adap.getItem(position).id_transaksi!!)
                            for (i in 0..listDtl.size - 1) kotlin.run {
                                db.cafeDao().deleteDetailTransaksi(listDtl[i])
                            }
                            adap.notifyItemRemoved(position)
                            val intent = intent
                            finish()
                            startActivity(intent)
                        }

                        ItemTouchHelper.RIGHT -> {
                            var adapter: TransaksiAdapter = itemRv.adapter as TransaksiAdapter
                            var transaksi = adapter.getItem(position)
                            if (db.cafeDao().getMeja(
                                    db.cafeDao().getTransaksi(transaksi.id_transaksi!!).id_meja
                                ).Used == true
                            )
                            {
                                val moveIntent =
                                    Intent(this@TransaksiActivity, EditTransaksi::class.java)
                                moveIntent.putExtra("ID", transaksi.id_transaksi)
                                startActivity(moveIntent)
                            }
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(itemRv)
    }
}