@file:Suppress("UNCHECKED_CAST")

package `in`.ponshere.toggler

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.annotations.models.BaseToggleMethodImplementation
import `in`.ponshere.toggler.helpers.ToggleMethodCreator
import `in`.ponshere.toggler.helpers.TogglesInvocationHandler
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
    internal lateinit var methodCreator: ToggleMethodCreator
    @VisibleForTesting
    internal lateinit var clazz: Class<*>
    @VisibleForTesting
    internal lateinit var toggles: Any

    internal var toggleValueProvider = ToggleValueProvider.FIREBASE

    internal val allToggles: MutableList<BaseToggleMethodImplementation<*>> by lazy {
        val map = mutableMapOf<Method, BaseToggleMethodImplementation<*>>()
        clazz.methods.forEach { method ->
            method?.annotations?.forEach {
                if (it is SwitchToggle) {
                    map[method] = methodCreator.createSwitchToggleMethod(it, method, toggleValueProvider)
                } else if (it is SelectToggle) {
                    map[method] = methodCreator.createSelectToggleMethod(it, method, toggleValueProvider)
                }
            }
        }
        map.values.toMutableList()
    }


    fun <T> init(context: Context, clazz: Class<T>, toggleValueProvider: ToggleValueProvider = ToggleValueProvider.FIREBASE): T {
        this.clazz = clazz
        val sharedPreferences =
            context.getSharedPreferences("toggler_preferences", Context.MODE_PRIVATE)
        methodCreator =
            ToggleMethodCreator(sharedPreferences)
        val togglesInvocationHandler =
            TogglesInvocationHandler(methodCreator, this)
        toggles = Proxy.newProxyInstance(
            Toggler::class.java.classLoader,
            arrayOf<Class<*>>(clazz),
            togglesInvocationHandler
        )
        this.toggleValueProvider = toggleValueProvider
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

