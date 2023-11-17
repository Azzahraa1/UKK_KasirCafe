package com.example.ukk_kasircafe.Data.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Entity.Menu
import com.example.ukk_kasircafe.R

class MenusAdapter (var items: List<Menu>): RecyclerView.Adapter<MenusAdapter.ViewHolder>(){
    var onAddClick: ((Menu)-> Unit)? = null
    var onSubClick: ((Menu)-> Unit)? = null
    lateinit var jumlahText: TextView
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var txtMenu: TextView
        var txtHarga: TextView
        var jumlah: TextView
        var addButton: ImageButton
        var substract: ImageButton

        init {
            txtMenu = view.findViewById(R.id.namamenu)
            txtHarga = view.findViewById(R.id.harga)
            jumlah = view.findViewById(R.id.txtJumlah)
            addButton = view.findViewById(R.id.buttonAdd)
            substract = view.findViewById(R.id.buttonSubstract)




        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_menu, parent, false)
        return ViewHolder(view)    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = items[position]
        holder.txtMenu.text = items[position].nama_menu
        holder.txtHarga.text = "Rp."+ items[position].harga
        jumlahText = holder.jumlah
        holder.addButton.setOnClickListener {
            var i:Int = holder.jumlah.text.toString().toInt()+1
            holder.jumlah.text = ""+ i
            onAddClick?.invoke(menu)
        }

        holder.substract.setOnClickListener {
            var i:Int = holder.jumlah.text.toString().toInt()
            if(i>0){
                i -= 1
                holder.jumlah.text = ""+ i
                onSubClick?.invoke(menu)
            }
        }    }

}