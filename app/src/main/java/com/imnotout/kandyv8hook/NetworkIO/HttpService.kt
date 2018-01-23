package com.imnotout.kandyv8hook.NetworkIO

import kotlinx.coroutines.experimental.*
import okhttp3.*

const val BASE_WEB_API_URL = "http://10.0.2.2:3000/"
//const val BASE_WEB_API_URL = "https://imnotout.com/api/"
class HttpService {
    companion object {
        val httpClient = OkHttpClient()
        suspend fun get(uri: String) : String {
            val request = Request.Builder()
                    .url(BASE_WEB_API_URL + uri)
                    .build()
            return makeIORequest(request).await()
        }
        suspend fun post(uri: String, jsonBody: String) : String {
            val JsonType = MediaType.parse("application/json; charset=utf-8")
            val requestBody = RequestBody.create(JsonType, jsonBody)
            val request = Request.Builder()
                    .url(BASE_WEB_API_URL + uri)
                    .post(requestBody)
                    .build()
            return makeIORequest(request).await()
        }
        private suspend inline fun makeIORequest(request: Request) : Call = async(CommonPool) {
            // Runs in background
            httpClient.newCall(request)
        }.await()
    }
}