package com.febryan.autentikasifirebase.ui.user

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.febryan.autentikasifirebase.LoginActivity
import com.febryan.autentikasifirebase.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class UserFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentUserBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var imgUri: Uri

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        binding.cviUser.setOnClickListener {
            goToCamera()
        }

        binding.btnKeluar.setOnClickListener {
            tombolKeluar()
        }

        if (user != null) {

            if (user.photoUrl != null) {
                Picasso.get().load(user.photoUrl).into(binding.cviUser)
            } else {
                Picasso.get().load("https://bit.ly/3wc3Lcn").into(binding.cviUser)
            }

            binding.edtEmail.setText(user.email)
            binding.edtName.setText(user.displayName)

            if (user.isEmailVerified) {
                binding.imgVerifikasi.visibility = View.VISIBLE
                binding.imgBelumVerifikasi.visibility = View.GONE
            } else {
                binding.imgVerifikasi.visibility = View.GONE
                binding.imgBelumVerifikasi.visibility = View.VISIBLE
            }

        }

        binding.btnSave.setOnClickListener saveProfile@{

            val image = when {
                ::imgUri.isInitialized -> imgUri
                user?.photoUrl == null -> Uri.parse("https://bit.ly/3wc3Lcn")
                else -> user?.photoUrl
            }

            val name = binding.edtName.text.toString()
            if (name.isEmpty()){
                binding.edtName.error = "Nama belum diisi!"
                binding.edtName.requestFocus()
                return@saveProfile
            }

            //Update disini
            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(image)
                .build().also {
                    user?.updateProfile(it)?.addOnCompleteListener { Task->
                        if (Task.isSuccessful){
                            val toast = Toast.makeText(activity, "Data profile berhasil disimpan!", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.TOP, 0, 0)
                            toast.show()
                        }else{
                            Toast.makeText(activity, "${Task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        binding.btnVerifyEmail.setOnClickListener {
            user?.sendEmailVerification()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        activity,
                        "Email verifikasi berhasil dikirim!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent?.resolveActivity(it).also {
                    startActivityForResult(intent, REQ_CAM)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAM && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImageToFirebase(imgBitmap)
        }
    }

    private fun uploadImageToFirebase(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref =
            FirebaseStorage.getInstance().reference.child("img_user/${FirebaseAuth.getInstance().currentUser?.uid}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val img = baos.toByteArray()
        ref.putBytes(img)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener { Task ->
                        Task.result?.let { Uri ->
                            imgUri = Uri
                            binding.cviUser.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }

    }

    private fun tombolKeluar() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val i = Intent(context, LoginActivity::class.java)
        startActivity(i)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQ_CAM = 100
    }
}