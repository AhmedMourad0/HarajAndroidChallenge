package com.example.harajtask

import android.content.Context
import android.view.View
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.harajtask.common.MainActivity
import com.example.harajtask.posts.adapter.PostsRecyclerAdapter.ViewHolder
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FindPostsFragmentTests {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var appCtx: Context
    private lateinit var decorView: View

    @Before
    fun setup() {
        appCtx = InstrumentationRegistry.getInstrumentation().targetContext
        activityRule.scenario.onActivity { activity: MainActivity ->
            decorView = activity.window.decorView
        }
    }

    @Test
    fun postsList_isVisible() {
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))
    }

    @Test
    fun postsListItem_onClickGoesToPostDetailsFragment() {
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler))
            .perform(actionOnItemAtPosition<ViewHolder>(4, click()))
        onView(withId(R.id.body)).check(matches(isDisplayed()))
    }

    @Test
    fun onBackWhenPostDetailsFragmentIsShow_goBackToFindPostsFragment() {
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler))
            .perform(actionOnItemAtPosition<ViewHolder>(4, click()))
        onView(withId(R.id.body)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))
    }

    @Test
    fun menuItems_onClickedShowsComingSoonToast() {
        onView(withContentDescription(R.string.search)).perform(click())
        onView(withText(R.string.coming_soon))
            .inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigationDrawerIcon_onClickedShowsComingSoonToast() {
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description))
            .perform(click())
        onView(withText(R.string.coming_soon))
            .inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()))
    }
}
