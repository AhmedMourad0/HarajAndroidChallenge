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
class PostDetailsFragmentTests {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var appCtx: Context

    @Before
    fun setup() {
        appCtx = InstrumentationRegistry.getInstrumentation().targetContext
        onView(withId(R.id.recycler))
            .check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<ViewHolder>((1..5).random(), click()))
    }

    @Test
    fun allViews_areVisible() {
        onView(withId(R.id.thumbnail)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.date)).check(matches(isDisplayed()))
        onView(withId(R.id.username)).check(matches(isDisplayed()))
        onView(withId(R.id.username_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.city)).check(matches(isDisplayed()))
        onView(withId(R.id.city_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.date)).check(matches(isDisplayed()))
        onView(withId(R.id.body)).check(matches(isDisplayed()))
    }

    @Test
    fun shareMenuItem_isDisplayed() {
        onView(withContentDescription(R.string.share)).check(matches(isDisplayed()))
    }

    @Test
    fun navigationDrawerIcon_onClickedGoesBackToFindPostsFragment() {
        onView(withContentDescription(R.string.nav_app_bar_navigate_up_description))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))
    }
}
