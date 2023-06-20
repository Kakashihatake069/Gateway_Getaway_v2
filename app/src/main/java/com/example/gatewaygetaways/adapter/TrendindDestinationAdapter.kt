package com.example.gatewaygetaways.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatewaygetaways.modelclass.ModelClassForDestinaion
import com.example.gatewaygetaways.R
import com.example.gatewaygetaways.fragment.ExploreFragment

class TrendindDestinationAdapter(var context: ExploreFragment, var topdestinationlist: ArrayList<ModelClassForDestinaion>, var clickontopdestination : (ModelClassForDestinaion) -> Unit ) : RecyclerView.Adapter<TrendindDestinationAdapter.MyViewHolder>() {
    class MyViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var imgdestinationimg: ImageView = itemview.findViewById(R.id.imgdestinationimg)
        var txtdestinationname: TextView = itemview.findViewById(R.id.txtdestinationname)
        var louttopdestination: LinearLayout = itemview.findViewById(R.id.louttopdestination)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v =
            LayoutInflater.from(parent.context).inflate(R.layout.trendingdestinationitemfile, parent, false)
        var view = MyViewHolder(v)
        return view
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(topdestinationlist[position].image).into(holder.imgdestinationimg)
        holder.txtdestinationname.setText(topdestinationlist[position].name)

        holder.louttopdestination.setOnClickListener {
            clickontopdestination.invoke(topdestinationlist[position])
        }
    }

    override fun getItemCount(): Int {
       return topdestinationlist.size
    }
    fun updatelist(topdestinationlist: ArrayList<ModelClassForDestinaion>) {
        this.topdestinationlist = ArrayList()
        this.topdestinationlist = topdestinationlist
        notifyDataSetChanged()
    }
}