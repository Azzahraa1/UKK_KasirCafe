package com.example.ukk_kasircafe.Data.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.Transaksi
import com.example.ukk_kasircafe.R

class TransaksiAdapter (var trans: List<Transaksi>): RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var txtNm: TextView
        var txtWkt: TextView
        var txtTgl: TextView
        var txtStts: TextView
        var txtMj: TextView
        var rltv: RelativeLayout

        init {
            txtNm = view.findViewById(R.id.namaPelangganList)
            txtWkt = view.findViewById(R.id.wktuList)
            txtTgl = view.findViewById(R.id.tglList)
            txtStts = view.findViewById(R.id.statusList)
            txtMj = view.findViewById(R.id.mejaList)
            rltv = view.findViewById(R.id.relative)
        }

    }
    lateinit var db: CafeDatabase
    var onHlder: ((Transaksi) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        db = CafeDatabase.getInstance(parent.context)
        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_transaksi, parent, false)
        return TransaksiAdapter.ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return trans.size
    }
    fun getItem(position: Int): Transaksi{
        return trans.get(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNm.text = trans[position].nama_pelanggan
        holder.txtWkt.text = trans[position].waktu_transaksi
        holder.txtTgl.text = trans[position].tgl_transaksi
        holder.txtStts.text = trans[position].status
        holder.txtMj.text = db.cafeDao().getMeja(trans[position].id_meja).nomor_meja
        holder.rltv.setOnClickListener {
            onHlder?.invoke(trans[position])
        }
    }
}