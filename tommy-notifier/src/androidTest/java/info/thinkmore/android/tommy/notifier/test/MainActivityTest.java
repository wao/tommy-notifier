package info.thinkmore.android.tommy.notifier.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.test.suitebuilder.annotation.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import info.thinkmore.android.tommy.notifier.*;
import info.thinkmore.android.tommy.notifier.R;
import android.support.test.internal.util.AndroidRunnerParams;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity_> mActivityRule = new ActivityTestRule<>(
            MainActivity_.class);

    @Test
    public void testActivity() {
        onView(withId(R.id.btnNotify)).check(matches(withText("Button")));
    }
}

