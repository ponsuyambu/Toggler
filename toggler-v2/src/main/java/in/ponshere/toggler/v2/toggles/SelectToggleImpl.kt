package `in`.ponshere.toggler.v2.toggles

class SelectToggleImpl(key: String, defaultValue: String, displayName: String, selectOptions: Array<String>) :
    Toggle<String>(Type.Select, key, defaultValue, displayName) {

    override fun classType() = String::class.java
}