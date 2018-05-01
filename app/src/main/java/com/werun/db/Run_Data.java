package com.werun.db;

import org.litepal.crud.DataSupport;

/**
 * Created by winta on 2018/4/5.
 */

public class Run_Data extends DataSupport {

    private String runDate;
    private String userId;
    private String beginTime;
    private int time;
    private float speed;
    private float distance;
    private int calorie =0;
    private String trackPic;

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setTime(int time) {
        this.time = time;

    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public void setTrackPic(String trackPic) {
        this.trackPic = trackPic;
    }

    public String getRunDate() {
        return runDate;
    }

    public String getUserId(){
        return userId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public int getTime() {
        return time;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDistance() {
        return distance;
    }

    public int getCalorie() {
        return calorie;
    }

    public String getTrackPic() {
        return trackPic;
    }
}
