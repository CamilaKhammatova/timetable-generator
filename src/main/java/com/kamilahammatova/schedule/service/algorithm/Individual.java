package com.kamilahammatova.schedule.service.algorithm;

import lombok.Data;

import java.util.Arrays;

@Data
public class Individual implements Cloneable {

    private int geneLength;
    private int fitness = 0;
    private Gen[] genes;
    private long distances;

    public Individual(Gen[] genes) {
        this.genes = genes;
        this.geneLength = genes.length;
        fitness = 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Individual individual = (Individual) super.clone();
        System.arraycopy(this.genes, 0, individual.genes, 0, individual.genes.length);
        return individual;
    }

    @Override
    public String toString() {
        return "[genes=" + Arrays.toString(genes) + "]";
    }

}