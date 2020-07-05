package `in`.ponshere.toggler.providers

import com.google.firebase.remoteconfig.FirebaseRemoteConfig

internal object FirebaseProvider : ToggleValueProvider<FirebaseProvider.Value<*>>() {
    override val type: Type = Type.FIREBASE

    override fun getStringValue(key: String, defaultValue: String): String {
        return FirebaseRemoteConfig.getInstance().getString(key)
    }

    override fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return FirebaseRemoteConfig.getInstance().getBoolean(key)
    }
    override fun update(key: String, value: Value<*>) {
        throwUnsupportedOperation()
    }

    inline fun<reified T : Value<*>> get(key: String, defaultValue: T) : T {
        return if(T::class == StringValue::class) {
            StringValue(FirebaseRemoteConfig.getInstance().getString(key)) as T
        } else {
            BooleanValue(FirebaseRemoteConfig.getInstance().getBoolean(key)) as T
        }
    }

    private fun throwUnsupportedOperation() {
        throw UnsupportedOperationException("Firebase Value Provider does not permit this operation")
    }

    interface Adapter {
        fun firebaseProviderValue(): String
        fun isFirebaseKeyConfigured(): Boolean
    }

    internal open class Value<T> constructor(val value : T)
    class StringValue(value: String) : Value<String> (value)
    class BooleanValue(value: Boolean) : Value<Boolean> (value)

}