package com.kamilahammatova.schedule.service;

import com.kamilahammatova.schedule.entities.mongodb.Audience;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class DistanceService {
    private final float[][] dist = new float[150][150];

    @PostConstruct
    public void init() throws FileNotFoundException {
        var file = new File("src/main/resources/data/distances.csv");

        var sc = new Scanner(file);
        sc.nextLine();

        for (int i = 0; i < 88; i++) {
            var str = sc.nextLine();
            var distances = Arrays.stream(str.replaceAll("\"", "").split(","))
                    .map(Float::parseFloat)
                    .collect(Collectors.toList());

            for (int j = 1; j < i; j++) {
                dist[i][j] = distances.get(j);
                dist[j][i] = distances.get(j);
            }
        }
    }

    public Float getDistanceBetween(Audience one, Audience two) {
        return dist[one.getId().intValue()][two.getId().intValue()];
    }
}
