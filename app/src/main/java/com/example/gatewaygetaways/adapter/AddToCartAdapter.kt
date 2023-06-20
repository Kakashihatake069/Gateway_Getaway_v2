package com.example.gatewaygetaways.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatewaygetaways.R
import com.example.gatewaygetaways.modelclass.ModelClassForDestinaion

class AddToCartAdapter(var context: Context, var AddToCart: (Int,String) -> Unit) : RecyclerView.Adapter<AddToCartAdapter.MyViewHolder>() {
    var cartlist =ArrayList<ModelClassForDestinaion>()
    class MyViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        var img_cart_image: ImageView = itemview.findViewById(R.id.img_cart_image)
        var txt_cart_location: TextView = itemview.findViewById(R.id.txt_cart_location)
        var txtcartname: TextView = itemview.findViewById(R.id.txtcartname)
        var txt_cart_amount: TextView = itemview.findViewById(R.id.txt_cart_amount)
        var imgAddCart: ImageView = itemview.findViewById(R.id.imgiconCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.add_to_cart_iemfile, parent, false)
        var view = MyViewHolder(v)
        return view
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtcartname.text = cartlist[position].name
        holder.txt_cart_location.text = cartlist[position].location
        holder.txt_cart_amount.text = cartlist[position].amount

        Glide.with(context).load(cartlist[position].image).placeholder(R.drawable.defalutimage).into(holder.img_cart_image)

        holder.imgAddCart.setImageResource(R.drawable.shoppingcart)

        holder.imgAddCart.setOnClickListener {
            AddToCart.invoke(0,cartlist[position].name)
            cartlist[position].cart = 0

            Log.e("TAG", "like: "+cartlist[position].status )

            delete(position)
        }
    }
    override fun getItemCount(): Int {
        return cartlist.size
    }
    private fun delete(position: Int){
        cartlist.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,cartlist.size)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updatecart(cartlist:ArrayList<ModelClassForDestinaion>) {
        this.cartlist = ArrayList()
        this.cartlist = cartlist
        notifyDataSetChanged()
    }

}