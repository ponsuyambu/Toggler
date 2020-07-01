package `in`.ponshere.toggler

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

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

internal object LocalProvider : ToggleValueProvider() {
    lateinit var sharedPreferences: SharedPreferences
    override val type: Type = Type.LOCAL

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("toggler_preferences", Context.MODE_PRIVATE)
    }
    override fun getStringValue(key: String, defaultValue: String): String {
        return sharedPreferences.all[key]?.toString() ?: defaultValue
    }

    override fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun setStringValue(key: String, value: String) {
        sharedPreferences.edit().apply {
            this.putString(key, value).apply()
        }
    }

    override fun setBooleanValue(key: String, value: Boolean) {
        sharedPreferences.edit().apply {
            this.putBoolean(key, value).apply()
        }
    }
}

internal object FirebaseProvider : ToggleValueProvider() {
    override val type: Type = Type.FIREBASE

    override fun getStringValue(key: String, defaultValue: String): String {
        return FirebaseRemoteConfig.getInstance().getString(key)
    }

    override fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return FirebaseRemoteConfig.getInstance().getBoolean(key)
    }


    override fun setStringValue(key: String, value: String) {
        throwUnsupportedOperation()
    }

    override fun setBooleanValue(key: String, value: Boolean) {
        throwUnsupportedOperation()
    }

    private fun throwUnsupportedOperation() {
        throw UnsupportedOperationException("Firebase Value Provider does not permit this operation")
    }

}