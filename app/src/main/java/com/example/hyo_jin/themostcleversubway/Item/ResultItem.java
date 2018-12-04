package com.example.hyo_jin.themostcleversubway.Item;

/**
 * Created by Hyo-Jin on 2018-06-08.
 */

public class ResultItem {

    // 1: 출발/환승역    2: 환승   3: 도착역
    private int type;

    private String station = null;
    private String direction = null;
    private String[] time_array = null;
    private String time = null;
    private int left;
    private String cell_best = null, cell_fast = null;
    private String line = null;

    public ResultItem(int type, String station, String direction, String time, int left, String line) {
        this.type = type;
        this.station = station;
        this.direction = direction;
        this.time = time;
        this.left = left;
        this.line = line;
    }

    public ResultItem(int type, int left, String cell_best, String cell_fast) {
        this.type = type;
        this.left = left;
        this.cell_best = cell_best;
        this.cell_fast = cell_fast;
    }

    public ResultItem(int type, String station, String direction, String[] time_array, int left, String line) {
        this.type = type;
        this.station = station;
        this.direction = direction;
        this.time_array = time_array;
        this.left = left;
        this.line = line;
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

    public String[] getTime_array() {
        return time_array;
    }

    public void setTime_array(String[] time_array) {
        this.time_array = time_array;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
