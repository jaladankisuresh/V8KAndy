package com.imnotout.kandyv8hook.Views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.imnotout.kandyv8hook.LOG_APP_TAG
import com.imnotout.kandyv8hook.Models.*
import com.imnotout.kandyv8hook.NetworkIO.loadImage
import com.imnotout.kandyv8hook.R
import com.imnotout.kandyv8hook.ViewAdapters.EstablishmentsArrayAdapter
import com.imnotout.kandyv8hook.ViewModels.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.getViewModel
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
//    private val vm = ViewModel<Main>()
//    private val vm = getViewModel<ViewModel<Main>>()
    private lateinit var vm: ViewModel<Main>
    private val listener : IActionListener by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        vm = getViewModel<ViewModel<Main>>()
        vm.onInit{data: Either<Throwable, Main> ->
            // Do whatever you want with the model object now
            when(data) {
                is Either.Left -> Log.e(LOG_APP_TAG, data.value.message)
                is Either.Right -> data.value.run {
                    txt_name.setText(name)
                    txt_desc.setText(description)
                    loadImage(ctx = this@MainActivity, imgPath = imgPath, view = img_remote_item,
                           error = R.drawable.error,  placeHolder = R.drawable.loading)
                    txt_item_count.setText("Total Count: $count")
                    list_establishments.layoutManager = LinearLayoutManager(this@MainActivity)
                    list_establishments.adapter = EstablishmentsArrayAdapter(listener, establishments)
                }
            }
        }

        listener.on<Bundle>("establishment_add_comment") {
            Log.i(LOG_APP_TAG, "key: establishment_add_comment and inside callback $it")
            val showCommentFragment = CreateEditCommentFragment()
            showCommentFragment.arguments = it
            showCommentFragment.show(supportFragmentManager, "detail_ESTABLISHMENT_ADD_COMMENT")
        }

    }

    override fun onStop() {
        vm.onDestroy()
        listener.off<Bundle>("establishment_add_comment")
        super.onStop()
    }
}
