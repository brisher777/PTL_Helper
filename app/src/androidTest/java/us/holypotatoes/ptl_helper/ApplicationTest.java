package us.holypotatoes.ptl_helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.test.ActivityUnitTestCase;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityUnitTestCase<member_display> {

    public ApplicationTest(Class<member_display> activityClass) {
        super(activityClass);
    }
    public ApplicationTest() {
        super(member_display.class);
    }

    static Activity main_activity;

    // This isn't working at the moment...
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

    public void test_database_add_member() {
        DBHandler db_handler = DBHandler.getInstance(main_activity);

        Member ben = new Member(0,"Ben", "Male", "12-20-1982", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(ben);
    }

    public void test_database_count_function() {
        DBHandler db_handler = DBHandler.getInstance(main_activity);
        Member ben = new Member(0,"Ben", "Male", "12-20-1982", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(ben);
        assertEquals(db_handler.getMembersCount(), 1);
        Member sarah = new Member(1,"Sarah", "Female", "04-21-1984", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(sarah);
        assertEquals(db_handler.getMembersCount(), 2);
    }

    public void test_database_remove_member_and_retest_count() {
        DBHandler db_handler = DBHandler.getInstance(main_activity);
        Member sarah = new Member(1,"Sarah", "Female", "04-21-1984", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(sarah);
        assertEquals(db_handler.getMembersCount(), 1);
        db_handler.deleteMember(sarah);
        assertEquals(db_handler.getMembersCount(), 0);
        db_handler.addMember(sarah);
        Member sarahs = new Member(2,"Sarah", "Female", "04-21-1984", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(sarahs);
        Member ben = new Member(0,"Ben", "Male", "12-20-1982", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(ben);
        assertEquals(db_handler.getMembersCount(), 3);
        db_handler.deleteMember(sarahs);
        assertEquals(db_handler.getMembersCount(), 2);
        db_handler.deleteMember(ben);
        assertEquals(db_handler.getMembersCount(), 1);
        db_handler.deleteMember(sarah);
        assertEquals(db_handler.getMembersCount(), 0);
    }

    public void test_database_get_all_members_function() {
        DBHandler db_handler = DBHandler.getInstance(main_activity);
        Member sarah = new Member(1,"Sarah", "Female", "04-21-1984", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(sarah);
        Member sarahs = new Member(2,"Sarah", "Female", "04-21-1984", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(sarahs);
        Member ben = new Member(0,"Ben", "Male", "12-20-1982", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(ben);
        List<Member> memberList = db_handler.getAllMembers();
        assertEquals(db_handler.getMembersCount(), 3);
        assertEquals(memberList.size(), 3);
        assertEquals(memberList.isEmpty(), false);
        assertEquals((memberList instanceof List), true);
    }

    public void test_database_get_member_function() {
        DBHandler db_handler = DBHandler.getInstance(main_activity);
        Member ben = new Member(0,"Ben", "Male", "12-20-1982", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(ben);
        Member new_ben = db_handler.getMember(0);
        assertEquals(ben.get_birthday(), new_ben.get_birthday());
        assertEquals(ben.get_next_test_date(), ben.get_next_test_date());

    }

    public void test_database_update_member() {
        DBHandler db_handler = DBHandler.getInstance(main_activity);
        Member ben = new Member(0,"Ben", "Male", "12-20-1982", "06-31-2014", "12-31-2014",
                60, 60, 28, "9:30", 0, "", "");
        db_handler.addMember(ben);
        assertEquals(ben.get_gender(), "Male");
        ben.set_gender("Wookie");
        assertEquals(ben.get_gender(), "Wookie");
        db_handler.updateMember(ben);
        Member new_ben = db_handler.getMember(0);
        assertEquals(new_ben.get_gender(), "Wookie");
    }
}