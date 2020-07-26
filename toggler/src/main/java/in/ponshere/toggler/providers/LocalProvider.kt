package `in`.ponshere.toggler.providers

import android.content.Context
import android.content.SharedPreferences

internal object LocalProvider : ToggleValueProvider<LocalProvider.Value<*>>() {
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

    internal open class Value<T> constructor(val value : T)
    class StringValue(value: String) : Value<String> (value)
    class BooleanValue(value: Boolean) : Value<Boolean> (value)

//    class Value {
//        var value : Any
//        constructor(value: String) {
//            this.value = value
//        }
//        constructor(value: Boolean) {
//            this.value = value
//        }
//    }

    interface Adapter {
        fun localProviderValue(): String
    }

    override fun update(key: String, value: Value<*>) {
        value.value.let {
            when(it) {
                is String -> sharedPreferences.edit().apply {
                    this.putString(key, it).apply()
                }
                is Boolean -> sharedPreferences.edit().apply {
                    this.putBoolean(key, it).apply()
                }
                else ->  { }
            }
        }
    }

//    override fun<T : Value<*>> get(key: String, defaultValue: T) : T {
//        return if(defaultValue::class == StringValue::class) {
//            StringValue(sharedPreferences.all[key]?.toString() ?: defaultValue.value.toString()) as T
//        } else {
//            BooleanValue(sharedPreferences.getBoolean(key, defaultValue.value.toString().toBoolean())) as T
//        }
//    }
}