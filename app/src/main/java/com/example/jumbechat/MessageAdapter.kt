package com.example.jumbechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jumbechat.Activities.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIEVE = 1;
    val ITEM_SENT = 2;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.recieve_layout, parent, false)

            return ReceiveViewHolder(view)
        } else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent_layout, parent, false)
            return sendViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if (holder.javaClass == sendViewHolder::class.java){


            val viewHolder= holder as sendViewHolder

            holder.sentMessage.text = currentMessage.message

        }
        else{
            val viewHolder = holder as ReceiveViewHolder

            holder.recievedMessage.text = currentMessage.message

        }
    }

    override fun getItemCount(): Int = messageList.size

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        } else{
            return ITEM_RECIEVE
        }
    }

    class sendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sentMessage)


    }
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val recievedMessage = itemView.findViewById<TextView>(R.id.txt_recieveMessage)

    }
}