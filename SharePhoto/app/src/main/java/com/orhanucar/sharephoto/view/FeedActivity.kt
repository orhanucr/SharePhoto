package com.orhanucar.sharephoto.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.orhanucar.sharephoto.R
import com.orhanucar.sharephoto.adapter.FeedRecyclerViewAdapter
import com.orhanucar.sharephoto.databinding.ActivityFeedBinding
import com.orhanucar.sharephoto.model.Post

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var postArrayList: ArrayList<Post>
    private lateinit var feedAdapter: FeedRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore

        postArrayList = ArrayList<Post>()
        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedRecyclerViewAdapter(postArrayList)
        binding.recyclerView.adapter = feedAdapter
    }

    private fun getData() {
        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {
                        val documents = value.documents
                        postArrayList.clear()
                        for (documents in documents) {

                            val userEmail = documents.get("userEmail") as String
                            val comment = documents.get("comment") as String
                            val downloadUrl = documents.get("downloadUrl") as String

                            val post = Post(userEmail,comment,downloadUrl)
                            postArrayList.add(post)
                        }

                        feedAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.photo_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_post) {
            val intent = Intent(this@FeedActivity, UploadActivity :: class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.signout) {
            auth.signOut()
            val intent = Intent(this@FeedActivity, MainActivity :: class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}