package com.imnotout.kandyv8hook.ViewAdapters

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.TextView
import com.imnotout.kandyv8hook.LOG_APP_TAG
import com.imnotout.kandyv8hook.R
import com.imnotout.kandyv8hook.Models.*
import com.imnotout.kandyv8hook.R.layout.*
import com.imnotout.kandyv8hook.Views.IActionListener
import kotlinx.android.synthetic.main.content_comments_section.view.*
import kotlinx.android.synthetic.main.list_item_establishment_layout.view.*
import kotlinx.android.synthetic.main.list_item_hotel_layout.view.*
import kotlinx.android.synthetic.main.list_item_restaurant_layout.view.*
import kotlinx.android.synthetic.main.list_item_theatre_layout.view.*


class EstablishmentsArrayAdapter(private val listener: IActionListener, private val collection: List<IEstablishment>) :
        RecyclerView.Adapter<EstablishmentsArrayAdapter.EstablishmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstablishmentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val rootView = layoutInflater.inflate(R.layout.list_item_establishment_layout, parent, false)
        rootView.stub_establishment_child.run{
            when(viewType) {
                Hotel::class.hashCode() -> {
                    setLayoutResource(list_item_hotel_layout)
                    inflate()
                    return HotelViewHolder(rootView)
                }
                Restaurant::class.hashCode() -> {
                    setLayoutResource(list_item_restaurant_layout)
                    inflate()
                    return RestaurantViewHolder(rootView)
                }
                else -> {
                    setLayoutResource(list_item_theatre_layout)
                    inflate()
                    return TheatreViewHolder(rootView)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: EstablishmentViewHolder, position: Int) {
        val establishmentItem = collection[position]
        holder.bind(establishmentItem)
    }

    override fun getItemViewType(position: Int): Int {
        return collection[position]::class.hashCode() ?: -1
    }

    override fun getItemCount(): Int {
        return collection.size ?: 0
    }

    inner abstract class EstablishmentViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView),
            View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private val img_edit_options: View = rootView.img_edit_options
        private val txt_name: TextView = rootView.txt_name
        private val txt_type: TextView = rootView.txt_type
        private val txt_location: TextView = rootView.txt_location
        private val txt_rating: TextView = rootView.txt_rating
        private val lyt_content_comments_section: View = rootView.lyt_content_comments_section
        private lateinit var model: IEstablishment

        open fun bind(item: IEstablishment) {
            model = item //assign to member property model
            item.run {
                img_edit_options.setOnClickListener(this@EstablishmentViewHolder)
                txt_name.setText(name)
                txt_type.setText(type)
                txt_location.setText(location)
                txt_rating.setText(rating.toString())
                comments?.let{
                    lyt_content_comments_section.run {
                        txt_comments_count.setText("Comments ${ if( it.size > 0 ) it.size else ""}")
                        list_comments_generic.layoutManager = LinearLayoutManager(list_comments_generic.context)
                        list_comments_generic.adapter = CommentsArrayAdapter(listener, it)
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
                    try {
                        val args = Bundle()
                        args.putSerializable("model", model)
                        listener.emit("establishment_add_comment", args)
                        return true
                    } catch (ex: Exception) {
                        Log.e(LOG_APP_TAG, "key: establishment_add_comment and inside exception ${ex.message}")
                    }

                }
                R.id.menu_delete_item -> return false
            }
            return false
        }
    }

    inner class HotelViewHolder(rootView: View) : EstablishmentViewHolder(rootView) {
        private val txt_stars: TextView = rootView.txt_stars
        private val txt_rooms_count: TextView = rootView.txt_rooms_count

        override fun bind(item: IEstablishment) {
            super.bind(item)
            (item as Hotel).run {
                txt_stars.setText(stars.toString())
                txt_rooms_count.setText(roomsCount.toString())
            }
        }
    }

    inner class TheatreViewHolder(rootView: View) : EstablishmentViewHolder(rootView) {
        private val txt_screen_count: TextView = rootView.txt_screen_count
        private val txt_theatre_seating: TextView = rootView.txt_theatre_seating
        private val txt_show_count: TextView = rootView.txt_show_count

        override fun bind(item: IEstablishment) {
            super.bind(item)
            (item as Theatre).run {
                txt_screen_count.setText(screensCount.toString())
                txt_theatre_seating.setText(seatingCapacity.toString())
                txt_show_count.setText(showsPerScreen.toString())
            }
        }
    }

    inner class RestaurantViewHolder(rootView: View) : EstablishmentViewHolder(rootView) {
        private val txt_cuisine_type: TextView = rootView.txt_cuisine_type
        private val txt_chefs_count: TextView = rootView.txt_chefs_count
        private val txt_restaurant_seating: TextView = rootView.txt_restaurant_seating

        override fun bind(item: IEstablishment) {
            super.bind(item)
            (item as Restaurant).run {
                txt_cuisine_type.setText(cuisineType)
                txt_chefs_count.setText(chefsCount.toString())
                txt_restaurant_seating.setText(seatingCapacity.toString())
            }
        }
    }
}
