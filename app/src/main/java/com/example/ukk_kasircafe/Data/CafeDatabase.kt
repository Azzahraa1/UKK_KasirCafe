package com.example.ukk_kasircafe.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ukk_kasircafe.Data.Entity.DetailTransaksi
import com.example.ukk_kasircafe.Data.Entity.Meja
import com.example.ukk_kasircafe.Data.Entity.Menu
import com.example.ukk_kasircafe.Data.Entity.Transaksi
import com.example.ukk_kasircafe.Data.Entity.User

@Database(
    entities = [
        User::class,
        Meja::class,
        Menu::class,
        Transaksi::class,
        DetailTransaksi::class
    ],
    version = 4
)

abstract class CafeDatabase:RoomDatabase(){
    abstract fun cafeDao():CafeDao

    companion object{
        private var instance:CafeDatabase? = null

        fun getInstance(context: Context): CafeDatabase{
            if (instance==null){
                instance = Room.databaseBuilder(context, CafeDatabase::class.java, "cafe_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}