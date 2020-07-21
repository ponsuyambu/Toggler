package `in`.ponshere.toggler.firebase.provider

import `in`.ponshere.toggler.v2.provider.ToggleValueProvider
import `in`.ponshere.toggler.v2.toggles.Toggle
import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import java.lang.reflect.Method

object FirebaseValueProvider : ToggleValueProvider() {
    override val name: String = "Firebase"
    override val isSaveAllowed: Boolean = false

    override fun init(context: Context) {

    }

    override fun isSupported(toggle: Toggle<*>): Boolean {
        return toggle.method.isAnnotationPresent(FirebaseToggle::class.java)
    }

    override fun <T> get(key: String, defaultValue: T, clazz: Class<T>, method: Method): T {

        if(clazz.isAssignableFrom(Boolean::class.java)) {
            return FirebaseRemoteConfig.getInstance().getBoolean(key) as T
        } else if(clazz.isAssignableFrom(String::class.java)) {
            return FirebaseRemoteConfig.getInstance().getString(key) as T
        }
        throw IllegalArgumentException("Firbase Provider only supports Boolean and String types")
    }

    override fun <T> save(key: String, value: T, clazz: Class<T>) {
        throw UnsupportedOperationException("You can't update firebase values from the app.")
    }

    override fun configurationMap(): Map<String, String> {
        TODO("Not yet implemented")
    }
}