package `in`.ponshere.featuretoggler

import `in`.ponshere.toggler.Toggler
import `in`.ponshere.toggler.providers.ToggleValueProvider
import android.app.Application

class TogglerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Toggler.init(this, AppToggles::class.java, ToggleValueProvider.Type.FIREBASE)
    }
}