package com.ingray.samagam.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ingray.samagam.DataClass.Events
import com.ingray.samagam.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.ingray.samagam.DataClass.Posts

class ProfilePostAdapter (options: FirebaseRecyclerOptions<Posts?>) :
    FirebaseRecyclerAdapter<Posts?, ProfilePostAdapter .userAdapterHolder?>(options) {
    override fun onBindViewHolder(
        holder: userAdapterHolder,
        position: Int,
        model: Posts
    ) {
        Toast.makeText(holder.posterImage.context,"reached", Toast.LENGTH_SHORT).show()
        Glide.with(holder.posterImage.context).load(model.postUrl).into(holder.posterImage)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): userAdapterHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.profile_post_item,parent,false)
        return userAdapterHolder(view)
    }

    inner class userAdapterHolder(innerView:View):RecyclerView.ViewHolder(innerView) {
        var posterImage:ImageView


        init {
            posterImage =innerView.findViewById(R.id.image_post)
        }
    }
}