package com.imnotout.kandyv8hook.Views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.imnotout.kandyv8hook.LOG_APP_TAG
import com.imnotout.kandyv8hook.Models.Either
import com.imnotout.kandyv8hook.R
import com.imnotout.kandyv8hook.Models.Launcher
import com.imnotout.kandyv8hook.ViewModels.ViewModel
import kotlinx.android.synthetic.main.activity_launcher.*
import org.jetbrains.anko.*

class LauncherActivity : AppCompatActivity() {
    val vm = ViewModel<Launcher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
    }

    override fun onStart() {
        super.onStart()

        vm.onInit{data: Either<Throwable, Launcher> ->
            // Do whatever you want with the model object now
            when(data) {
                is Either.Left -> Log.e(LOG_APP_TAG, data.value.message)
                is Either.Right -> btn_launch_directory.run {
                    val model = data.value
                    text = model.label
                    setOnClickListener({
                        startActivity<MainActivity>()
                    })
                }
            }

        }
    }

    override fun onStop() {
        vm.onDestroy()
        super.onStop()
    }
}
