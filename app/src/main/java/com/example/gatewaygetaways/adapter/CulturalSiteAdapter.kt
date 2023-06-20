package com.example.gatewaygetaways.adapter

import android.util.Log
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

class CulturalSiteAdapter (var context: ExploreFragment, var culturalsitelist: ArrayList<ModelClassForDestinaion>,var clickculturalsite :(ModelClassForDestinaion) -> Unit, var likeculturalsitedata : (Int,String) -> Unit ) : RecyclerView.Adapter<CulturalSiteAdapter.MyViewHolder>()  {
    class MyViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
       var  imgculturalimage: ImageView = itemview.findViewById(R.id.imgculturalimage)
        var txtdetails: TextView = itemview.findViewById(R.id.txtdetails)
        var loutculturalsite : LinearLayout = itemview.findViewById(R.id.loutculturalsite)
        var culturallikeicon : ImageView = itemview.findViewById(R.id.culturallikeicon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.culturalsiteitemfileone, parent, false)
        var view = MyViewHolder(v)
        return view
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(culturalsitelist[position].image).into(holder.imgculturalimage)
        holder.txtdetails.setText(culturalsitelist[position].name)

        holder.loutculturalsite.setOnClickListener {
            clickculturalsite.invoke(culturalsitelist[position])
        }

        // like invoke
        if(culturalsitelist[position].status==1)
        {
            holder.culturallikeicon.setImageResource(R.drawable.heartfill)
        } else {
            holder.culturallikeicon.setImageResource(R.drawable.heart)
        }

        holder.culturallikeicon.setOnClickListener {

            if (culturalsitelist[position].status == 1) {
                likeculturalsitedata.invoke(0,culturalsitelist[position].name)
                holder.culturallikeicon.setImageResource(R.drawable.heart)
                culturalsitelist[position].status = 0
                Log.e(
                    "text",
                    "display:" + culturalsitelist[position].status.toString() + " " + culturalsitelist[position].name
                )
            } else {
                likeculturalsitedata.invoke(1,culturalsitelist[position].name)
                holder.culturallikeicon.setImageResource(R.drawable.heartfill)
                culturalsitelist[position].status = 1
            }
        }
    }

    override fun getItemCount(): Int {
        return culturalsitelist.size
    }
}