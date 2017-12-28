package com.imnotout.kandyv8hook.Utils

import com.imnotout.kandyv8hook.Models.IParsable

// Kotlin port of Node.js Core EventEmitter
// https://github.com/nodejs/node/blob/3bb6f07d528905d2ba6b9a4710890bc74350d7f0/lib/events.js
object EventEmitter {
    private var events : Map<String, out ArrayList<(IParsable)-> Unit>> = mutableMapOf()
    fun<T: IParsable> on(key: String, cb: (T)-> Unit) {
        events.get(key)?.run {
            add { cb }
        }
    }
    fun<T: IParsable> off(key: String, cb: (T)-> Unit) {
        events.get(key)?.run {
            val cbPosition = indexOf { cb }
            if(cbPosition == -1) return
            remove { cbPosition }
        }
    }
    fun<T: IParsable> emit(key: String, obj: T) {
        events.get(key)?.map {
            when(it) {
                is (ParsableWrap<T>) -> Unit -> {
                    // listener is registered as once callback, call the listener only once and remove the listener silently
                    it(ParsableWrap(obj))
                    this.off(key, it)
                }
                else -> it( obj )
            }
        }
    }
    fun<T: IParsable> once(key: String, cb: (T)-> Unit) = on(key, { k : ParsableWrap<T> -> cb(k.obj) })
    fun<T: IParsable> addListener(key: String, cb: (T)-> Unit) = on(key, cb)

    fun<T: IParsable> removeListener(key: String, cb: (T)-> Unit) = off(key, cb)
}
class ParsableWrap<T: IParsable>(val obj: T) : IParsable by obj {
    val onceOnly = true
}

