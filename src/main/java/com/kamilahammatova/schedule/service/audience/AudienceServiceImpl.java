package com.kamilahammatova.schedule.service.audience;

import com.kamilahammatova.schedule.entities.mongodb.Audience;
import com.kamilahammatova.schedule.repository.AudienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudienceServiceImpl implements AudienceService {

  @Autowired
  private AudienceRepository audienceRepository;

  @Override
  public Audience save(Audience audience) {
    return audienceRepository.save(audience);
  }

  @Override
  public List<Audience> getAll() {
    return audienceRepository.findAll();
  }
}
