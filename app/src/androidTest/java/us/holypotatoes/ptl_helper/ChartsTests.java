package us.holypotatoes.ptl_helper;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

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
        Member ben = new Member("Ben", "Male", "09-17-1986", "06-31-2014", "12-31-2014",
                60, 58, 28, 9.01, 0, "", "");

    }
}

