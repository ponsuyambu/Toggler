package `in`.ponshere.toggler.v2

import `in`.ponshere.toggler.v2.annotations.SelectToggle
import `in`.ponshere.toggler.v2.annotations.SwitchToggle
import `in`.ponshere.toggler.v2.helpers.ToggleFactory
import `in`.ponshere.toggler.v2.helpers.TogglesInvocationHandler
import `in`.ponshere.toggler.v2.provider.LocalValueProvider
import `in`.ponshere.toggler.v2.provider.ToggleValueProvider
import `in`.ponshere.toggler.v2.toggles.Toggle
import `in`.ponshere.toggler.v2.ui.TogglerActivity
import android.app.Activity
import android.content.Context
import android.content.Intent
import java.lang.reflect.Method
import java.lang.reflect.Proxy

object Toggler {
    private val valueProviders: MutableList<ToggleValueProvider> = mutableListOf(LocalValueProvider)
    lateinit var highPriorityValueProvider : ToggleValueProvider
    lateinit var clazz: Class<*>
    internal lateinit var toggles: Any
    private lateinit var toggleFactory: ToggleFactory

    @Suppress("UNCHECKED_CAST")
    fun<T> init(context: Context, clazz: Class<T>,
                valueProviders: List<ToggleValueProvider> = emptyList(),
                highPriorityValueProvider : ToggleValueProvider = LocalValueProvider
    ) : T {
        this.clazz = clazz
        this.highPriorityValueProvider = LocalValueProvider
        if(valueProviders.isNotEmpty()) {
            this.valueProviders.addAll(valueProviders)
        }
        toggleFactory =
            ToggleFactory()
        val togglesInvocationHandler =
            TogglesInvocationHandler(toggleFactory, this)
        toggles = Proxy.newProxyInstance(
            Toggler::class.java.classLoader,
            arrayOf<Class<*>>(clazz),
            togglesInvocationHandler
        )
        this.valueProviders.forEach { it.init(context) }
        return toggles as T
    }

    internal val allToggles: MutableList<Toggle<*>> by lazy {
        val map = mutableMapOf<Method, Toggle<*>>()
        clazz.methods.forEach { method ->
            method?.annotations?.forEach {
                if (it is SwitchToggle) {
                    map[method] = toggleFactory.createSwitchToggle(it, method)
                } else if (it is SelectToggle) {
                    map[method] = toggleFactory.createSelectToggleMethod(it, method)
                }
            }
        }
        map.values.toMutableList()
    }

    fun <T> get() = toggles as T

    fun showAllToggles(context: Context) {
        val intent = Intent(context, TogglerActivity::class.java)
        if((context is Activity).not()) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}