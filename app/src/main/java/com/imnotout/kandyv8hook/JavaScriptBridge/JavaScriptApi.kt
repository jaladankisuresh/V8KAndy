package com.imnotout.kandyv8hook.JavaScriptBridge

import com.eclipsesource.v8.JavaVoidCallback
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.imnotout.kandyv8hook.JS
import com.imnotout.kandyv8hook.NetworkIO.HttpService
import kotlinx.coroutines.experimental.*
import java.io.IOException

sealed class JavaScriptApi {
    companion object {
        inline suspend fun makeHttpRequest(runtime: V8, parameters: Array<String>) {
            val url: String
            val data: String
            val token = parameters[0]
            //Mock of JavaScript function for reference only
            //httpGet : function(token, url, data) {
            //dump interface that would be overriden in the target environment (browser, android, iOS)
            //}

            val iAmApp = runtime.getObject("iAmApp")
            val iAmAjax = iAmApp.getObject("iAmAjax")
            try {
                val dataJSON : String = when (parameters.size) {
                    2 -> {
                        url = parameters[1]
                        HttpService.get(url)
                    }
                    3 -> {
                        url = parameters[1]
                        data = parameters[2]
                        HttpService.post(url, data)
                    }
                    else -> throw IOException("Invalid Paramters Exception")
                }
                val onResponseParams = V8Array(runtime).push(token).push(dataJSON)
                iAmAjax.executeVoidFunction("onSuccessResponse", onResponseParams)
            }
            catch (e : Exception) {
                val onResponseParams = V8Array(runtime).push(token).push(e.message)
                e.printStackTrace()
                iAmAjax.executeVoidFunction("onErrorResponse", onResponseParams)
            }
            finally {
                iAmAjax.release()
                iAmApp.release()
            }
        }

        fun prepareIORequestJsFunc(runtime: V8) {
            val ioRequestJavaCallback = JavaVoidCallback { _, parameters ->
                val strParamters = Array<String>(parameters.length()) { parameters[it]!! as String }
                async(JS) { makeHttpRequest(runtime, strParamters) }
            }
            val iAmApp = runtime.getObject("iAmApp")
            val iAmAjax = iAmApp.getObject("iAmAjax")
            iAmAjax.registerJavaMethod(ioRequestJavaCallback, "httpGet")

            iAmAjax.release()
            iAmApp.release()
        }
    }
}