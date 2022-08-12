package com.orhanucar.sharephoto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orhanucar.sharephoto.databinding.RecyclerRowBinding
import com.orhanucar.sharephoto.model.Post
import com.squareup.picasso.Picasso


class FeedRecyclerViewAdapter(private val postList: ArrayList<Post>): RecyclerView.Adapter<FeedRecyclerViewAdapter.PostHolder>() {

    class PostHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.recyclerViewEmailText.text = postList.get(position).email
        holder.binding.recyclerCommentText.text = postList.get(position).comment
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.recyclerImageView)
    }

    override fun getItemCount(): Int {
       return postList.size
    }
}