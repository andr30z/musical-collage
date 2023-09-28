package com.musical.collage.musicalcollage.dto.lastfm;

import org.springframework.web.bind.annotation.RequestParam;

public record LastFMRequestParams(
  @RequestParam("user") String user,
  @RequestParam(name = "size", defaultValue = "5") int size,
  @RequestParam(name = "period", defaultValue = "overall") String period
) {}
