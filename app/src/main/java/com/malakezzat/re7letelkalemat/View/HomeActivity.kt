package com.malakezzat.re7letelkalemat.View

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.malakezzat.re7letelkalemat.Model.User
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    lateinit var db: ActivityHomeBinding
    private lateinit var navController: NavController
    lateinit var  bottomNavigationView: BottomNavigationView
    lateinit var user : FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(db.root)
        navController = findNavController(R.id.nav_host_home_fragment)
        bottomNavigationView = db.bottomNavigation
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        user = FirebaseAuth.getInstance().currentUser!!
        getUserScore(user?.email?.substringBefore("."))
    }

    // Function to upload image
    fun uploadImage(imageUri: Uri/*, callback: UploadCallback*/) {
        // Get Firebase Storage reference
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.getReference()


        // Create a unique file name based on timestamp
        val fileName = "images/" + System.currentTimeMillis() + ".jpg"
        val imageRef: StorageReference = storageRef.child(fileName)


        // Upload image
        val uploadTask: UploadTask = imageRef.putFile(imageUri)
//        uploadTask.addOnSuccessListener { taskSnapshot ->
//            // Get the download URL
//            imageRef.getDownloadUrl().addOnSuccessListener { uri ->
//                // Pass the download URL to the callback
//                callback.onSuccess(uri.toString())
//
//            }.addOnFailureListener { exception ->
//                callback.onFailure(exception)
//            }
//        }.addOnFailureListener { exception ->
//            callback.onFailure(exception)
//        }
    }

    // Define the callback interface
    interface UploadCallback {
        fun onSuccess(imageUrl: String?)
        fun onFailure(exception: Exception?)
    }

    fun storeUserData(userEmail:String?,userName: String?, imageUrl: String?, userScore: Int) {
        // Get Firebase Realtime Database reference
        val database = FirebaseDatabase.getInstance()
        val userId = userEmail?.substringBefore(".")
        val usersRef = database.getReference("users").child(userId ?: (userName + imageUrl))


        // Create a User object
        val user = User(userName, imageUrl, userScore)

        uploadImage(imageUrl?.toUri() ?:
        AppCompatResources.getDrawable(applicationContext,R.drawable.vector__1_).toString().toUri())
        // Store the User object in the database
        usersRef.setValue(user).addOnSuccessListener { aVoid: Void? -> }
            .addOnFailureListener { exception: java.lang.Exception? -> }
    }

    fun getUserScore(userId: String?) : String {
        var score : String = "0"
        // Get Firebase Realtime Database reference
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users").child(userId!!)

        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user2 = task.result.getValue(User::class.java)
                score = user2?.score?.toString() ?: "0"
                storeUserData(user?.email,user?.displayName, user?.photoUrl.toString(),user2?.score ?: 0)
            } else {
                // Handle potential errors, you can pass a default score in case of failure
            }
        }
//        // Attach a listener to read the data
//        userRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Check if the data exists
//                if (dataSnapshot.exists()) {
//                    // Retrieve the User object from the snapshot
//
//                } else {
//
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle potential errors
//
//            }
//        })

        return score
    }
}