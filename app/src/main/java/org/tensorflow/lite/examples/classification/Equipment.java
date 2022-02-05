package org.tensorflow.lite.examples.classification;

import java.io.Serializable;

public class Equipment implements Serializable {
    private int eqId;
    private double target;
    private String eqName, metric1, metric2, metric3;

    public Equipment(int eqId, String eqName, String metric1, String metric2, String metric3, double target) {
        this.eqId = eqId;
        this.eqName = eqName;
        this.metric1 = metric1;
        this.metric2 = metric2;
        this.metric3 = metric3;
        this.target = target;
    }

    public Equipment(String eqName, String metric1, String metric2, String metric3, double target) {
        eqId = -1;
        this.eqName = eqName;
        this.metric1 = metric1;
        this.metric2 = metric2;
        this.metric3 = metric3;
        this.target = target;
    }

    public int getEqId() {
        return eqId;
    }

    public void setEqId(int eqId) {
        this.eqId = eqId;
    }

    public String getEqName() {
        return eqName;
    }

    public void setEqName(String eqName) {
        this.eqName = eqName;
    }

    public String getMetric1() {
        return metric1;
    }

    public void setMetric1(String metric1) {
        this.metric1 = metric1;
    }

    public String getMetric2() {
        return metric2;
    }

    public void setMetric2(String metric2) {
        this.metric2 = metric2;
    }

    public String getMetric3() {
        return metric3;
    }

    public void setMetric3(String metric3) {
        this.metric3 = metric3;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }
}
