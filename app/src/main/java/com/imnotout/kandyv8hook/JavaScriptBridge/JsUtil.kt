package com.imnotout.kandyv8hook.JavaScriptBridge

import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.imnotout.kandyv8hook.LOG_APP_TAG

sealed class JsUtil {
    companion object {
        fun registerUtilFunctions(runtime: V8) {
            val v8Console = V8Object(runtime)
            runtime.add("jConsole", v8Console)
            v8Console.registerJavaMethod(Console, "log", "log", arrayOf<Class<*>>(String::class.java))
            v8Console.registerJavaMethod(Console, "error", "error", arrayOf<Class<*>>(String::class.java))
            v8Console.release()
        }
    }

    object Console {
        fun log(message: String) {
            Log.i(LOG_APP_TAG, "[JS_LOG] : $message")
        }

        fun error(message: String) {
            Log.e(LOG_APP_TAG, "[JS_ERROR] : $message")
        }
    }

}
