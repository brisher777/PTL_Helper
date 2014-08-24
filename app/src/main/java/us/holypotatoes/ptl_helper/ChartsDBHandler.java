package us.holypotatoes.ptl_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ben on 8/23/14.
 */
public class ChartsDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pt_database.db";

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

    public static ChartsDBHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ChartsDBHandler(context.getApplicationContext());
        }
        return mInstance;
    }

    private ChartsDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHARTS_TABLE = "CREATE TABLE " + TABLE_CHARTS + "(" + SKEY_ID +
                " INTEGER PRIMARY KEY," + SKEY_GENDER + " TEXT," +
                SKEY_AGE_LOWER_LIMIT + " INTEGER," + SKEY_AGE_UPPER_LIMIT + " INTEGER," +
                SKEY_ACTION + " TEXT," + SKEY_ACTION_LOWER_LIMIT + " INTEGER," +
                SKEY_ACTION_UPPER_LIMIT + " INTEGER," + SKEY_POINTS + " INTEGER)";
        Log.d("DB CREATE CHARTS TABLE", CREATE_CHARTS_TABLE);
        db.execSQL(CREATE_CHARTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if present
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHARTS);

        // Create tables again
        onCreate(db);

    }

    public long addScoreMetric(ScoreMetric metric) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SKEY_ID, metric.get_id());
        values.put(SKEY_GENDER, metric.get_gender());
        values.put(SKEY_AGE_LOWER_LIMIT, metric.get_age_low());
        values.put(SKEY_AGE_UPPER_LIMIT, metric.get_age_high());
        values.put(SKEY_ACTION, metric.get_action());
        values.put(SKEY_ACTION_LOWER_LIMIT, metric.get_act_low());
        values.put(SKEY_ACTION_UPPER_LIMIT, metric.get_act_high());
        values.put(SKEY_POINTS, metric.get_points());

        long id = db.insert(TABLE_CHARTS, null, values);
        db.close();
        return id;
    }
    public int getMetricsCount() {
        String countQuery = "SELECT * FROM " + TABLE_CHARTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public ScoreMetric getScoreMetric(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ScoreMetric metric = null;
        Cursor cursor = db.query(TABLE_CHARTS, null, SKEY_ID + "=?", new String[] {String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            metric = new ScoreMetric(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4), Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6)),Integer.parseInt(cursor.getString(7)));
        }
        return metric;
    }

    public int updateScoreMetric(ScoreMetric metric) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SKEY_ID, metric.get_id());
        values.put(SKEY_GENDER, metric.get_gender());
        values.put(SKEY_AGE_LOWER_LIMIT, metric.get_age_low());
        values.put(SKEY_AGE_UPPER_LIMIT, metric.get_age_high());
        values.put(SKEY_ACTION, metric.get_action());
        values.put(SKEY_ACTION_LOWER_LIMIT, metric.get_act_low());
        values.put(SKEY_ACTION_UPPER_LIMIT, metric.get_act_high());
        values.put(SKEY_POINTS, metric.get_points());

        return db.update(TABLE_CHARTS, values, SKEY_ID + "=?", new String[] {String.valueOf(metric.get_id())});
    }

    //TODO: implement a time delta between now and next test date and figure out where to put it
    public int get_points(Member member, String action) {
        SQLiteDatabase db = this.getReadableDatabase();
        int points = -1;

        String selectQuery = "SELECT " + SKEY_POINTS + " FROM " + TABLE_CHARTS + " WHERE gender='" +
                member.get_gender() + "' AND " + SKEY_AGE_LOWER_LIMIT + "<" + member.get_age() +
                " AND " + member.get_age() + "<" + SKEY_AGE_UPPER_LIMIT + " AND action='" + action +
                "' AND " + SKEY_ACTION_LOWER_LIMIT + "<" + member.get_run_time() + " AND " +
                member.get_run_time() + "<" + SKEY_ACTION_UPPER_LIMIT;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            points = cursor.getInt(0); //this may be wrong
            cursor.close();
            db.close();
        }
        return points;
    }
}
