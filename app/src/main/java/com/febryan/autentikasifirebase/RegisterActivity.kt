package com.febryan.autentikasifirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.febryan.autentikasifirebase.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {

            val email = binding.edtEmailRegister.text.toString()
            val password = binding.edtPasswordRegister.text.toString()

            if (email.isEmpty()){
                binding.edtEmailRegister.error = "Email harus diisi!"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmailRegister.error = "Email tidak valid!"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                binding.edtPasswordRegister.error = "Password harus diisi!"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6){
                binding.edtPasswordRegister.error = "Password minimal 6 karakter!"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }


            daftarUserFirebase(email, password)

        }

        binding.tvToLogin.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

    }

    private fun daftarUserFirebase(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){

//                    Toast.makeText(this,"$email berhasil didaftarkan!", Toast.LENGTH_SHORT).show()
//                    val i = Intent(this, MainActivity::class.java)
//                    startActivity(i)
//                    finish()

                    Intent(this, HomeActivity::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }

                }else{
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null){
            val i = Intent(this, HomeActivity::class.java)
            Toast.makeText(this,"Welcome, ${FirebaseAuth.getInstance().currentUser?.email}", Toast.LENGTH_SHORT).show()
            startActivity(i)
        }

    }
}