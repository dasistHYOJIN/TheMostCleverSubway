package com.example.hyo_jin.themostcleversubway.Item;

/**
 * Created by Hyo-Jin on 2018-06-06.
 */

public class ResultItem2 {

    private int left;
    private String cell_best, cell_fast;

    public ResultItem2(int left, String cell_best, String cell_fast) {
        this.left = left;
        this.cell_best = cell_best;
        this.cell_fast = cell_fast;
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
