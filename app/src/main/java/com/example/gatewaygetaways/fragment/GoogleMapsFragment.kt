package com.example.gatewaygetaways.fragment

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gatewaygetaways.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.IOException

class GoogleMapsFragment : Fragment() {
    var addmarker: Marker? = null
    lateinit var MyDatabase: DatabaseReference

    private val callback = OnMapReadyCallback { googleMap ->

        MyDatabase = FirebaseDatabase.getInstance().getReference()
        val bundle = arguments
//        val value = arguments?.getString("name").toString()
//
        val value = arguments?.getString("name")
        Log.e("TAG", "valueM: " + value)

        if (arguments?.getBoolean("topddestination") == true) {

            MyDatabase.child("topddestination").child(value!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        var location = snapshot.child("location").value.toString()

                        Log.e("TAG", "toplocation: " + location)


                        var addressList: List<Address>? = null
                        if (location != null || location == "") {
                            val geocoder = Geocoder(requireContext())
                            try {
                                addressList = geocoder.getFromLocationName(location, 1)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            val address = addressList!![0]
                            val latlng = LatLng(address.latitude, address.longitude)

                            Log.e(
                                "TAG",
                                "longitude: " + address.longitude + " " + "latitude:" + address.latitude
                            )

                            addmarker = googleMap.addMarker(
                                MarkerOptions().position(latlng).title(location)
                            )!!

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13f))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

        }
        else if (arguments?.getBoolean("loadsPosition") == true) {
            MyDatabase.child("mountain").child(value!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        var location = snapshot.child("location").value.toString()

                        Log.e("TAG", "onDataChangelocation: " + location)


                        var addressList: List<Address>? = null
                        if (location != null || location == "") {
                            val geocoder = Geocoder(requireContext())
                            try {
                                addressList = geocoder.getFromLocationName(location, 1)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            val address = addressList!![0]
                            val latlng = LatLng(address.latitude, address.longitude)

                            Log.e(
                                "TAG",
                                "longitude: " + address.longitude + " " + "latitude:" + address.latitude
                            )

                            addmarker = googleMap.addMarker(
                                MarkerOptions().position(latlng).title(location)
                            )!!

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13f))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
        else if (arguments?.getBoolean("jungledestination") == true) {

            MyDatabase.child("junglesafari").child(value!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        var location = snapshot.child("location").value.toString()

                        Log.e("TAG", "junglelocation: " + location)


                        var addressList: List<Address>? = null
                        if (location != null || location == "") {
                            val geocoder = Geocoder(requireContext())
                            try {
                                addressList = geocoder.getFromLocationName(location, 1)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            val address = addressList!![0]
                            val latlng = LatLng(address.latitude, address.longitude)

                            Log.e(
                                "TAG",
                                "longitude: " + address.longitude + " " + "latitude:" + address.latitude
                            )

                            addmarker = googleMap.addMarker(
                                MarkerOptions().position(latlng).title(location)
                            )!!

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13f))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

        }
        else if (arguments?.getBoolean("beachdestination") == true) {

            MyDatabase.child("warmdestination").child(value!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        var location = snapshot.child("location").value.toString()

                        Log.e("TAG", "beachlocation: " + location)


                        var addressList: List<Address>? = null
                        if (location != null || location == "") {
                            val geocoder = Geocoder(requireContext())
                            try {
                                addressList = geocoder.getFromLocationName(location, 1)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            val address = addressList!![0]
                            val latlng = LatLng(address.latitude, address.longitude)

                            Log.e(
                                "TAG",
                                "longitude: " + address.longitude + " " + "latitude:" + address.latitude
                            )

                            addmarker = googleMap.addMarker(
                                MarkerOptions().position(latlng).title(location)
                            )!!

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13f))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })


        }
        else if (arguments?.getBoolean("culturalsite") == true) {

            MyDatabase.child("culturalsites").child(value!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        var location = snapshot.child("location").value.toString()

                        Log.e("TAG", "templelocation: " + location)


                        var addressList: List<Address>? = null
                        if (location != null || location == "") {
                            val geocoder = Geocoder(requireContext())
                            try {
                                addressList = geocoder.getFromLocationName(location, 1)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            val address = addressList!![0]
                            val latlng = LatLng(address.latitude, address.longitude)

                            Log.e(
                                "TAG",
                                "longitude: " + address.longitude + " " + "latitude:" + address.latitude
                            )

                            addmarker = googleMap.addMarker(
                                MarkerOptions().position(latlng).title(location)
                            )!!

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13f))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_google_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}