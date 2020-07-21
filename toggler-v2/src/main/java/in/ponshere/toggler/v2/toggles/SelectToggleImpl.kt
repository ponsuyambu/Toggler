package `in`.ponshere.toggler.v2.toggles

import java.lang.reflect.Method

class SelectToggleImpl(key: String, defaultValue: String, displayName: String, selectOptions: Array<String>, method: Method) :
    Toggle<String>(Type.Select, key, defaultValue, displayName, method) {

    override fun classType() = String::class.java
}