package com.example.hyo_jin.themostcleversubway.Item;

/**
 * Created by Hyo-Jin on 2018-06-04.
 */

public class ResultItem1 {

/*    private String station_dep, station_arr;
    private String[] station_trans;
    private boolean star;
    private String train;
    private String direction;
    private String time;
    private String cell_best;
    private String cell_fast;*/

    private String station;
    //private String[] station_trans;
    private String direction;
    private String[] lefttime;

    public ResultItem1(String station, String direction, String[] lefttime) {
        this.station = station;
        this.direction = direction;
        this.lefttime = lefttime;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String[] getLefttime() {
        return lefttime;
    }

    public void setLefttime(String[] lefttime) {
        this.lefttime = lefttime;
    }
}
