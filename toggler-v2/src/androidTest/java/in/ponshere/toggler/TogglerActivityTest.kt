package `in`.ponshere.toggler

import `in`.ponshere.toggler.mocks.FakeToggles
import `in`.ponshere.toggler.testutils.RecyclerViewItemCountAssertion
import `in`.ponshere.toggler.v2.Toggler
import `in`.ponshere.toggler.v2.ui.TogglerActivity
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TogglerActivityTest {

    private lateinit var appToggles: FakeToggles

    @Before
    fun setup() {
        appToggles = Toggler.init(ApplicationProvider.getApplicationContext(), FakeToggles::class.java)
    }

    @Test
    fun shouldDisplayAllTogglesInScreen() {
        launchActivity<TogglerActivity>()
        onView(withId(R.id.togglerList)).check(RecyclerViewItemCountAssertion(appToggles.totalNumberOfToggles() +2))
    }
}