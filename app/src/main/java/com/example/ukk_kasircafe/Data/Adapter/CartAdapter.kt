package com.example.ukk_kasircafe.Data.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Entity.Menu
import com.example.ukk_kasircafe.R

class CartAdapter (var items: List<Menu>): RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var txtMenu : TextView
        var txtHarga: TextView

        init{
            txtMenu = view.findViewById(R.id.namamenu)
            txtHarga = view.findViewById(R.id.harga)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_cart, parent, false)
        return CartAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = items[position]
        holder.txtMenu.text = items[position].nama_menu
        holder.txtHarga.text = "Rp." + items[position].harga
    }


}