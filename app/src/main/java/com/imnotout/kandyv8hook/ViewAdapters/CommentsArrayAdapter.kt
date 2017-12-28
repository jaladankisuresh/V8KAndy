package com.imnotout.kandyv8hook.ViewAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.imnotout.kandyv8hook.Models.Comment
import com.imnotout.kandyv8hook.R
import kotlinx.android.synthetic.main.list_item_comment_layout.view.*

class CommentsArrayAdapter(private val collection: List<Comment>) :
        RecyclerView.Adapter<CommentsArrayAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsArrayAdapter.CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rootView = layoutInflater.inflate(R.layout.list_item_comment_layout, parent, false)

        return CommentViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: CommentsArrayAdapter.CommentViewHolder, position: Int) {
        holder.bind(collection[position])
    }

    override fun getItemCount(): Int {
        return collection.size ?: 0
    }

    inner class CommentViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
        private val img_edit_options: View = rootView.img_edit_options
        private val txt_comment: TextView = rootView.txt_comment
        fun bind(item: Comment) {
            item.run {
                txt_comment.setText(text)
            }
        }
    }
}

