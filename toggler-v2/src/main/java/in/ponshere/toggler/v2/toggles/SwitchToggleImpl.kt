package `in`.ponshere.toggler.v2.toggles

class SwitchToggleImpl(key: String, defaultValue: Boolean, displayName: String) :
    Toggle<Boolean>(Type.Switch, key, defaultValue, displayName) {

    override fun classType() = Boolean::class.java

}