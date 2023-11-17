package com.example.ukk_kasircafe.Transaksi

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Adapter.CartAdapter
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.DetailTransaksi
import com.example.ukk_kasircafe.Data.Entity.Menu
import com.example.ukk_kasircafe.R

class CartActivity : AppCompatActivity() {
    private var listCart = arrayListOf<Int?>()
    private var listMenu = arrayListOf<Menu>()

    lateinit var db : CafeDatabase
    lateinit var recycler : RecyclerView
    lateinit var checkout: Button
    lateinit var cartadap: CartAdapter

    var id_user : Int = 0
    var addAgain: Boolean = false
    var id_transaksi: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        db = CafeDatabase.getInstance(applicationContext)
        listCart = intent.getIntegerArrayListExtra("CART")!!
        id_user = intent.getIntExtra("id_user", 0)
        id_transaksi = intent.getIntExtra("id_transaksi",0)
        addAgain = intent.getBooleanExtra("addAgain", false)

        recycler = findViewById(R.id.recycler_cart)
        checkout = findViewById(R.id.checkOut)

        for (i in listCart){
            var menu = db.cafeDao().getMenu(i!!)
            listMenu.add(menu)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        listMenu.sortBy { it.jenis }
        cartadap = CartAdapter(listMenu)
        recycler.adapter = cartadap

        var total: Int = 0
        for (i in listMenu){
            total += i.harga
        }
        checkout.text = "Checkout (" + listMenu.size + ") " + "Rp."+ total

        checkout.setOnClickListener {
            val ingten = Intent(this@CartActivity, CheckOutActivity::class.java)
            ingten.putExtra("id_user", id_user)
            ingten.putExtra("id_transaksi", id_transaksi)
            ingten.putExtra("addAgain", addAgain)
            var listIdMenu = arrayListOf<Int?>()
            for(i in listMenu){
                listIdMenu.add(i.id_menu)
            }
            if (addAgain == true){
                for (i in listMenu){
                    db.cafeDao().insertDetailTransaksi(
                        DetailTransaksi(
                            null,
                            id_transaksi,
                            i.id_menu!!,
                            i.harga
                        )
                    )
                }
            }
            else{
                ingten.putIntegerArrayListExtra("list", listIdMenu)
                startActivity(ingten)
            }
            finish()
        }

    }
}