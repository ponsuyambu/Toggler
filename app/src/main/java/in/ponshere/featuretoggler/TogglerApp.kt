package `in`.ponshere.featuretoggler

import `in`.ponshere.toggler.ToggleValueProvider
import `in`.ponshere.toggler.Toggler
import android.app.Application

class TogglerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Toggler.init(this, AppToggles::class.java, ToggleValueProvider.Type.FIREBASE)
    }
}