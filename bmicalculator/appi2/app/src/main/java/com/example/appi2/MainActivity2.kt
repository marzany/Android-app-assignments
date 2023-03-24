package com.example.appi2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate


class MainActivity2 : AppCompatActivity() {
    lateinit var barChart : BarChart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main2)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val tulokset = intent.getStringArrayListExtra("bmit")
        barChart = findViewById(R.id.chart)

        val entries: ArrayList<BarEntry> = ArrayList()

        if (tulokset != null) {
            for (i in tulokset.indices) {
                val score = tulokset?.get(i)
                if (score != null) {
                    entries.add(BarEntry(i.toFloat(), score.toFloat()))
                }
            }
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = BarData(barDataSet)
        barChart.data = data

        barChart.invalidate()
        recyclerView.layoutManager = LinearLayoutManager(this)

    }


}