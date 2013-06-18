package com.phf.PO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 13-4-28
 * Time: 下午5:02
 * Project: GenerateRN
 */
public class Hz_split {
    private int num;
    private List<Hzgpstaxi> list;

    public Hz_split(int num, List<Hzgpstaxi> list) {
        this.num = num;
        this.list = list;
    }

    public Hz_split() {
    }

    public List<Hzgpstaxi> getList() {
        return list;
    }

    public void setList(List<Hzgpstaxi> list) {
        this.list = list;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
