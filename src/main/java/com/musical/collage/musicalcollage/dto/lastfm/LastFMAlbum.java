package com.musical.collage.musicalcollage.dto.lastfm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastFMAlbum {
    private String name;
    private List<LastFMImage> image;
}
