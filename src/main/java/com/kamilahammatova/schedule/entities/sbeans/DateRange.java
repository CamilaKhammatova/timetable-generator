package com.kamilahammatova.schedule.entities.sbeans;

import java.time.LocalDateTime;

public record DateRange(LocalDateTime from, LocalDateTime to) implements Comparable<DateRange> {

  @Override
  public int compareTo(DateRange other) {
    if (this.from.isBefore(other.from)) {
      return -1;
    } else if (this.from.isEqual(other.from) && this.to.isBefore(other.to)) {
      return -1;
    } else if (this.from.isEqual(other.from) && this.to.isEqual(other.to)) {
      return 0;
    } else {
      return 1;
    }
  }

  public boolean overlaps(DateRange range2) {
    LocalDateTime start1 = this.from();
    LocalDateTime end1 = this.to;
    LocalDateTime start2 = range2.from();
    LocalDateTime end2 = range2.to;

    if (start1.isBefore(start2) && end1.isAfter(end2)) {
      return true;
    } else if (start2.isBefore(start1) && end2.isAfter(end1)) {
      return true;
    }
    return start1.isBefore(end2) && start2.isBefore(end1);
  }
}
