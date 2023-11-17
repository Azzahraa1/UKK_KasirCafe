package com.example.ukk_kasircafe.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ukk_kasircafe.Data.Entity.CountMenu
import com.example.ukk_kasircafe.Data.Entity.CountTransaksi
import com.example.ukk_kasircafe.Data.Entity.DetailTransaksi
import com.example.ukk_kasircafe.Data.Entity.Meja
import com.example.ukk_kasircafe.Data.Entity.Menu
import com.example.ukk_kasircafe.Data.Entity.Transaksi
import com.example.ukk_kasircafe.Data.Entity.User

@Dao
interface CafeDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailTransaksi(detailTransaksi: DetailTransaksi)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMenu(menu: Menu)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeja(meja: Meja)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaksi(transaksi: Transaksi)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE email = :mEmail AND password = :mPass")
    fun login(mEmail: String, mPass: String): List<User>

    @Query("Select * From Menu")
    fun getAllMenu(): List <Menu>

    @Query("SELECT * From User")
    fun getAllUser(): List<User>

    @Query("SELECT * FROM Transaksi")
    fun getAllTransaksi(): List<Transaksi>

    @Query("Select * From Meja")
    fun getAllMeja(): List <Meja>

    @Query("SELECT * FROM DetailTransaksi WHERE id_transaksi = :idTransaksi")
    fun getDetailTransaksifromTransaksi(idTransaksi: Int): List<DetailTransaksi>

    @Query("SELECT * FROM Menu WHERE jenis = :jenisMenu")
    fun getMenuFilterJenis(jenisMenu: String): List<Menu>

    @Delete
    fun deleteMenu(menu: Menu)

    @Update
    fun update(menu: Menu)

    @Update
    fun update(meja: Meja)

    @Query("UPDATE Menu SET nama_menu = :namaMenu, jenis = :Jenis, harga = :Harga WHERE id_menu = :id")
    fun updateMenu(namaMenu: String, Jenis: String, Harga: Int, id: Int)

    @Query("UPDATE User SET nama = :namaUser, email = :Email, password = :Password, role = :Role WHERE id_user = :id" )
    fun updateUser(namaUser: String, Email: String, Password: String, Role: String, id: Int )

    @Query("SELECT * FROM Menu WHERE id_menu = :Id")
    fun getMenu(Id: Int): Menu

    @Query("SELECT * FROM User WHERE id_user = :Id")
    fun getUser(Id: Int): User

   @Query("UPDATE Meja SET nomor_meja = :nomorMeja, used = :Used WHERE id_meja = :id")
   fun updateMeja(nomorMeja: String, id: Int, Used: Boolean)

    @Delete
    fun deleteMeja(meja: Meja)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT nomor_meja FROM Meja WHERE used = 0")
    fun getAllNomorMeja(): List<String>

    @Query("SELECT id_meja FROM Meja WHERE nomor_meja = :nomorMeja")
    fun getIdMejaFromNo(nomorMeja: String): Int

    @Query("SELECT id_transaksi FROM Transaksi WHERE tgl_transaksi = :tglTransaksi AND id_user = :idUser AND id_meja = :idMeja AND nama_pelanggan = :namaPelanggan AND status = :Status")
    fun getIdTransaksiFromOther(tglTransaksi: String, idUser: Int,  idMeja: Int, namaPelanggan: String, Status: String): Int

    @Query("SELECT * FROM Meja WHERE id_meja = :id")
    fun getMeja(id: Int): Meja

    @Delete
    fun deleteTransaksi(transaksi: Transaksi)

    @Delete
    fun deleteDetailTransaksi(detailTransaksi: DetailTransaksi)


    @Query("UPDATE Transaksi SET nama_pelanggan = :namaPelanggan, status = :Status, tunai= :Tunai WHERE id_transaksi = :idTransaksi")
    fun updateTransaksi(namaPelanggan: String, Status: String, Tunai: Int, idTransaksi: Int)
    @Query("SELECT * FROM DetailTransaksi WHERE id_transaksi = :Id")
    fun getDetailTransaksi(Id: Int): List<DetailTransaksi>

    @Query("SELECT * FROM Transaksi WHERE id_transaksi = :Id")
    fun getTransaksi(Id: Int): Transaksi

    @Query("SELECT COUNT(id_detail_transaksi) as count, id_menu FROM DetailTransaksi GROUP BY id_menu")
    fun getCountDetailTransaksi(): List<CountMenu>

    @Query("SELECT COUNT(id_transaksi) as count, tgl_transaksi FROM Transaksi GROUP BY tgl_transaksi")
    fun getCountTransaksi(): List<CountTransaksi>
}
