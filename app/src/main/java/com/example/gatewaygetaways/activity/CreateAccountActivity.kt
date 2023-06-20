package com.example.gatewaygetaways.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.gatewaygetaways.modelclass.UserModelClass
import com.example.gatewaygetaways.databinding.ActivityCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.UUID


class CreateAccountActivity : AppCompatActivity() {
    lateinit var createAccountBinding: ActivityCreateAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private val PICK_IMAGE_REQUEST = 22
    lateinit var storage: FirebaseStorage
    private var filePath: Uri? = null
    lateinit var storageReference: StorageReference
    lateinit var userModelClass: UserModelClass
    var image = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createAccountBinding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(createAccountBinding.root)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()
        initview()
        uploadimage()
    }

    private fun initview() {

        auth = FirebaseAuth.getInstance()
        createAccountBinding.btnCreateAccount.setOnClickListener {
            var firstName = createAccountBinding.edtFirstC.text.toString()
            var lastName = createAccountBinding.edtLastNameC.text.toString()
            var email = createAccountBinding.edtCreateEmail.text.toString()
            var password = createAccountBinding.edtCreatePassword.text.toString()
            if (firstName.isEmpty()) {
                Toast.makeText(
                    this,
                    "FirstName value is empty. please fill firstName ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (lastName.isEmpty()) {
                Toast.makeText(
                    this,
                    "LastName value is empty. please fill LastName ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (email.isEmpty()) {
                Toast.makeText(this, "email value is empty. please fill email ", Toast.LENGTH_SHORT)
                    .show()
            } else if (password.isEmpty()) {
                Toast.makeText(
                    this,
                    "password value is empty. please fill password ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {


                        addUserToDatabase(firstName, lastName, email, auth.currentUser?.uid!!,image)



                        Toast.makeText(this, " Account Create successfully", Toast.LENGTH_SHORT)
                            .show()
                        var i = Intent(this, MainActivity::class.java)
                        finish()
                        startActivity(i)
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }


        }

        createAccountBinding.txtLoginPage.setOnClickListener {
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        //pick image
        createAccountBinding.btnpick.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Select Image from here..."
                ), PICK_IMAGE_REQUEST
            )
        }

        //upload photo
        createAccountBinding.btnupload.setOnClickListener {
            uploadimage()
        }

    }

    // Override onActivityResult method
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            // Get the Uri of data
            filePath = data.data
            try {

                // Setting image on image view using Bitmap
                val bitmap = MediaStore.Images.Media
                    .getBitmap(
                        contentResolver,
                        filePath
                    )
                createAccountBinding.CreateDp.setImageBitmap(bitmap)
            } catch (e: IOException) {
                // Log the exception
                e.printStackTrace()
            }
        }
    }


    private fun uploadimage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            // Defining the child of storageReference
            var ref = storageReference.child("images/" + UUID.randomUUID().toString())


            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath!!)
                .addOnCompleteListener {

                    ref.downloadUrl.addOnSuccessListener {
                        image=it.toString()
                    }

                }
                .addOnSuccessListener { // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            this@CreateAccountActivity,
                            "Image Uploaded!!",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            this@CreateAccountActivity,
                            "Failed " + e.message,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->

                    // Progress Listener for loading
                    // percentage on the dialog box
                    val progress = (100.0
                            * taskSnapshot.bytesTransferred
                            / taskSnapshot.totalByteCount)
                    progressDialog.setMessage(
                        "Uploaded "
                                + progress.toInt() + "%"
                    )
                }
        }
    }

    private fun addUserToDatabase(firstName: String, lastName: String, email: String, uid: String,image: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(UserModelClass(firstName, lastName, email, uid,image))

    }
}