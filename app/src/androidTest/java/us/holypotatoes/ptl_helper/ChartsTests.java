package us.holypotatoes.ptl_helper;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import java.util.HashMap;

/**
 * Created by ben on 8/23/14.
 */
public class ChartsTests extends ActivityUnitTestCase<member_display> {
    public ChartsTests(Class<member_display> activityClass) {
        super(activityClass);
    }
    public ChartsTests() {
        super(member_display.class);
    }

    static Activity main_activity;

    public void setUp() throws Exception {
        super.setUp();
        Intent mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
                member_display.class);
        startActivity(mLaunchIntent, null, null);
        main_activity = getActivity();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void test_charts_database_get_run_points() {
        ChartsDBHandler db_handler = new ChartsDBHandler(main_activity);
        Member ben = new Member("Ben", "Male", "09-17-1986", "06-31-2014", "12-31-2014",
                60, 58, 28, 9.01, 0, "", "");
        assertEquals(db_handler.get_points(ben, "run"), 60.0);
    }

    public void test_charts_database_get_minimums_for_the_members_age_group() {
        ChartsDBHandler db_handler = new ChartsDBHandler(main_activity);

        Member ben = new Member("Ben", "Male", "09-17-1992", "06-31-2014", "12-31-2014",
                60, 58, 28, 9.01, 0, "", "");
        Member sarah = new Member("Sarah", "Female", "09-17-1992", "06-31-2014", "12-31-2014",
                60, 58, 28, 9.01, 0, "", "");

        //less than 30
        HashMap<String, Double> dict = db_handler.get_minimums(ben);
        assertEquals(dict.isEmpty(), false);
        assertEquals(dict.get("run"), 13.36);
        assertEquals(dict.get("waist"), 39.0);
        assertEquals(dict.get("pushups"), 33.0);
        assertEquals(dict.get("situps"), 42.0);
        dict = db_handler.get_minimums(sarah);
        assertEquals(dict.isEmpty(), false);
        assertEquals(dict.get("run"), 16.22);
        assertEquals(dict.get("waist"), 35.5);
        assertEquals(dict.get("pushups"), 18.0);
        assertEquals(dict.get("situps"), 38.0);

        //30-39
        ben.set_birthday("09-17-1982");
        dict = db_handler.get_minimums(ben);
        assertEquals(dict.isEmpty(), false);
        assertEquals(dict.get("run"), 14.00);
        assertEquals(dict.get("waist"), 39.0);
        assertEquals(dict.get("pushups"), 27.0);
        assertEquals(dict.get("situps"), 39.0);
        sarah.set_birthday("09-17-1982");
        dict = db_handler.get_minimums(sarah);
        assertEquals(dict.isEmpty(), false);
        assertEquals(dict.get("run"), 16.57);
        assertEquals(dict.get("waist"), 35.5);
        assertEquals(dict.get("pushups"), 14.0);
        assertEquals(dict.get("situps"), 29.0);

        //40-49
        ben.set_birthday("09-17-1972");
        dict = db_handler.get_minimums(ben);
        assertEquals(dict.isEmpty(), false);
        assertEquals(dict.get("run"), 14.52);
        assertEquals(dict.get("waist"), 39.0);
        assertEquals(dict.get("pushups"), 21.0);
        assertEquals(dict.get("situps"), 34.0);
        sarah.set_birthday("09-17-1972");
        dict = db_handler.get_minimums(sarah);
        assertEquals(dict.isEmpty(), false);
        assertEquals(dict.get("run"), 18.14);
        assertEquals(dict.get("waist"), 35.5);
        assertEquals(dict.get("pushups"), 11.0);
        assertEquals(dict.get("situps"), 24.0);

        //50-59
        ben.set_birthday("09-17-1962");
        dict = db_handler.get_minimums(ben);
        assertEquals(dict.isEmpty(), false);
        assertEquals(dict.get("run"), 16.22);
        assertEquals(dict.get("waist"), 39.0);
        assertEquals(dict.get("pushups"), 15.0);
        assertEquals(dict.get("situps"), 28.0);
        sarah.set_birthday("09-17-1962");
        dict = db_handler.get_minimums(sarah);
        assertEquals(dict.isEmpty(), false);
        assertEquals(dict.get("run"), 19.43);
        assertEquals(dict.get("waist"), 35.5);
        assertEquals(dict.get("pushups"), 9.0);
        assertEquals(dict.get("situps"), 20.0);

        //60+
        ben.set_birthday("09-17-1952");
        dict = db_handler.get_minimums(ben);
        assertEquals(dict.isEmpty(), false);
        assertEquals(dict.get("run"), 18.14);
        assertEquals(dict.get("waist"), 39.0);
        assertEquals(dict.get("pushups"), 14.0);
        assertEquals(dict.get("situps"), 22.0);
        sarah.set_birthday("09-17-1952");
        dict = db_handler.get_minimums(sarah);
        assertEquals(dict.isEmpty(), false);
        assertEquals(dict.get("run"), 22.28);
        assertEquals(dict.get("waist"), 35.5);
        assertEquals(dict.get("pushups"), 7.0);
        assertEquals(dict.get("situps"), 11.0);
    }
}

