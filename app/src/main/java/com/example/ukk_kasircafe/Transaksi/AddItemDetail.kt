package com.example.ukk_kasircafe.Transaksi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Adapter.MenusAdapter
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.Menu
import com.example.ukk_kasircafe.R

class AddItemDetail : AppCompatActivity() {
    lateinit var recyclerMakanan : RecyclerView
    lateinit var recyclerMinuman : RecyclerView

    lateinit var adapterMakanan: MenusAdapter
    lateinit var adapterMinuman: MenusAdapter

    lateinit var db: CafeDatabase
    lateinit var checkout: Button

    private var listMakanan = mutableListOf<Menu>()
    private var listMinuman = mutableListOf<Menu>()

    private var listCart = arrayListOf<Int?>()

    var id_transaksi: Int = 0
    var addAgain: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item_detail)
        recyclerMakanan = findViewById(R.id.recylerMakanan)
        recyclerMinuman = findViewById(R.id.recyclerMinuman)
        checkout = findViewById(R.id.checkOut)

        db = CafeDatabase.getInstance(applicationContext)
        id_transaksi= intent.getIntExtra("id_transaksi", 0)

        adapterMakanan = MenusAdapter(listMakanan)
        adapterMakanan.onAddClick = {
            listCart.add(it.id_menu)
            checkout.isEnabled = true
            checkout.visibility = View.VISIBLE
            checkout.text = "Checkout ("+ listCart.size +")"
        }
        adapterMinuman = MenusAdapter(listMinuman)
        adapterMinuman.onAddClick={
            listCart.add(it.id_menu)
            checkout.isEnabled = true
            checkout.visibility = View.VISIBLE
            checkout.text = "Checkout(" + listCart.size+")"
        }
        adapterMakanan.onSubClick = {
            listCart.remove(it.id_menu)
            if(listCart.size == 0){
                checkout.isEnabled = false
                checkout.visibility = View.INVISIBLE
            }
            checkout.text = "Checkout("+listCart.size + ")"
        }
        adapterMinuman.onSubClick = {
            listCart.remove(it.id_menu)
            if (listCart.size == 0){
                checkout.isEnabled = false
                checkout.visibility = View.INVISIBLE
            }
            checkout.text = "Checkout("+listCart.size + ")"
        }

        recyclerMakanan.adapter = adapterMakanan
        recyclerMakanan.layoutManager = LinearLayoutManager(this)
        recyclerMinuman.adapter = adapterMinuman
        recyclerMinuman.layoutManager = LinearLayoutManager(this)

        checkout.setOnClickListener {
            val inten = Intent(this@AddItemDetail, CartActivity::class.java)
            addAgain = true
            inten.putIntegerArrayListExtra("CART", listCart)
            inten.putExtra("id_transaksi", id_transaksi)
            inten.putExtra("addAgain", addAgain)
            finish()
            startActivity(inten)

        }
    }

    override fun onResume() {
        super.onResume()
        getMenu()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun getMenu(){
        listMakanan.clear()
        listMinuman.clear()
        listMakanan.addAll(db.cafeDao().getMenuFilterJenis("Makanan"))
        listMinuman.addAll(db.cafeDao().getMenuFilterJenis("Minuman"))
        adapterMakanan.notifyDataSetChanged()
        adapterMinuman.notifyDataSetChanged()
    }
}