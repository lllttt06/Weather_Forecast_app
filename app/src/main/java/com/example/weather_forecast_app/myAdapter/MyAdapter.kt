package com.example.weather_forecast_app.myAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_forecast_app.R
import com.example.weather_forecast_app.model.ApiResponse
import kotlinx.android.synthetic.main.item.view.*

//import com.example.weather_forecast_app.ui.MyViewHolder

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    private var myList = emptyList<ApiResponse>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("MyAdapter", "myList.size="+myList.size.toString() )
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.info1.text = myList[position].temp
        Log.d("MyAdapter", myList[position].temp)
        holder.itemView.info2.text = myList[position].clouds
        holder.itemView.info3.text = myList[position].weather
        holder.itemView.info4.text = myList[position].wind
    }

    fun setData(newList: List<ApiResponse>){
        myList = newList
        notifyDataSetChanged()
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