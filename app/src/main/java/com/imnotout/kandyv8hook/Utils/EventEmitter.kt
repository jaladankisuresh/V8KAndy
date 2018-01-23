package com.imnotout.kandyv8hook.Utils

import android.util.Log
import com.imnotout.kandyv8hook.LOG_APP_TAG
import com.imnotout.kandyv8hook.Views.IActionListener

// Kotlin port of Node.js Core EventEmitter
// https://github.com/nodejs/node/blob/3bb6f07d528905d2ba6b9a4710890bc74350d7f0/lib/events.js
// It also takes cues from Node.kt MIT Project
// https://github.com/jonninja/node.kt/blob/master/src/main/kotlin/node/EventEmitter.kt

open class EventEmitter {
    private val events = HashMap<String, MutableList<Pair<Any?, (Any?)-> Unit>>>()
    fun<T> on(key: String, callPair: Pair<Any?, (T?)-> Unit>) {
        val cbUnsafe = callPair.second as (Any?)-> Unit
        if(events[key] == null) {
            events[key] = mutableListOf<Pair<Any?, (Any?)-> Unit>>()
        }
        events[key]!!.add(Pair(callPair.first, cbUnsafe))
    }
    fun<T> on(key: String, cb: (T?)-> Unit) = on(key, callPair = Pair(null, cb))
    fun<T> off(key: String, caller: Any) {
        events[key]?.run {
            map { if(it.first?.equals(caller) ?: false) remove(it) }
            if( size == 0) events.remove(key)
        }
    }
    fun<T> off(key: String, cb: (T?)-> Unit) {
        events[key]?.run {
            val cbUnsafe = cb as (Any?)-> Unit
            map {
                if(it.second.equals(cbUnsafe)) remove(it)
            }
            if( size == 0) events.remove(key)
        }
    }
    fun<T> emit(key: String, obj: T) {
        events[key]?.forEach {
            it.second(obj)
        }
    }
    fun<T> once(key: String, cb: (T?)-> Unit) {
        val cbUnsafe = cb as (Any?)-> Unit
        fun<T> callOnce(obj: T?) {
            off<T>(key, ::callOnce)
            cbUnsafe(obj)
        }
        on<T>(key, ::callOnce)
    }
    fun<T> addListener(key: String, cb: (T?)-> Unit) = on(key, cb)
    fun<T> addListener(key: String, callPair: Pair<Any?, (T?)-> Unit>) = on(key, callPair = callPair)
    fun<T> removeListener(key: String, caller: Any) = off<T>(key, caller = caller)
    fun<T> removeListener(key: String, cb: (T?)-> Unit) = off(key, cb)
}