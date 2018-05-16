package com.example.hyo_jin.themostcleversubway.Item;

/**
 * Created by Hyo-Jin on 2018-05-16.
 */

public class HistoryListItem {
    private String station1;
    private String station2;
    private boolean star;

    public HistoryListItem(String station1, String station2, boolean star) {
        this.station1 = station1;
        this.station2 = station2;
        this.star = star;
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

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }
}
