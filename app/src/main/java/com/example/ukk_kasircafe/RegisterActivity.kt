package com.example.ukk_kasircafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.User
import com.example.ukk_kasircafe.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class RegisterActivity :AppCompatActivity(){
    lateinit var nama: EditText
    lateinit var email: EditText
    lateinit var pw:EditText
    lateinit var role: Spinner
    lateinit var save: Button
    lateinit var db : CafeDatabase
    lateinit var lgn : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
        setDataSpinner()
        save.setOnClickListener {
            if(nama.text.toString().isNotEmpty() && email.text.toString().isNotEmpty() && pw.text.toString().isNotEmpty() && role.selectedItem.toString() != "Pilih Role"){
            db.cafeDao().insertUser(User(
                null,
                nama.text.toString(),
                email.text.toString(),
                pw.text.toString(),
                role.selectedItem.toString()
            ))
                Toast.makeText(applicationContext, "Register Berhasil", Toast.LENGTH_SHORT).show()
                finish()
            }
            if (nama.text.toString().isEmpty()){
                nama.setError("Masukkan Nama")
            }
            if (email.text.toString().isEmpty()){
                email.setError("Masukkan Email")
            }
            if (pw.text.toString().isEmpty()){
                pw.setError("Masukkan Password")
            }
        }

        lgn.setOnClickListener {
            val inten = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(inten)
        }
    }
    fun init(){
        nama = findViewById(R.id.edt_nama_regis)
        email = findViewById(R.id.edt_email_regis)
        pw = findViewById(R.id.edt_pw_regis)
        save = findViewById(R.id.btn_regis)
        role = findViewById(R.id.spinerRole)
        lgn = findViewById(R.id.tv_to_log)

        db = CafeDatabase.getInstance(applicationContext)
    }
    private fun setDataSpinner(){
        val adap = ArrayAdapter.createFromResource(applicationContext, R.array.roles, android.R.layout.simple_spinner_item)
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        role.adapter = adap
    }
}
//
//class RegisterActivity : AppCompatActivity() {
//    lateinit var binding: ActivityRegisterBinding
//    lateinit var auth: FirebaseAuth
//    override fun onCreate(savedInstanceState: Bundle?) {
//        binding = ActivityRegisterBinding.inflate(layoutInflater)
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        auth = FirebaseAuth.getInstance()
//
//        binding.tvToLog.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.btnRegis.setOnClickListener {
//            val email = binding.edtEmailRegis.text.toString()
//            val pw = binding.edtPwRegis.text.toString()
//            if(email.isEmpty()){
//                binding.edtEmailRegis.error ="Email harus di isi"
//                binding.edtEmailRegis.requestFocus()
//                return@setOnClickListener
//            }
//            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                binding.edtEmailRegis.error = "Email tidak valid"
//                binding.edtEmailRegis.requestFocus()
//                return@setOnClickListener
//            }
//            if(pw.isEmpty()){
//                binding.edtPwRegis.error ="Password harus di isi"
//                binding.edtPwRegis.requestFocus()
//                return@setOnClickListener
//            }
//            if(pw.length<6){
//                binding.edtPwRegis.error = "Password minimal 6 karakter"
//                binding.edtPwRegis.requestFocus()
//                return@setOnClickListener
//            }
//            RegisterFirebase(email,pw)
//        }
//
//    }
//
//    private fun RegisterFirebase(email: String, pw: String) {
//        auth.createUserWithEmailAndPassword(email,pw)
//            .addOnCompleteListener(this){
//                if(it.isSuccessful){
//                    Toast.makeText(this,"Register Berhasil", Toast.LENGTH_SHORT).show()
//                    val intet = Intent(this, LoginActivity::class.java)
//                    startActivity(intet)
//                }else{
//                    Toast.makeText(this, "${it.exception?.message}",Toast.LENGTH_SHORT).show()
//                }
//            }
//
//    }
//}