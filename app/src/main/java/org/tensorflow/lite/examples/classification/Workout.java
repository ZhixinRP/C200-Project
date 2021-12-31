package org.tensorflow.lite.examples.classification;

import java.io.Serializable;

public class Workout extends Exercise implements Serializable {
    private int workoutId, metric1, metric2, metric3;
    private String date;

    public Workout(int exerciseId, String name, int workoutId, String date, int metric1, int metric2, int metric3) {
        super(exerciseId, name);
        this.workoutId = workoutId;
        this.date = date;
        this.metric1 = metric1;
        this.metric2 = metric2;
        this.metric3 = metric3;
    }

    public Workout(int exerciseId, String name, String date, int metric1, int metric2, int metric3) {
        super(exerciseId, name);
        workoutId = -1;
        this.date = date;
        this.metric1 = metric1;
        this.metric2 = metric2;
        this.metric3 = metric3;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getMetric1() {
        return metric1;
    }

    public void setMetric1(int metric1) {
        this.metric1 = metric1;
    }

    public int getMetric2() {
        return metric2;
    }

    public void setMetric2(int metric2) {
        this.metric2 = metric2;
    }

    public int getMetric3() {
        return metric3;
    }

    public void setMetric3(int metric3) {
        this.metric3 = metric3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String display = String.format("Date: %s \nExercise: %s \nMetric 1: %d \nMetric 2: %d \nMetric 3: %d \n", getDate(), getName(), getMetric1(), getMetric2(), getMetric3());
        return display;
    }
}
