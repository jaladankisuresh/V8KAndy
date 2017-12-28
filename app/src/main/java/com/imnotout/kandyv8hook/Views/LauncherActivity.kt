package com.imnotout.kandyv8hook.Views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import com.imnotout.kandyv8hook.R
import com.imnotout.kandyv8hook.Models.Launcher
import com.imnotout.kandyv8hook.ViewModels.ViewModel
import kotlinx.android.synthetic.main.activity_launcher.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk19.listeners.onClick

class LauncherActivity : AppCompatActivity() {
    val vm = ViewModel<Launcher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        activityUI = LauncherActivityUI().setContentView(this)
        setContentView(R.layout.activity_launcher)
    }

    override fun onStart() {
        super.onStart()

        vm.onInit{model: Launcher ->
            // Do whatever you want with the model object now
            btn_launch_directory.run {
                text = model.label
                setOnClickListener({
                    startActivity<MainActivity>()
                })
            }
        }
    }

    override fun onStop() {
        vm.onDestroy()
        super.onStop()
    }

//    class LauncherActivityUI : AnkoComponent<LauncherActivity> {
//        companion object {
//            const val btn_launch_directory = 1
//        }
//        override fun createView(ui: AnkoContext<LauncherActivity>) = with(ui) {
//            frameLayout {
//                button("Click Here"){
//                   id =  btn_launch_directory
//                }.lparams(gravity = Gravity.CENTER)
//            }
//        }
//    }

}
