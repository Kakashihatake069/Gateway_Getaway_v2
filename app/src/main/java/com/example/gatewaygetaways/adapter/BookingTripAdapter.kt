package com.example.gatewaygetaways.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatewaygetaways.R
import com.example.gatewaygetaways.fragment.BookingFragment
import com.example.gatewaygetaways.modelclass.ModelClassForDestinaion

class BookingTripAdapter (var context: BookingFragment, var bookdestinationlist: ArrayList<ModelClassForDestinaion>) : RecyclerView.Adapter<BookingTripAdapter.MyViewHolder>() {
    class MyViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {

        var imgplaceimage: ImageView = itemview.findViewById(R.id.imgplaceimage)
        var txtplacename: TextView = itemview.findViewById(R.id.txtplacename)
        var txtrateing: TextView = itemview.findViewById(R.id.txtrateing)
        var txtplaceamount: TextView = itemview.findViewById(R.id.txtplaceamount)
        var txtabouttheplace: TextView = itemview.findViewById(R.id.txtabouttheplace)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.bookingdestinationitemfile, parent, false)
        val view = MyViewHolder(v)
        return view
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
            Glide.with(context).load(bookdestinationlist[position].image).into(holder.imgplaceimage)
            holder.txtplacename.setText(bookdestinationlist[position].name)
            holder.txtrateing.setText(bookdestinationlist[position].rateing)
            holder.txtplaceamount.setText(bookdestinationlist[position].amount)
            holder.txtabouttheplace.setText(bookdestinationlist[position].info)
    }

    override fun getItemCount(): Int {
            return bookdestinationlist.size
    }

}