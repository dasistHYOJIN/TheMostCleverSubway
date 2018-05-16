package com.example.hyo_jin.themostcleversubway.Item;

/**
 * Created by Hyo-Jin on 2018-05-14.
 */

public class FavoriteGridItem {

    private String station1;
    private String station2;
    private boolean star;
    private String train;
    private String direction;
    private String time;
    private String cell_best;
    private String cell_fast;

    public FavoriteGridItem(String station1, String station2, boolean star, String train, String direction, String time, String cell_best, String cell_fast) {
        this.station1 = station1;
        this.station2 = station2;
        this.star = star;
        this.train = train;
        this.direction = direction;
        this.time = time;
        this.cell_best = cell_best;
        this.cell_fast = cell_fast;
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

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
