@file:Suppress("UNCHECKED_CAST")

package `in`.ponshere.toggler

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.helpers.ToggleFactory
import `in`.ponshere.toggler.helpers.TogglesInvocationHandler
import `in`.ponshere.toggler.providers.FirebaseProvider
import `in`.ponshere.toggler.providers.LocalProvider
import `in`.ponshere.toggler.providers.ToggleValueProvider
import `in`.ponshere.toggler.toggles.Toggle
import `in`.ponshere.toggler.ui.TogglerActivity
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.annotation.VisibleForTesting
import java.lang.reflect.Method
import java.lang.reflect.Proxy


object Toggler {
    @VisibleForTesting
    internal lateinit var toggleFactory: ToggleFactory
    @VisibleForTesting
    internal lateinit var clazz: Class<*>
    @VisibleForTesting
    internal lateinit var toggles: Any

    internal var toggleValueProviderType = ToggleValueProvider.Type.FIREBASE

    internal val toggleValueProvider: ToggleValueProvider<*>
        get() {
            if(toggleValueProviderType == ToggleValueProvider.Type.FIREBASE)
                return FirebaseProvider
            return LocalProvider
        }

    internal val allToggles: MutableList<Toggle<*>> by lazy {
        val map = mutableMapOf<Method, Toggle<*>>()
        clazz.methods.forEach { method ->
            method?.annotations?.forEach {
                if (it is SwitchToggle) {
                    map[method] = toggleFactory.createSwitchToggle(it, method, toggleValueProviderType)
                } else if (it is SelectToggle) {
                    map[method] = toggleFactory.createSelectToggleMethod(it, method, toggleValueProviderType)
                }
            }
        }
        map.values.toMutableList()
    }


    fun <T> init(context: Context, clazz: Class<T>, toggleValueProviderType: ToggleValueProvider.Type = ToggleValueProvider.Type.FIREBASE): T {
        this.clazz = clazz
        val sharedPreferences =
            context.getSharedPreferences("toggler_preferences", Context.MODE_PRIVATE)
        LocalProvider.init(context)
        toggleFactory =
            ToggleFactory(sharedPreferences)
        val togglesInvocationHandler =
            TogglesInvocationHandler(toggleFactory, this)
        toggles = Proxy.newProxyInstance(
            Toggler::class.java.classLoader,
            arrayOf<Class<*>>(clazz),
            togglesInvocationHandler
        )
        this.toggleValueProviderType = toggleValueProviderType
        return toggles as T
    }

    fun <T> get() = toggles as T

    fun showAllToggles(context: Context) {
        val intent = Intent(context, TogglerActivity::class.java)
        if((context is Activity).not()) {
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}

