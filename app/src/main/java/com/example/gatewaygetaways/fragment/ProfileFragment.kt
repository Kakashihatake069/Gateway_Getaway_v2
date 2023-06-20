package com.example.gatewaygetaways.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.gatewaygetaways.R
import com.example.gatewaygetaways.activity.TypesOfLoginActivity
import com.example.gatewaygetaways.databinding.FragmentProfileBinding
import com.example.gatewaygetaways.modelclass.UserModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference


class ProfileFragment : Fragment() {
    lateinit var profileBinding: FragmentProfileBinding
    lateinit var firebaseDatabase: DatabaseReference
    lateinit var firebaseUser: FirebaseUser
    lateinit var storageReference: StorageReference
    lateinit var mAuth: FirebaseAuth
    var userlist =ArrayList<UserModelClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("user")

        mAuth = Firebase.auth
        initview()
        return profileBinding.root
    }



    private fun initview() {



        // for dynamic name and id
        var query : Query = firebaseDatabase.orderByChild("email").equalTo(mAuth.currentUser!!.email)
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children){
                    var firstname = postSnapshot.child("firstName").value
                    var lastname = postSnapshot.child("lastName").value
                    var EmailId = postSnapshot.child("email").value
                    var image = postSnapshot.child("image").value

                    Log.e("TAG", "onDataChangedf:"+firstname+" "+lastname+" "+image)

                    profileBinding.txtFname.text = firstname.toString()
                    profileBinding.txtLname.text= lastname.toString()
                    profileBinding.txtemailid.text= EmailId.toString()
                    Glide.with(this@ProfileFragment).load(image).into(profileBinding.imgcircleDP)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        // Pick Image Method
        profileBinding.imgcircleDP.setOnClickListener {
            val Gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            Gallery_Launcher.launch(Gallery)
        }

    }

    var Gallery_Launcher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK){
            val data = result.data
            val uri = data!!.data
            profileBinding.imgcircleDP.setImageURI(uri)
        }
    }

}