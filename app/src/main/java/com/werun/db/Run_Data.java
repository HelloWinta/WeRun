package com.werun.db;

import org.litepal.crud.DataSupport;

/**
 * Created by winta on 2018/4/5.
 */

public class Run_Data extends DataSupport {

    private int RunId;
    private int UserId;
    private String beginTime;
    private String endTime;
    private double speed;
    private double distance;
    private String trackPic;

    public void setId(int RunId) {
        this.RunId = RunId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;

    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setTrackPic(String trackPic) {
        this.trackPic = trackPic;
    }

    public int getRunId() {
        return RunId;
    }

    public int getUserId(){
        return UserId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDistance() {
        return distance;
    }

    public String getTrackPic() {
        return trackPic;
    }
}
