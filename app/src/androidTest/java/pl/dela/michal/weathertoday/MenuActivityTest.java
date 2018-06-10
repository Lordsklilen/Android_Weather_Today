package pl.dela.michal.weathertoday;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MenuActivityTest {

    @Rule
    public ActivityTestRule<MenuActivity> mActivityRule = new ActivityTestRule<MenuActivity>(MenuActivity.class, false, true);
    private MenuActivity menuActivity = null;
   // @Before
  //  public void setUp(){
      //  menuActivity = mActivityRule.getActivity();
 //   }

    @Test
    public void menuClickMain() {
       // LinearLayout mainLayout =
        //mActivityRule.getActivity().findViewByid(R.id.layout5days);
        //mActivityRule.getActivity().onCreate(null);
        onView(withId(R.id.layout5days)).perform(click());
        SystemClock.sleep(100);
        intended(hasComponent(MainActivity.class.getName()));

    }

}