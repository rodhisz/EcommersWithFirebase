package com.febryan.autentikasifirebase.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.febryan.autentikasifirebase.Adapter.AdapterProduk
import com.febryan.autentikasifirebase.Adapter.AdapterSlider
import com.febryan.autentikasifirebase.R
import com.febryan.autentikasifirebase.databinding.FragmentHomeBinding
import com.febryan.autentikasifirebase.model.ModelProduk

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    lateinit var vpSlider : ViewPager
    lateinit var rvBaju : RecyclerView
    lateinit var rvBuku : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
//        (requireActivity() as AppCompatActivity).window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val view : View = inflater.inflate(R.layout.fragment_home, container, false)

        //<-- Slider -->
        vpSlider = view.findViewById(R.id.vp_slider)

        val arraySlider = ArrayList<Int>()
        arraySlider.add(R.drawable.carousel2)
        arraySlider.add(R.drawable.carousel3)
        arraySlider.add(R.drawable.carousel4)

        val adapterSlider = AdapterSlider(arraySlider, activity)
        vpSlider.adapter = adapterSlider

        //<-- RecyclerView -->
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        rvBaju = view.findViewById(R.id.rv_baju)

        val adapterBaju = AdapterProduk(ArrayBaju,activity)
        rvBaju.setHasFixedSize(true)
        rvBaju.layoutManager = lm
        rvBaju.adapter = adapterBaju


        //<-- RecyclerView -->
        val lym = LinearLayoutManager(activity)
        lym.orientation = LinearLayoutManager.HORIZONTAL
        rvBuku = view.findViewById(R.id.rv_buku)

        val adapterbuku = AdapterProduk(ArrayBuku,activity)
        rvBuku.setHasFixedSize(true)
        rvBuku.layoutManager = lym
        rvBuku.adapter = adapterbuku


        return view
    }

    val ArrayBaju : ArrayList<ModelProduk>get() {

        val arraybaju = ArrayList<ModelProduk>()

        //1
        val produkbaju1 = ModelProduk()
        produkbaju1.namaProduk = "Baju VNWear"
        produkbaju1.hargaProduk = "Rp. 99.000"
        produkbaju1.gambarProduk = R.drawable.baju_1

        //2
        val produkbaju2 = ModelProduk()
        produkbaju2.namaProduk = "Baju VSchool"
        produkbaju2.hargaProduk = "Rp. 99.000"
        produkbaju2.gambarProduk = R.drawable.baju_2

        //3
        val produkbaju3 = ModelProduk()
        produkbaju3.namaProduk = "Baju Kerah Hitam"
        produkbaju3.hargaProduk = "Rp. 129.000"
        produkbaju3.gambarProduk = R.drawable.kerah_1

        //4
        val produkbaju4 = ModelProduk()
        produkbaju4.namaProduk = "Baju Kerah Merah"
        produkbaju4.hargaProduk = "Rp. 129.000"
        produkbaju4.gambarProduk = R.drawable.kerah_2

        //5
        val produkbaju5 = ModelProduk()
        produkbaju5.namaProduk = "Jaket Merah"
        produkbaju5.hargaProduk = "Rp. 149.000"
        produkbaju5.gambarProduk = R.drawable.jaket_1

        //6
        val produkbaju6 = ModelProduk()
        produkbaju6.namaProduk = "Jaket Hitam"
        produkbaju6.hargaProduk = "Rp. 149.000"
        produkbaju6.gambarProduk = R.drawable.jaket_2

        arraybaju.add(produkbaju1)
        arraybaju.add(produkbaju2)
        arraybaju.add(produkbaju3)
        arraybaju.add(produkbaju4)
        arraybaju.add(produkbaju5)
        arraybaju.add(produkbaju6)

        return arraybaju
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    val ArrayBuku : ArrayList<ModelProduk>get() {

        val arraybuku = ArrayList<ModelProduk>()

        //1
        val produkbuku1 = ModelProduk()
        produkbuku1.namaProduk = "Buku 1"
        produkbuku1.hargaProduk = "Rp. 99.000"
        produkbuku1.gambarProduk = R.drawable.buku_1

        //2
        val produkbuku2 = ModelProduk()
        produkbuku2.namaProduk = "Buku 2"
        produkbuku2.hargaProduk = "Rp. 99.000"
        produkbuku2.gambarProduk = R.drawable.buku_2

        //3
        val produkbuku3 = ModelProduk()
        produkbuku3.namaProduk = "Buku 3"
        produkbuku3.hargaProduk = "Rp. 129.000"
        produkbuku3.gambarProduk = R.drawable.buku_4

        arraybuku.add(produkbuku1)
        arraybuku.add(produkbuku2)
        arraybuku.add(produkbuku3)

        return arraybuku
    }
}