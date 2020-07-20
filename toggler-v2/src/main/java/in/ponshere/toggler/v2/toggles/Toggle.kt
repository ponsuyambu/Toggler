package `in`.ponshere.toggler.v2.toggles

import `in`.ponshere.toggler.v2.Toggler

abstract class Toggle<T>(val type: Type,
                         val key: String,
                         private val defaultValue: T,
                         val displayName: String,
                         var isExpanded: Boolean = false
) {

    abstract fun classType() : Class<T>

    fun value() : T {
        return Toggler.highPriorityValueProvider.get(key, defaultValue, classType())
    }

    sealed class Type : Comparable<Type> {
        abstract val value: Int
        override fun compareTo(other: Type) = value - other.value

        internal object Switch : Type() {
            override val value: Int = 1
        }

        internal object Select : Type() {
            override val value: Int = 2

        }
    }
}