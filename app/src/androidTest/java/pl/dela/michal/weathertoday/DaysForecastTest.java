package pl.dela.michal.weathertoday;

import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DaysForecastTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mainActivity = null;
    @Before
    public void setUp(){
        mainActivity = mActivityRule.getActivity();
    }
    @Test
    public void testJSON() {
        mainActivity = mActivityRule.getActivity();
        assertNotNull(mainActivity);
        assertNotNull(mainActivity.getArrayList());
    }
    @Test
    public void testActivity() {
        assertNotNull(mainActivity);
        assertThat((((Button)mainActivity.findViewById(R.id.backBtn)).getText().toString()),(anyOf(is("Back"),is("Wróć"))));
    }
}