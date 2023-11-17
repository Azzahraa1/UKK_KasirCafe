package com.example.ukk_kasircafe.Data.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukk_kasircafe.Data.Entity.User
import com.example.ukk_kasircafe.R

class UserAdapter(var items: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var dialog: Dialog

    fun setDialog(dialog: Dialog){
        this.dialog = dialog
    }
    interface Dialog{
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = items[position]
        holder.txtnamauser.text = items[position].nama
        holder.txtemailuser.text = items[position].email
        holder.txtroleuser.text = items[position].role
        holder.txtpwuser.text = items[position].password
    }
    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        var txtnamauser: TextView
        var txtemailuser: TextView
        var txtroleuser: TextView
        var txtpwuser: TextView

        init {
            txtnamauser = view.findViewById(R.id.namauser)
            txtemailuser = view.findViewById(R.id.emailuser)
            txtroleuser = view.findViewById(R.id.roleuser)
            txtpwuser = view.findViewById(R.id.pwuser)

            view.setOnClickListener {
                dialog.onClick(layoutPosition)
            }
        }

    }
}
