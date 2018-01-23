package com.imnotout.kandyv8hook.Views

import com.imnotout.kandyv8hook.Utils.EventEmitter
import org.koin.android.ext.android.inject

interface IActionListener {
    val _emitterObject : EventEmitter
    fun<T> on(key: String, cb: (T?)-> Unit) = _emitterObject.on(key, Pair(this, cb))
    fun<T> once(key: String, cb: (T?)-> Unit) = _emitterObject.once(key, cb)
    fun<T> off(key: String, cb: (T?)-> Unit) = _emitterObject.off(key, cb)
    fun<T> off(key: String) = _emitterObject.off<T>(key, this)
    fun<T> emit(key: String, obj: T) = _emitterObject.emit<T>(key, obj)
}

class ActionListener(override val _emitterObject: EventEmitter) : IActionListener
