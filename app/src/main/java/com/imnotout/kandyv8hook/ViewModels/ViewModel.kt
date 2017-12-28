package com.imnotout.kandyv8hook.ViewModels

import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object
import com.imnotout.kandyv8hook.AndroidApplication
import com.imnotout.kandyv8hook.JS
import com.imnotout.kandyv8hook.LOG_APP_TAG
import com.imnotout.kandyv8hook.NetworkIO.JsonBuilder
import java.lang.reflect.Type
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

//interface IViewModel<T> {
//    fun onInit (cb: (T)-> Unit)
//    fun onDestroy()
//}

class ViewModel<T> {
    var jsVM: V8Object? = null

    inline fun<reified T> onInit(crossinline cb: (T)-> Unit) {
        val mClazz: Type = T::class.java
        val mClazzName = mClazz.toString().substringAfterLast('.')
        async(JS) {
            val jsRuntime : V8 = AndroidApplication.jsRuntime
            val iAmApp = jsRuntime.getObject("iAmApp")
            iAmApp.registerJavaMethod({ _, v8Array ->
                v8Array.getString(0)?.let {
                    Log.e(LOG_APP_TAG, v8Array.getString(0))
                    return@registerJavaMethod
                }
                val modelJson = v8Array.getString(1)
                jsVM = v8Array.getObject(2)
//                Log.i(LOG_APP_TAG, "[KT_LOG] : $modelJson")
                async(UI) {
                    val jsonAdapter = JsonBuilder.instance.adapter<T>(mClazz)
                    val model: T = jsonAdapter.fromJson(modelJson)!!
                    cb(model)
                }.invokeOnCompletion { it?.printStackTrace() }
            }, "get${mClazzName}VMCallback")
            val getCallback = iAmApp.getObject("get${mClazzName}VMCallback")
            val getParams = V8Array(jsRuntime).push("${mClazzName}VM").push(getCallback)
            iAmApp.executeVoidFunction("getViewModel", getParams)

            getParams.release()
            getCallback.release()
            iAmApp.release()
        }.invokeOnCompletion { it?.printStackTrace() }
    }

    fun onDestroy() {
        runBlocking(JS) {jsVM?.release()}
    }

}