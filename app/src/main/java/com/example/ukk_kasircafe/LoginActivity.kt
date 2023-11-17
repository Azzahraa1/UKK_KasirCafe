package com.example.ukk_kasircafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.User
import com.example.ukk_kasircafe.Manager.ManagerActivity
import com.example.ukk_kasircafe.Transaksi.TransaksiActivity
import com.example.ukk_kasircafe.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(){
    lateinit var email : EditText
    lateinit var pw : EditText
    lateinit var btnLgn : Button
    lateinit var regis : TextView

    lateinit var db : CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        btnLgn.setOnClickListener {
            if(email.text.toString().isNotEmpty() && pw.text.toString().isNotEmpty()){
                var list: List<User> = db.cafeDao().login(email.text.toString(), pw.text.toString())
                if(list.size > 0){
                    val nama = list[0].nama
                    val role = list[0].role
                    val id_user = list[0].id_user
                    if (role == "Manager"){
                        val inten = Intent(this@LoginActivity,  ManagerActivity::class.java)
                        inten.putExtra("nama", nama)
                        inten.putExtra("role", role)
                        inten.putExtra("id_user", id_user)
                        startActivity(inten)
                    }else{
                        val ingten = Intent(this@LoginActivity, MainActivity::class.java)
                        ingten.putExtra("nama", nama)
                        ingten.putExtra("role", role)
                        ingten.putExtra("id_user", id_user)
                        startActivity(ingten)
                    }


                }

                else{
                    Toast.makeText(applicationContext,"User not found", Toast.LENGTH_SHORT).show()
                }
            }

            if (email.text.toString().isEmpty()){
                email.setError("Email harus di isi")
            }
            if (pw.text.toString().isEmpty()){
                pw.setError("Password harus di isi")
            }

            regis.setOnClickListener {
                val ingten = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(ingten)
            }
        }

    }
    fun init(){
        email = findViewById(R.id.edt_email_lgn)
        pw = findViewById(R.id.edt_pw_lgn)
        btnLgn = findViewById(R.id.btn_lgn)
        regis = findViewById(R.id.tv_to_regis)

        db = CafeDatabase.getInstance(applicationContext)
    }
}

