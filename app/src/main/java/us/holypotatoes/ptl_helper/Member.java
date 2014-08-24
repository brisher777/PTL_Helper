package us.holypotatoes.ptl_helper;

import org.joda.time.LocalDate;
import org.joda.time.Years;

public class Member {
    int _push_ups, _sit_ups, _waist_size, _on_profile;
    long _id;
    double _run_time;
    String _name, _profile_description, _gender, _profile_date, _last_test_date,
            _next_test_date, _birthday;

    public Member(){}

    public Member(long id, String name, String gender, String birthday, String last_test_date,
                  String next_test_date, int push_ups, int sit_ups, int waist_size, double run_time,
                  int on_profile, String profile_date, String profile_description) {
        //on a profile with _id
        this._id = id;
        this._name = name;
        this._gender = gender;
        this._birthday = birthday;
        this._last_test_date = last_test_date;
        this._next_test_date = next_test_date;
        this._push_ups = push_ups;
        this._sit_ups = sit_ups;
        this._waist_size = waist_size;
        this._run_time = run_time;
        this._on_profile = on_profile;
        this._profile_date = profile_date;
        this._profile_description = profile_description;
    }
    public Member(long id, String name, String gender, String birthday, String last_test_date,
                  String next_test_date, int push_ups, int sit_ups, int waist_size, double run_time,
                  int on_profile) {
        //not on a profile with _id
        this._id = id;
        this._name = name;
        this._gender = gender;
        this._birthday = birthday;
        this._last_test_date = last_test_date;
        this._next_test_date = next_test_date;
        this._push_ups = push_ups;
        this._sit_ups = sit_ups;
        this._waist_size = waist_size;
        this._run_time = run_time;
        this._on_profile = on_profile;
    }

    public Member(String name, String gender, String birthday, String last_test_date,
                  String next_test_date, int push_ups, int sit_ups, int waist_size, double run_time,
                  int on_profile, String profile_date, String profile_description) {
        //on a profile without _id
        this._name = name;
        this._gender = gender;
        this._birthday = birthday;
        this._last_test_date = last_test_date;
        this._next_test_date = next_test_date;
        this._push_ups = push_ups;
        this._sit_ups = sit_ups;
        this._waist_size = waist_size;
        this._run_time = run_time;
        this._on_profile = on_profile;
        this._profile_date = profile_date;
        this._profile_description = profile_description;
    }
    public Member(String name, String gender, String birthday, String last_test_date,
                  String next_test_date, int push_ups, int sit_ups, int waist_size, double run_time,
                  int on_profile) {
        //not on a profile without _id
        this._name = name;
        this._gender = gender;
        this._birthday = birthday;
        this._last_test_date = last_test_date;
        this._next_test_date = next_test_date;
        this._push_ups = push_ups;
        this._sit_ups = sit_ups;
        this._waist_size = waist_size;
        this._run_time = run_time;
        this._on_profile = on_profile;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int get_push_ups() {
        return _push_ups;
    }

    public void set_push_ups(int _push_ups) {
        this._push_ups = _push_ups;
    }

    public int get_sit_ups() {
        return _sit_ups;
    }

    public void set_sit_ups(int _sit_ups) {
        this._sit_ups = _sit_ups;
    }

    public int get_waist_size() {
        return _waist_size;
    }

    public void set_waist_size(int _waist_size) {
        this._waist_size = _waist_size;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_run_time() {
        return _run_time;
    }

    public void set_run_time(double _run_time) {
        this._run_time = _run_time;
    }

    public String get_profile_description() {
        return _profile_description;
    }

    public void set_profile_description(String _profile_description) {
        this._profile_description = _profile_description;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public int is_on_profile() {
        return _on_profile;
    }

    public void set_on_profile(int _on_profile) {
        this._on_profile = _on_profile;
    }

    public String get_profile_date() {
        return _profile_date;
    }

    public void set_profile_date(String _profile_date) {
        this._profile_date = _profile_date;
    }

    public String get_last_test_date() {
        return _last_test_date;
    }

    public void set_last_test_date(String _last_test_date) {
        this._last_test_date = _last_test_date;
    }

    public String get_next_test_date() {
        return _next_test_date;
    }

    public void set_next_test_date(String _next_test_date) {
        this._next_test_date = _next_test_date;
    }

    public String get_birthday() {
        return _birthday;
    }

    public void set_birthday(String _birthday) {
        this._birthday = _birthday;
    }

    public int get_age() {
        //"12-31-2014"
        int _month = Integer.parseInt(this._birthday.split("-")[0]);
        int _day = Integer.parseInt(this._birthday.split("-")[1]);
        int _year = Integer.parseInt(this._birthday.split("-")[2]);

        LocalDate birthday = new LocalDate (_year, _month, _day);
        LocalDate now = new LocalDate();
        Years age = Years.yearsBetween(birthday, now);

        return age.getYears();
    }
}