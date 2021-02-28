package com.example.weather_forecast_app.myAdapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_forecast_app.R
import com.example.weather_forecast_app.model.ApiResponse
import com.example.weather_forecast_app.utils.IconMap.weatherIconsDetector
import kotlinx.android.synthetic.main.item.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){

    private var myList = emptyList<ApiResponse>()
    private var localTime = LocalDateTime.now()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val temp = convertTemp(myList[position].temp)
        val iconLabel = weatherIconsDetector[myList[position].weather]
        val localTimeNew = localTime.plusHours(position.toLong()).format(DateTimeFormatter.ofPattern("HH"))

        holder.itemView.info3.text = myList[position].clouds
        holder.itemView.info4.text = myList[position].wind
        if (iconLabel != null) {
            holder.itemView.info2.setImageResource(iconLabel)
        }
        holder.itemView.info5.text = myList[position].humidity
        holder.itemView.info1.text = "$localTimeNew:00"
    }

    fun setData(newList: List<ApiResponse>){
        myList = newList
        notifyDataSetChanged()
    }

    private fun convertTemp(absoluteTemp: String): String {
        var relativeTemp = absoluteTemp.toFloat() - 273.15
        return relativeTemp.roundToInt().toString()
    }

}
