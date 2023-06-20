package com.example.gatewaygetaways.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatewaygetaways.R
import com.example.gatewaygetaways.fragment.WishlistFragment
import com.example.gatewaygetaways.modelclass.ModelClassForDestinaion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class LikeAdapter(var context:Context, var likepost: (Int,String) -> Unit) : RecyclerView.Adapter<LikeAdapter.MyViewHolder>() {
    var likelist =ArrayList<ModelClassForDestinaion>()
    lateinit var FirebaseDatabase : DatabaseReference
    lateinit var auth: FirebaseAuth

    class MyViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        var txt_like_image: ImageView = itemview.findViewById(R.id.txt_like_image)
        var txt_like_location: TextView = itemview.findViewById(R.id.txt_like_location)
        var txt_like_name: TextView = itemview.findViewById(R.id.txt_like_name)
        var txt_like_info: TextView = itemview.findViewById(R.id.txt_like_info)
        var txt_like_amount: TextView = itemview.findViewById(R.id.txt_like_amount)
        var txt_like_rateing: TextView = itemview.findViewById(R.id.txt_like_rateing)
        var loutlikedisplay: LinearLayout = itemview.findViewById(R.id.loutlikedisplay)
        var like_icon: ImageView = itemview.findViewById(R.id.like_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.like_data_display_itemfile, parent, false)
        var view = MyViewHolder(v)
        return view
    }

    override fun onBindViewHolder(holder: LikeAdapter.MyViewHolder, position: Int) {
        holder.txt_like_name.text = likelist[position].name
        holder.txt_like_location.text = likelist[position].location
        holder.txt_like_rateing.text = likelist[position].rateing
        holder.txt_like_amount.text = likelist[position].amount
        holder.txt_like_info.text = likelist[position].info

        Glide.with(context).load(likelist[position].image).placeholder(R.drawable.defalutimage).into(holder.txt_like_image)

        holder.like_icon.setImageResource(R.drawable.heartfill)

        holder.like_icon.setOnClickListener {
            likepost.invoke(0,likelist[position].name)
            likelist[position].status = 0

            Log.e("TAG", "like: "+likelist[position].status )

            delete(position)
        }

    }

    override fun getItemCount(): Int {
        return likelist.size
    }
    fun updatelist(likelist: java.util.ArrayList<ModelClassForDestinaion>) {
        this.likelist = ArrayList()
        this.likelist = likelist
        notifyDataSetChanged()
    }
    private fun delete(position: Int){
        likelist.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,likelist.size)
    }
}