package us.holypotatoes.ptl_helper;

/**
 * Created by ben on 8/22/14.
 */
public class ScoreMetric {
    int _id, _age_low, _age_high, _act_low, _act_high, _points;
    String _gender, _action;

    public ScoreMetric(int _id, String _gender, int _age_low, int _age_high, String _action,
                       int _act_low, int _act_high, int _points) {
        this._id = _id;
        this._age_low = _age_low;
        this._age_high = _age_high;
        this._act_low = _act_low;
        this._act_high = _act_high;
        this._points = _points;
        this._gender = _gender;
        this._action = _action;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_age_low() {
        return _age_low;
    }

    public void set_age_low(int _age_low) {
        this._age_low = _age_low;
    }

    public int get_age_high() {
        return _age_high;
    }

    public void set_age_high(int _age_high) {
        this._age_high = _age_high;
    }

    public int get_act_low() {
        return _act_low;
    }

    public void set_act_low(int _act_low) {
        this._act_low = _act_low;
    }

    public int get_act_high() {
        return _act_high;
    }

    public void set_act_high(int _act_high) {
        this._act_high = _act_high;
    }

    public int get_points() {
        return _points;
    }

    public void set_points(int _points) {
        this._points = _points;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public String get_action() {
        return _action;
    }

    public void set_action(String _action) {
        this._action = _action;
    }

}
