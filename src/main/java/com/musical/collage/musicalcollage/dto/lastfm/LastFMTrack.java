package com.musical.collage.musicalcollage.dto.lastfm;

import java.util.List;

import com.musical.collage.musicalcollage.dto.CollageImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastFMTrack {
    private String name;
    private List<CollageImage> image;
}
