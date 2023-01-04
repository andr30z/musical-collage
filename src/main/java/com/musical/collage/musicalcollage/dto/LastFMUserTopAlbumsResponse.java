package com.musical.collage.musicalcollage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LastFMUserTopAlbumsResponse {
    @JsonProperty("topalbums")
    LastFMUserTopAlbums topAlbums;
}
