package org.tensorflow.lite.examples.classification;

import java.io.Serializable;

public class Exercise implements Serializable {
    private int exerciseId;
    private String name;

    public Exercise(int exerciseId, String name) {
        this.exerciseId = exerciseId;
        this.name = name;
    }

    public Exercise(String name) {
        exerciseId = -1;
        this.name = name;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
