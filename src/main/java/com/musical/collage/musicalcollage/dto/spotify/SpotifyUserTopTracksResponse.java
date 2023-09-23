package com.musical.collage.musicalcollage.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class SpotifyUserTopTracksResponse {

  @JsonProperty("items")
  private List<SpotifyTrack> tracks;
}
