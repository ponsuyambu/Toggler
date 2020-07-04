package `in`.ponshere.toggler.providers

import android.content.Context
import android.content.SharedPreferences

internal object LocalProvider : ToggleValueProvider() {
    lateinit var sharedPreferences: SharedPreferences
    override val type: Type =
        Type.LOCAL

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

    interface Adapter {
        fun localProviderValue(): String
    }
}