package `in`.ponshere.toggler.firebase.provider

import `in`.ponshere.toggler.v2.provider.ToggleValueProvider
import `in`.ponshere.toggler.v2.toggles.Toggle
import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

object FirebaseValueProvider : ToggleValueProvider() {
    override val name: String = "Firebase"
    override val isSaveAllowed: Boolean = false

    override fun init(context: Context) {

    }

    override fun isSupported(toggle: Toggle<*>): Boolean {
        return hasAnnotation(toggle)
    }

    private fun hasAnnotation(toggle: Toggle<*>) =
        toggle.method.isAnnotationPresent(FirebaseToggle::class.java)

    override fun <T> get(toggle: Toggle<*>, clazz: Class<T>): T {
        if(clazz.isAssignableFrom(Boolean::class.java)) {
            return FirebaseRemoteConfig.getInstance().getBoolean(toggle.key) as T
        } else if(clazz.isAssignableFrom(String::class.java)) {
            return FirebaseRemoteConfig.getInstance().getString(toggle.key) as T
        }
        throw IllegalArgumentException("Firbase Provider only supports Boolean and String types")
    }

    override fun <T> save(key: String, value: T, clazz: Class<T>) {
        throw UnsupportedOperationException("You can't update firebase values from the app.")
    }

    override fun configurationMap(toggle: Toggle<*>): Map<String, String> {
        val configurations = mutableMapOf<String, String>()
        if(hasAnnotation(toggle)) {
            val annotation = toggle.method.getAnnotation(FirebaseToggle::class.java)
            if(annotation != null)
                configurations["Remote config key"] = annotation.remoteConfigKey
        }
        return configurations
    }
}