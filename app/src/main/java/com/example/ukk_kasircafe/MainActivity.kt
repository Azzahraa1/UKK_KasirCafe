package com.example.ukk_kasircafe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Adapter.MenuAdapter
import com.example.ukk_kasircafe.Data.Adapter.MenusAdapter
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.Menu
import com.example.ukk_kasircafe.Meja.TampilMeja
import com.example.ukk_kasircafe.Menu.EditMenu
import com.example.ukk_kasircafe.Menu.MenuActivity
import com.example.ukk_kasircafe.Transaksi.CartActivity
import com.example.ukk_kasircafe.Transaksi.TransaksiActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
lateinit var recyclerMakanan : RecyclerView
lateinit var recyclerMinuman: RecyclerView

lateinit var adapterMakanan: MenusAdapter
lateinit var adapterMinuman: MenusAdapter
lateinit var adapMakanan : MenuAdapter
lateinit var adapMinuman: MenuAdapter

lateinit var db: CafeDatabase
lateinit var fab : FloatingActionButton
lateinit var mejaBtn: ImageButton
lateinit var transBtn: ImageButton
lateinit var checkout:Button
lateinit var userbtn: ImageButton

private var listMakanan = mutableListOf<Menu>()
    private var listMinuman = mutableListOf<Menu>()

    private var listCart = arrayListOf<Int?>()

    var nama: String = ""
    var role: String = ""
    var id_user: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerMakanan = findViewById(R.id.recyclerMakanan)
        recyclerMinuman = findViewById(R.id.recyclerMinuman)
        fab = findViewById(R.id.fab)
        mejaBtn = findViewById(R.id.buttonMeja)
        transBtn = findViewById(R.id.buttonTransaksi)
        checkout = findViewById(R.id.checkOut)
        userbtn = findViewById(R.id.buttonUser)

        db = CafeDatabase.getInstance(applicationContext)
        adapMakanan = MenuAdapter(listMakanan)
        adapMakanan.setDialog(object : MenuAdapter.Dialog{
            override fun onClick(position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(listMakanan[position].nama_menu)
                dialog.setItems(R.array.item_option) { dialog, which ->
                    if (which == 0) {
                        val igten = Intent(this@MainActivity, EditMenu::class.java)
                        igten.putExtra("id", listMakanan[position].id_menu)
                        startActivity(igten)
                    } else if (which == 1) {
                        db.cafeDao().deleteMenu(listMakanan[position])
                        getMenu()
                        Toast.makeText(applicationContext, "Berhasil Hapus Menu", Toast.LENGTH_SHORT).show()

                    }
                }
                val dilog = dialog.create()
                dilog.show()

            }
        })
        adapterMakanan = MenusAdapter(listMakanan)
        adapterMakanan.onAddClick = {
            listCart.add(it.id_menu)
            checkout.isEnabled = true
            checkout.visibility = View.VISIBLE
            checkout.text = "Checkout ("+ listCart.size +")"
        }

        adapMinuman = MenuAdapter(listMinuman)
        adapMinuman.setDialog(object : MenuAdapter.Dialog{
            override fun onClick(position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(listMinuman[position].nama_menu)
                dialog.setItems(R.array.item_option) { dialog, which ->
                    if (which == 0) {
                        val igten = Intent(this@MainActivity, EditMenu::class.java)
                        igten.putExtra("id", listMinuman[position].id_menu)
                        startActivity(igten)
                    } else if (which == 1) {
                        db.cafeDao().deleteMenu(listMinuman[position])
                        getMenu()
                        Toast.makeText(applicationContext, "Berhasil Hapus Menu", Toast.LENGTH_SHORT).show()
                    }
                }
                val dilog = dialog.create()
                dilog.show()

            }

        })

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


        nama = intent.getStringExtra("nama")!!
        role = intent.getStringExtra("role")!!
        id_user = intent.getIntExtra("id_user", 0)
        Toast.makeText(applicationContext, "Login as " + nama, Toast.LENGTH_SHORT).show()

        if (role == "Admin"){
            recyclerMakanan.adapter = adapMakanan
            recyclerMakanan.layoutManager = LinearLayoutManager(this)
            recyclerMinuman.adapter = adapMinuman
            recyclerMinuman.layoutManager = LinearLayoutManager(this)
            checkout.isEnabled = false
            checkout.visibility = View.INVISIBLE
        }else{
            recyclerMakanan.adapter = adapterMakanan
            recyclerMakanan.layoutManager = LinearLayoutManager(this)
            recyclerMinuman.adapter = adapterMinuman
            recyclerMinuman.layoutManager = LinearLayoutManager(this)
        }


        if (role == "Kasir"){
            fab.isEnabled = false
            fab.visibility = View.INVISIBLE
            mejaBtn.isEnabled = false
            mejaBtn.visibility = View.INVISIBLE
            userbtn.isEnabled = false
            userbtn.visibility = View.INVISIBLE
        }

        fab.setOnClickListener {
            val inten =Intent(this@MainActivity, MenuActivity::class.java)
            startActivity(inten)
        }

        mejaBtn.setOnClickListener {
            val moveIntent = Intent(this@MainActivity, TampilMeja::class.java)
            startActivity(moveIntent)
        }
        transBtn.setOnClickListener {
            val ingten = Intent(this@MainActivity, TransaksiActivity::class.java)
            startActivity(ingten)
        }
        userbtn.setOnClickListener {
            val inten = Intent(this@MainActivity, UserActivity::class.java)
            startActivity(inten)
        }

        checkout.setOnClickListener {
            val ingten = Intent(this@MainActivity, CartActivity::class.java )
            ingten.putIntegerArrayListExtra("CART", listCart)
            ingten.putExtra("id_user", id_user)
            startActivity(ingten)
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
        adapMakanan.notifyDataSetChanged()
        adapMinuman.notifyDataSetChanged()
    }


}

