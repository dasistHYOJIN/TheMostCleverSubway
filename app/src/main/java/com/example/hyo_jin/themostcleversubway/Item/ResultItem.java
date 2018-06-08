package com.example.hyo_jin.themostcleversubway.Item;

/**
 * Created by Hyo-Jin on 2018-06-08.
 */

public class ResultItem {

    /*
    private boolean star;
    private String train;
    private String direction;
    private String[] station_trans;
    */
    // 1: 출발/환승역    2: 환승   3: 도착역
    private int type;

    private String station;
    private String direction;
    private String[] time;
    private int left;
    private String cell_best, cell_fast;

    public ResultItem(int type, String station, String direction, String[] time, int left, String cell_best, String cell_fast) {
        this.type = type;
        this.station = station;
        this.direction = direction;
        this.time = time;
        this.left = left;
        this.cell_best = cell_best;
        this.cell_fast = cell_fast;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public String getCell_best() {
        return cell_best;
    }

    public void setCell_best(String cell_best) {
        this.cell_best = cell_best;
    }

    public String getCell_fast() {
        return cell_fast;
    }

    public void setCell_fast(String cell_fast) {
        this.cell_fast = cell_fast;
    }
}
