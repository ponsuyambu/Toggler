package `in`.ponshere.toggler

import `in`.ponshere.toggler.mocks.FakeToggles
import `in`.ponshere.toggler.ui.TogglerActivity
import android.content.ComponentName
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TogglerUITest {

    private lateinit var appToggles: FakeToggles

//    @JvmField @Rule
//     val intentsTestRule = IntentsTestRule(TogglerActivity::class.java)

    @Before
    fun setup() {
        appToggles = Toggler.init(ApplicationProvider.getApplicationContext(), FakeToggles::class.java)
    }

    @Test
    fun shouldLaunchTogglerActivityWhenRequested() {
        Intents.init()
        launchActivity<TogglerActivity>()

        Toggler.showAllToggles(ApplicationProvider.getApplicationContext())

        intended(hasComponent(ComponentName(
                    ApplicationProvider.getApplicationContext(),
                    TogglerActivity::class.java)))

        Intents.release()

    }
}