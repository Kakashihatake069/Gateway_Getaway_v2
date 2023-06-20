package com.example.gatewaygetaways.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatewaygetaways.R
import com.example.gatewaygetaways.activity.DisplayplaceActivity
import com.example.gatewaygetaways.modelclass.ModelClassForDestinaion


class HotelAdapter(var context: DisplayplaceActivity , var hotellist: ArrayList<ModelClassForDestinaion>, var hotel : (ModelClassForDestinaion) -> Unit) : RecyclerView.Adapter<HotelAdapter.MyViewHolder>(){
    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        var txthotelimage: ImageView = itemview.findViewById(R.id.txthotelimage)
        var txthotellocation: TextView = itemview.findViewById(R.id.txthotellocation)
        var txthotelname: TextView = itemview.findViewById(R.id.txthotelname)
        var txthotelamount: TextView = itemview.findViewById(R.id.txthotelamount)
        var louthotel: LinearLayout = itemview.findViewById(R.id.louthotel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v =
            LayoutInflater.from(parent.context).inflate(R.layout.hotelitemfile, parent, false)
        var view = MyViewHolder(v)
        return view
    }

    override fun onBindViewHolder(holder: HotelAdapter.MyViewHolder, position: Int) {
        Glide.with(context).load(hotellist[position].image).into(holder.txthotelimage)
        holder.txthotellocation.setText(hotellist[position].location)
        holder.txthotelname.setText(hotellist[position].name)
        holder.txthotelamount.setText(hotellist[position].amount)

        holder.louthotel.setOnClickListener {
            hotel.invoke(hotellist[position])
        }
    }

    override fun getItemCount(): Int {
        return hotellist.size
    }
}