package com.example.gatewaygetaways.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatewaygetaways.R
import com.example.gatewaygetaways.modelclass.ModelClassForDestinaion

class SearchAdapter(var context: Context, var searchlist: ArrayList<ModelClassForDestinaion>) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    class MyViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var txtsearchimage: ImageView = itemview.findViewById(R.id.txtsearchimage)
        var txtsearchlocation: TextView = itemview.findViewById(R.id.txtsearchlocation)
        var txtsearchname: TextView = itemview.findViewById(R.id.txtsearchname)
        var txtsearchamount: TextView = itemview.findViewById(R.id.txtsearchamount)
        var loutsearch: LinearLayout = itemview.findViewById(R.id.loutsearch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v =
            LayoutInflater.from(parent.context).inflate(R.layout.searchitemfile, parent, false)

        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(searchlist[position].image).into(holder.txtsearchimage)
        holder.txtsearchlocation.setText(searchlist[position].location)
        holder.txtsearchname.setText(searchlist[position].name)
        holder.txtsearchamount.setText(searchlist[position].amount)

    }

    override fun getItemCount(): Int {
        return searchlist.size
    }

    fun updatelist(searchlist: java.util.ArrayList<ModelClassForDestinaion>) {
        this.searchlist = ArrayList()
        this.searchlist = searchlist
        notifyDataSetChanged()
    }

}