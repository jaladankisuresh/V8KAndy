package com.imnotout.kandyv8hook.ViewAdapters

import android.os.Bundle
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import com.imnotout.kandyv8hook.R
import com.imnotout.kandyv8hook.Models.*
import kotlinx.android.synthetic.main.content_comments_section.view.*
import kotlinx.android.synthetic.main.list_item_establishment_layout.view.*
import kotlinx.android.synthetic.main.list_item_hotel_layout.view.*
import kotlinx.android.synthetic.main.list_item_restaurant_layout.view.*
import kotlinx.android.synthetic.main.list_item_theatre_layout.view.*


class EstablishmentsArrayAdapter(private val collection: List<IEstablishment>) :
        RecyclerView.Adapter<EstablishmentsArrayAdapter.EstablishmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstablishmentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val rootView = layoutInflater.inflate(R.layout.list_item_establishment_layout, parent, false)
        rootView.stub_establishment_child.run{
            when(viewType) {
                EstablishmentType.HOTEL.value.hashCode() ->
                    setLayoutResource(R.layout.list_item_hotel_layout)
                EstablishmentType.RESTAURANT.value.hashCode() ->
                    setLayoutResource(R.layout.list_item_restaurant_layout)
                else ->
                    setLayoutResource(R.layout.list_item_theatre_layout)
            }
            inflate()
        }
        return EstablishmentViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: EstablishmentViewHolder, position: Int) {
        val establishmentItem = collection[position]
        holder.bind(establishmentItem)
    }

    override fun getItemViewType(position: Int): Int {
        return collection[position].type?.hashCode() ?: -1
    }

    override fun getItemCount(): Int {
        return collection.size ?: 0
    }

    inner class EstablishmentViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView),
            View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private val img_edit_options: View = rootView.img_edit_options
        private val txt_name: TextView = rootView.txt_name
        private val txt_type: TextView = rootView.txt_type
        private val txt_location: TextView = rootView.txt_location
        private val txt_rating: TextView = rootView.txt_rating
        private val lyt_content_comments_section: View = rootView.lyt_content_comments_section
        private lateinit var model: IEstablishment

        fun bind(item: IEstablishment) {
            model = item //assign to member property model
            item.run {
                img_edit_options.setOnClickListener(this@EstablishmentViewHolder)
                txt_name.setText(name)
                txt_type.setText(type)
                txt_location.setText(location)
                txt_rating.setText(rating)
                when(this) {
                    is Hotel -> {
                        rootView.txt_stars.setText(stars)
                        rootView.txt_rooms_count.setText(roomsCount)
                    }
                    is Restaurant -> {
                        rootView.txt_cuisine_type.setText(cuisineType)
                        rootView.txt_chefs_count.setText(chefsCount)
                        rootView.txt_restaurant_seating.setText(seatingCapacity)
                    }
                    is Theatre -> {
                        rootView.txt_screen_count.setText(screensCount)
                        rootView.txt_theatre_seating.setText(seatingCapacity)
                        rootView.txt_show_count.setText(showsPerScreen)
                    }
                }
                comments?.let{
                    lyt_content_comments_section.run {
                        txt_comments_count.setText("Comments ${comments!!.size}")
                        list_comments_generic.adapter = CommentsArrayAdapter(comments!!)
                    }
                }
            }
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.img_edit_options -> {
                    val establishmentOptionsPopup = PopupMenu(v.context, v)
                    establishmentOptionsPopup.setOnMenuItemClickListener(this)
                    establishmentOptionsPopup.inflate(R.menu.establishment_options_menu)
                    establishmentOptionsPopup.show()
                }
            }
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu_add_commment -> {
                    val args = Bundle()
                    args.putSerializable("model", model)
                    return true
                }
            }
            return false
        }
    }
}
