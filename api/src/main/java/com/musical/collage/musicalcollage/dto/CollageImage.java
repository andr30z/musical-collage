package com.musical.collage.musicalcollage.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollageImage {

  @JsonAlias({"size", "width"})
  private String size;

  @JsonAlias({"#text", "url"})
  private String imageLink;
}
