package com.malakezzat.re7letelkalemat.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.malakezzat.re7letelkalemat.Model.User
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.LeaderboardItemBinding

class LeaderBoardAdapter(
    private val userList: List<User>
) : RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {

    // ViewHolder class to hold and recycle views
    inner class ViewHolder(val db: LeaderboardItemBinding) : RecyclerView.ViewHolder(db.root) {
        val orderNumber : TextView = db.orderNumber
        val userName: TextView = db.userName
        val userScore: TextView = db.scoreText2
        val profileImg : ImageView = db.profileImg
    }

    // Inflate the item layout and create ViewHolder objects
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val db= LeaderboardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(db)
    }

    // Bind the data to the views in each list item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.orderNumber.text = (position + 1).toString()
        holder.userName.text = user.name?.substringBefore(" ")
        holder.userScore.text = user.score.toString()
        Glide.with(holder.profileImg.context)
            .load(user.imageUrl)
            .apply(RequestOptions().override(200, 200))
            .placeholder(R.drawable.vector__1_)
            .into(holder.profileImg)
    }

    // Return the total number of list items
    override fun getItemCount(): Int {
        return userList.size
    }
}
