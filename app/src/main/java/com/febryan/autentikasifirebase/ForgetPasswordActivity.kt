package com.febryan.autentikasifirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.febryan.autentikasifirebase.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

private lateinit var binding: ActivityForgetPasswordBinding
private lateinit var auth: FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnResetPassword.setOnClickListener {

            val email = binding.edtInputForgetEmail.text.toString()
            val edtEmail = binding.edtInputForgetEmail

            if (email.isEmpty()) {
                edtEmail.error = "Email diperlukan"
                edtEmail.requestFocus()
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edtEmail.error = "Email Tidak Valid"
                edtEmail.requestFocus()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    val i = Intent(this, LoginActivity::class.java)
                    Toast.makeText(applicationContext, "Email Verifikasi Telah Dikirim ke $email", Toast.LENGTH_SHORT).show()
                    startActivity(i)
                    finish()

                } else {
                    edtEmail.error = "${it.exception?.message}"
                    edtEmail.requestFocus()
                }
            }


        }
    }
}