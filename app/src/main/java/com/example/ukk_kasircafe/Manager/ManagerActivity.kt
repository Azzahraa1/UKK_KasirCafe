package com.example.ukk_kasircafe.Manager

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.ukk_kasircafe.Data.CafeDatabase
import com.example.ukk_kasircafe.Data.Entity.CountMenu
import com.example.ukk_kasircafe.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class ManagerActivity : AppCompatActivity() {

    lateinit var barChart: BarChart
//    lateinit var lineChart: LineChart
    lateinit var db: CafeDatabase
    private var listFavorit: List<CountMenu> = listOf()

    lateinit var btnTransk: ImageButton
    lateinit var btnMeja: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        barChart = findViewById(R.id.barChart)
//        lineChart = findViewById(R.id.lineChart)
        db = CafeDatabase.getInstance(applicationContext)

        btnTransk = findViewById(R.id.buttonTransaksi)
        btnMeja = findViewById(R.id.buttonMeja)

        btnTransk.setOnClickListener {
            val inten = Intent(this@ManagerActivity,ManagerTransaksi::class.java)
            startActivity(inten)
        }
        btnMeja.setOnClickListener {
            val intem = Intent(this@ManagerActivity, ManagerMeja::class.java)
            startActivity(intem)
        }
    }

    override fun onResume() {
        super.onResume()
        barChart.data = null
//        lineChart.data = null

        val list: ArrayList<BarEntry> = ArrayList()

        listFavorit = db.cafeDao().getCountDetailTransaksi()
        var listSorted = listFavorit.sortedByDescending { it.count }

        for (i in 0..listSorted.size - 1) run {
            list.add(
                BarEntry(
                    i.toFloat(),
                    listSorted[i].count.toFloat(),
                    db.cafeDao().getMenu(listSorted[i].id_menu).nama_menu
                )
            )
        }

        val barDataSet = BarDataSet(list, "Menu Favorit")

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS, 255)
        barDataSet.valueTextColor = Color.BLACK

        val barData = BarData(barDataSet)

        barChart.data = barData
        barChart.animateY(2000)
        barChart.description.isEnabled = false
        barChart.invalidate()

        var legendEntries: ArrayList<String> = ArrayList()
        for (i in 0..list.size - 1) run {
            legendEntries.add(list[i].data.toString())
        }

        var yAxis: YAxis = barChart.axisLeft
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 50f
        yAxis.axisLineWidth = 2f
        yAxis.axisLineColor = Color.BLACK
        yAxis.setLabelCount(11, true)

        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(legendEntries)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.granularity = 1f
        barChart.xAxis.isGranularityEnabled = true
        barChart.axisRight.isEnabled = false

//        lineChart.axisRight.setDrawLabels(false)
//        lineChart.description = null
//
//        var xAxisLine: XAxis = lineChart.xAxis
//        var yAxisLine: YAxis = lineChart.axisLeft
//        xAxisLine.position = XAxis.XAxisPosition.BOTTOM
//
//        var xValues: ArrayList<String> = ArrayList()
//        var listLine = db.cafeDao().getCountTransaksi()
//        var entries: ArrayList<Entry> = ArrayList()
//        for (i in 0..listLine.size - 1) run {
//            xValues.add(listLine[i].tgl_transaksi)
//            entries.add(Entry(i.toFloat()     `, listLine[i].count.toFloat()))
//        }

//        var lineDataSet: LineDataSet = LineDataSet(entries, "Penjualan")
//        lineDataSet.color = Color.BLACK
//
//        var lineData = LineData(lineDataSet)
//        lineChart.data = lineData
//        lineChart.invalidate()
//
//        xAxisLine.valueFormatter = IndexAxisValueFormatter(xValues)
//        xAxisLine.textSize = 8f
//        xAxisLine.labelCount = 4
//        xAxisLine.granularity = 1f
//
//        yAxisLine.axisMinimum = 0f
//        yAxisLine.axisMaximum = 10f
//        yAxisLine.axisLineWidth = 2f
//        yAxisLine.axisLineColor = Color.BLACK
//        yAxisLine.labelCount = 10
    }
}



//        lineChrt.axisRight.setDrawLabels(false)
//        lineChrt.description = null
//
//        var xAxisLine: XAxis = lineChrt.xAxis
//        var yAxisLine : YAxis = lineChrt.axisLeft
//        xAxisLine.position =XAxis.XAxisPosition.BOTTOM
//
//        var xValue: ArrayList<String> = ArrayList()
//        var listLine = db.cafeDao().getCountTransaksi()
//        var entries: ArrayList<Entry> = ArrayList()
//        for (i in 0..listLine.size - 1) run {
//            xValue.add(listLine[1].tgl_transaksi)
//            entries.add(Entry(i.toFloat(), listLine[i].count.toFloat()))
//        }
//        var lineDataSet: LineDataSet = LineDataSet(entries, "Penjualan")
//        lineDataSet.color = Color.BLACK
//
//        var lineData = LineData(lineDataSet)
//        lineChrt.data = lineData
//        lineChrt.invalidate()
//
//        xAxisLine.valueFormatter =IndexAxisValueFormatter(xValue)
//        xAxisLine.textSize = 8f
//        xAxisLine.labelCount = 4
//        xAxisLine.granularity = 1f
//
//        yAxisLine.axisMinimum = 0f
//        yAxisLine.axisMaximum = 10f
//        yAxisLine.axisLineWidth = 2f
//        yAxisLine.axisLineColor = Color.BLACK
//        yAxisLine.labelCount = 10
