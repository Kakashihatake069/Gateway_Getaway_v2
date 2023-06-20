package com.example.gatewaygetaways.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gatewaygetaways.adapter.LikeAdapter
import com.example.gatewaygetaways.databinding.FragmentExploreBinding
import com.example.gatewaygetaways.databinding.FragmentWishlistBinding
import com.example.gatewaygetaways.modelclass.ModelClassForDestinaion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class WishlistFragment : Fragment() {
    lateinit var wishlistBinding: FragmentWishlistBinding
    lateinit var likeAdapter: LikeAdapter
    lateinit var rcvlike: RecyclerView
    lateinit var firebaseDatabase: DatabaseReference
    lateinit var auth: FirebaseAuth
    var likelist = ArrayList<ModelClassForDestinaion>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        wishlistBinding = FragmentWishlistBinding.inflate(layoutInflater, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance().getReference()
        auth = Firebase.auth
        initview()
        return wishlistBinding.root
    }

    private fun initview() {

        likeAdapter = LikeAdapter(requireContext(),{status,name->
            firebaseDatabase.child("user").child(auth.currentUser?.uid!!).child("user_records").child("status").child(name)
        })
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        wishlistBinding.rcvlike.layoutManager = layoutManager
        wishlistBinding.rcvlike.adapter = likeAdapter

        firebaseDatabase.child("user").child(auth.currentUser?.uid!!).child("user_records")
            .child("status").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                likelist.clear()

                for (postsnapshot in snapshot.children) {

                    val currentUser = postsnapshot.getValue(ModelClassForDestinaion::class.java)
                    currentUser?.let { likelist.add(it) }
                }
                likeAdapter.updatelist(likelist)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}