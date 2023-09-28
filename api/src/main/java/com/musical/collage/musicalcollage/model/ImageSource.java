package com.musical.collage.musicalcollage.model;

import com.musical.collage.musicalcollage.enums.ImageSizeTypes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageSource {
    private ImageSizeTypes imageSizeTypes;
    private String url;
}
