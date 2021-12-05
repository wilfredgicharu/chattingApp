package com.example.jumbechat.Activities

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jumbechat.R
import com.example.jumbechat.User
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context, val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View= LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        holder.user_textview.text= currentUser.email

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatsActivity::class.java)
            intent.putExtra("name", currentUser.email)
            intent.putExtra("uid",currentUser.uId)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int =userList.size

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val user_textview= itemView.findViewById<TextView>(R.id.user_textView)

    }
}


