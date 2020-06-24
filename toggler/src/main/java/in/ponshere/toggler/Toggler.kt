@file:Suppress("UNCHECKED_CAST")

package `in`.ponshere.toggler

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.annotations.models.BaseToggleMethod
import `in`.ponshere.toggler.helpers.ToggleMethodCreator
import `in`.ponshere.toggler.helpers.TogglesInvocationHandler
import `in`.ponshere.toggler.ui.TogglerActivity
import android.content.Context
import android.content.Intent
import java.lang.reflect.Method
import java.lang.reflect.Proxy


object Toggler {
    private lateinit var methodCreator: ToggleMethodCreator
    private lateinit var clazz: Class<*>
    private lateinit var toggles: Any

    internal val allToggles: MutableList<BaseToggleMethod<*>> by lazy {
        val map = mutableMapOf<Method, BaseToggleMethod<*>>()
        clazz.methods.forEach { method ->
            method?.annotations?.forEach {
                if (it is SwitchToggle) {
                    map[method] = methodCreator.createSwitchToggleMethod(it, method)
                } else if (it is SelectToggle) {
                    map[method] = methodCreator.createSelectToggleMethod(it, method)
                }
            }
        }
        map.values.toMutableList()
    }


    fun <T> init(context: Context, clazz: Class<T>): T {
        this.clazz = clazz
        val sharedPreferences =
            context.getSharedPreferences("toggler_preferences", Context.MODE_PRIVATE)
        methodCreator =
            ToggleMethodCreator(sharedPreferences)
        val togglesInvocationHandler =
            TogglesInvocationHandler(methodCreator)
        toggles = Proxy.newProxyInstance(
            Toggler::class.java.classLoader,
            arrayOf<Class<*>>(clazz),
            togglesInvocationHandler
        )
        return toggles as T
    }

    fun <T> get() = toggles as T

    fun showAllToggles(context: Context) {
        val intent = Intent(context, TogglerActivity::class.java)
        context.startActivity(intent)
    }
}

