package com.musical.collage.musicalcollage.dto.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastFMImage {

  private String size;

  @JsonProperty("#text")
  private String imageLink;
}
