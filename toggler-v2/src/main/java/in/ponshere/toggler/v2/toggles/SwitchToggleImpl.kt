package `in`.ponshere.toggler.v2.toggles

import java.lang.reflect.Method

class SwitchToggleImpl constructor(key: String, defaultValue: Boolean, displayName: String, method: Method) :
    Toggle<Boolean>(Type.Switch, key, defaultValue, displayName, method) {

    override fun classType() = Boolean::class.java

}