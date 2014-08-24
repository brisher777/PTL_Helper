package us.holypotatoes.ptl_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 8/18/14.
 */

public class MembersDBHandler extends SQLiteOpenHelper {
    //db info
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pt_database.db";

    private static MembersDBHandler mInstance = null;

    //tables
    private static final String TABLE_MEMBERS = "members";

    //columns for members
    private static final String MKEY_ID = "id";
    private static final String MKEY_NAME = "name";
    private static final String MKEY_GENDER = "gender";
    private static final String MKEY_BIRTHDAY = "birthday";
    private static final String MKEY_LAST_TEST_DATE = "last_test_date";
    private static final String MKEY_NEXT_TEST_DATE = "next_test_date";
    private static final String MKEY_PUSH_UPS = "push_ups";
    private static final String MKEY_SIT_UPS = "sit_ups";
    private static final String MKEY_WAIST_SIZE = "waist_size";
    private static final String MKEY_RUN_TIME = "run_time";
    private static final String MKEY_ON_PROFILE = "on_profile";
    private static final String MKEY_PROFILE_DATE = "profile_date";
    private static final String MKEY_PROFILE_DESCRIPTION = "profile_description";

    public static MembersDBHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MembersDBHandler(context.getApplicationContext());
        }
        return mInstance;
    }

    private MembersDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + TABLE_MEMBERS + "(" + MKEY_ID +
                " INTEGER PRIMARY KEY," + MKEY_NAME + " TEXT," + MKEY_GENDER + " TEXT," +
                MKEY_BIRTHDAY + " TEXT," + MKEY_LAST_TEST_DATE + " TEXT," + MKEY_NEXT_TEST_DATE +
                " TEXT," + MKEY_PUSH_UPS + " INTEGER," + MKEY_SIT_UPS + " INTEGER," + MKEY_WAIST_SIZE +
                " INTEGER," + MKEY_RUN_TIME + " TEXT," + MKEY_ON_PROFILE + " INTEGER," +
                MKEY_PROFILE_DATE + " TEXT," + MKEY_PROFILE_DESCRIPTION + " TEXT)";
        Log.d("DB CREATE MEMBERS TABLE", CREATE_MEMBERS_TABLE);

        db.execSQL(CREATE_MEMBERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if present
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);

        // Create tables again
        onCreate(db);
    }

    public long addMember(Member member) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id;

        ContentValues values = new ContentValues();
        values.put(MKEY_NAME, member.get_name());
        values.put(MKEY_GENDER, member.get_gender());
        values.put(MKEY_BIRTHDAY, member.get_birthday());
        values.put(MKEY_LAST_TEST_DATE, member.get_last_test_date());
        values.put(MKEY_NEXT_TEST_DATE, member.get_next_test_date());
        values.put(MKEY_PUSH_UPS, member.get_push_ups());
        values.put(MKEY_SIT_UPS, member.get_sit_ups());
        values.put(MKEY_WAIST_SIZE, member.get_waist_size());
        values.put(MKEY_RUN_TIME, member.get_run_time());
        values.put(MKEY_ON_PROFILE, member.is_on_profile());
        values.put(MKEY_PROFILE_DATE, member.get_profile_date());
        values.put(MKEY_PROFILE_DESCRIPTION, member.get_profile_description());

        id = db.insert(TABLE_MEMBERS, null, values);
        db.close();
        return id;
    }

    public Member getMember(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Member member = null;
        Cursor cursor = db.query(TABLE_MEMBERS, null, MKEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            member = new Member(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)),
                    Double.parseDouble(cursor.getString(9)), Integer.parseInt(cursor.getString(10)),
                    cursor.getString(11), cursor.getString(12));
        }
        return member;
    }

    public List<Member> getAllMembers() {
        List<Member> memberList = new ArrayList<Member>();
        String selectQuery = "SELECT * FROM " + TABLE_MEMBERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Member member = new Member();
                member.set_id(Long.parseLong(cursor.getString(0)));
                member.set_name(cursor.getString(1));
                member.set_gender(cursor.getString(2));
                member.set_birthday(cursor.getString(3));
                member.set_last_test_date(cursor.getString(4));
                member.set_next_test_date(cursor.getString(5));
                member.set_push_ups(Integer.parseInt(cursor.getString(6)));
                member.set_sit_ups(Integer.parseInt(cursor.getString(7)));
                member.set_waist_size(Integer.parseInt(cursor.getString(8)));
                member.set_run_time(Double.parseDouble(cursor.getString(9)));
                member.set_on_profile(Integer.parseInt(cursor.getString(10)));
                member.set_profile_date(cursor.getString(11));
                member.set_profile_description(cursor.getString(12));

                memberList.add(member);
            } while (cursor.moveToNext());
        }
        return memberList;
    }

    public int getMembersCount() {
        String countQuery = "SELECT * FROM " + TABLE_MEMBERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public int updateMember(Member member) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MKEY_ID, member.get_id());
        values.put(MKEY_NAME, member.get_name());
        values.put(MKEY_GENDER, member.get_gender());
        values.put(MKEY_BIRTHDAY, member.get_birthday());
        values.put(MKEY_LAST_TEST_DATE, member.get_last_test_date());
        values.put(MKEY_NEXT_TEST_DATE, member.get_next_test_date());
        values.put(MKEY_PUSH_UPS, member.get_push_ups());
        values.put(MKEY_SIT_UPS, member.get_sit_ups());
        values.put(MKEY_WAIST_SIZE, member.get_waist_size());
        values.put(MKEY_RUN_TIME, member.get_run_time());
        values.put(MKEY_ON_PROFILE, member.is_on_profile());
        values.put(MKEY_PROFILE_DATE, member.get_profile_date());
        values.put(MKEY_PROFILE_DESCRIPTION, member.get_profile_description());

        return db.update(TABLE_MEMBERS, values, MKEY_ID + "=?", new String[] {String.valueOf(member.get_id())});
    }

    public void deleteMember(Member member) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMBERS, MKEY_ID + "=?", new String[]{String.valueOf(member.get_id())});
        db.close();
    }



}