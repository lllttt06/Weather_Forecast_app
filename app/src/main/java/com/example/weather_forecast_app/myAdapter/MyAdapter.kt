package com.example.weather_forecast_app.myAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_forecast_app.R
import com.example.weather_forecast_app.model.ApiResponse
import kotlinx.android.synthetic.main.item.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.Comparator
import kotlin.math.roundToInt
import kotlin.reflect.typeOf

//import com.example.weather_forecast_app.ui.MyViewHolder

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    private var myList = emptyList<ApiResponse>()
    private val timeComparator: Comparator<ApiResponse> = compareBy<ApiResponse> { it.Time.toInt() }
    //private val LocalTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd HH"))
    private val iconsDetector = mapOf(
        "01d" to R.drawable.clear_sky,
        "01n" to R.drawable.clear_sky,
        "02d" to R.drawable.few_clouds,
        "02n" to R.drawable.few_clouds,
        "03d" to R.drawable.scattered_clouds,
        "03n" to R.drawable.scattered_clouds,
        "04d" to R.drawable.broken_clouds,
        "04n" to R.drawable.broken_clouds,
        "09d" to R.drawable.shower_rain,
        "09n" to R.drawable.shower_rain,
        "10d" to R.drawable.rain,
        "10n" to R.drawable.rain,
        "11d" to R.drawable.thunderstorm,
        "11n" to R.drawable.thunderstorm,
        "13d" to R.drawable.snow,
        "13n" to R.drawable.snow,
        "50d" to R.drawable.mist,
        "50n" to R.drawable.mist)


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun getItemCount(): Int {
        //Log.d("MyAdapter", "myList.size="+myList.size.toString() )
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val temp = convertTemp(myList[position].temp)
        val iconLabel = iconsDetector[myList[position].weather]
        holder.itemView.info1.text = temp
        holder.itemView.info2.text = myList[position].clouds
        if (iconLabel != null) {
            holder.itemView.info3.setImageResource(iconLabel)
        }
        holder.itemView.info4.text = myList[position].humidity
        //holder.itemView.info5.text = //myList[position].Time
    }

    fun setData(newList: List<ApiResponse>){
        myList = newList.sortedWith(timeComparator)
        Log.d("MyAdapter", "myList:$myList")
        notifyDataSetChanged()
    }

    private fun convertTemp(absoluteTemp: String): String {
        var relativeTemp = absoluteTemp.toFloat() - 273.15
        return relativeTemp.roundToInt().toString()
    }

}





/*
/**
 * Adapter の実装（データを結びつけ、ViewHolder の生成とデータ反映を行う）
 */
class MyAdapter(private val data: IntArray) : RecyclerView.Adapter<MyViewHolder>() {
    /** 表示用データの要素数（ここでは IntArray のサイズ） */
    override fun getItemCount(): Int = data.size

    /** 新しく ViewHolder オブジェクトを生成するための実装 */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater.inflate(R.layout.item, parent, false))
    }

    /** position の位置のデータを使って、表示内容を適切に設定（更新）する */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val num = data[position]
        holder.info1.text = "Element-$num"
    }
}*/