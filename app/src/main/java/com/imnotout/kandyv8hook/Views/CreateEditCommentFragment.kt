package com.imnotout.kandyv8hook.Views

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imnotout.kandyv8hook.Models.*
import com.imnotout.kandyv8hook.R
import kotlinx.android.synthetic.main.fragment_create_edit_comment.*

class CreateEditCommentFragment : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_create_edit_comment, container, false)
        arguments?.run {
            val index = getInt("index", -1)
            val establishment = getSerializable("model") as IEstablishment
        }
        btn_submit_comment.setOnClickListener(this)

        dialog.setTitle("CreateEditCommentFragment")
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog)
        return rootView
    }

    override fun onClick(view: View) {
        arguments?.putString("newComment", txt_add_edit_comment.getText().toString())
    }
}
