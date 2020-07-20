package `in`.ponshere.toggler.v2.provider

import android.content.Context
import android.content.SharedPreferences

object LocalValueProvider : ToggleValueProvider() {
    lateinit var sharedPreferences: SharedPreferences

    override val name = "Local"
    override val isSaveAllowed = true

    override val priorityOrder =  Priority.VERY_LOW

    override fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("toggler_preferences", Context.MODE_PRIVATE)
    }

    override fun <T> get(key: String, defaultValue: T, clazz: Class<T>): T {
        if(clazz.isAssignableFrom(Boolean::class.java)) {
            return sharedPreferences.getBoolean(key, defaultValue as Boolean) as T
        } else if(clazz.isAssignableFrom(String::class.java)) {
            return sharedPreferences.getString(key, defaultValue as String) as T
        }
        throw IllegalArgumentException("LocalProvider only supports Boolean and String types")
    }

    override fun <T> save(key: String, value: T) {
        TODO("Not yet implemented")
    }

    override fun configurationMap(): Map<String, String> {
        TODO("Not yet implemented")
    }
}