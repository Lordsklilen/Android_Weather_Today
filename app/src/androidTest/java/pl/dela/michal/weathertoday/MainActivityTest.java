package pl.dela.michal.weathertoday;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.Button;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mainActivity = null;

    @Before
    public void setUp(){
        mainActivity = mActivityRule.getActivity();
    }

    @Test
    public void testActivity() {
        assertNotNull(mainActivity);
        //Resources resources = InstrumentationRegistry.getContext().getResources();
        //String btnText = resources.getString((R.string.backButton));
        //Log.i("test" , btnText);
        Log.i("test" , ((Button)mainActivity.findViewById(R.id.backBtn)).getText().toString());
        assertThat((((Button)mainActivity.findViewById(R.id.backBtn)).getText().toString()),(anyOf(is("Back"),is("Wróć"))));
    }
    @Test
    public void testJSON() {
        assertNotNull(mainActivity);
        assertNotNull(mainActivity.getArrayList());
    }
    @After
    public void tearDown(){
        mainActivity = null;
    }
}