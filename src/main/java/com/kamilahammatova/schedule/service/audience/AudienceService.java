package com.kamilahammatova.schedule.service.audience;

import com.kamilahammatova.schedule.entities.mongodb.Audience;

import java.util.List;

public interface AudienceService {

  Audience save(Audience audience);

  List<Audience> getAll();
}
