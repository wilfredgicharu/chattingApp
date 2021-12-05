package com.example.jumbechat.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jumbechat.MessageAdapter
import com.example.jumbechat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatsActivity : AppCompatActivity() {

    private lateinit var message_Box: EditText
    private lateinit var chat_recyclerView: RecyclerView
    private lateinit var sendButton : ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>


    private lateinit var mDBAuth : DatabaseReference

    var senderRoom: String? = null
    var recieverRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        mDBAuth = FirebaseDatabase.getInstance().getReference()

        val name = intent.getStringExtra("name")
        val recieverUid = intent.getStringExtra("uid")

        supportActionBar?.title = name


        val senderUid = FirebaseAuth.getInstance().currentUser?.uid


        senderRoom = recieverUid + senderUid
        recieverRoom = senderUid + recieverUid


        message_Box = findViewById(R.id.messageBox)
        chat_recyclerView= findViewById(R.id.chat_recyclerView)
        sendButton = findViewById(R.id.sendButton)

        messageList= ArrayList()
        messageAdapter = MessageAdapter(this, messageList)
        chat_recyclerView.layoutManager = LinearLayoutManager(this)
        chat_recyclerView.adapter = messageAdapter


        //adding messages to the recyclerView

        mDBAuth.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        // when the send button is pressed
        sendButton.setOnClickListener {
            val message = message_Box.text.toString()

            val messageObject = Message(message, senderUid)

            mDBAuth.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDBAuth.child("chats").child(recieverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
        }
        message_Box.setText("")
    }
}