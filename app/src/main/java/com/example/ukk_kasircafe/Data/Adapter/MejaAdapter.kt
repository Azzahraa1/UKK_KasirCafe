package com.example.ukk_kasircafe.Data.Adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Entity.Meja
import com.example.ukk_kasircafe.R

class MejaAdapter (var list: List<Meja>):RecyclerView.Adapter<MejaAdapter.ViewHolder>(){
    private lateinit var dialog: Dialog

    fun setDialog(dialog: Dialog){
        this.dialog = dialog
    }
    interface Dialog{
        fun onClick(position: Int)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var nomor_meja: TextView

        init {
            nomor_meja = view.findViewById(R.id.nomeja)

            view.setOnClickListener {
                dialog.onClick(layoutPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_meja, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nomor_meja.text = list[position].nomor_meja
    }

    fun getItem(position: Int):Meja{
        return list.get(position)
    }

}