package com.example.gatewaygetaways.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.window.OnBackInvokedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatewaygetaways.R
import com.example.gatewaygetaways.adapter.BookingTripAdapter
import com.example.gatewaygetaways.adapter.JungleSafariAdapter
import com.example.gatewaygetaways.adapter.LikeAdapter
import com.example.gatewaygetaways.adapter.MountainAdapter
import com.example.gatewaygetaways.databinding.FragmentBookingBinding
import com.example.gatewaygetaways.databinding.FragmentExploreBinding
import com.example.gatewaygetaways.databinding.FragmentWishlistBinding
import com.example.gatewaygetaways.modelclass.ModelClassForDestinaion
import com.example.gatewaygetaways.modelclass.ModelClassForPlaceDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class BookingFragment : Fragment() {
    lateinit var bookingBinding: FragmentBookingBinding
    lateinit var firebaseDatabase: DatabaseReference
    lateinit var addToCartAdapter: AddToCartAdapter
    lateinit var rcvbookings: RecyclerView
    lateinit var auth: FirebaseAuth
    var cartlist = ArrayList<ModelClassForDestinaion>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bookingBinding = FragmentBookingBinding.inflate(layoutInflater, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth
        initview()

        return bookingBinding.root
    }


    private fun initview() {

        addToCartAdapter = AddToCartAdapter(requireContext(),{cart,name->
            firebaseDatabase.child("cart_Data").child(auth.currentUser?.uid!!).child("cart_records").child("list").child(name)
        })

        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bookingBinding.rcvbookings.layoutManager = layoutManager
        bookingBinding.rcvbookings.adapter = addToCartAdapter

        firebaseDatabase.child("user").child(auth.currentUser?.uid!!).child("user_records")
            .child("status").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    cartlist.clear()

                    for (postsnapshot in snapshot.children) {

                        val currentUser = postsnapshot.getValue(ModelClassForDestinaion::class.java)
                        currentUser?.let { cartlist.add(it) }
                    }
                    addToCartAdapter.updatecart(cartlist)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }
}