package us.holypotatoes.ptl_helper;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityUnitTestCase;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class MembersTests extends ActivityUnitTestCase<member_display> {

    public MembersTests(Class<member_display> activityClass) {
        super(activityClass);
    }
    public MembersTests() {
        super(member_display.class);
    }

    static Activity main_activity;

    public void setUp() throws Exception {
        super.setUp();
        Intent mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
                member_display.class);
        startActivity(mLaunchIntent, null, null);
        main_activity = getActivity();
        MembersDBHandler db_handler = MembersDBHandler.getInstance(main_activity);
    }

    public void tearDown() throws Exception {
        super.tearDown();
        MembersDBHandler db_handler = MembersDBHandler.getInstance(main_activity);
        SQLiteDatabase db = db_handler.getWritableDatabase();
        db_handler.onUpgrade(db, 1,1);
    }

    public void test_member_database_add_member() {
        MembersDBHandler db_handler = MembersDBHandler.getInstance(main_activity);

        Member ben = new Member("Ben", "Male", "09-17-1986", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        ben.set_id(db_handler.addMember(ben));
    }

    public void test_member_database_count_function() {
        MembersDBHandler db_handler = MembersDBHandler.getInstance(main_activity);
        Member ben = new Member("Ben", "Male", "09-17-1982", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        ben.set_id(db_handler.addMember(ben));
        assertEquals(db_handler.getMembersCount(), 1);
        Member sarah = new Member("Sarah", "Female", "04-11-1986", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        sarah.set_id(db_handler.addMember(sarah));
        assertEquals(sarah.get_id(), 2);
        assertEquals(db_handler.getMembersCount(), 2);
        assertEquals(ben.get_age(), 31);
    }

    public void test_member_database_remove_member_and_retest_count() {
        MembersDBHandler db_handler = MembersDBHandler.getInstance(main_activity);
        Member sarah = new Member("Sarah", "Female", "04-11-1986", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        sarah.set_id(db_handler.addMember(sarah));
        assertEquals(db_handler.getMembersCount(), 1);
        db_handler.deleteMember(sarah);
        assertEquals(db_handler.getMembersCount(), 0);
        sarah.set_id(db_handler.addMember(sarah));

        Member sarahs = new Member("Sarahs", "Female", "04-11-1986", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        sarahs.set_id(db_handler.addMember(sarahs));
        Member ben = new Member("Ben", "Male", "09-17-1986", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        ben.set_id(db_handler.addMember(ben));
        assertEquals(db_handler.getMembersCount(), 3);
        db_handler.deleteMember(sarahs);
        assertEquals(db_handler.getMembersCount(), 2);
        db_handler.deleteMember(ben);
        assertEquals(db_handler.getMembersCount(), 1);
        db_handler.deleteMember(sarah);
        assertEquals(db_handler.getMembersCount(), 0);
    }

    public void test_member_database_get_all_members_function() {
        MembersDBHandler db_handler = MembersDBHandler.getInstance(main_activity);
        Member sarah = new Member("Sarah", "Female", "04-11-1986", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        sarah.set_id(db_handler.addMember(sarah));
        Member sarahs = new Member("Sarah", "Female", "04-11-1986", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        sarahs.set_id(db_handler.addMember(sarahs));
        Member ben = new Member("Ben", "Male", "09-17-1986", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        ben.set_id(db_handler.addMember(ben));
        List<Member> memberList = db_handler.getAllMembers();
        assertEquals(db_handler.getMembersCount(), 3);
        assertEquals(memberList.size(), 3);
        assertEquals(memberList.isEmpty(), false);
    }

    public void test_member_database_get_member_function() {
        MembersDBHandler db_handler = MembersDBHandler.getInstance(main_activity);
        Member ben = new Member("Ben", "Male", "09-17-1986", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        ben.set_id(db_handler.addMember(ben));
        Member new_ben = db_handler.getMember(ben.get_id());
        assertEquals(ben.get_birthday(), new_ben.get_birthday());
        assertEquals(ben.get_next_test_date(), ben.get_next_test_date());
    }

    public void test_member_database_update_member() {
        MembersDBHandler db_handler = MembersDBHandler.getInstance(main_activity);
        Member ben = new Member("Ben", "Male", "09-17-1986", "06-31-2014", "12-31-2014",
                60, 60, 28, 9.3, 0, "", "");
        ben.set_id(db_handler.addMember(ben));
        assertEquals(ben.get_gender(), "Male");
        ben.set_gender("Wookie");
        assertEquals(ben.get_gender(), "Wookie");
        db_handler.updateMember(ben);
        Member new_ben = db_handler.getMember(ben.get_id());
        assertEquals(new_ben.get_gender(), "Wookie");
    }

}