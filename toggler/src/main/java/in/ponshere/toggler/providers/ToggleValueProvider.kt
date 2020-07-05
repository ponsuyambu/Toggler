package `in`.ponshere.toggler.providers

abstract class ToggleValueProvider<in T> {
    enum class Type {
        LOCAL, FIREBASE
    }
    abstract val type: Type

    abstract fun getStringValue(key: String, defaultValue: String = "") : String
    abstract fun getBooleanValue(key: String, defaultValue: Boolean = false) : Boolean

    abstract fun update(key: String, value: T)
//    abstract fun< T : LocalProvider.Value<*>> get(key: String, defaultValue: T) : T
}



