package com.musical.collage.musicalcollage.dto.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LastFMUserTopAlbumsResponse {
    @JsonProperty("topalbums")
    private LastFMUserTopAlbums topAlbums;
}
