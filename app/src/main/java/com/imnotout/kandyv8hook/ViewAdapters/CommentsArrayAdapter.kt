package com.imnotout.kandyv8hook.ViewAdapters

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.imnotout.kandyv8hook.Models.Comment
import com.imnotout.kandyv8hook.R
import com.imnotout.kandyv8hook.Views.IActionListener
import kotlinx.android.synthetic.main.list_item_comment_layout.view.*

class CommentsArrayAdapter(val listener: IActionListener, private val items: List<Comment>) :
        RecyclerView.Adapter<CommentsArrayAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsArrayAdapter.CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rootView = layoutInflater.inflate(R.layout.list_item_comment_layout, parent, false)

        return CommentViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: CommentsArrayAdapter.CommentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size ?: 0
    }

    inner class CommentViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView),
    View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private val img_edit_options: View = rootView.img_edit_options
        private val txt_comment: TextView = rootView.txt_comment
        fun bind(item: Comment) {
            item.run {
                img_edit_options.setOnClickListener(this@CommentViewHolder)
                txt_comment.setText(text)
            }
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.img_edit_options -> {
                    val commentOptionsPopup = PopupMenu(v.context, v)
                    commentOptionsPopup.run {
                        setOnMenuItemClickListener(this@CommentViewHolder)
                        inflate(R.menu.comment_options_menu)
                        show()
                    }
                }
            }
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu_edit_commment -> {
                    return true
                }
                R.id.menu_delete_commment -> {
                    return true
                }
            }
            return false
        }
    }
}

