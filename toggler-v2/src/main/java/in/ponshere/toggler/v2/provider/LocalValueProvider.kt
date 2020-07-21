package `in`.ponshere.toggler.v2.provider

import `in`.ponshere.toggler.v2.toggles.Toggle
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object LocalValueProvider : ToggleValueProvider() {
    private lateinit var sharedPreferences: SharedPreferences

    override val name = "Local"
    override val isSaveAllowed = true

    override fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("toggler_preferences", Context.MODE_PRIVATE)
    }

    override fun isSupported(toggle: Toggle<*>) = true

    override fun <T> get(toggle: Toggle<*>, clazz: Class<T>): T {
        if(clazz.isAssignableFrom(Boolean::class.java)) {
            return sharedPreferences.getBoolean(toggle.key, toggle.defaultValue as Boolean) as T
        } else if(clazz.isAssignableFrom(String::class.java)) {
            return sharedPreferences.getString(toggle.key, toggle.defaultValue as String) as T
        }
        throw IllegalArgumentException("LocalProvider only supports Boolean and String types")
    }

    override fun <T> save(key: String, value: T, clazz: Class<T>) {
        when {
            clazz.isAssignableFrom(Boolean::class.java) -> {
                sharedPreferences.edit {
                    putBoolean(key, value as Boolean)
                }
            }
            clazz.isAssignableFrom(String::class.java) -> {
                sharedPreferences.edit {
                    putString(key, value as String)
                }
            }
            else -> throw IllegalArgumentException("LocalProvider only supports Boolean and String types")
        }

    }

    override fun configurationMap(toggle: Toggle<*>): Map<String, CharSequence> {
        return emptyMap()
    }
}