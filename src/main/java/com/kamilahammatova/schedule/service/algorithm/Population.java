package com.kamilahammatova.schedule.service.algorithm;

import lombok.Data;

import java.util.Arrays;

@Data
public class Population {
    private int popSize;
    private final Individual[] individuals;
    private int fittestScore = 0;


    public Population(int popSize) {
        individuals = new Individual[popSize];
    }

    public float getMeanDistances() {
        return Arrays.stream(individuals).map(Individual::getDistances).reduce(Long::sum).orElse(Long.MAX_VALUE).floatValue() / individuals.length;
    }

    public Individual selectFittest() {
        int minFit = Integer.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (minFit > individuals[i].getFitness()) {
                minFit = individuals[i].getFitness();
                minFitIndex = i;
            }
        }
        fittestScore = individuals[minFitIndex].getFitness();

        try {
            return (Individual) individuals[minFitIndex].clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Individual selectSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].getFitness() < individuals[maxFit1].getFitness()) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (individuals[i].getFitness() < individuals[maxFit2].getFitness()) {
                maxFit2 = i;
            }
        }
        try {
            return (Individual) individuals[maxFit2].clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getLeastFittestIndex() {
        int minFitVal = Integer.MIN_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (minFitVal < individuals[i].getFitness()) {
                minFitVal = individuals[i].getFitness();
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }

    public int getFittestIndex() {
        int maxFit = Integer.MAX_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (maxFit > individuals[i].getFitness()) {
                maxFit = individuals[i].getFitness();
                maxFitIndex = i;
            }
        }
        return maxFitIndex;
    }

}