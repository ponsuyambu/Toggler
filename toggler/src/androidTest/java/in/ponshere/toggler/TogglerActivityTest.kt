package `in`.ponshere.toggler

import `in`.ponshere.toggler.mocks.FakeToggles
import `in`.ponshere.toggler.testutils.RecyclerViewItemCountAssertion
import `in`.ponshere.toggler.ui.TogglerActivity
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TogglerActivityTest {

    @Before
    fun setup() {
        Toggler.init(ApplicationProvider.getApplicationContext(), FakeToggles::class.java)
        //FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().context)

    }

    @Test
    fun shouldDisplayAllTogglesInScreen() {
        launchActivity<TogglerActivity>()
        onView(withId(R.id.togglerList)).check(RecyclerViewItemCountAssertion(6))
    }
}