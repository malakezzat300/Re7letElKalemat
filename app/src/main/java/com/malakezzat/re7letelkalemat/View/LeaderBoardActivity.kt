package com.malakezzat.re7letelkalemat.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.malakezzat.re7letelkalemat.Model.User
import com.malakezzat.re7letelkalemat.R

class LeaderBoardActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        retrieveAndSortUsers { sortedUsersList ->
            // Use the sorted list of users
            sortedUsersList.forEach { user ->
                println("Name: ${user.name}, Score: ${user.score}, Image URL: ${user.imageUrl}")
            }

            // Show a toast message with the top user's name and score
            if (sortedUsersList.isNotEmpty()) {
                val topUser = sortedUsersList.first()
                Toast.makeText(
                    this,
                    "Top User: ${topUser.name}, Score: ${topUser.score}",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(this, "No users found", Toast.LENGTH_LONG).show()
            }
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
