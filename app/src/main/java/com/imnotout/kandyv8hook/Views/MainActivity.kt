package com.imnotout.kandyv8hook.Views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.imnotout.kandyv8hook.LOG_APP_TAG
import com.imnotout.kandyv8hook.Models.Main
import com.imnotout.kandyv8hook.NetworkIO.GlideApp
import com.imnotout.kandyv8hook.R
import com.imnotout.kandyv8hook.ViewAdapters.EstablishmentsArrayAdapter
import com.imnotout.kandyv8hook.ViewModels.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {
    val vm = ViewModel<Main>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        MainActivityUI<MainActivity>().setContentView(this)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        vm.onInit{model: Main ->
            // Do whatever you want with the model object now
            model.run {
                txt_name.setText(name)
                txt_desc.setText(description)
                GlideApp
                        .with(this@MainActivity)
                        .load(imgPath)
                        .centerCrop()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(img_remote_item);
                txt_item_count.setText("Total Count: $count")
                list_establishments.adapter = EstablishmentsArrayAdapter(establishments)
            }
        }
    }

    override fun onStop() {
        vm.onDestroy()
        super.onStop()
    }

//    class MainActivityUI<T> : AnkoComponent<T> {
//        override fun createView(ui: AnkoContext<T>) = with(ui) {
//            frameLayout {
//                progressBar {
//                    visibility = View.GONE
//                }
//                verticalLayout {
//                    button("Delete One Item")
//                            .lparams(width = wrapContent)
//                    textView("Name")
//                            .lparams(width = matchParent)
//                    textView("Description")
//                            .lparams(width = matchParent)
//                    textView("Total Items")
//                            .lparams(width = matchParent, height = wrapContent)
//                    recyclerView  {
//                    }.lparams(width = matchParent, height = wrapContent)
//                }.lparams(width = matchParent, height = matchParent)
//            }
//        }
//    }
}
