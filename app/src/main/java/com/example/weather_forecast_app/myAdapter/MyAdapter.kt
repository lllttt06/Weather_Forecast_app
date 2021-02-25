package com.example.weather_forecast_app.myAdapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_forecast_app.R
import com.example.weather_forecast_app.model.ApiResponse
import com.example.weather_forecast_app.utils.IconMap.iconsDetector
import kotlinx.android.synthetic.main.item.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

//import com.example.weather_forecast_app.ui.MyViewHolder

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    private var myList = emptyList<ApiResponse>()
    private var localTime = LocalDateTime.now()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun getItemCount(): Int {
        //Log.d("MyAdapter", "myList.size="+myList.size.toString() )
        return myList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val temp = convertTemp(myList[position].temp)
        val iconLabel = iconsDetector[myList[position].weather]
        val localTimeNew = localTime.plusHours(position.toLong()).format(DateTimeFormatter.ofPattern("HH"))
        holder.itemView.index3.text = "$temp℃"
        holder.itemView.index4.text = myList[position].clouds + "%"
        if (iconLabel != null) {
            holder.itemView.index2.setImageResource(iconLabel)
        }
        holder.itemView.index5.text = myList[position].humidity + "%"
        holder.itemView.index1.text = "$localTimeNew:00"
    }

    fun setData(newList: List<ApiResponse>){
        myList = newList
        Log.d("MyAdapter", "myList:$myList")
        notifyDataSetChanged()
    }

    private fun convertTemp(absoluteTemp: String): String {
        var relativeTemp = absoluteTemp.toFloat() - 273.15
        return relativeTemp.roundToInt().toString()
    }

}
