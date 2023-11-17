package com.example.ukk_kasircafe.Data.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.DetailTransaksi
import com.example.ukk_kasircafe.R

class DetailAdapter (var dtl: List<DetailTransaksi>): RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var namamenu: TextView
        var harga: TextView

        init{
            namamenu = view.findViewById(R.id.namamenu)
            harga = view.findViewById(R.id.harga)
        }

    }
    lateinit var db : CafeDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        db = CafeDatabase.getInstance(parent.context)
        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_detail, parent, false)
        return DetailAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dtl.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.namamenu.text = db.cafeDao().getMenu(dtl.get(position).id_menu).nama_menu
        holder.harga.text = "Rp." +db.cafeDao().getMenu(dtl.get(position).id_menu).harga.toString()
    }

}