package com.malakezzat.re7letelkalemat.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.malakezzat.re7letelkalemat.Model.User
import com.malakezzat.re7letelkalemat.R

class LeaderBoardAdapter(
    private val userList: List<User>
) : RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {

    // ViewHolder class to hold and recycle views
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userScore: TextView = itemView.findViewById(R.id.scoreText)
        val orderNumber: TextView = itemView.findViewById(R.id.order_number)
    }

    // Inflate the item layout and create ViewHolder objects
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.leaderboard_item, parent, false)
        return ViewHolder(view)
    }

    // Bind the data to the views in each list item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.userName.text = user.name
        holder.userScore.text = user.score.toString()
        holder.orderNumber.text = (position + 1).toString()
    }

    // Return the total number of list items
    override fun getItemCount(): Int {
        return userList.size
    }
}
