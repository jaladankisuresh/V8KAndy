package com.imnotout.kandyv8hook

import android.app.Application
import android.content.Context
import android.os.Handler
import com.eclipsesource.v8.V8
import android.os.HandlerThread
import android.util.Log
import com.imnotout.kandyv8hook.JavaScriptBridge.JavaScriptApi
import com.imnotout.kandyv8hook.JavaScriptBridge.JsUtil
import kotlinx.coroutines.experimental.android.HandlerContext
import kotlinx.coroutines.experimental.*
import java.io.InputStream

const val LOG_APP_TAG = "androidj2v8hook"
val JS: HandlerContext by lazy{
    AndroidApplication.JS
}
class AndroidApplication : Application() {
    companion object {
        private lateinit var jsHandlerThread: HandlerThread
        lateinit var jsRuntime: V8 private set
        lateinit var JS: HandlerContext private set
    }
    override fun onCreate() {
        super.onCreate()
        //Create JavaScript Runtime
        jsHandlerThread = HandlerThread("JSHandlerThread")
        jsHandlerThread.start()
        JS = HandlerContext(Handler(jsHandlerThread.looper), "JS")
        Log.e(LOG_APP_TAG,"ImNotOut App Started")
        runBlocking(JS) {
            jsRuntime = V8.createV8Runtime()
            initJSRuntime(jsRuntime)
        }
    }

    override fun onTerminate() {
        //Release JavaScript Runtime Native Handle (C++ Pointer)
        runBlocking(JS) {
            jsRuntime.release()
        }
        jsHandlerThread.quitSafely()
        super.onTerminate()
    }

    private fun initJSRuntime(runtime: V8) {
        JsUtil.registerUtilFunctions(runtime)  // Init runtime with console.log and console.error fn()
        val inStream : InputStream = assets.open("js/app.bundle.js")
        runtime.executeVoidScript(inStream.bufferedReader().use { it.readText() })
        JavaScriptApi.prepareIORequestJsFunc(runtime)
    }
}