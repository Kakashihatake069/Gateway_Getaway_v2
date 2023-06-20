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
import com.example.gatewaygetaways.modelclass.ModelClassForPlaceDetails

class JungleSafariAdapter(var context: ExploreFragment,var junglelist: ArrayList<ModelClassForDestinaion>, var jungleclick : (ModelClassForDestinaion) -> Unit, var likejungledata : (Int,String) -> Unit, var addtocartjungle : (Int,String) -> Unit ) :
    RecyclerView.Adapter<JungleSafariAdapter.MyViewHolder>() {
    class MyViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var txtjungleimage: ImageView = itemview.findViewById(R.id.txtjungleimage)
        var txtjunglelocation: TextView = itemview.findViewById(R.id.txtjunglelocation)
        var txtjunglename: TextView = itemview.findViewById(R.id.txtjunglename)
        var txtjungleamount: TextView = itemview.findViewById(R.id.txtjungleamount)
        var loutjunglesafari: LinearLayout = itemview.findViewById(R.id.loutjunglesafari)
        var likeiconjungle: ImageView = itemview.findViewById(R.id.likeiconjungle)
        var jungle_addtocart: ImageView = itemview.findViewById(R.id.jungle_addtocart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.junglesafariitemfile, parent, false)
        var view = MyViewHolder(v)
        return view
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
       Glide.with(context).load(junglelist[position].image).into(holder.txtjungleimage)
        holder.txtjunglelocation.setText(junglelist[position].location)
        holder.txtjunglename.setText(junglelist[position].name)
        holder.txtjungleamount.setText(junglelist[position].amount)

        holder.loutjunglesafari.setOnClickListener {
            jungleclick.
            invoke(junglelist[position])
        }

        // like invoke
        if(junglelist[position].status==1)
        {
            holder.likeiconjungle.setImageResource(R.drawable.heartfill)
        } else {
            holder.likeiconjungle.setImageResource(R.drawable.heart)
        }

        holder.likeiconjungle.setOnClickListener {

            if (junglelist[position].status == 1) {
                likejungledata.invoke(0,junglelist[position].name)
                holder.likeiconjungle.setImageResource(R.drawable.heart)
                junglelist[position].status = 0
                Log.e(
                    "text",
                    "display:" + junglelist[position].status.toString() + " " + junglelist[position].name
                )
            } else {
                likejungledata.invoke(1,junglelist[position].name)
                holder.likeiconjungle.setImageResource(R.drawable.heartfill)
                junglelist[position].status = 1
            }
        }

        // cart invoke

        if(junglelist[position].cart==1)
        {
            holder.jungle_addtocart.setImageResource(R.drawable.addtocart2)
        } else {
            holder.jungle_addtocart.setImageResource(R.drawable.shoppingcart)
        }

        holder.jungle_addtocart.setOnClickListener {

            if (junglelist[position].cart == 1) {
                addtocartjungle.invoke(0,junglelist[position].name)
                holder.jungle_addtocart.setImageResource(R.drawable.shoppingcart)
                junglelist[position].cart = 0
                Log.e(
                    "text",
                    "displaycart:" + junglelist[position].cart.toString() + " " + junglelist[position].name
                )
            } else {
                addtocartjungle.invoke(1,junglelist[position].name)
                holder.jungle_addtocart.setImageResource(R.drawable.addtocart2)
                junglelist[position].cart = 1
            }
        }

    }

    override fun getItemCount(): Int {
        return junglelist.size
    }

    fun updatelist(junglelist: java.util.ArrayList<ModelClassForDestinaion>) {
        this.junglelist = ArrayList()
        this.junglelist = junglelist
        notifyDataSetChanged()
    }
}