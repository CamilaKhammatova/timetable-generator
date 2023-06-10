package com.kamilahammatova.schedule.controller;

import com.kamilahammatova.schedule.entities.mongodb.Audience;
import com.kamilahammatova.schedule.service.audience.AudienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/audiences")
public class AudienceController {

  @Autowired
  private AudienceService audienceService;

  @PostMapping
  public Audience saveAudience(@RequestBody Audience audience) {
    return audienceService.save(audience);
  }

  @GetMapping
  public List<Audience> getAll() {
    return audienceService.getAll();
  }
}
