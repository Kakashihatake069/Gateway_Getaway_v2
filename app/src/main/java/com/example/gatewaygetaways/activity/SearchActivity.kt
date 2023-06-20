package com.example.gatewaygetaways.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gatewaygetaways.adapter.SearchAdapter
import com.example.gatewaygetaways.databinding.ActivitySearchBinding
import com.example.gatewaygetaways.modelclass.ModelClassForDestinaion
import com.google.firebase.database.*


class SearchActivity : AppCompatActivity() {
    lateinit var firebaseDatabase: DatabaseReference
    lateinit var searchBinding: ActivitySearchBinding
    var searchlist = ArrayList<ModelClassForDestinaion>()
    lateinit var rcvsearch: RecyclerView
    lateinit var searchadapter: SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)

        firebaseDatabase = FirebaseDatabase.getInstance().reference

        searchBinding.imgsearch.setOnClickListener {
            searchBinding.edtsearchBox
            initview()
        }


    }

    private fun initview() {


        searchBinding.imgsearch.setOnClickListener {
            firebaseDatabase = FirebaseDatabase.getInstance().reference


            val searchvalue = searchBinding.edtsearchBox.getText().toString()
            Log.e("TAG", "initviewvjfgj: " + searchvalue)


            searchadapter = SearchAdapter(this, searchlist)
            firebaseDatabase.child("search").child(searchvalue)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var image = snapshot.child("image").value.toString()
                        var name = snapshot.child("name").value.toString()
                        var location = snapshot.child("location").value.toString()
                        var amount = snapshot.child("amount").value.toString()

                        Log.e(
                            "TAG",
                            "onDataChangemountain: " + image + " " + name + " " + location + " " + amount
                        )

                        val LayoutManager =
                            LinearLayoutManager(
                                this@SearchActivity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        searchBinding.rcvsearch.layoutManager = LayoutManager
                        searchBinding.rcvsearch.adapter = searchadapter
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

        }

        var search = searchBinding.edtsearchBox.text.toString()

        if (search.isEmpty()) {
            Toast.makeText(this, "Please enter something to Search", Toast.LENGTH_SHORT).show()

        } else {



            firebaseDatabase.child("mountain").child(search)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        var image = snapshot.child("image").value.toString()
                        var name = snapshot.child("name").value.toString()
                        var location = snapshot.child("location").value.toString()
                        var amount = snapshot.child("amount").value.toString()
                        var rateing = ""
                        var info = ""
                        var status = 0
                        var cart = 0

                        var model = ModelClassForDestinaion(
                            image,
                            name,
                            location,
                            amount,
                            rateing,
                            info,
                            status,
                            cart
                        )

                        searchlist.add(model)
                        Log.e(
                            "TAG",
                            "onDataChangemountain: " + image + " " + name + " " + location + " " + amount
                        )
                        Log.e("TAG", "search value: " + search)
                        searchadapter = SearchAdapter(this@SearchActivity, searchlist)
                        val LayoutManager =
                            LinearLayoutManager(
                                this@SearchActivity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        searchBinding.rcvsearch.layoutManager = LayoutManager
                        searchBinding.rcvsearch.adapter = searchadapter

                        searchadapter.updatelist(searchlist)

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

        }

    }
}