package com.example.myapplication;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.FailureHandler;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.entities.ToDoEntity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private List<ToDoEntity> todos = new ArrayList<>();

    @Test
    public void view_isCorrect() {
        onView(withId(R.id.addNote)).check(matches(isDisplayed()));
        onView(withId(R.id.toDoListRecyclerView)).check((matches(isDisplayed())));

        onView(withId(R.id.addNote)).perform(click());
        onView(withText("To do")).inRoot(isDialog()).withFailureHandler(new FailureHandler() {
            @Override
            public void handle(Throwable error, Matcher<View> viewMatcher){

            }
        }).check(matches(isDisplayed()));

    }
    @Test
    public void add_note_test(){
        onView(withId(R.id.toDoText)).inRoot(isPlatformPopup()).perform(clearText(), typeText("Go to the gym"));
        onView(withId(R.id.toDoDescription)).inRoot(isPlatformPopup()).perform(clearText(), typeText("This is my description"));

        onView(withId(R.id.addNote)).inRoot(isPlatformPopup()).perform(click());

    }
    @Test
    public void useAppContext() {
        // Context of the app under test.

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.myapplication", appContext.getPackageName());
    }
}
