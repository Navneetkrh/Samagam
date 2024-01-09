package com.ingray.samagam.Fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.protobuf.Value
import com.ingray.samagam.Activity.LoginActivity
import com.ingray.samagam.Adapters.FeedAdapter
import com.ingray.samagam.Adapters.ProfilePostAdapter
import com.ingray.samagam.DataClass.Posts
import com.ingray.samagam.DataClass.Users
import com.ingray.samagam.R
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {
    private lateinit var profileImage : CircleImageView
    private lateinit var profileName : TextView
    private lateinit var profileBranch:TextView
    var deRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var view :View
    private lateinit var recyclerPost: RecyclerView
    private lateinit var ppAdapter: ProfilePostAdapter
    private lateinit var logout:LinearLayout
    val Pick_image=1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_profile, container, false)
        callById()
        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(
                activity, LoginActivity::class.java
            )
            startActivity(intent)

        }
        deRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
             var us : Users
             if(snapshot.exists()){
                    us = snapshot.getValue(Users::class.java)!!
                    profileName.setText(us.name)
                    Glide.with(view.context).load(us.purl).into(profileImage)
                    profileBranch.setText(us.username)
                }
            }

                override fun onCancelled(error: DatabaseError) {
            // Handle errors here
            Log.e("Firebase", "Error fetching events: ${error.message}")
        }
    })
        recyclerPost.itemAnimator = null
        val options: FirebaseRecyclerOptions<Posts?> = FirebaseRecyclerOptions.Builder<Posts>().
        setQuery(deRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("Posts"), Posts::class.java).build()
        ppAdapter = ProfilePostAdapter(options)
        recyclerPost.adapter = ppAdapter
        ppAdapter.startListening()




        profileImage.setOnClickListener{
            openGallery()
        }
        return view

    }

    private fun openGallery() {
        val gallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(gallery,Pick_image)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Pick_image && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri = data.data!!
            profileImage.setImageURI(imageUri)
        }
    }

    private fun callById() {
        profileImage = view.findViewById(R.id.profileImage)
        profileName = view.findViewById(R.id.profileName)
        profileBranch = view.findViewById(R.id.profileBranch)
        recyclerPost=view.findViewById(R.id.profile_recycler)
        logout = view.findViewById(R.id.logout)
    }


}