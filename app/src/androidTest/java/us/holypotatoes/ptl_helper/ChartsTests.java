package us.holypotatoes.ptl_helper;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
        ChartsDBHandler db_handler = ChartsDBHandler.getInstance(main_activity);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        ChartsDBHandler db_handler = ChartsDBHandler.getInstance(main_activity);
        SQLiteDatabase db = db_handler.getWritableDatabase();
        db_handler.onUpgrade(db, 1,1);
    }

    public void test_charts_database_add_metric() {
        ChartsDBHandler db_handler = ChartsDBHandler.getInstance(main_activity);
        ScoreMetric metric = new ScoreMetric("Male",30,39,"Sit Ups",50,59,60);
        metric.set_id(db_handler.addScoreMetric(metric));
        assertEquals(metric.get_id(), 0);
        assertEquals(db_handler.getMetricsCount(), 1);
    }

    public void test_charts_database_get_metric() {
        ChartsDBHandler db_handler = ChartsDBHandler.getInstance(main_activity);
        ScoreMetric metric = new ScoreMetric("Male",30,39,"Sit Ups",50,59,60);
        metric.set_id(db_handler.addScoreMetric(metric));
        ScoreMetric new_metric = db_handler.getScoreMetric(metric.get_id());
        assertEquals(metric.get_act_high(), new_metric.get_act_high());
        assertEquals(metric.get_points(), new_metric.get_points());
    }

    public void test_charts_database_update_metric() {
        ChartsDBHandler db_handler = ChartsDBHandler.getInstance(main_activity);
        ScoreMetric metric = new ScoreMetric("Male",30,39,"Sit Ups",50,59,60);
        db_handler.addScoreMetric(metric);
        assertEquals(metric.get_gender(), "Male");
        metric.set_gender("Female");
        assertEquals(metric.get_gender(), "Female");
        db_handler.updateScoreMetric(metric);
        ScoreMetric new_metric = db_handler.getScoreMetric(metric.get_id());
        assertEquals(new_metric.get_gender(), "Female");
    }

    public void test_charts_database_get_points() {
        ChartsDBHandler db_handler = ChartsDBHandler.getInstance(main_activity);
        ScoreMetric metric = new ScoreMetric("Male",30,39,"run",0.01,9.40,60);
        db_handler.addScoreMetric(metric);
        Member ben = new Member("Ben", "Male", "09-17-1982", "06-31-2014", "12-31-2014",
                60, 58, 28, 9.3, 0, "", "");
        assertEquals(db_handler.get_points(ben, "run"), 60);
    }

}

