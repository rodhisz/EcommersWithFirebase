package com.febryan.autentikasifirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.febryan.autentikasifirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {

            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()

            if (email.isEmpty()){
                binding.edtEmailLogin.error = "Email harus diisi!"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmailLogin.error = "Email tidak valid!"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                binding.edtPasswordLogin.error = "Password harus diisi!"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }

//            if (password.length < 6){
//                binding.edtPasswordLogin.error = "Password minimal 6 karakter!"
//                binding.edtPasswordLogin.requestFocus()
//                return@setOnClickListener
//            }

            loginUserToFirebase(email, password)

        }

        binding.tvToRegister.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.tvLupaPassword.setOnClickListener {
            val i = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(i)
        }

    }

    private fun loginUserToFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val i = Intent(this, HomeActivity::class.java)
                    Toast.makeText(this,"Welcome, $email", Toast.LENGTH_SHORT).show()
                    startActivity(i)
                    finish()
                }else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
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