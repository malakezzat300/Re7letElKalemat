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
        getUserScore(user.email?.substringBefore(".")) { s -> { } }
    }

    // Function to upload the image to Firebase Storage and get its URL
    fun uploadImage(imageUri: Uri, callback: (String) -> Unit) {
        // Get Firebase Storage reference
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        // Create a unique file name based on timestamp
        val fileName = "images/" + System.currentTimeMillis() + ".jpg"
        val imageRef = storageRef.child(fileName)

        // Upload image
        val uploadTask = imageRef.putFile(imageUri)

        // Get the download URL after successful upload
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                callback(downloadUri)  // Return the download URL
            } else {
                // Handle failure
            }
        }
    }

    fun storeUserData(userEmail: String?, userName: String?, imageUri: Uri, userScore: Int) {
        // First, upload the image to Firebase Storage
        uploadImage(imageUri) { imageUrl ->
            // After getting the image URL, store the user data
            val database = FirebaseDatabase.getInstance()
            val userId = userEmail?.substringBefore(".") ?: (userName + imageUrl)
            val usersRef = database.getReference("users").child(userId)

            // Create a User object with image URL
            val user = User(userName, imageUrl, userScore)

            // Store the User object in the Realtime Database
            usersRef.setValue(user).addOnSuccessListener {
                // Successfully stored the user data
            }.addOnFailureListener {
                // Handle failure to store data
            }
        }
    }

    fun getUserScore(userEmail: String?, callback: (String) -> Unit) {
        // Get Firebase Realtime Database reference
        val database = FirebaseDatabase.getInstance()
        val userId = userEmail?.substringBefore(".") ?: ""
        val userRef = database.getReference("users").child(userId)

        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val dataSnapshot = task.result
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(User::class.java)
                    val score = user?.score?.toString() ?: "0"

                    // Retrieve user info from Firebase Authentication
                    val firebaseUser = FirebaseAuth.getInstance().currentUser
                    firebaseUser?.let {
                        val userName = firebaseUser.displayName ?: firebaseUser.email
                        val imageUrl = firebaseUser.photoUrl?.toString()?.toUri()
                            ?: AppCompatResources.getDrawable(applicationContext, R.drawable.vector__1_)
                                .toString().toUri()

                        storeUserData(firebaseUser.email, userName, imageUrl, user?.score ?: 0)
                    }

                    callback(score) // Return the score
                } else {
                    // No data found, handle as a new user
                    callback("0") // Default score for new users
                }
            } else {
                // Handle error and return a default score
                callback("0")
            }
        }
    }


}