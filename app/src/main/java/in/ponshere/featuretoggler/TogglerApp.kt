package `in`.ponshere.featuretoggler

import `in`.ponshere.toggler.Toggler
import android.app.Application

class TogglerApp : Application() {
    companion object {
        lateinit var togglerConfiguration: AppToggles
    }

    override fun onCreate() {
        super.onCreate()
        togglerConfiguration = Toggler.initialize(this, AppToggles::class.java)
    }
}