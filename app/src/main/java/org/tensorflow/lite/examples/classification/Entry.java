package org.tensorflow.lite.examples.classification;

import java.io.Serializable;

public class Entry extends Equipment implements Serializable {
    private int entryId, value1, value2;
    private double value3;
    private String date, time;
    private boolean targetHit;

    public Entry(Equipment equipment, int entryId, String date, String time, int value1, int value2, double value3, boolean targetHit) {
        super(equipment.getEqId(), equipment.getEqName(), equipment.getMetric1(), equipment.getMetric2(), equipment.getMetric3(), equipment.getTarget());
        this.entryId = entryId;
        this.date = date;
        this.time = time;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.targetHit = targetHit;
    }

    public Entry(Equipment equipment, String date, String time, int value1, int value2, double value3) {
        super(equipment.getEqId(), equipment.getEqName(), equipment.getMetric1(), equipment.getMetric2(), equipment.getMetric3(), equipment.getTarget());
        this.entryId = -1;
        this.date = date;
        this.time = time;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.targetHit = targetHit;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public double getValue3() {
        return value3;
    }

    public void setValue3(double value3) {
        this.value3 = value3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTargetHit() {
        return targetHit;
    }

    public void setTargetHit(boolean targetHit) {
        this.targetHit = targetHit;
    }
}
