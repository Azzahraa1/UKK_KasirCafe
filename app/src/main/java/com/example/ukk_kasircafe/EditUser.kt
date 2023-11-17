package com.example.ukk_kasircafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.ukk_kasircafe.Data.CafeDatabase

class EditUser : AppCompatActivity() {
    lateinit var edtnama : EditText
    lateinit var edtemail : EditText
    lateinit var edtpw : EditText
    lateinit var edtrole: Spinner
    lateinit var sv: Button
    lateinit var db: CafeDatabase

    var id: Int = 0
    var namauser: String = ""
    var emailuser :String =""
    var pw : String = ""
    var roleuser : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)
        id = intent.getIntExtra("id", 0)
        init()
        setDataSpinner()
        db  = CafeDatabase.getInstance(applicationContext)
        namauser = db.cafeDao().getUser(id).nama
        emailuser = db.cafeDao().getUser(id).email
        pw = db.cafeDao().getUser(id).password
        roleuser = db.cafeDao().getUser(id).role

        edtnama.setText(db.cafeDao().getUser(id).nama)
        edtemail.setText(db.cafeDao().getUser(id).email)
        edtpw.setText(db.cafeDao().getUser(id).password)
        val adap = ArrayAdapter.createFromResource(applicationContext, R.array.roles, android.R.layout.simple_spinner_item)
        edtrole.setSelection(adap.getPosition(roleuser))

        sv.setOnClickListener {
            if (edtnama.text.toString().isNotEmpty()&& edtemail.text.toString().isNotEmpty()&&edtpw.text.toString().isNotEmpty()&&edtrole.selectedItem.toString() != "Pilih Role"){
                val namauser = edtnama.text.toString()
                val emailuser = edtemail.text.toString()
                val pwuser = edtpw.text.toString()
                val roleuser = edtrole.selectedItem.toString()
                db.cafeDao().updateUser(namauser, emailuser, pwuser, roleuser, id)
                Toast.makeText(applicationContext, "Data User berhasil diubah", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    fun init(){
        edtnama = findViewById(R.id.edt_nama_regis)
        edtemail = findViewById(R.id.edt_email_regis)
        edtpw = findViewById(R.id.edt_pw_regis)
        edtrole = findViewById(R.id.spinerRole)
        sv = findViewById(R.id.btn_regis)
    }
    fun setDataSpinner(){
        val adap = ArrayAdapter.createFromResource(applicationContext, R.array.roles, android.R.layout.simple_spinner_item)
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtrole.adapter = adap
    }
}