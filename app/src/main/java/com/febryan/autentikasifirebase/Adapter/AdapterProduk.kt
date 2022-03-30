package com.febryan.autentikasifirebase.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.febryan.autentikasifirebase.R
import com.febryan.autentikasifirebase.model.ModelProduk

class AdapterProduk (var data:ArrayList<ModelProduk>, var context: Activity?) : RecyclerView.Adapter<AdapterProduk.MyViewHolder>(){
    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val namaProduk = view.findViewById<TextView>(R.id.tv_nama)
        val hargaProduk = view.findViewById<TextView>(R.id.tv_harga)
        val gambarProduk = view.findViewById<ImageView>(R.id.img_produk)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.namaProduk.text = data[position].namaProduk
        holder.hargaProduk.text = data[position].hargaProduk
        holder.gambarProduk.setImageResource(data[position].gambarProduk)
    }

    override fun getItemCount(): Int {
        return data.size
    }


}