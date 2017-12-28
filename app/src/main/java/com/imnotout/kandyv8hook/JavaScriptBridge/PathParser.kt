package com.imnotout.kandyv8hook.JavaScriptBridge

import com.imnotout.kandyv8hook.Models.IParsable
import java.io.InvalidObjectException

tailrec fun pathParser (obj: Any, path: String) : IParsable {
    val index = path.indexOf('/', 1)
    if(index == -1) return (obj as IParsable).get(path.substring(1))

    val childObj = when(obj) {
        is IParsable -> obj.get(path.substring(1, index))
        is List<*> -> obj.get(path.substring(1, index) as Int)
        else -> throw(InvalidObjectException("obj type is not IParsable/List<IParsable>"))
    }
    return pathParser(childObj!!, path.substring(index))
}

inline fun <T : IParsable> T.get(prop: String): T {
    return this.javaClass
            .getMethod("get${prop.capitalize()}") // to get property called `name`
            .invoke(this) as T
}

//var pathParser = function(obj, path) {
//    let index = path.indexOf('/', 1);
//    if(index === -1) {
//        return obj[path.substring(1)];
//    }
//    let childObj = obj[path.substring(1, index)];
//    return pathParser(childObj, path.substring(index));
//};
