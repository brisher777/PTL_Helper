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
        DBHandler db_handler = DBHandler.getInstance(main_activity);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        DBHandler db_handler = DBHandler.getInstance(main_activity);
        SQLiteDatabase db = db_handler.getWritableDatabase();
        db_handler.onUpgrade(db, 1,1);
    }

    public void test_charts_database_add_metric() {
        DBHandler db_handler = DBHandler.getInstance(main_activity);
        ScoreMetric metric = new ScoreMetric(0,"Male",30,39,"Sit Ups",50,59,60);
        db_handler.addScoreMetric(metric);
        assertEquals(db_handler.getMetricCount(), 1);
    }

    public void test_charts_database_get_metric() {
        DBHandler db_handler = DBHandler.getInstance(main_activity);
        ScoreMetric metric = new ScoreMetric(0,"Male",30,39,"Sit Ups",50,59,60);
        db_handler.addScoreMetric(metric);
        ScoreMetric new_metric = db_handler.getScoreMetric(metric.get_id());
        assertEquals(metric.get_act_high(), new_metric.get_act_high());
        assertEquals(metric.get_points(), new_metric.get_points());
    }

    public void test_charts_database_update_metric() {
        DBHandler db_handler = DBHandler.getInstance(main_activity);
        ScoreMetric metric = new ScoreMetric(0,"Male",30,39,"Sit Ups",50,59,60);
        db_handler.addScoreMetric(metric);
        assertEquals(metric.get_gender(), "Male");
        metric.set_gender("Female");
        assertEquals(metric.get_gender(), "Female");
        db_handler.updateScoreMetric(metric);
        ScoreMetric new_metric = db_handler.getScoreMetric(metric.get_id());
        assertEquals(new_metric.get_gender(), "Female");
    }


}

