package pl.dela.michal.weathertoday;

import android.content.ComponentName;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.util.regex.Pattern.matches;
import static org.junit.Assert.*;

public class MenuActivityTest {

    @Rule
    public IntentsTestRule<MenuActivity> mLoginActivityActivityTestRule = new IntentsTestRule<>(MenuActivity.class);
    @Test
    public void mainActivityClick() {
        onView(withId(R.id.layout5days)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
    @Test
    public void hourforecastActivityClick() {
        onView(withId(R.id.layout12hours)).perform(click());
        intended(hasComponent(HourForecast.class.getName()));
    }
    @Test
    public void historyforecastActivityClick() {
        onView(withId(R.id.layout24history)).perform(click());
        intended(hasComponent(History24Hours.class.getName()));
    }
    @Test
    public void settingsActivityClick() {
        onView(withId(R.id.layoutSettings)).perform(click());
        intended(hasComponent(UserSettingActivity.class.getName()));
    }
}