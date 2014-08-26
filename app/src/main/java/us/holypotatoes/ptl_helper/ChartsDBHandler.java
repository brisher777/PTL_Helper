package us.holypotatoes.ptl_helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.HashMap;

/**
 * Created by ben on 8/23/14.
 */
public class ChartsDBHandler extends SQLiteAssetHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "charts.db";

    private static ChartsDBHandler mInstance = null;

    private static final String TABLE_CHARTS = "charts";

    //columns for charts
    private static final String SKEY_ID = "id";
    private static final String SKEY_GENDER = "gender";
    private static final String SKEY_AGE_LOWER_LIMIT = "age_low";
    private static final String SKEY_AGE_UPPER_LIMIT = "age_high";
    private static final String SKEY_ACTION= "action";
    private static final String SKEY_ACTION_LOWER_LIMIT = "act_low";
    private static final String SKEY_ACTION_UPPER_LIMIT = "act_high";
    private static final String SKEY_POINTS = "points";

    public ChartsDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public double get_points(Member member, String action) {
        SQLiteDatabase db = getReadableDatabase();
        double points = -1;
        String action_val;

        if (action.equals("run"))
            action_val = String.valueOf(member.get_run_time());
        else if (action.equals("situps"))
            action_val = String.valueOf(member.get_sit_ups());
        else if (action.equals("pushups"))
            action_val = String.valueOf(member.get_push_ups());
        else
            action_val = String.valueOf(member.get_waist_size());

        String selectQuery = "SELECT " + SKEY_POINTS + " FROM " + TABLE_CHARTS + " WHERE gender='" +
                member.get_gender() + "' AND " + SKEY_AGE_LOWER_LIMIT + "<" + member.get_age() +
                " AND " + member.get_age() + "<" + SKEY_AGE_UPPER_LIMIT + " AND action='" + action +
                "' AND " + SKEY_ACTION_LOWER_LIMIT + "<" + action_val + " AND " +
                action_val + "<" + SKEY_ACTION_UPPER_LIMIT;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            points = cursor.getDouble(0);
            cursor.close();
            db.close();
        }
        return points;
    }

    public HashMap get_minimums(Member member) {
        SQLiteDatabase db = getReadableDatabase();
        HashMap dict = new HashMap();
        //query db
        //return all minimums for the members age bracket

    }
}
