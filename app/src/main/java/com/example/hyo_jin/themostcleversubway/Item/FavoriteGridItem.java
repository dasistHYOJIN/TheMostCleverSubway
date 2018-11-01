package com.example.hyo_jin.themostcleversubway.Item;

/**
 * Created by Hyo-Jin on 2018-05-14.
 */

public class FavoriteGridItem {

    private String station1;
    private String station2;
    private String way;

    public FavoriteGridItem(String station1, String station2, String way) {
        this.station1 = station1;
        this.station2 = station2;
        this.way = way;
    }

    public String getStation1() {
        return station1;
    }

    public void setStation1(String station1) {
        this.station1 = station1;
    }

    public String getStation2() {
        return station2;
    }

    public void setStation2(String station2) {
        this.station2 = station2;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

}
