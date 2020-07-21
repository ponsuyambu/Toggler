package `in`.ponshere.toggler.firebase.provider

import `in`.ponshere.toggler.v2.helpers.Utils
import `in`.ponshere.toggler.v2.provider.ToggleValueProvider
import `in`.ponshere.toggler.v2.toggles.Toggle
import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

object FirebaseValueProvider : ToggleValueProvider() {
    override val name: String = "Firebase"
    override val isSaveAllowed: Boolean = false

    override fun init(context: Context) { }

    override fun isSupported(toggle: Toggle<*>): Boolean {
        return hasAnnotation(toggle)
    }

    private fun hasAnnotation(toggle: Toggle<*>) =
        toggle.method.isAnnotationPresent(FirebaseToggle::class.java)

    override fun <T> get(toggle: Toggle<*>, clazz: Class<T>): T {
        var key = toggle.key

        if(hasAnnotation(toggle)) {
            val annotation = toggle.method.getAnnotation(FirebaseToggle::class.java)
            if(annotation != null && annotation.remoteConfigKey.isNotBlank()) {
                key = annotation.remoteConfigKey
            }
        }

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

    override fun configurationMap(toggle: Toggle<*>): Map<String, CharSequence> {
        val configurations = mutableMapOf<String, CharSequence>()
        if(hasAnnotation(toggle)) {
            val annotation = toggle.method.getAnnotation(FirebaseToggle::class.java)
            if(annotation != null)
                configurations["Remote config key"] = if(annotation.remoteConfigKey.isBlank())
                    Utils.notConfiguredNotation() else annotation.remoteConfigKey
        }
        return configurations
    }
}