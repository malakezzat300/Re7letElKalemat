package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.malakezzat.re7letelkalemat.Model.User
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityHomeBinding
import com.malakezzat.re7letelkalemat.databinding.ActivityLeaderBoardBinding

class LeaderBoardActivity : AppCompatActivity()  {

    lateinit var db: ActivityLeaderBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityLeaderBoardBinding.inflate(layoutInflater)
        setContentView(db.root)

        db.myCardRecycler.layoutManager = LinearLayoutManager(this)

        // Retrieve and set sorted users
        retrieveAndSortUsers { sortedUsersList ->
            val adapter = LeaderBoardAdapter(sortedUsersList)
            db.myCardRecycler.adapter = adapter
        }
    }

    private fun retrieveAndSortUsers(callback: (List<User>) -> Unit) {
        // Get Firebase Realtime Database reference
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        // Attach a listener to read the data
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usersList = mutableListOf<User>()

                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null) {
                        usersList.add(user)
                    }
                }

                // Sort the list by score in descending order
                val sortedUsersList = usersList.sortedByDescending { it.score }

                // Pass the sorted list to the callback
                callback(sortedUsersList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle potential errors
                databaseError.toException().printStackTrace()
                Toast.makeText(
                    this@LeaderBoardActivity,
                    "Failed to retrieve data: ${databaseError.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
