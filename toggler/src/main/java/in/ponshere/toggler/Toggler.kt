@file:Suppress("UNCHECKED_CAST")

package `in`.ponshere.toggler

import `in`.ponshere.toggler.annotations.SelectToggle
import `in`.ponshere.toggler.annotations.SwitchToggle
import `in`.ponshere.toggler.annotations.models.FeatureToggleMethod
import android.content.Context
import android.content.Intent
import java.lang.reflect.Method
import java.lang.reflect.Proxy


object Toggler {
    private lateinit var methodCreator: ToggleMethodCreator
    private lateinit var clazz: Class<*>

    internal val allToggles: MutableList<FeatureToggleMethod<*>> by lazy {
        val map = mutableMapOf<Method, FeatureToggleMethod<*>>()
        clazz.methods.forEach { method ->
            method?.annotations?.forEach {
                if (it is SwitchToggle) {
                    map[method] = methodCreator.createSwitchToggleMethod(it)
                } else if (it is SelectToggle) {
                    map[method] = methodCreator.createSelectToggleMethod(it)
                }
            }
        }
        map.values.toMutableList()
    }

    fun <T> initialize(context: Context, clazz: Class<T>): T {
        this.clazz = clazz
        val sharedPreferences =
            context.getSharedPreferences("toggler_preferences", Context.MODE_PRIVATE)
        methodCreator = ToggleMethodCreator(sharedPreferences)
        val togglesInvocationHandler = TogglesInvocationHandler(methodCreator)
        val proxyInstance = Proxy.newProxyInstance(
            Toggler::class.java.classLoader,
            arrayOf<Class<*>>(clazz),
            togglesInvocationHandler
        )
        return proxyInstance as T
    }


    fun launchTogglerActivity(context: Context) {
        val intent = Intent(context, TogglerActivity::class.java)
        context.startActivity(intent)
    }
}

