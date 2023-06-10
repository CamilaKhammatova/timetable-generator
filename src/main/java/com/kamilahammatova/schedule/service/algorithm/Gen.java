package com.kamilahammatova.schedule.service.algorithm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Gen implements Cloneable {
    public Long request;
    public Long auditory;

    @Override
    public Gen clone() {
        return new Gen(this.request, this.auditory);
    }
}
