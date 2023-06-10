package com.kamilahammatova.schedule.dictionary;

public enum LessonTime {
    FIRST("8:30 - 10:00"),
    SECOND("10:10 - 11:40"),
    THIRD("11:50 - 13:20"),
    FOURTH("14:00 - 15:30"),
    FIFTH("15:40 - 17:10"),
    SIXTH("17:20 - 19:50"),
    SEVENTH("20:00 - 21:30");

    public final String time;

    LessonTime(String s) {
        time = s;
    }
}
