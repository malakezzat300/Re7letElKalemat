package com.malakezzat.re7letelkalemat.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malakezzat.re7letelkalemat.databinding.ItemPersonalCardBinding

class MyCardAdapter(val e:(cardTitle:String)->Unit) : ListAdapter<String, MyCardAdapter.MyCardViewHolder> (mydiff()){


    class mydiff:DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    class MyCardViewHolder(val db: ItemPersonalCardBinding): RecyclerView.ViewHolder(db.root)
    {
        fun bind(data:String,e:(cardTitle:String)->Unit){
            db.cardTitle.text=data
            db.root.setOnClickListener{
                e(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCardViewHolder {
       return MyCardViewHolder(ItemPersonalCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyCardViewHolder, position: Int) {
        holder.bind(getItem(position),e)
    }
}