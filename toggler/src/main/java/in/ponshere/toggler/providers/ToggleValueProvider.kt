package `in`.ponshere.toggler.providers

abstract class ToggleValueProvider {
    enum class Type {
        LOCAL, FIREBASE
    }
    abstract val type: Type

    abstract fun getStringValue(key: String, defaultValue: String = "") : String
    abstract fun getBooleanValue(key: String, defaultValue: Boolean = false) : Boolean
    abstract fun setStringValue(key: String, value: String)
    abstract fun setBooleanValue(key: String, value: Boolean)

    internal companion object {
        internal fun get(type: Type): ToggleValueProvider {
            if (type == Type.LOCAL) {
                return LocalProvider
            } else if (type == Type.FIREBASE){

            }
            return LocalProvider
        }
    }
}



