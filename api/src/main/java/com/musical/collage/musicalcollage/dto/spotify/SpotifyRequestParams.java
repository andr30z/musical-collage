package com.musical.collage.musicalcollage.dto.spotify;
import org.springframework.web.bind.annotation.RequestParam;

public record SpotifyRequestParams(
  @RequestParam(name = "size", defaultValue = "5") int size,
  @RequestParam(name = "period", defaultValue = "long_term") String period
) {}
