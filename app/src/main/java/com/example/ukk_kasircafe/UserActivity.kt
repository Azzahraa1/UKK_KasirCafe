package com.example.ukk_kasircafe

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
import com.example.ukk_kasircafe.Data.Adapter.UserAdapter
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.User
import com.example.ukk_kasircafe.Meja.EditMeja
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserActivity : AppCompatActivity() {
    private lateinit var recycler : RecyclerView
    private lateinit var fab : FloatingActionButton
    private var list = mutableListOf<User>()

    private lateinit var db: CafeDatabase
    private lateinit var adap : UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        recycler = findViewById(R.id.recycleruser)
        fab = findViewById(R.id.fab)
        db = CafeDatabase.getInstance(applicationContext)
        adap = UserAdapter(list)
        adap.setDialog(object : UserAdapter.Dialog{
            override fun onClick(position: Int) {
                val dialog = AlertDialog.Builder(this@UserActivity)
                dialog.setTitle(list[position].nama)
                dialog.setItems(R.array.item_option, DialogInterface.OnClickListener{ dialog, which ->
                    if(which == 0){
                        val intent = Intent(this@UserActivity, EditUser::class.java)
                        intent.putExtra("id", list[position].id_user)
                        startActivity(intent)
                    } else if(which == 1){
                        db.cafeDao().deleteUser(list[position])
                        getUser()
                        Toast.makeText(applicationContext, "Berhasil Hapus User", Toast.LENGTH_SHORT).show()

                    }

                })
                val dialogView = dialog.create()
                dialogView.show()
            }
        })


//        adap.setDialog(object : UserAdapter.Dialog{
//            override fun onClick(position: Int) {
//                val dialog = AlertDialog.Builder(this@UserActivity)
//                dialog.setTitle(list[position].nama)
//                dialog.setItems(R.array.item_option, DialogInterface.OnClickListener { dialog, which ->
//                    if (which == 0){
//                        val inten = Intent(this@UserActivity, EditUser::class.java)
//                        inten.putExtra("id", list[position].id_user)
//                        startActivity(inten)
//                    }else if (which == 1){
//                        db.cafeDao().deleteUser(list[position])
//                        getUser()
//                        Toast.makeText(applicationContext, "Berhasil Hapus User", Toast.LENGTH_SHORT).show()
//                    }
//                })
//                val dialogView = dialog.create()
//                dialogView.show()
//            }
//        })

        recycler.adapter = adap
        recycler.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false)
        recycler.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))

        fab.setOnClickListener {
            val intent = Intent(this@UserActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        }

    override fun onResume() {
        super.onResume()
        getUser()
    }
    fun getUser(){
        list.clear()
        list.addAll(db.cafeDao().getAllUser())
        adap.notifyDataSetChanged()
    }
    }
